import java.util.Set;

public class Convert {
    public static String arrayToString(int[] arr) {
        return arr[0] + "," + arr[1];
    }
    public static int[] stringToArray(String str) {
        String[] parts = str.split(",");
        int[] arr = new int[2];
        arr[0] = Integer.parseInt(parts[0]);
        arr[1] = Integer.parseInt(parts[1]);
        return arr;
    }
    public static int[][] convertSetToArray(Set<int[]> set) {
        int[][] array = new int[set.size()][];
        int index = 0;

        for (int[] arr : set) {
            array[index++] = arr;
        }

        return array;
    }
}
