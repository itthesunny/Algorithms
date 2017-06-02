import java.io.*;
import java.util.*;
import java.text.*;
import java.math.*;
import java.util.regex.*;
//Solution to: https://www.hackerrank.com/challenges/minimum-loss
//Expected O(N) runtime.
public class Solution {
    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        int length = in.nextInt();
        long[] arr = new long[length];
        HashMap<Long, Integer> indexMap = new HashMap<Long, Integer>();
        for (int i = 0; i < length; i++) {
            arr[i] = Long.parseLong(in.next());
            indexMap.put(arr[i], i);
        }
        Arrays.sort(arr);
        long minDiff = Integer.MAX_VALUE;
        for (int i = 1; i < length; i++) {
            if (arr[i] - arr[i - 1] < minDiff) {
                if (indexMap.get(arr[i]) < indexMap.get(arr[i - 1])) minDiff = arr[i] - arr[i - 1];
            }
        }
        System.out.println(minDiff);
    }
}
