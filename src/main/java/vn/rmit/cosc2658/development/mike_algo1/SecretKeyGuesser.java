package vn.rmit.cosc2658.development.mike_algo1;

import vn.rmit.cosc2658.development.SecretKey;


public class SecretKeyGuesser {
    private static final char[] CHAR = "RMIT".toCharArray();  // Possible letters.


    public static String start(SecretKey secretKey, int secretKeyLength, boolean verbose) {
        int[] charFreq = new int[CHAR.length];  // Store the number of occurrences (frequency) for each character R, M, I, and T

        int matchCount, totalFreq = 0, mostCommonCharHash = 0;
        for (
                // Stop if reaching T, or if the total number of occurrences has reached 16 already, to save us more
                // SecretKey.guess() calls.
                int charHash = 0;
                totalFreq < secretKeyLength && charHash < CHAR.length - 1;
                charHash++
        ) {
            String guess = Character.toString(CHAR[charHash]).repeat(secretKeyLength);
            matchCount = secretKey.guess(guess);
            if (verbose) System.out.printf("Guessing \"%s\", %d match...\n", guess, matchCount);

            if (matchCount == secretKeyLength) {
                // Early termination for edge cases of keys that contains only 1 repeating character.
                if (verbose) System.out.printf("I found the secret key. It is \"%s\"\n", guess);
                return guess;
            }

            charFreq[charHash] = matchCount;
            totalFreq += matchCount;
            if (charHash != 0 && charFreq[mostCommonCharHash] < matchCount) mostCommonCharHash = charHash;
        }

        if (totalFreq == 0) {
            // No occurrences of all other characters means the key contains only the character T. This saves us 1
            // SecretKey.guess() call.
            String guess = "T".repeat(secretKeyLength);
            if (verbose) System.out.printf("I found the secret key. It is \"%s\"\n", guess);
            return guess;
        }

        if (totalFreq < secretKeyLength) {
            // The frequency of the last character T can be obtained by simply subtracting the key length by the total
            // number of occurrences of the other characters. This saves us 1 SecretKey.guess() call.
            charFreq[CHAR.length - 1] = secretKeyLength - totalFreq;
        }


        // Baseline guess is a string filled with the most common character (the one with the highest frequency). This
        // will save us more SecretKey.guess() calls, as our Main algorithm below would not have to call
        // SecretKey.guess() for:
        //     - Characters that we know are not in the key (frequency equal to 0 after the above steps);
        //     - Multiple incorrect guesses the same index.
        char[] guess = Character.toString(CHAR[mostCommonCharHash]).repeat(secretKeyLength).toCharArray();
        matchCount = charFreq[mostCommonCharHash];
        boolean[] correct = new boolean[secretKeyLength];  // Assume that no correct character has been found

        // Main algorithm
        for (
                // Consider the remaining characters
                int charHash = (mostCommonCharHash + 1) % CHAR.length;
                charHash != mostCommonCharHash;
                charHash = (charHash + 1) % CHAR.length
        ) {
            for (
                    // Linear search: Consider each character index of the key from left to right to be replaced with
                    // one of the remaining characters. Stop early if we have used up our replacing character.
                    int index = 0;
                    charFreq[charHash] > 0 && index < secretKeyLength;
                    index++
            ) {
                if (correct[index]) continue;  // Skip if we know we have found the correct character for this position


                char originalChar = guess[index];
                guess[index] = CHAR[charHash];
                int newMatchCount = secretKey.guess(String.valueOf(guess));
                if (verbose) System.out.printf("Guessing \"%s\", %d match...\n", String.valueOf(guess), matchCount);

                switch (newMatchCount - matchCount) {
                    case 1 -> {  // Found the correct character for this position: New replacement character
                        correct[index] = true;
                        charFreq[charHash]--;
                        matchCount = newMatchCount;
                    }
                    case -1 -> {  // Found the correct character for this position: Original baseline guess
                        correct[index] = true;
                        guess[index] = originalChar;
                    }
                }
            }
        }

        if (verbose) System.out.printf("I found the secret key. It is \"%s\"\n", String.valueOf(guess));
        return String.valueOf(guess);
    }

    public static String start(SecretKey sk, int skLen) {
        return start(sk, skLen, true);
    }
}
