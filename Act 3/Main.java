import java.util.*;

public class Main {
    public static void main(String[] args) {

        int slotindex[];
        int bookingids[] =      new int[5];
        int hoursOrunits[] =    new int[5];
        int customerSlots[] =   new int[5];
        int slots[] =           new int[5];

        String customerName[] = new String[5];
        String status[] =       new String[5];
        String services[] =     new String[5];

        String serviceTypes[] =     {"TUTORIAL", "REPAIR", "RENTAL", "CLEANING"};
        String dayLabels[] =        {"Monday", "Tuesday", "Wednesday", "Thursday", "Friday"};
        String timeLabels[] =       {"6:00 AM", "9:00 AM", "12:00 PM", "3:00 PM", "6:00 PM"};

        String slotStatus[][] = {{"FREE",    "BLOCKED", "BLOCKED", "FREE",    "FREE"}, 
                                {"BLOCKED", "FREE",    "FREE",    "FREE",    "BLOCKED"}, 
                                {"FREE",    "FREE",    "BLOCKED", "FREE",    "FREE"},   
                                {"FREE",    "BLOCKED", "FREE",    "BLOCKED", "FREE"},   
                                {"BLOCKED", "FREE",    "FREE",    "FREE",    "BLOCKED"}};  
                            

        String scheduleGrid[][] = new String[dayLabels.length][timeLabels.length];
        for (int i = 0; i < dayLabels.length; i++) {
            for (int j = 0; j < timeLabels.length; j++) {
                scheduleGrid[i][j] = timeLabels[j] + " " + slotStatus[i][j];
            }
        }

        double rates[] =        {200.0, 350.0, 150.0, 250.0};
        double amountDue[] =    new double[5];
        double amountPaid[] =   new double[5];
        double bills[] =        new double[5];

        boolean serviceValid = false;
        int userChoice = 0;

        Scanner sc = new Scanner(System.in);

        do {
            System.out.println("1 = ADD BOOKING   | 2 = VIEW AVAILABLE SCHEDULE | 3 = RESERVE SLOT");
            System.out.println("4 = COMPUTE BILL  | 5 = RECORD PAYMENT          | 6 = SEARCH BOOKING");
            System.out.println("7 = UPDATE/CANCEL | 8 = VIEW REPORTS            | 9 = EXIT");
            userChoice = sc.nextInt();

            switch (userChoice) {
                case 1:
                    sc.nextLine(); 
                    for (int i = 0; i < customerName.length; i++) {
                        System.out.println("Enter Customer Name: ");
                        customerName[i] = sc.nextLine();

                        System.out.println("Enter Service Type (TUTORIAL / REPAIR / RENTAL / CLEANING): ");
                        services[i] = sc.nextLine().toUpperCase();

                        serviceValid = false;
                        for (int j = 0; j < serviceTypes.length; j++) {
                            if (serviceTypes[j].equalsIgnoreCase(services[i])) {
                                serviceValid = true;
                                break;
                            }
                        }
                        if (!serviceValid) {
                            System.out.println("Invalid service. Skipping customer " + (i + 1));
                            customerName[i] = null;
                            services[i] = null;
                            continue;
                        }

                        boolean slotIdTaken = false;
                        do {
                            System.out.println("Enter desired slot (1-5): ");
                            customerSlots[i] = sc.nextInt();
                            sc.nextLine();

                            slotIdTaken = false;
                                for (int k = 0; k < customerName.length; k++) {
                                    if (k != i && customerName[k] != null && customerSlots[k] == customerSlots[i]) {
                                        slotIdTaken = true;
                                        break;
                                    }
                                }

                                if (slotIdTaken) {
                                    System.out.println("Slot " + customerSlots[i] + " is already taken by another customer. Please choose another.");
                                    }
                                } while (slotIdTaken);

                        switch (services[i]) {
                            case "TUTORIAL":
                                System.out.println("Enter number of hours: ");
                                hoursOrunits[i] = sc.nextInt();
                                System.out.println("Slot: " + customerSlots[i] + " | Hours: " + hoursOrunits[i] + " | Rate: PHP " + rates[0] + "/hr");
                                break;
                            case "REPAIR":
                                System.out.println("Enter number of hours: ");
                                hoursOrunits[i] = sc.nextInt();
                                System.out.println("Slot: " + customerSlots[i] + " | Hours: " + hoursOrunits[i] + " | Rate: PHP " + rates[1] + "/hr");
                                break;
                            case "RENTAL":
                                System.out.println("Enter number of units: ");
                                hoursOrunits[i] = sc.nextInt();
                                System.out.println("Slot: " + customerSlots[i] + " | Units: " + hoursOrunits[i] + " | Rate: PHP " + rates[2] + "/unit");
                                break;
                            case "CLEANING":
                                System.out.println("Enter number of hours: ");
                                hoursOrunits[i] = sc.nextInt();
                                System.out.println("Slot: " + customerSlots[i] + " | Hours: " + hoursOrunits[i] + " | Rate: PHP " + rates[3] + "/hr");
                                break;
                        }


                        boolean bookingSlotTaken = false;
                        int chosenBookSlot = 0;

                        do {
                            System.out.println("Select Booking Slot (1-5): ");
                            chosenBookSlot = sc.nextInt();
                            sc.nextLine();

                            bookingSlotTaken = false;
                            for (int k = 0; k < customerName.length; k++) {
                                if (k != i && bookingids[k] == chosenBookSlot && customerName[k] != null) {
                                    bookingSlotTaken = true;
                                    break;
                                }
                            }

                            if (bookingSlotTaken) {
                                System.out.println("Booking slot " + chosenBookSlot + " is already taken by another customer. Please choose another.");
                            }
                        } while (bookingSlotTaken);

                        bookingids[i] = chosenBookSlot;

                        System.out.println("------------------------------");
                        System.out.println("Booking saved for customer " + (i + 1) + ":");
                        System.out.println("Name         : " + customerName[i]);
                        System.out.println("Service      : " + services[i]);
                        System.out.println("Slot         : " + customerSlots[i]);
                        System.out.println("Booking Slot : " + bookingids[i]);
                        System.out.println("------------------------------");

     
                        if (i == customerName.length - 1) {
                            System.out.println("All customer slots filled.");
                            break;
                        }

                        System.out.println("Add another customer? 1 = Yes | 2 = No");
                        int addMore = sc.nextInt();
                        sc.nextLine(); 

                        if (addMore == 2) {
                            System.out.println("Stopped adding customers at slot " + (i + 1) + ".");
                            break;
                        } else if (addMore != 1) {
                            System.out.println("Invalid input. Stopping.");
                            break;
                        }
                    }
                    break;
                case 2:
                    System.out.println("=====< AVAILABLE SCHEDULE >=====");
                    for (int i = 0; i < dayLabels.length; i++) {
                        System.out.println("---< " + dayLabels[i] + " >---");
                        for (int j = 0; j < scheduleGrid[i].length; j++) {
                            System.out.println("  " + (j + 1) + ". " + scheduleGrid[i][j]);
                        }
                    }
                    System.out.println("================================");
                    break;

                case 3:

                if (customerName != null && customerSlots != null) {
                    System.out.println("Enter Booking ID to reserve a slot: ");
                    int bookIDSlot = sc.nextInt();
                    sc.nextLine(); 

                    boolean idFound = false;
                    
                    for (int i = 0; i < bookingids.length; i++) {
                        if (bookingids[i] == bookIDSlot) {
                            idFound = true;
                            break;
                        }
                    }

                    if (!idFound) {
                        System.out.println("Booking ID " + bookIDSlot + " not found.");
                        break;
                    }

                    System.out.println("Select a day:");
                    for (int i = 0; i < dayLabels.length; i++) {
                        System.out.println((i + 1) + " = " + dayLabels[i]);
                    }
                    int dayChoice = sc.nextInt() - 1;
                    sc.nextLine();

                    System.out.println("Select a time:");
                    for (int i = 0; i < timeLabels.length; i++) {
                        System.out.println((i + 1) + " = " + timeLabels[i]);
                    }
                    int timeChoice = sc.nextInt() - 1;
                    sc.nextLine(); 

                    System.out.println("Enter slot number to reserve (1-" + slotStatus[dayChoice].length + "): ");
                    int reserveSlot = sc.nextInt();
                    sc.nextLine(); 

                    try {
                        int slotIdx = reserveSlot - 1;
                        String chosenDay = dayLabels[dayChoice];
                        String chosenTime = timeLabels[timeChoice];
                        String currentSlotStatus = slotStatus[dayChoice][slotIdx];

                        switch (currentSlotStatus) {
                            case "FREE":
                                slotStatus[dayChoice][slotIdx] = "RESERVED";
                                scheduleGrid[dayChoice][slotIdx] = timeLabels[slotIdx] + " RESERVED";
                                slotindex = new int[bookingids.length];
                                slotindex[bookIDSlot - 1] = slotIdx;
                                slots[bookIDSlot - 1] = reserveSlot; 

                                System.out.println("Slot " + reserveSlot + " successfully reserved.");
                                System.out.println("Booking ID  : " + bookIDSlot);
                                System.out.println("Day         : " + chosenDay);
                                System.out.println("Time        : " + chosenTime);
                                System.out.println("Slot Number : " + reserveSlot);
                                System.out.println("Slot Status : " + slotStatus[dayChoice][slotIdx]);
                                break;
                            case "RESERVED":
                                System.out.println("Slot " + reserveSlot + " is already RESERVED.");
                                break;
                            case "BLOCKED":
                                System.out.println("Slot " + reserveSlot + " is BLOCKED and cannot be reserved.");
                                break;
                            default:
                                System.out.println("Slot " + reserveSlot + " has unknown status.");
                        }
                    } catch (ArrayIndexOutOfBoundsException e) {
                        System.out.println("Invalid input. Check day, time, or slot range.");
                    }
                    break;
                } else {
                    System.out.println("Add customer before reserving.");
                    break;
                }
                
                case 4:
                    System.out.println("Enter customer number to compute bill (1-5): ");
                    int custIndex = sc.nextInt() - 1;

                    try {
                        if (customerName[custIndex] == null || services[custIndex] == null) {
                            System.out.println("No booking found for customer " + (custIndex + 1));
                            break;
                        }

                        switch (services[custIndex]) {
                            case "TUTORIAL":
                                bills[custIndex] = rates[0] * hoursOrunits[custIndex];
                                break;
                            case "REPAIR":
                                bills[custIndex] = rates[1] * hoursOrunits[custIndex];
                                break;
                            case "RENTAL":
                                bills[custIndex] = rates[2] * hoursOrunits[custIndex];
                                break;
                            case "CLEANING":
                                bills[custIndex] = rates[3] * hoursOrunits[custIndex];
                                break;
                            default:
                                System.out.println("No valid service for customer " + (custIndex + 1));
                        }

                        System.out.println("Customer    : " + customerName[custIndex]);
                        System.out.println("Service     : " + services[custIndex]);
                        System.out.println("Slot        : " + customerSlots[custIndex]);
                        System.out.println("Amount Due  : PHP " + bills[custIndex]);
                    } catch (ArrayIndexOutOfBoundsException e) {
                        System.out.println("Invalid customer number. Choose (1-5) only.");
                    }
                    break;

                case 5:
                    System.out.println("Enter customer number to record payment (1-5): ");
                    int payIndex = sc.nextInt() - 1;

                    try {
                        if (customerName[payIndex] == null) {
                            System.out.println("No booking found for customer " + (payIndex + 1));
                            break;
                        }

                        if (bills[payIndex] == 0.0) {
                            System.out.println("No bill computed for customer " + (payIndex + 1) + ". Please compute bill first (Case 4).");
                            break;
                        }

                        System.out.println("Customer    : " + customerName[payIndex]);
                        System.out.println("Service     : " + services[payIndex]);
                        System.out.println("Amount Due  : PHP " + bills[payIndex]);

                        System.out.println("Enter amount to pay: ");
                        amountPaid[payIndex] = sc.nextDouble();

                        if (amountPaid[payIndex] == bills[payIndex]) {
                            status[payIndex] = "PAID_FULL";
                            System.out.println("Payment Status  : " + status[payIndex]);
                            System.out.println("Amount Paid     : PHP " + amountPaid[payIndex]);
                            System.out.println("Balance         : PHP 0.00");
                        } else if (amountPaid[payIndex] < bills[payIndex]) {
                            status[payIndex] = "PAID_PARTIAL";
                            amountDue[payIndex] = bills[payIndex] - amountPaid[payIndex];
                            System.out.println("Payment Status  : " + status[payIndex]);
                            System.out.println("Amount Paid     : PHP " + amountPaid[payIndex]);
                            System.out.println("Remaining Bal   : PHP " + amountDue[payIndex]);
                        } else {
                            status[payIndex] = "PAID_EXCESS";
                            double change = amountPaid[payIndex] - bills[payIndex];
                            System.out.println("Payment Status  : " + status[payIndex]);
                            System.out.println("Amount Paid     : PHP " + amountPaid[payIndex]);
                            System.out.println("Change          : PHP " + change);
                        }
                    } catch (ArrayIndexOutOfBoundsException e) {
                        System.out.println("Invalid customer number. Choose only (1-5).");
                    } catch (InputMismatchException e) {
                        System.out.println("Numerical inputs only.");
                    }
                    break;

                case 6:
                    System.out.println("Select Search Mode: 1 = Booking ID | 2 = Customer Name | 3 = Slot");
                    int searchMode = sc.nextInt();

                    switch (searchMode) {
                        case 1:
                            System.out.println("=====< BOOKING IDs >=====");
                            for (int i = 0; i < bookingids.length; i++) {
                                System.out.println("Customer " + (i + 1) + " | Booking ID: " + bookingids[i]);
                            }
                            break;
                        case 2:
                            System.out.println("=====< CUSTOMER NAMES >=====");
                            for (int i = 0; i < customerName.length; i++) {
                                if (customerName[i] != null) {
                                    System.out.println("Customer " + (i + 1) + " : " + customerName[i]);
                                }
                            }
                            break;
                        case 3:
                            System.out.println("=====< SLOT STATUS >=====");
                            for (int i = 0; i < slotStatus.length; i++) { 
                                System.out.println("--- " + dayLabels[i] + " ---");
                                for (int j = 0; j < slotStatus[i].length; j++) {
                                    System.out.println("  Slot " + (j + 1) + ": " + slotStatus[i][j]);
                                }
                            }
                            break;
                        default:
                            System.out.println("Invalid Search Mode.");
                            break;
                    }
                    break;

                case 7:
                    System.out.println("Select customer number to Update or Cancel booking (1-5): ");
                    int updateCustomer = sc.nextInt() - 1;
                    sc.nextLine();

                    try {
                        if (customerName[updateCustomer] == null) {
                            System.out.println("No booking found for customer " + (updateCustomer + 1));
                            break;
                        }

                        System.out.println("Customer  : " + customerName[updateCustomer]);
                        System.out.println("Service   : " + services[updateCustomer]);
                        System.out.println("Slot      : " + customerSlots[updateCustomer]); 
                        System.out.println("Status    : " + status[updateCustomer]);

                        System.out.println("1 = Update Service | 2 = Update Slot | 3 = Cancel Booking");
                        int updateChoice = sc.nextInt();
                        sc.nextLine();

                        switch (updateChoice) {
                            case 1:
                                System.out.println("Current Service: " + services[updateCustomer]);
                                System.out.println("Enter new Service (TUTORIAL / REPAIR / RENTAL / CLEANING): ");
                                String newService = sc.next().toUpperCase();
                                sc.nextLine();

                                serviceValid = false;
                                for (int i = 0; i < serviceTypes.length; i++) {
                                    if (serviceTypes[i].equalsIgnoreCase(newService)) {
                                        serviceValid = true;
                                        break;
                                    }
                                }

                                if (!serviceValid) {
                                    System.out.println("Invalid service type.");
                                    break;
                                }

                                System.out.println("Confirm update from " + services[updateCustomer] + " to " + newService + "? 1 = Yes | 2 = No");
                                int confirmService = sc.nextInt();
                                sc.nextLine();

                                if (confirmService == 1) {
                                    services[updateCustomer]   = newService;
                                    bills[updateCustomer]      = 0.0;
                                    amountPaid[updateCustomer] = 0.0;
                                    status[updateCustomer]     = "RESERVED";
                                    System.out.println("Service updated to: " + services[updateCustomer]);
                                    System.out.println("[NOTE]: Recompute bill (Case 4) and re-record payment (Case 5).");
                                } else {
                                    System.out.println("Update cancelled.");
                                }
                                break;

                            case 2:
                                System.out.println("Current Slot: " + customerSlots[updateCustomer]); 
                                System.out.println("Select a day:");
                                for (int i = 0; i < dayLabels.length; i++) {
                                    System.out.println((i + 1) + " = " + dayLabels[i]);
                                }
                                int newDayChoice = sc.nextInt() - 1;
                                sc.nextLine();

                                System.out.println("Select a time:");
                                for (int i = 0; i < timeLabels.length; i++) {
                                    System.out.println((i + 1) + " = " + timeLabels[i]);
                                }
                                int newTimeChoice = sc.nextInt() - 1;
                                sc.nextLine();

                                try {
                                    String newChosenDay  = dayLabels[newDayChoice];
                                    String newChosenTime = timeLabels[newTimeChoice];

                                    System.out.println("Enter new slot number (1-" + slotStatus[newDayChoice].length + "): ");
                                    int newSlot    = sc.nextInt();
                                    int newSlotIdx = newSlot - 1;
                                    sc.nextLine();

                                    try {
                                        String newSlotStatus = slotStatus[newDayChoice][newSlotIdx];

                                        if (!newSlotStatus.equals("FREE")) {
                                            System.out.println("Slot " + newSlot + " on " + newChosenDay + " is not available (" + newSlotStatus + ").");
                                            break;
                                        }

                                        System.out.println("Confirm update from slot " + customerSlots[updateCustomer] + " to slot " + newSlot + " on " + newChosenDay + ", " + newChosenTime + "? 1 = Yes | 2 = No"); 
                                        int confirmSlot = sc.nextInt();
                                        sc.nextLine();

                                        if (confirmSlot == 1) {
                                       
                                            slotStatus[newDayChoice][customerSlots[updateCustomer] - 1] = "FREE"; 
                                            scheduleGrid[newDayChoice][customerSlots[updateCustomer] - 1] = timeLabels[customerSlots[updateCustomer] - 1] + " FREE"; 

                                            
                                            slotStatus[newDayChoice][newSlotIdx] = "RESERVED";
                                            scheduleGrid[newDayChoice][newSlotIdx] = timeLabels[newSlotIdx] + " RESERVED";

                                            customerSlots[updateCustomer] = newSlot; 
                                            System.out.println("Slot updated to: " + customerSlots[updateCustomer] + " on " + newChosenDay + ", " + newChosenTime);
                                        } else {
                                            System.out.println("Update cancelled.");
                                        }
                                    } catch (ArrayIndexOutOfBoundsException e) {
                                        System.out.println("Invalid slot. Choose only between 1-" + slotStatus[newDayChoice].length + ".");
                                    }
                                } catch (ArrayIndexOutOfBoundsException e) {
                                    System.out.println("Invalid day or time. Check your selection.");
                                }
                                break;

                            case 3:
                                System.out.println("Are you sure you want to CANCEL booking for " + customerName[updateCustomer] + "? 1 = Yes | 2 = No");
                                int confirmCancel = sc.nextInt();
                                sc.nextLine();

                                if (confirmCancel == 1) {
                  
                                    slotStatus[0][customerSlots[updateCustomer] - 1] = "FREE"; 
                                    scheduleGrid[0][customerSlots[updateCustomer] - 1] = timeLabels[customerSlots[updateCustomer] - 1] + " FREE"; 

                                    customerName[updateCustomer]  = null;
                                    services[updateCustomer]      = null;
                                    customerSlots[updateCustomer] = 0; 
                                    hoursOrunits[updateCustomer]  = 0;
                                    bills[updateCustomer]         = 0.0;
                                    amountPaid[updateCustomer]    = 0.0;
                                    status[updateCustomer]        = "CANCELLED";
                                    System.out.println("Booking for customer " + (updateCustomer + 1) + " has been CANCELLED.");
                                } else {
                                    System.out.println("Cancellation aborted.");
                                }
                                break;

                            default:
                                System.out.println("Invalid choice.");
                                break;
                        }
                    } catch (ArrayIndexOutOfBoundsException e) {
                        System.out.println("Invalid customer number. Only customers (1-5) exist.");
                    }
                    break;

                case 8:
                    System.out.println("Select Report:");
                    System.out.println("1 = Full Booking List | 2 = Paid / Unpaid List");
                    System.out.println("3 = Daily Schedule    | 4 = Revenue Summary");
                    int reportChoice = sc.nextInt();

                    switch (reportChoice) {
                        case 1:
                            System.out.println("=====< FULL BOOKING LIST >=====");
                            boolean hasBooking = false;
                            for (int i = 0; i < customerName.length; i++) {
                                if (customerName[i] != null) {
                                    hasBooking = true;
                                    System.out.println("------------------------------");
                                    System.out.println("Customer #    : " + (i + 1));
                                    System.out.println("Booking ID    : " + bookingids[i]);
                                    System.out.println("Customer Name : " + customerName[i]);
                                    System.out.println("Service       : " + services[i]);
                                    System.out.println("Slot          : " + customerSlots[i]);
                                    System.out.println("Reserved Slot : " + slots[i]);
                                    System.out.println("Hours/Units   : " + hoursOrunits[i]);
                                    System.out.println("Amount Due    : PHP " + bills[i]);
                                    System.out.println("Amount Paid   : PHP " + amountPaid[i]);
                                    System.out.println("Status        : " + status[i]);
                                }
                            }
                            if (!hasBooking) {
                                System.out.println("No bookings found.");
                            }
                            System.out.println("==============================");
                            break;

                        case 2:
                            System.out.println("=====< PAID BOOKINGS >=====");
                            boolean hasPaid = false;
                            for (int i = 0; i < customerName.length; i++) {
                                if (customerName[i] != null && status[i] != null &&
                                   (status[i].equals("PAID_FULL") || status[i].equals("PAID_EXCESS"))) {
                                    hasPaid = true;
                                    System.out.println("------------------------------");
                                    System.out.println("Customer Name : " + customerName[i]);
                                    System.out.println("Service       : " + services[i]);
                                    System.out.println("Amount Paid   : PHP " + amountPaid[i]);
                                    System.out.println("Status        : " + status[i]);
                                }
                            }
                            if (!hasPaid) {
                                System.out.println("No paid bookings found.");
                            }

                            System.out.println("=====< UNPAID / PARTIAL BOOKINGS >=====");
                            boolean hasUnpaid = false;
                            for (int i = 0; i < customerName.length; i++) {
                                if (customerName[i] != null && status[i] != null &&
                                    status[i].equals("PAID_PARTIAL")) {
                                    hasUnpaid = true;
                                    double remaining = bills[i] - amountPaid[i];
                                    System.out.println("------------------------------");
                                    System.out.println("Customer Name : " + customerName[i]);
                                    System.out.println("Service       : " + services[i]);
                                    System.out.println("Amount Due    : PHP " + bills[i]);
                                    System.out.println("Amount Paid   : PHP " + amountPaid[i]);
                                    System.out.println("Remaining Bal : PHP " + remaining);
                                    System.out.println("Status        : " + status[i]);
                                }
                            }
                            if (!hasUnpaid) {
                                System.out.println("No unpaid or partial bookings found.");
                            }
                            System.out.println("==============================");
                            break;

                        case 3:
                            System.out.println("=====< DAILY SCHEDULE >=====");
                            for (int i = 0; i < dayLabels.length; i++) {
                                System.out.println("---< " + dayLabels[i] + " >---");
                                for (int j = 0; j < slotStatus[i].length; j++) {
                                    System.out.println("  Slot " + (j + 1) + " : " + slotStatus[i][j]);
                                }
                            }
                            System.out.println("============================");
                            break;

                        case 4:
                            System.out.println("=====< REVENUE SUMMARY >=====");
                            double totalRevenue = 0.0;
                            double totalPaid    = 0.0;
                            double totalUnpaid  = 0.0;
                            double tutorialRev  = 0.0;
                            double repairRev    = 0.0;
                            double rentalRev    = 0.0;
                            double cleaningRev  = 0.0;

                            for (int i = 0; i < customerName.length; i++) {
                                if (customerName[i] != null) {
                                    totalRevenue += bills[i];
                                    totalPaid    += amountPaid[i];
                                    totalUnpaid  += (bills[i] - amountPaid[i]);

                                    if (services[i] != null) {
                                        switch (services[i]) {
                                            case "TUTORIAL": tutorialRev += bills[i]; break;
                                            case "REPAIR":   repairRev   += bills[i]; break;
                                            case "RENTAL":   rentalRev   += bills[i]; break;
                                            case "CLEANING": cleaningRev += bills[i]; break;
                                        }
                                    }
                                }
                            }

                            System.out.println("------------------------------");
                            System.out.println("Revenue per Service Type:");
                            System.out.println("  TUTORIAL  : PHP " + tutorialRev);
                            System.out.println("  REPAIR    : PHP " + repairRev);
                            System.out.println("  RENTAL    : PHP " + rentalRev);
                            System.out.println("  CLEANING  : PHP " + cleaningRev);
                            System.out.println("------------------------------");
                            System.out.println("Total Expected Revenue : PHP " + totalRevenue);
                            System.out.println("Total Collected        : PHP " + totalPaid);
                            System.out.println("Total Outstanding      : PHP " + totalUnpaid);
                            System.out.println("==============================");
                            break;

                        default:
                            System.out.println("Invalid report choice.");
                            break;
                    }
                    break;

                case 9:
                    System.out.println("Thank you for using the program.");
                    System.out.println("Terminating...");
                    sc.close();
                    break;

                default:
                    System.out.println("Invalid choice. Please select 1-9.");
                    break;
            }
        } while (userChoice != 9);
    }
}