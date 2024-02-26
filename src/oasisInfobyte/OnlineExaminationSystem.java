package oasisInfobyte;

import java.util.*;

class User {
    private String username;
    private String password;

    public User(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String newPassword) {
        this.password = newPassword;
    }
}

class Question {
    private int id;
    private String text;
    private String correctAnswer;

    public Question(int id, String text, String correctAnswer) {
        this.id = id;
        this.text = text;
        this.correctAnswer = correctAnswer;
    }

    public int getId() {
        return id;
    }

    public String getText() {
        return text;
    }

    public String getCorrectAnswer() {
        return correctAnswer;
    }
}

class ExamManager {
    private List<Question> questions;
    private Map<String, String> userAnswers;

    public ExamManager(List<Question> questions) {
        this.questions = questions;
        this.userAnswers = new HashMap<>();
    }

    public void displayQuestions() {
        System.out.println("\nMCQs for the Exam:");
        for (int i = 0; i < questions.size(); i++) {
            System.out.println((i + 1) + ". " + questions.get(i).getText());
        }
    }

    public void takeUserAnswers(Scanner scanner) {
        for (int i = 0; i < questions.size(); i++) {
            System.out.print("Enter your answer for question " + (i + 1) + ": \n");
            String answer = scanner.nextLine();
            userAnswers.put(Integer.toString(questions.get(i).getId()), answer);
        }
    }

    public void submitExam() {
        System.out.println("\nSubmitting Exam...");

        // Calculate and display the result
        int correctCount = 1;
        for (Question question : questions) {
            String userAnswer = userAnswers.get(Integer.toString(question.getId()));
            String correctAnswer = question.getCorrectAnswer();

            if (userAnswer != null && userAnswer.equalsIgnoreCase(correctAnswer)) {
                correctCount++;
            }
        }

        System.out.println("Exam submitted. Results:");
        System.out.println("Correct Answers: " + correctCount + " out of " + questions.size());
    }
}

public class OnlineExaminationSystem {
    private static final int MENU_UPDATE_PROFILE = 1;
    private static final int MENU_START_EXAM = 2;
    private static final int MENU_LOGOUT = 3;

    private static Scanner scanner = new Scanner(System.in);
    private static Map<String, User> users = new HashMap<>();
    private static List<Question> questions = new ArrayList<>();
    private static User currentUser;
    private static int timeLimit = 300; // 5 minutes in seconds

    public static void main(String[] args) {
        initializeUsers();
        initializeQuestions();

        System.out.println("Welcome to the Online Examination System!");

        login();

        while (true) {
            displayMenu();

            int choice = getIntInput("Enter your choice: ");

            switch (choice) {
                case MENU_UPDATE_PROFILE:
                    updateProfile();
                    break;
                case MENU_START_EXAM:
                    startExam();
                    break;
                case MENU_LOGOUT:
                    logout();
                    return;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
    }

    private static void initializeUsers() {
        users.put("student1", new User("student1", "password1"));
        users.put("student2", new User("student2", "password2"));
        // Add more users as needed
    }

    private static void initializeQuestions() {
        questions.add(new Question(1, "What is the capital of France?", "1"));
        questions.add(new Question(2, "What is the largest planet in our solar system?", "2"));
        // Add more questions and answers as needed
    }

    private static void login() {
        System.out.print("Enter username: ");
        String username = scanner.nextLine();
        System.out.print("Enter password: ");
        String password = scanner.nextLine();

        if (users.containsKey(username) && users.get(username).getPassword().equals(password)) {
            currentUser = users.get(username);
            System.out.println("Login successful. Welcome, " + currentUser.getUsername() + "!");
        } else {
            System.out.println("Invalid credentials. Please try again.");
            login();
        }
    }

    private static void updateProfile() {
        System.out.println("Update Profile for " + currentUser.getUsername());
        System.out.print("Enter new password: ");
        String newPassword = scanner.nextLine();
        currentUser.setPassword(newPassword);
        System.out.println("Password updated successfully!");
    }

    private static void startExam() {
        System.out.println("Starting Exam...");

        ExamManager examManager = new ExamManager(questions);

        Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                System.out.println("\nTime's up! Exam will be auto-submitted.");
                examManager.submitExam();
            }
        }, timeLimit * 1000);

        Collections.shuffle(questions); // Randomize question order
        examManager.displayQuestions();
        examManager.takeUserAnswers(scanner);

        timer.cancel(); // Cancel the timer as the user has submitted the exam
        examManager.submitExam();
    }

    private static void logout() {
        System.out.println("Logging out. Best of luck, " + currentUser.getUsername() + "!");
        currentUser = null;
    }

    private static void displayMenu() {
        System.out.println("\n1. Update Profile and Password");
        System.out.println("2. Start Exam");
        System.out.println("3. Logout");
    }

    private static int getIntInput(String prompt) {
        System.out.print(prompt);
        while (!scanner.hasNextInt()) {
            System.out.println("Invalid input. Please enter a valid number.");
            System.out.print(prompt);
            scanner.next(); // Consume the invalid input
        }
        return scanner.nextInt();
    }
}
