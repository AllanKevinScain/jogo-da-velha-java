import java.util.Scanner;

public class PvpPlayer extends Field {
    public static int[] chooseFields(Scanner reader, char currentSymbol) {
        int[] coordinates = new int[2];

        System.out.printf("%s %c%n", "Quem Joga", currentSymbol);
        System.out.print("Informe a linha: ");
        coordinates[0] = reader.nextInt();
        System.out.print("Informe a coluna: ");
        coordinates[1] = reader.nextInt();

        return coordinates;
    };
    public static Boolean checkChoyceFieldPlay(Field[][] field, int[] coordinates, char currentSymbol) {
        if (field[coordinates[0]][coordinates[1]].getSimbol() == ' ') {
            field[coordinates[0]][coordinates[1]].setSimbol(currentSymbol);
            return true;
        } else {
            return false;
        }

    }
}
