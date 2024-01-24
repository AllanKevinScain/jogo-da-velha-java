import java.util.ArrayList;

public class Board extends Field {
    public static void startGame(Field[][] field) {
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                field[i][j] = new Field();
            }
        }
    }
    public static void clearGame() {
        for (int i = 0; i < 5; i++) {
            System.out.println(" ");
        }
    }
    public static void printGame(Field[][] field) {
        clearGame();
        System.out.println("\n\n\n\n\n\n");
        System.out.println("\n\n\n\n\n\n");
        System.out.println("\n\n\n\n\n\n");
        System.out.println("\n\n\n\n\n\n");
        System.out.println("\n\n\n\n\n\n");

        System.out.println("     0    1    2");
        System.out.printf("0     %c | %c | %c %n", field[0][0].getSimbol(), field[0][1].getSimbol(), field[0][2].getSimbol());
        System.out.println("     -------------");
        System.out.printf("1     %c | %c | %c %n", field[1][0].getSimbol(), field[1][1].getSimbol(), field[1][2].getSimbol());
        System.out.println("     -------------");
        System.out.printf("2     %c | %c | %c %n", field[2][0].getSimbol(), field[2][1].getSimbol(), field[2][2].getSimbol());
    }

    public static String loopForCheck(Field[][] field, char aux) {
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                // Jogadas Verticais
                if (i == 0 && field[0][j].getSimbol() == aux && field[1][j].getSimbol()  == aux && field[2][j].getSimbol()  == aux) {
                    return "O " + aux + " é vencedor!";
                }
                // Jogadas Horizontais
                if (field[i][0].getSimbol() == aux && field[i][1].getSimbol()  == aux && field[i][2].getSimbol()  == aux) {
                    return "O " + aux + " é vencedor!";
                }
            }
        }
        if (field[0][0].getSimbol() == aux && field[1][1].getSimbol()  == aux && field[2][2].getSimbol()  == aux) {
            return "O " + aux + " é vencedor!";
        }

        // linha cruzada 2
        if (field[0][2].getSimbol() == aux && field[1][1].getSimbol()  == aux && field[2][0].getSimbol()  == aux) {
            return "O " + aux + " é vencedor!";
        }

        return "";
    }
    public static String checkVictory(Field[][] field) {
        ArrayList<String> arrVelha = new ArrayList<>();

        // Validar o X
        if (!loopForCheck(field, 'x').isEmpty()) {
            return loopForCheck(field, 'x');
        }

        // Verificar se deu velha
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if (field[i][j].getSimbol() != ' ') {
                    arrVelha.add(""+field[i][j].getSimbol());
                }
            }
        };
        if (arrVelha.size() == 9) {
            return "Deu Velha!";
        }

        // Validar o B
        return loopForCheck(field, 'b');
    }
}
