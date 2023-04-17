package vn.rmit.cosc2658.tony_algo1;

import org.junit.jupiter.api.Test;
import vn.rmit.cosc2658.SecretKey;

import static org.junit.jupiter.api.Assertions.*;

class SecretKeyGuesserTest {

    private final SecretKey secretKey1 = new SecretKey("RMIT");
    private final SecretKey secretKey2 = new SecretKey("RRRR");
    private final SecretKey secretKey3 = new SecretKey("MMMM");
    private final SecretKey secretKey4 = new SecretKey("IIII");
    private final SecretKey secretKey5 = new SecretKey("TTTT");
    private final SecretKey secretKey6 = new SecretKey("ITRM");
    private final SecretKey secretKey7 = new SecretKey("ITMT");


    @Test
    void test1() {
        SecretKeyGuesser.start(secretKey1);
        System.out.printf("%s took %d guesses.\n", secretKey1.getKey(), secretKey1.getGuessCount());
    }

    @Test
    void test2() {
        SecretKeyGuesser.start(secretKey2);
        System.out.printf("%s took %d guesses.\n", secretKey2.getKey(), secretKey2.getGuessCount());
    }

    @Test
    void test3() {
        SecretKeyGuesser.start(secretKey3);
        System.out.printf("%s took %d guesses.\n", secretKey3.getKey(), secretKey3.getGuessCount());
    }

    @Test
    void test4() {
        SecretKeyGuesser.start(secretKey4);
        System.out.printf("%s took %d guesses.\n", secretKey4.getKey(), secretKey4.getGuessCount());
    }

    @Test
    void test5() {
        SecretKeyGuesser.start(secretKey5);
        System.out.printf("%s took %d guesses.\n", secretKey5.getKey(), secretKey5.getGuessCount());
    }

    @Test
    void test6() {
        SecretKeyGuesser.start(secretKey6);
        System.out.printf("%s took %d guesses.\n", secretKey6.getKey(), secretKey6.getGuessCount());
    }

    @Test
    void test7() {
        SecretKeyGuesser.start(secretKey7);
        System.out.printf("%s took %d guesses.\n", secretKey7.getKey(), secretKey7.getGuessCount());
    }
}