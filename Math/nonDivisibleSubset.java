import java.io.*;
import java.util.*;
import java.text.*;
import java.math.*;
import java.util.regex.*;
//Solution to https://www.hackerrank.com/challenges/non-divisible-subset
//Runtime O(N).  To find the largest subset where addition of any 2 elements is non divisible by, use number theory 
// to identify the unique counts of each elements in ZmodK*Z. Then we know two elements cant be in the maximal set if
//they are additive inverse of each other in modK. Then just iterate through the unique mods by following the stated rule.
//Special case exist when unique mod is 0 or inverse and unique mod are equal to each other.
public class Solution {
    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        HashMap<Integer, Integer> moduloToCountMap = new HashMap<Integer, Integer>();
        int num = in.nextInt();
        int k = in.nextInt();
        for (int i = 0; i < k; i++) {
            moduloToCountMap.put(i, 0);
        }
        int totalNumber = num;
        while (num > 0) {
            int current = in.nextInt();
            int mod = (current + k) % k;
            int count = moduloToCountMap.get(mod);
            moduloToCountMap.put(mod, count + 1);
            num--;
        }
        int max = 0;
        for (int i = 1; i < k; i++) {
            int moduloCompliment = k - i;
            if (i == moduloCompliment) {
                max = max + Math.min(1, moduloToCountMap.get(i));
                continue;
            }
            max = max + Math.max(moduloToCountMap.get(i), moduloToCountMap.get(moduloCompliment));
            moduloToCountMap.put(i, 0);
            moduloToCountMap.put(moduloCompliment, 0);
        }
        System.out.println(max + Math.min(1, moduloToCountMap.get(0)));
    }
}
