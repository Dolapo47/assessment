package com.adedolapo.assessment;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class AlgorithmSolutions {

    public static boolean sumToTarget(int[] numbers, int target) {
        // initialize result
        boolean result = false;
        int size = numbers.length;
        // checks if array has less than2 elements and returns false
        if (size < 2) {
            boolean b = false;
        }
        else {
            // loops through each value and checks against the rest,
            // to know the element that adds up to the target value
            for (int i = 0; i < size - 1; i++) {
                for (int j = i + 1; j < size; j++) {
                    if ((target - numbers[i]) == numbers[j]) {
                        // set the result to true if it finds the right addition
                        result = true;
                        break;
                    }
                }
            }
        }
        return result;
    }

    public static int[] keyIndex(int[] array, int key) {
        int[] result = {-1, -1};
        int low = findLowIndex(array, key);
        int high = findHighIndex(array, key);
        if (low <= high) {
            result[0] = low;
            result[1] = high;
        }
        return result;
    }

    private static int findLowIndex(int[] array, int key) {
        int low = 0;
        int high = array.length - 1;
        while (low <= high) {
            int mid = low + (high - low) / 2;
            if (array[mid] >= key) {
                high = mid - 1;
            } else {
                low = mid + 1;
            }
        }
        return low;
    }

    private static int findHighIndex(int[] array, int key) {
        int low = 0;
        int high = array.length - 1;
        while (low <= high) {
            int mid = low + (high - low) / 2;
            if (array[mid] <= key) {
                low = mid + 1;
            } else {
                high = mid - 1;
            }
        }
        return high;
    }

    public static void main(String[] args) {
        System.out.println(sumToTarget(new int[]{1, 2, 3}, 4));

        int[] array = {1, 2, 3, 3, 3, 4, 5, 5, 5, 6, 6, 6};
        int key = 6;
        System.out.println("Lower Index: " + keyIndex(array, key)[0]);
        System.out.println("Higher Index: " + keyIndex(array, key)[1]);

    }
}
