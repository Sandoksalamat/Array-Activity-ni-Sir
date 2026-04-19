import java.util.Scanner;
import java.util.InputMismatchException;

public class StudentGradeRecovery {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
		
        int maxStudents = 0;
        boolean sizeSet = false;

        while (!sizeSet) {
            try {
				System.out.print("========== IMPORTANT ==========");
                System.out.print("\nEnter max number of students to register: ");
                maxStudents = scanner.nextInt();
                if (maxStudents <= 0) {
                    System.out.println("Invalid input, try again.");
                } else {
                    sizeSet = true;
                }
            } catch (InputMismatchException e) {
                System.out.println("Invalid input. Please enter a valid number.");
                scanner.next();
            }
        }

        int[] studentIds = new int[maxStudents];
        String[] studentNames = new String[maxStudents];
        String[] sections = new String[maxStudents];
        double[][] quizScores = new double[maxStudents][3];
        double[] examScores = new double[maxStudents];
        double[] averages = new double[maxStudents];
        String[] remarks = new String[maxStudents];
        boolean[] isRegistered = new boolean[maxStudents];

        int choice = 0;

        do {
            System.out.println("\n========== Student Grade Recovery Manager ==========");
            System.out.println("[1] Register Students");
            System.out.println("[2] Encode Quiz Scores");
            System.out.println("[3] Encode Exam Score");
            System.out.println("[4] Compute Averages and Remarks");
            System.out.println("[5] Search Student");
            System.out.println("[6] Update Student Record");
            System.out.println("[7] View Reports");
            System.out.println("[8] Clear Record or Reset All");
            System.out.println("[9] Exit");
            System.out.print("Select an option: ");

            try {
                choice = scanner.nextInt();

                switch (choice) {
                    case 1:
                        registerStudents(scanner, studentIds, studentNames, sections, isRegistered);
                        break;
                    case 2: 
                        encodeQuizzes(scanner, quizScores, isRegistered, studentNames);
                        break;
                    case 3: 
                        encodeExam(scanner, examScores, isRegistered, studentNames);
                        break;
                    case 4:
                        computeAverages(quizScores, examScores, averages, remarks, isRegistered);
                        break;
                    case 5:
                        searchStudent(scanner, studentIds, studentNames, averages, isRegistered);
                        break;
                    case 6:
                        updateRecord(scanner, sections, quizScores, isRegistered, studentNames);
                        break;
                    case 7:
                        viewReports(scanner, studentIds, studentNames, sections, examScores, averages, remarks, isRegistered);
                        break;
                    case 8:
                        resetData(scanner, isRegistered);
                        break;
                    case 9:
                        System.out.println("Okay, bye!");
                        break;
                    default:
                        System.out.println("Invalid input, please try again.");
                }
            } catch (InputMismatchException e) {
                System.out.println("Select a number from the menu.");
                scanner.next();
            }
        } while (choice != 9);
    }

    public static void registerStudents(Scanner sc, int[] ids, String[] names, String[] secs, boolean[] reg) {
    for (int i = 0; i < ids.length; i++) {
        if (!reg[i]) {
            boolean isInputValid = false;

            while (!isInputValid) {
                try {
                    System.out.print("\nEnter Student ID for slot [" + (i + 1) + "]: ");
                    ids[i] = sc.nextInt();
                    sc.nextLine(); 

                    String inputName = "";
                    while (inputName.isEmpty()) {
                        System.out.print("Enter Student Name: ");
                        inputName = sc.nextLine().trim();
                        if (inputName.isEmpty()) {
                            System.out.println("Student Name cannot be empty. Please try again.");
                        }
                    }
                    names[i] = inputName;

                    String inputSec = "";
                    while (inputSec.isEmpty()) {
                        System.out.print("Enter Section: ");
                        inputSec = sc.nextLine().trim();
                        if (inputSec.isEmpty()) {
                            System.out.println("Section cannot be empty.");
                        }
                    }
                    secs[i] = inputSec;

                    reg[i] = true;
                    isInputValid = true; 
                    System.out.println("Slot number " + (i+1) + " registered successfully.");

                } catch (InputMismatchException e) {
                    System.out.println("Invalid ID format. Please use numbers only.");
                    sc.next();
                }
            }
        }
    }
}

    public static void encodeQuizzes(Scanner sc, double[][] quizzes, boolean[] reg, String[] names) {
        System.out.print("Enter student slot to encode scores: ");
        try {
            int idx = sc.nextInt() - 1 ;
            if (reg[idx]) {
                for (int q = 0; q < 3; q++) {
                    System.out.print("Enter score for Quiz " + (q + 1) + " for " + names[idx] + ": ");
                    double score = sc.nextDouble();
                    if (score < 0 || score > 100) {
                        System.out.println("Invalid score. Range 0-100.");
                        q--;
                    } else {
                        quizzes[idx][q] = score;
                    }
                }
            } else {
                System.out.println("Slot is empty.");
            }
        } catch (ArrayIndexOutOfBoundsException e) {
            System.out.println("Student slot does not exist.");
        } catch (InputMismatchException e) {
            System.out.println("Numeric input required.");
            sc.next();
        }
    }

    public static void encodeExam(Scanner sc, double[] exams, boolean[] reg, String[] names) {
        System.out.print("Enter student slot: ");
        try {
            int idx = sc.nextInt() - 1;
            if (reg[idx]) {
                System.out.print("Enter Exam Score for " + names[idx] + ": ");
                double score = sc.nextDouble();
                if (score >= 0 && score <= 100) exams[idx] = score;
                else System.out.println("Invalid range.");
            }
        } catch (Exception e) {
            System.out.println("Error processing exam score.");
            sc.nextLine();
        }
    }

    public static void computeAverages(double[][] quizzes, double[] exams, double[] avgs, String[] rems, boolean[] reg) {
        for (int i = 0; i < reg.length; i++) {
            if (reg[i]) {
                double qSum = 0;
                for (int q = 0; q < 3; q++) qSum += quizzes[i][q];
                
                try {
                    avgs[i] = ((qSum / 3.0) * 0.4) + (exams[i] * 0.6);
                    rems[i] = (avgs[i] >= 75) ? "Pass" : "Fail";
                } catch (ArithmeticException e) {
                    System.out.println("Math error for student " + i);
                }
            }
        }
        System.out.println("Averages computed for all registered students.");
    }

    public static void searchStudent(Scanner sc, int[] ids, String[] names, double[] avgs, boolean[] reg) {
        System.out.print("Enter ID to search: ");
        int searchId = sc.nextInt();
        boolean found = false;
        for (int i = 0; i < ids.length; i++) {
            if (reg[i] && ids[i] == searchId) {
                System.out.println("Found: " + names[i] + " | Average: " + avgs[i]);
                found = true;
                break;
            }
        }
        if (!found) System.out.println("Student ID not found.");
    }

    public static void updateRecord(Scanner sc, String[] secs, double[][] quizzes, boolean[] reg, String[] names) {
        System.out.print("Enter student slot: ");
        try {
            int idx = sc.nextInt() - 1;
            if (reg[idx]) {
                System.out.println("1. Update Section\n2. Update Quiz Score");
                int sub = sc.nextInt();
                if (sub == 1) {
                    System.out.print("New section: ");
                    secs[idx] = sc.next();
                } else if (sub == 2) {
                    System.out.print("Which quiz (1-3)? ");
                    int qIdx = sc.nextInt() - 1;
                    System.out.print("New score: ");
                    quizzes[idx][qIdx] = sc.nextDouble();
                }
            }
        } catch (Exception e) {
            System.out.println("Update failed. Returning to menu.");
        }
    }

    public static void viewReports(Scanner sc, int[] ids, String[] names, String[] secs, double[] exams, double[] avgs, String[] rems, boolean[] reg) {
        System.out.println("\nReport Selection:\n1. Class List\n2. Failing Students\n3. Top Student");
        int rChoice = sc.nextInt();
        
        switch (rChoice) {
            case 1:
                System.out.printf("%-5s %-15s %-10s %-10s %-10s%n", "ID", "Name", "Section", "Avg", "Remark");
                for (int i = 0; i < reg.length; i++) {
                    if (reg[i]) System.out.printf("%-5d %-15s %-10s %-10.2f %-10s%n", ids[i], names[i], secs[i], avgs[i], rems[i]);
                }
                break;
            case 2:
                System.out.println("========== Failing Students ==========");
                for (int i = 0; i < reg.length; i++) {
                    if (reg[i] && "Fail".equals(rems[i])) System.out.println(names[i] + " (" + avgs[i] + ")");
                }
                break;
            case 3:
                int topIdx = -1;
                double max = -1;
                for (int i = 0; i < reg.length; i++) {
                    if (reg[i] && avgs[i] > max) {
                        max = avgs[i];
                        topIdx = i;
                    }
                }
                if (topIdx != -1) System.out.println("Top Student: " + names[topIdx] + " with " + max);
                break;
        }
    }

    public static void resetData(Scanner sc, boolean[] reg) {
        System.out.print("Are you sure you want to reset all? Press [y] Yes [n] No: ");
        if (sc.next().equalsIgnoreCase("y")) {
            for (int i = 0; i < reg.length; i++) reg[i] = false;
            System.out.println("All data cleared.");
        }else {
        System.out.println("Reset cancelled. Data is safe.");
    }
    }
}