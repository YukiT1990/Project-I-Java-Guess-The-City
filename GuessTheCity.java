package miniproject1;


import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Scanner;
import java.lang.*;


public class GuessTheCity {
    public static void main(String[] args) {
        /**
         * import the contents of the file "cities.txt"
         */
        // relative path does not work
//        File file = new File("cities.txt");
        // This did work when I wrote this code at the first time, but it does not work now
//        File file = new File("src/miniproject1/cities.txt");
        File file = new File("/Users/yuki.t/IdeaProjects/IntroToOOP/src/miniproject1/cities.txt");
        String[] cityNames = null;
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(file)))) {
            List<String> lineList = new ArrayList<String>();
            String line;
            while ((line = reader.readLine()) != null) {
                lineList.add(line);
            }
            cityNames = lineList.toArray(new String[lineList.size()]);
        } catch (IOException e) {
            e.printStackTrace();
        }

        /**
         * check the contents of cityNames
         */
//        for (int i = 0; i < cityNames.length; i ++) {
//            System.out.println(cityNames[i]);
//        }

        /**
         * check the length of the list --> 100
         */
//        System.out.println(cityNames.length);

        /**
         * randomly choose an integer between 0 and 99
         */
        Random rnd = new Random();
        int pickAWordIndex = rnd.nextInt(cityNames.length);
//        System.out.println(pickAWordIndex);
//        System.out.println(cityNames[pickAWordIndex]);

        /**
         * pick a city name to guess
         */
        String wordToGuess = cityNames[pickAWordIndex];
        // check the target word
//        System.out.println(wordToGuess);

        /**
         * create array of char from the string
         */
        char[] wordToGuess2 = new char[wordToGuess.length()];
        for (int i = 0; i < wordToGuess.length(); i++) {
            wordToGuess2[i] = wordToGuess.charAt(i);
        }



        /**
         * prepare to receive user input
         */
        Scanner input = new Scanner(System.in);


        /**
         * array to store user answer
         */
        char[] userAnswer = new char[wordToGuess.length()];
        for (int i = 0; i < wordToGuess.length(); i++) {
            userAnswer[i] = '_';
        }

        /**
         * replace _ into ' ' if the city name has more than two words
         */
        userAnswer = replaceChars(wordToGuess2, userAnswer, ' ');


        /**
         * message to the user
         */

        System.out.println("Here's the question.");
        System.out.println(arrayToString(userAnswer));

        int numOfWrongGuesses = 0;
        char[] wrongLetters = new char[10];
        for (int i = 0; i < 10; i++) {
            wrongLetters[i] = ' ';
        }

        // inside while loop
        while (numOfWrongGuesses < 10 && !isUserWon(userAnswer)) {
            System.out.println();
            System.out.print("Guess a letter: ");
            char userGuess = Character.toLowerCase(input.next().charAt(0));
            if (isGuessCorrect(wordToGuess2, userGuess)) {
                replaceChars(wordToGuess2, userAnswer, userGuess);
            } else {
                numOfWrongGuesses += 1;
                wrongLetters[numOfWrongGuesses - 1] = userGuess;
            }
            if (numOfWrongGuesses >= 10 || isUserWon(userAnswer)) {
                break;
            }
            System.out.print("You are guessing: ");
            System.out.println(arrayToString(userAnswer));
            String sf1 = String.format("You have guessed (%d) wrong letters: ", numOfWrongGuesses);
            System.out.print(sf1);
            for (int i = 0; i < wrongLetters.length; i++) {
                System.out.print(wrongLetters[i] + " ");
            }
        } // finish while loop

        if (isUserWon(userAnswer)) {
            System.out.println("You win!");
            System.out.println("You have guessed \'" + arrayToString(wordToGuess2) + "\' correctly.");
        }
        if (numOfWrongGuesses >= 10) {
            System.out.println("You have guessed (10) wrong letters: x x x x x x x x x x");
            System.out.println("You lose!");
            System.out.println("The correct word was \'" + arrayToString(wordToGuess2) +  "\'!");
        }

    }


    private static boolean isUserWon(char[] array) {
        for (int i = 0; i < array.length; i++) {
            if (array[i] == '_') {
                return false;
            }
        }
        return true;
    }

    private static boolean isGuessCorrect(char[] array, char c) {
        for (int i = 0; i < array.length; i++) {
            if (Character.toLowerCase(array[i]) == c) {
                return true;
            }
        }
        return false;
    }


    /**
     * from array of char into string
     */
    private static String arrayToString(char[] array) {
        return new String(array);
    }

    /**
     * replace letters (array1 and array2 must be the same length)
     * if array1 has targetletters, the letters at the same index in array2 will be
     * replaced into the letters at the same index in array1, then return array2
     */
    private static char[] replaceChars(char[] array1, char[] array2, char targetLetter) {
        for (int i = 0; i < array1.length; i++) {
            if (Character.toLowerCase(array1[i]) == targetLetter) {
                array2[i] = array1[i];
            }
        }
        return array2;
    }


}
