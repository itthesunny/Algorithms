import java.io.*;
import java.util.*;
import java.text.*;
import java.math.*;
import java.util.regex.*;
//Solution to: https://www.hackerrank.com/challenges/the-great-xor
public class Solution {

    static long theGreatXor(long x){
        long current = 1;
        long sum = 0;
        while (x > 0) {
            long lastBit = x & 1;
            if (lastBit == 0) {
                sum = sum + (current);
            }
            current = current * 2;
            x = x >> 1;
        }
        return sum;
    }

    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        int q = in.nextInt();
        for(int a0 = 0; a0 < q; a0++){
            long x = in.nextLong();
            long result = theGreatXor(x);
            System.out.println(result);
        }
    }
}
