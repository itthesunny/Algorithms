import java.io.*;
import java.util.*;
import java.text.*;
import java.math.*;
import java.util.regex.*;
//Runtime O(N). Solution to Hackerrank Absolute Permutaion Problem in linear time
public class Solution {
    public static void printArray(int[] arr) {
        for (int i = 0; i < arr.length; i++) {
            System.out.print(arr[i] + " ");
        }
        System.out.println("");
    }
    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        int numTestCases = in.nextInt();
        while (numTestCases > 0) {
            int length = in.nextInt();
            int[] arr = new int[length];
            int lastIndexVisited = 0;
            for (int i = 0; i < length; i++) {
                arr[i] = i + 1;
            }
            int k = in.nextInt();
            if (k == 0) {
                printArray(arr);
                continue;
            }
            for (int i = 0; i + (2 * k) - 1 < length; i = i + 2 * k) {
                lastIndexVisited = i + 2 * k;
                for (int j = i; j < i + k && j + k < length; j++) {
                    int temp = arr[j];
                    arr[j] = arr[j + k];
                    arr[j + k] = temp;
                }
            }
            if (lastIndexVisited < arr.length - 1) {
                System.out.println("-1");
            }
            else printArray(arr);
            numTestCases--;
        }
       
    }
}
