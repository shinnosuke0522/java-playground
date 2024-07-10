package org.example;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Hello world!
 */
public class App {

    public static void main(String[] args) {
        int[] arr1 = {1, 2, 3, 4, 5};
        int[] arr2 = {3, 4, 5, 6, 7};
        int[] result = App.execute(arr1, arr2);
        System.out.println(Arrays.toString(result));
    }

    public static int[] execute(int[] arr1, int[] arr2) {
        List<Integer> result = new ArrayList<>();
        for (int target : arr1) {
            if (haveSameValue(target, arr2)) {
                result.add(target);
            }
        }

        return result.stream().mapToInt(Integer::intValue).toArray();
    }

    private static boolean haveSameValue(int target, int[] source) {
        if (source.length == 0) {
            return false;
        }
        if (source.length <= 1) {
            return source[0] == target;
        }

        int midIdx = (source.length - 1)/2;
        if (source[midIdx] == target) {
            return true;
        } else if(target < source[midIdx]) {
            int[] newSource = Arrays.copyOfRange(source, 0, midIdx);
            return haveSameValue(target, newSource);
        } else {
            int[] newSource = Arrays.copyOfRange(source, midIdx+1, source.length);
            return haveSameValue(target, newSource);
        }
    }

}
