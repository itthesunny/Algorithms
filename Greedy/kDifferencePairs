import java.io.*;
import java.util.*;
import java.text.*;
import java.math.*;
import java.util.regex.*;
//Solution to https://www.hackerrank.com/challenges/pairs
// Finds number of pairs with K difference in O(NlogN) runtime. If array is sorted runtime is O(N).
public class Solution {


    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        int length = in.nextInt();
        int k = in.nextInt();
        int[] arr = new int[length];
        for (int i = 0; i < length; i++) {
            arr[i] = in.nextInt();
        }
        Arrays.sort(arr);
        int[] reducedArr = new int[length];
        for (int i = 0; i < arr.length; i++) {
            reducedArr[i] = arr[i] - k;
        }
        int pointerArr = 0;
        int pointerReducedArr = 0;
        int count = 0;
        while (pointerArr != length && pointerReducedArr != length) {
            if (arr[pointerArr] == reducedArr[pointerReducedArr]) count++;
            if (arr[pointerArr] > reducedArr[pointerReducedArr]) pointerReducedArr++;
            else pointerArr++;
        }
        System.out.println(count);
        
    }
}
