import java.io.*;
import java.util.*;
import java.text.*;
import java.math.*;
import java.util.regex.*;

public class Solution {

    public static void main(String[] args) {       
        Scanner in = new Scanner(System.in);
        String str1 = in.next();
        String str2 = in.next();
        int l1 = str1.length();
        int l2 = str2.length();   
        int[][] matrix = new int[l1 + 1][l2 + 1];
        for (int i = 0; i < l1 + 1; i++) {
            if (i == 0) continue;
            for (int j = 0; j < l2 + 1; j++) {
                if (j == 0) continue;
                if (str1.charAt(i - 1) == str2.charAt(j - 1)) {
                    matrix[i][j] = matrix[i - 1][j - 1] + 1;
                }
                else matrix[i][j] = Math.max(matrix[i - 1][j], matrix[i][j - 1]);
            }
        }
        System.out.println(matrix[l1][l2]);
    }
}
