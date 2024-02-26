package oasisInfobyte;

import java.util.Random;
import java.util.Scanner;

public class GuessTheNumber {
    public static void main(String[] args) {
        Random random = new Random();
        Scanner scanner = new Scanner(System.in);

        int totalRounds = 3; 
        // You can adjust the number of rounds
        int totalScore = 0;
        
        
        System.out.println("Welcome to the NUmber Guessing Game!");
        System.out.println("The computer has the secret number...");

        for (int round = 1; round <= totalRounds; round++) {
            int secretNumber = random.nextInt(100) + 1; 
            // Generate a random number between 1 and 100
            int numberOfGuesses = 0;
            int roundScore = 0;

            System.out.println("\nRound " + round + ": \nThe computer has the secret number...");

            while (true) {
                System.out.print("Enter your guess (1-100): ");
                int userGuess = scanner.nextInt();
                scanner.nextLine(); 

                numberOfGuesses++;

                if (userGuess < 1 || userGuess > 100) {
                    System.out.println("Bad guess. Please re-enter a number between 1 and 100.");
                } else if (userGuess < secretNumber) {
                    System.out.println("Too low... Try again.");
                } else if (userGuess > secretNumber) {
                    System.out.println("Too high... Try again.");
                } else {
                	 System.out.println("Yahooo! Its a Correct guess!");
                  
                    roundScore = calculateScore(numberOfGuesses);
                    System.out.println("You used " + numberOfGuesses + " chances to guess the computer's secret number.. \nRound Score: " + roundScore);
                    totalScore += roundScore;
                    break;
                }
            }
        }

        System.out.println("\nGame Over. \nYour Total Score: " + totalScore);

        scanner.close();
    }

    private static int calculateScore(int numberOfGuesses) {
        return 100 - (numberOfGuesses - 1) * 10;
    }
}
