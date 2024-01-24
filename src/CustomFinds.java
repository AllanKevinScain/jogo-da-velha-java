import java.util.HashSet;
import java.util.Set;

public class CustomFinds extends Convert {
    public static int[][] uniqueElements(int[][] array, int[][] commonElements) {
        Set<String> commonSet = new HashSet<>();
        for (int[] arr : commonElements) {
            commonSet.add(arrayToString(arr));
        }

        Set<String> uniqueSet = new HashSet<>();

        for (int[] arr : array) {
            String arrString = arrayToString(arr);
            if (!commonSet.contains(arrString)) {
                uniqueSet.add(arrString);
            }
        }

        int[][] result = new int[uniqueSet.size()][2];
        int index = 0;

        for (String arrString : uniqueSet) {
            result[index++] = stringToArray(arrString);
        }

        return result;
    }
    public static int[][] commonElements(int[][] array1, int[][] array2) {
        Set<String> set1 = new HashSet<>();
        Set<String> set2 = new HashSet<>();

        for (int[] arr : array1) {
            set1.add(arrayToString(arr));
        }

        for (int[] arr : array2) {
            String arrString = arrayToString(arr);
            if (set1.contains(arrString)) {
                set2.add(arrString);
            }
        }

        int[][] result = new int[set2.size()][2];
        int index = 0;

        for (String arrString : set2) {
            result[index++] = stringToArray(arrString);
        }

        return result;
    }
    public static void setPlay(Set<int[]> auxFields, int changedLine, int changedColumn) {
        int[] coordinates = new int[2];
        coordinates[0] = changedLine;
        coordinates[1] = changedColumn;

        auxFields.add(coordinates);
    }
}
