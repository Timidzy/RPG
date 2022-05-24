import java.util.Random;
import java.util.Scanner;

public class RpgMain {
    static Scanner scanner = new Scanner(System.in);
    static String playerOneName = askForFirstPlayerName();
    static String playerTwoName = askForSecondPlayerName();
    static String helloMsg = "Time to attack, press any key";
    static String shopMessage = "Hey!\nI see u have earned some cash!\nDo u wanna buy some goods?" +
            "\n1 - Sword (will give u + 5 damage) - cost: 100\n2 - shield (u will get 5 less damage)" +
            " - cost: 100\n3 - armor (u will have 50 health more) - cost: 300\n4 - lucky amulet" +
            " (ur critical chance will grow from 25% to 40%) - cost: 400\n5 - don't " +
            "buy anything\n6 - exit from the game";
    static int[] healthArray = new int[]{100, 100};
    static int balancePlayerOne = 0;
    static int balancePlayerTwo = 0;
    static int minAttackPlayerOne = 1;
    static int maxAttackPlayerOne = minAttackPlayerOne + 14;
    static int minAttackPlayerTwo = 1;
    static int maxAttackPlayerTwo = minAttackPlayerTwo + 14;
    static boolean flag = new Random().nextInt(7) == 0;

    public static void main(String[] args) {
        startFight();
    }

    private static void startFight() {
        while (true) {
            int playerOneHealth = healthArray[0];
            int playerTwoHealth = healthArray[1];
            while (playerOneHealth > 0 && playerTwoHealth > 0) {
                int playerOneAttack = calculateDamageForFirstPlayer();
                int playerTwoAttack = calculateDamageForSecondPlayer();
                System.out.println(helloMsg);
                scanner.nextLine();
                boolean playerOneCriticalAttack = isPlayerOneCriticalAttack(flag);
                boolean playerTwoCriticalAttack = isPlayerTwoCriticalAttack(flag);

                criticalAttack(playerOneAttack, playerTwoAttack, playerOneCriticalAttack, playerTwoCriticalAttack);
                playerOneHealth -= playerTwoAttack;
                playerTwoHealth -= playerOneAttack;
                System.out.println(playerOneName + " gets " + playerTwoAttack + " damage, " + "now "
                        + playerOneName + "'s health = " + playerOneHealth);
                System.out.println(playerTwoName + " gets " + playerOneAttack + " damage, " + "now "
                        + playerTwoName + "'s health = " + playerTwoHealth);
                calculatingWhoWins(playerOneHealth, playerTwoHealth);
            }
            checkPlayersMoney();
            playerOneBuyTime();
            System.out.println(shopMessage);
            playerTwoBuyTime();

        }
    }

    static String askForFirstPlayerName() {
        System.out.println("Enter the first player name");
        return scanner.nextLine();
    }

    static String askForSecondPlayerName() {
        System.out.println("Enter the second player name");
        return scanner.nextLine();
    }

    static void criticalAttack(int playerOneAttack, int playerTwoAttack, boolean playerOneCriticalAttack,
                               boolean playerTwoCriticalAttack) {
        if (playerOneCriticalAttack) {
            playerOneAttack *= 2;
            System.out.println(playerOneName + " has critical hit");
        } else if (playerTwoCriticalAttack) {
            playerTwoAttack *= 2;
            System.out.println(playerTwoName + " has critical hit");
        }
    }

    static void calculatingWhoWins(int playerOneHealth, int playerTwoHealth) {
        if (playerOneHealth <= 0 && playerTwoHealth > 0) {
            System.out.println(playerTwoName + " wins !");
            System.out.println(" ");
            balancePlayerTwo += 200;
            System.out.println(playerTwoName + "'s balance now is " + balancePlayerTwo);
        } else if (playerTwoHealth <= 0 && playerOneHealth > 0) {
            System.out.println(playerOneName + " wins !");
            System.out.println(" ");
            balancePlayerOne += 200;
            System.out.println(playerOneName + "'s balance now is " + balancePlayerOne);
        } else if (playerOneHealth <= 0) {
            balancePlayerOne += 100;
            balancePlayerTwo += 100;
            System.out.println("DRAW !!!!");
            System.out.println(playerTwoName + "'s balance now is " + balancePlayerTwo);
            System.out.println(playerOneName + "'s balance now is " + balancePlayerOne);
        }
    }

    static void checkPlayersMoney() {
        if (balancePlayerOne <= 0) {
            System.out.println(playerOneName + " sorry, but u haven't money");
        } else if (balancePlayerTwo <= 0) {
            System.out.println(playerTwoName + " sorry, but u haven't money");
        }
    }

    static void playerOneBuyTime() {
        int tmp = 0;
        while (balancePlayerOne > 0 && tmp == 0) {
            System.out.println(playerOneName + " has " + balancePlayerOne);
            System.out.println(shopMessage);
            System.out.println(playerOneName + "'s turn to buy");
            int playerOneBuyTime = scanner.nextInt();
            switch (playerOneBuyTime) {
                case 1:
                    balancePlayerOne -= 100;
                    minAttackPlayerOne += 5;
                    maxAttackPlayerOne += 5;
                    continue;
                case 2:
                    balancePlayerOne -= 100;
                    minAttackPlayerTwo -= 5;
                    maxAttackPlayerTwo -= 5;
                    continue;
                case 3:
                    balancePlayerOne -= 300;
                    healthArray[0] += 50;
                    continue;
                case 4:
                    balancePlayerOne -= 400;
                    boolean flag = new Random().nextInt(5) == 0;
                    isPlayerOneCriticalAttack(flag);
                    continue;
                case 5:
                    tmp += 1;
                    break;
                case 6:
                    break;
            }
        }
    }

    static void playerTwoBuyTime() {
        int tmp = 0;
        while (balancePlayerTwo > 0 && tmp == 0) {
            System.out.println(playerTwoName + " has " + balancePlayerTwo);
            System.out.println(playerTwoName + "'s turn to buy");
            int playerTwoBuyTime = scanner.nextInt();
            switch (playerTwoBuyTime) {
                case 1:
                    balancePlayerTwo -= 100;
                    minAttackPlayerTwo += 5;
                    maxAttackPlayerTwo += 5;
                    continue;
                case 2:
                    balancePlayerTwo -= 100;
                    minAttackPlayerOne -= 5;
                    maxAttackPlayerOne -= 5;
                    continue;
                case 3:
                    balancePlayerTwo -= 300;
                    healthArray[1] += 50;
                    continue;
                case 4:
                    balancePlayerTwo -= 400;
                    boolean flag = new Random().nextInt(5) == 0;
                    isPlayerTwoCriticalAttack(flag);
                    continue;
                case 5:
                    tmp += 1;
                    break;
                case 6:
                    break;
            }
        }
    }

    static int calculateDamageForFirstPlayer() {
        return (int) ((Math.random() * (maxAttackPlayerOne - minAttackPlayerOne)) + minAttackPlayerOne);
    }

    static int calculateDamageForSecondPlayer() {
        return (int) ((Math.random() * (maxAttackPlayerTwo - minAttackPlayerTwo)) + minAttackPlayerTwo);
    }
    static boolean isPlayerOneCriticalAttack(boolean flag){
        boolean playerOneCriticalAttack = flag;
        return playerOneCriticalAttack = new Random().nextInt(7) == 0;
    }
    static boolean isPlayerTwoCriticalAttack(boolean flag){
        boolean playerTwoCriticalAttack;
        return playerTwoCriticalAttack = new Random().nextInt(7) == 0;
    }
}
