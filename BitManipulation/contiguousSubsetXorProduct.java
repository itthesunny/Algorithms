import java.io.*;
import java.util.*;
import java.text.*;
import java.math.*;
import java.util.regex.*;

public class Solution {
    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        int numTestCases = in.nextInt();
        while (numTestCases > 0) {
            int length = in.nextInt();
            int[] arr = new int[length];
            for (int i = 0; i < length; i++) {
                arr[i] = in.nextInt();
            }
            if (length % 2 == 0) System.out.println("0");
            else {               
                int xorProduct = 0;
                for (int i = 0; i < length; i++) {
                    if (i % 2 == 0) xorProduct = xorProduct ^ arr[i];
                }
                System.out.println(xorProduct);
            }
            numTestCases--;
        }
    }
}
