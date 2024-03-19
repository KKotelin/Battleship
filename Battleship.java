import java.beans.IntrospectionException;
import java.io.IOException;
import java.util.Scanner;

public class Battleship {
    static String playerName_1; //Имя первого игрока
    static String playerName_2; //Имя второго игрока
    static Scanner scanner = new Scanner(System.in);
    static int[][] battlefield_1 = new int[10][10];
    static int[][] battlefield_2 = new int[10][10];
    static int[][] monitor_1 = new int[10][10];
    static int[][] monitor_2 = new int[10][10];


    public static void main(String[] args) {

        System.out.println("Игрок 1, пожалуйста представьтесь");
        playerName_1 = scanner.nextLine();

        System.out.println("Игрок 2, пожалуйста представьтесь");
        playerName_2 = scanner.nextLine();
        System.out.println("---------------------------------------");
        System.out.println("Привет " + playerName_1 + " и " + playerName_2 + "! Добро пожаловать на морское сражение!");
        placeShips(playerName_1, battlefield_1);
        placeShips(playerName_2, battlefield_2);
        while (true) {
            makeTurn(playerName_1, monitor_1, battlefield_2);
            if (isWinCondition()) {
                break;
            }
            makeTurn(playerName_2, monitor_2, battlefield_1);
            if (isWinCondition()) {
                break;
            }
        }

    }

    public static void placeShips(String playerName, int[][] battlefield) {
        int deck = 4;
        while (deck >= 1) {
            System.out.println(playerName + " выбери расположение " + deck + " - x" + " палубных кораблей:");
            System.out.println();

            drawField(battlefield);

            System.out.println("Пожалуйста укажи координату OX");
            int x = scanner.nextInt();
            System.out.println("Пожалуйста укажи координату OY");
            int y = scanner.nextInt();
            System.out.println("Выбери расположение корабля:");
            System.out.println("1 - вертикальное");
            System.out.println("2 - горизонтальное");
            int rotation = scanner.nextInt();
            if (!isAvailable(x, y, deck, rotation, battlefield)) {
                System.out.println("Ошибка при установке корабля.");
                continue;
            }


            for (int i = 0; i < deck; i++) {
                if (rotation == 1) {
                    battlefield[x][y + i] = 1;
                } else if (rotation == 2) {
                    battlefield[x + i][y] = 1;
                }
            }
            deck--;
            clearScreen();
        }
    }

    public static void drawField(int[][] battlefield) {
        System.out.println("  0 1 2 3 4 5 6 7 8 9");
        for (int i = 0; i < battlefield.length; i++) {
            System.out.print(i + " ");
            for (int j = 0; j < battlefield[1].length; j++) {
                if (battlefield[j][i] == 0) {
                    System.out.print("- ");
                } else {
                    System.out.print("X ");
                }

            }
            System.out.println();
        }
    }

    public static void makeTurn(String playerName, int[][] monitor, int[][] battlefield) {
        while (true) {


            System.out.println(playerName + " пожалуйста сделай свой ход");
            System.out.println("  0 1 2 3 4 5 6 7 8 9");
            for (int i = 0; i < monitor.length; i++) {
                System.out.print(i + " ");
                for (int j = 0; j < monitor[1].length; j++) {
                    if (monitor[j][i] == 0) {
                        System.out.print("- ");
                    } else if (monitor[j][i] == 1) {
                        System.out.print("* ");
                    } else {
                        System.out.print("X ");
                    }
                }
                System.out.println();
            }
            System.out.println("Пожалуйста укажи координату OX");
            int x = scanner.nextInt();
            System.out.println("Пожалуйста укажи координату OY");
            int y = scanner.nextInt();
            if (battlefield[x][y] == 1) {
                System.out.println("Попал! Ходи снова!");
                monitor[x][y] = 2;
            } else {
                System.out.println("Промазал. Ход твоего противника");
                monitor[x][y] = 1;
                break;

            }
            clearScreen();

        }
    }

    public static boolean isWinCondition() {
        int count_1 = 0;
        for (int i = 0; i < monitor_1.length; i++) {
            for (int j = 0; j < monitor_1[i].length; j++) {
                if (monitor_1[i][j] == 2) {
                    count_1++;
                }

            }

        }
        int count_2 = 0;
        for (int i = 0; i < monitor_2.length; i++) {
            for (int j = 0; j < monitor_2[i].length; j++) {
                if (monitor_2[i][j] == 2) {
                    count_2++;
                }

            }

        }
        if (count_1 >= 10) {
            System.out.println(playerName_1 + " выйграл!");
            return true;
        } else if (count_2 >= 10) {
            System.out.println(playerName_2 + " выйграл!");
            return true;

        }
        return false;
    }

    public static boolean isAvailable(int x, int y, int deck, int rotation, int[][] battlefield) {
        // проверка на вместимость корабля
        if (rotation == 1) {
            if (y + deck > battlefield.length) {
                return false;
            }
        }
        if (rotation == 2) {
            if (x + deck > battlefield[0].length) {
                return false;
            }
        }


        // Проверка на соседние корабли без диагоналей
        while (deck != 0) {
            for (int i = 0; i < deck; i++) {
                int xi = 0;
                int yi = 0;
                if (rotation == 1) {
                    yi = i;
                } else {
                    xi = i;

                }
                if (x + 1 + xi < battlefield.length && x + 1 + xi >= 0) {
                    if (battlefield[x + 1 + xi][y + yi] != 0) {
                        return false;
                    }
                }
                if (x - 1 + xi < battlefield.length && x - 1 + xi >= 0) {
                    if (battlefield[x - 1 + xi][y + yi] != 0) {
                        return false;
                    }

                }
                if (y + 1 + yi < battlefield.length && y + 1 + yi >= 0) {
                    if (battlefield[x + xi][y + 1 + yi] != 0) {
                        return false;
                    }
                }
                if (y - 1 + yi < battlefield.length && y - 1 + yi >= 0) {
                    if (battlefield[x + xi][y - 1 + yi] != 0) {
                        return false;
                    }

                }



            }

            deck--;
        }
        return true;
    }

    public static void clearScreen(){
        try {
            new ProcessBuilder("cmd", "/c", "cls").inheritIO().start().waitFor();
        } catch (InterruptedException | IOException e) {
            e.printStackTrace();
        }
    }

}




