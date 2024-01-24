import java.util.*;

public class Main extends Board {
    public static void main(String[] args) {
        Scanner reader = new Scanner(System.in);
        Field[][] fields = new Field[3][3];
        String gameOption;
        int dificultyMod = 0;
        int contNumberPlays = 0;
        int[] coordinatesPlay = new int[2];
        char currentSimbol = 'x';
        String victoryousPlayer;
        String messageIS;

        do {
            System.out.println("Escolha um dos modos de jogo: ");
            System.out.println("1 - Coop vs IA");
            System.out.println("2 - pvp");
            gameOption = reader.next();
            // Modo de jogo
        } while (!Objects.equals(gameOption, "1") && !Objects.equals(gameOption, "2"));

        // Dificuldade
        if (gameOption.equals("1")) {
            System.out.println("Escolha a dificuldade: ");
            System.out.println("1 - Imprevisível");
            System.out.println("2 - Dificil");
            dificultyMod = reader.nextInt();
        }

        //Inicar Game
        startGame(fields);

        // Enquanto o game está rolando
        do {
            try {
                // Desenha
                printGame(fields);

                // Verifica se existe uma linha inteira com os mesmos valores
                victoryousPlayer = checkVictory(fields);

                // Veririfica a vitória
                if (!victoryousPlayer.isEmpty()) {
                    System.out.printf("%s", victoryousPlayer);
                    break;
                }

                //PVP
                if (gameOption.equals("2")) {

                    coordinatesPlay = PvpPlayer.chooseFields(reader, currentSimbol);

                    if (PvpPlayer.checkChoyceFieldPlay(fields, coordinatesPlay, currentSimbol)) {
                        if (currentSimbol == 'x') {
                            currentSimbol = 'b';
                        } else {
                            currentSimbol = 'x';
                        }
                    } else {
                        System.out.println("Jogue novamente!");
                    }
                }
                // IA
                else {
                    contNumberPlays++;

                    coordinatesPlay = PvpPlayer.chooseFields(reader, currentSimbol);

                    if (PvpPlayer.checkChoyceFieldPlay(fields, coordinatesPlay, currentSimbol)) {
                        messageIS = playIA(fields, dificultyMod, contNumberPlays);
                        System.out.println(messageIS);
                    } else {
                        System.out.println("Jogue novamente!");
                    }
                }
            } catch (Exception e) {
                System.out.println("Jogue novamente"+e);
            }
        } while (true);
        System.out.println("Acabou o jogo!");
    }
    public static void randomPlayIA(Field[][] field) {
        Random r = new Random();
        int[] coordinates = new int[2];
        boolean validPlay = true;

        do {
            coordinates[0] = r.nextInt(3);
            coordinates[1] = r.nextInt(3);
            if (field[coordinates[0]][coordinates[1]].getSimbol() == ' ') {
                field[coordinates[0]][coordinates[1]].setSimbol('b');
                validPlay = false;
            }
        } while (validPlay);
    }
    public static int[][] findPlayIa(int[][] identifyFieldsWithSymbol, int[][] templatePlays) {

        int[][] commonElements = CustomFinds.commonElements(identifyFieldsWithSymbol, templatePlays);

        return CustomFinds.uniqueElements(identifyFieldsWithSymbol, commonElements);
    }
    public static void hardPlayIA(Field[][] field) {
        Set<int[]> arrDeX = new HashSet<>();
        Set<int[]> arrDeB = new HashSet<>();
        Set<int[]> prioritizePlays = new HashSet<>();
        int[] coordinates = new int[2];
        int contLoop = 0;
        boolean validPlay = true;

        int[][][] templatePlays = {
                {{0, 0}, {0, 1},{0, 2}}, // horizontal
                {{1, 0}, {1, 1},{1, 2}}, // horizontal
                {{2, 0}, {2, 1},{2, 2}}, // horizontal
                {{0, 0}, {1, 0},{2, 0}}, // vertical
                {{0, 1}, {1, 1},{2, 1}}, // vertical
                {{0, 2}, {1, 2},{2, 2}}, // vertical
                {{0, 0}, {1, 1},{2, 2}}, // diagonal
                {{0, 2}, {1, 1},{2, 0}} // diagonal
        };

        do {
            contLoop++;
            int[][] priorizedPlaysConverted = new int[0][0];
            int[][] intArrayX = new int[3][3];
            int[][] intArrayB = new int[3][3];

            for (int i = 0; i < 3; i++) {
                for (int j = 0; j < 3; j++) {
                    if (field[i][j].getSimbol() == 'b') {
                        CustomFinds.setPlay(arrDeB, i, j);
                    }
                    if (field[i][j].getSimbol() == 'x') {
                        CustomFinds.setPlay(arrDeX, i, j);
                    }
                }
            }
            intArrayX = CustomFinds.convertSetToArray(arrDeX);
            intArrayB = CustomFinds.convertSetToArray(arrDeB);

            for (int i = 0; i < templatePlays.length; i++) {
                if (findPlayIa(templatePlays[i], intArrayB).length == 1) {
                    coordinates[0] = findPlayIa(templatePlays[i], intArrayB)[0][0];
                    coordinates[1] = findPlayIa(templatePlays[i], intArrayB)[0][1];
                    CustomFinds.setPlay(prioritizePlays, coordinates[0], coordinates[1]);
                } else if (findPlayIa(templatePlays[i], intArrayX).length == 1) {
                    coordinates[0] = findPlayIa(templatePlays[i], intArrayX)[0][0];
                    coordinates[1] = findPlayIa(templatePlays[i], intArrayX)[0][1];
                    CustomFinds.setPlay(prioritizePlays, coordinates[0], coordinates[1]);
                }
            }

            priorizedPlaysConverted = CustomFinds.convertSetToArray(prioritizePlays);

            // Gambiarra
            if (Arrays.deepToString(priorizedPlaysConverted).equals("[]")) {
                randomPlayIA(field);
                contLoop = 0;
                validPlay = false;
            }

            System.out.println("Mostrar na aula"+Arrays.deepToString(priorizedPlaysConverted));
            coordinates[0] = priorizedPlaysConverted[priorizedPlaysConverted.length - 1][0];
            coordinates[1] = priorizedPlaysConverted[priorizedPlaysConverted.length - 1][1];

            // Gambiarra
            if (field[coordinates[0]][coordinates[1]].getSimbol() == ' ') {
                field[coordinates[0]][coordinates[1]].setSimbol('b');
                contLoop = 0;
                validPlay = false;
            } else if (contLoop > 20) {
                randomPlayIA(field);
                contLoop = 0;
                validPlay = false;
            }


            // Se der velha
            if (!checkVictory(field).isEmpty()) {
                contLoop = 0;
                validPlay = false;
            }
        } while (validPlay);
    }
    public static String playIA(Field[][] field, int difficulty, int contNumberPlays) {
        if (contNumberPlays > 1) {
            if (difficulty == 2) {
                hardPlayIA(field);
                return "Uma jogada dificil foi feita!";
            } else {
                randomPlayIA(field);
                return "Uma jogada aleatória foi feita!";
            }
        } else {
            randomPlayIA(field);
            return "Uma jogada aleatória foi feita!";
        }
    }
}
/*
        System.out.println("       0        1        2 ");
        System.out.printf ("0    [0][0] | [0][1] | [0][2]");
        System.out.println("     ________________________");
        System.out.printf ("1    [1][0] | [1][1] | [1][2]");
        System.out.println("     ________________________");
        System.out.printf ("2    [2][0] | [2][1] | [2][2]");
*/