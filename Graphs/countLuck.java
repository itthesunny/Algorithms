import java.io.*;
import java.util.*;
import java.text.*;
import java.math.*;
import java.util.regex.*;
//O(V+E) solution to https://www.hackerrank.com/challenges/count-luck
public class Solution {
    static class Point {
        int x = 0;
        int y = 0;
        public Point(int x, int y) {
            this.x = x;
            this.y = y;
        }
    }
    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        int num = in.nextInt();
        while (num > 0) {
            int n = in.nextInt();
            int m = in.nextInt();
            char[][] arr = new char[n][m];
            int[][] visitedArr = new int[n][m];
            Point[][] fromArr = new Point[n][m];
            Point source = null;
            Point dest = null;
            for (int i = 0; i < n; i++) {
                String current = in.next();
                for (int j = 0; j < current.length(); j++) {
                    arr[i][j] = current.charAt(j);
                    if (arr[i][j] == 'M') {
                        visitedArr[i][j] = 1;
                        source = new Point(i, j);
                    }
                    if (arr[i][j] == '*') {
                        dest = new Point(i, j);
                    }
                }
            }
            Stack<Point> stack = new Stack<Point>();
            Point current = source;
            while (true) {
                //Above direction
                if (current.y > 0) {
                    if (arr[current.x][current.y - 1] != 'X' && visitedArr[current.x][current.y - 1] == 0) {
                        Point newPoint = new Point(current.x, current.y - 1);
                        stack.push(newPoint);
                        visitedArr[current.x][current.y - 1] = 1;
                        fromArr[current.x][current.y - 1] = current;
                        if (newPoint.x == dest.x && newPoint.y == dest.y) break;
                    }
                }
                //Below direction
                if (current.y < m - 1) {
                    if (arr[current.x][current.y + 1] != 'X' && visitedArr[current.x][current.y + 1] == 0) {
                        Point newPoint = new Point(current.x, current.y + 1);
                        visitedArr[current.x][current.y + 1] = 1;
                        fromArr[current.x][current.y + 1] = current;
                        stack.push(newPoint);
                        if (newPoint.x == dest.x && newPoint.y == dest.y) break;
                    }
                }
                //Left
                if (current.x > 0) {
                   if (arr[current.x - 1][current.y] != 'X' && visitedArr[current.x - 1][current.y] == 0) {
                        Point newPoint = new Point(current.x - 1, current.y);
                        visitedArr[current.x - 1][current.y] = 1;
                        fromArr[current.x - 1][current.y] = current;
                        stack.push(newPoint);
                        if (newPoint.x == dest.x && newPoint.y == dest.y) break;
                    } 
                }
                //Right
                if (current.x < n - 1) {
                   if (arr[current.x + 1][current.y] != 'X' && visitedArr[current.x + 1][current.y] == 0) {
                        Point newPoint = new Point(current.x + 1, current.y);
                        visitedArr[current.x + 1][current.y] = 1;
                        fromArr[current.x + 1][current.y] = current;
                        stack.push(newPoint);
                        if (newPoint.x == dest.x && newPoint.y == dest.y) break;
                    } 
                }
                if (stack.size() == 0) break;
                current = stack.pop();
            }
            int k = in.nextInt();
            current = fromArr[dest.x][dest.y];
            int counter = 0;
            boolean last = false;
            while (true) {
                int tempCounter = 0;
                if (current.y > 0) {
                    if (arr[current.x][current.y - 1] != 'X') tempCounter++;
                }
                if (current.y < m - 1) {
                    if (arr[current.x][current.y + 1] != 'X') tempCounter++;
                }
                if (current.x > 0) {
                    if (arr[current.x - 1][current.y] != 'X') tempCounter++;
                }
                if (current.x < n - 1) {
                    if (arr[current.x + 1][current.y] != 'X') tempCounter++;
                }
                if (last && tempCounter > 1) counter++;
                else if (tempCounter > 2) counter++;
                if (current.y == source.y && current.x == source.x) break;
                current = fromArr[current.x][current.y];
                if (current.y == source.y && current.x == source.x) last = true;
            }
            if (k == counter) System.out.println("Impressed");
            else System.out.println("Oops!");
            num--;
        }
    }
}
