package vn.rmit.cosc2658.mike_algo1;


import vn.rmit.cosc2658.SecretKey;

public class SecretKeyGuesser {
    private static final char[] CHAR = "RMIT".toCharArray();  // Possible letters
    private static final int KEY_LENGTH = 16;

    public static void start(SecretKey sk) {
        int[] charCount = new int[CHAR.length];  // Store the number of occurrences for each character R, M, I, and T
        int matchCount;
        for (char c : CHAR) {               // Getting the number of occurrences for each character R, M, I, and T from the secret key
            String guess = Character.toString(c).repeat(KEY_LENGTH);
            matchCount = sk.guess(guess);
            System.out.printf("Guessing \"%s\", %d match...\n", guess, matchCount);

            if (matchCount == KEY_LENGTH) {       // Early termination for edge cases of keys that contains only 1 character 16 times
                System.out.printf("I found the secret key. It is \"%s\"\n", guess);
                return;
            }

            charCount[hash(c)] = matchCount;
        }


        // Assume we have found no correct character
        boolean[] correct = new boolean[KEY_LENGTH];
        for (int i = 0; i < KEY_LENGTH; i++) correct[i] = false;

        // First guess is 16 R's
        char[] guess = new char[KEY_LENGTH];
        for (int i = 0; i < KEY_LENGTH; i++) guess[i] = 'R';
        matchCount = charCount[hash('R')];

        for (int charHash = hash('R') + 1; charHash < CHAR.length; charHash++) {
            for (int i = 0; charCount[charHash] > 0 && i < KEY_LENGTH; i++) {
                if (correct[i]) continue;


                char originalChar = guess[i];
                guess[i] = CHAR[charHash];
                int newMatchCount = sk.guess(String.valueOf(guess));
                System.out.printf("Guessing \"%s\", %d match...\n", String.valueOf(guess), matchCount);

                switch (newMatchCount - matchCount) {
                    case 1 -> {
                        correct[i] = true;
                        charCount[charHash]--;
                        matchCount = newMatchCount;
                    }
                    case -1 -> {
                        correct[i] = true;
                        charCount[hash(originalChar)]--;
                        guess[i] = originalChar;
                    }
                    case 0 -> guess[i] = originalChar;
                }
            }
        }

        System.out.printf("I found the secret key. It is \"%s\"\n", String.valueOf(guess));
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
