package vn.rmit.cosc2658.development.mike_algo1;

import vn.rmit.cosc2658.development.SecretKey;


public class SecretKeyGuesser {
    private static final char[] CHAR = "RMIT".toCharArray();  // Possible letters
    

    public static String start(SecretKey sk, int skLen, boolean verbose) {
        int[] charCount = new int[CHAR.length];  // Store the number of occurrences for each character R, M, I, and T
        int matchCount;
        for (char c : CHAR) {                    // Getting the number of occurrences for each character R, M, I, and T from the secret key
            String guess = Character.toString(c).repeat(skLen);
            matchCount = sk.guess(guess);
            if (verbose) System.out.printf("Guessing \"%s\", %d match...\n", guess, matchCount);

            if (matchCount == skLen) {           // Early termination for edge cases of keys that contains only 1 character 16 times
                if (verbose) System.out.printf("I found the secret key. It is \"%s\"\n", guess);
                return guess;
            }

            charCount[hash(c)] = matchCount;
        }


        boolean[] correct = new boolean[skLen];  // Assume we have found no correct character
        char[] guess = "R".repeat(skLen).toCharArray();  // First guess is 16 R's
        matchCount = charCount[hash('R')];

        // Main algorithm
        for (int charHash = hash('R') + 1; charHash < CHAR.length; charHash++) {  // Consider M, I, and T
            for (int i = 0; charCount[charHash] > 0 && i < skLen; i++) {
                if (correct[i]) continue;


                char originalChar = guess[i];
                guess[i] = CHAR[charHash];
                int newMatchCount = sk.guess(String.valueOf(guess));
                if (verbose) System.out.printf("Guessing \"%s\", %d match...\n", String.valueOf(guess), matchCount);

                switch (newMatchCount - matchCount) {
                    case 1 -> {
                        correct[i] = true;
                        charCount[charHash]--;
                        matchCount = newMatchCount;
                    }
                    case -1 -> {
                        correct[i] = true;
                        guess[i] = originalChar;
                    }
                    case 0 -> guess[i] = originalChar;
                }
            }
        }

        if (verbose) System.out.printf("I found the secret key. It is \"%s\"\n", String.valueOf(guess));
        return String.valueOf(guess);
    }

    public static String start(SecretKey sk, int skLen) {
        return start(sk, skLen, true);
    }
    
    
    private static int hash(char c) {
        return switch (c) {
            case 'R' -> 0;
            case 'M' -> 1;
            case 'I' -> 2;
            case 'T' -> 3;
            default -> throw new IllegalStateException("Unexpected value: " + c);
        };
    }
}
