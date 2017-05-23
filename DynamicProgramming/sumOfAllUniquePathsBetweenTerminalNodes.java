import java.io.*;
import java.util.*;
import java.text.*;
import java.math.*;
import java.util.regex.*;
//Run time by using DP reduced to O(N)
// Solution to: https://www.hackerrank.com/contests/101hack49/challenges/summing-the-path-weights-between-nodes
public class Solution {
    static HashMap<Integer, HashMap<Integer, Integer>> edgeMap = new  HashMap<Integer, HashMap<Integer, Integer>>();
    static HashMap<Integer, Integer> vertexColorMap =  new HashMap<Integer, Integer>();
    static int vertex = 0;
    static int firstRed = 0;
    static int[] numDegrees;
    static HashSet<Integer> leafBlackSet = new HashSet<Integer>();
    static int numRed = 0;
    static int numBlack = 0;
    static int[] redNodeCount = new int[vertex + 1];
    static int[] blackNodeCount = new int[vertex + 1];   
    
    
    public static void determineSubTreeNodeCount(int current, int[] seenArray) {
        Iterator<Integer> iter = edgeMap.get(current).keySet().iterator();
        int redSum = 0;
        int blackSum = 0;
        while (iter.hasNext()) {
            int neighbor = iter.next();
            if (seenArray[neighbor] == 1) continue;
            seenArray[neighbor] = 1;
            determineSubTreeNodeCount(neighbor, seenArray);
            redSum = redNodeCount[neighbor] + redSum;
            blackSum = blackNodeCount[neighbor] + blackSum;
        }
        redNodeCount[current] = redSum;
        blackNodeCount[current] = blackSum;        
        if (vertexColorMap.get(current) == 0) redNodeCount[current] = redNodeCount[current] + 1;
        else blackNodeCount[current] = blackNodeCount[current] + 1;        
    }
    
    public static void traverse(int start) {
        int[] seenArray = new int[vertex + 1];
        int current = start;
        seenArray[current] = 1;
        int[] fromArray = new int[vertex + 1];
        fromArray[current] = current;    
        Queue<Integer> queue = new ArrayDeque<Integer>();
        long sum = 0;
        while (true) {
            Iterator<Integer> iter = edgeMap.get(current).keySet().iterator();
            while (iter.hasNext()) {
                int neighbor = iter.next();             
                if (seenArray[neighbor] == 1) continue;
                int weight = edgeMap.get(current).get(neighbor);                  
                seenArray[neighbor] = 1;
                fromArray[neighbor] = current;
                int redTerm =  redNodeCount[current];
                int blackTerm = blackNodeCount[current];
                if (vertexColorMap.get(current) == 0) redTerm--;
                else blackTerm--;
                long numPaths = (numBlack - blackNodeCount[neighbor])* redNodeCount[neighbor];
                numPaths = numPaths + (numRed - redNodeCount[neighbor])*  blackNodeCount[neighbor];
                sum = sum + (numPaths * weight);
                queue.add(neighbor);
            }
            if (queue.size() == 0) break;
            current = queue.poll();
        }
        System.out.println(sum); 
    }

    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        vertex = in.nextInt();
        boolean found = false;
        for (int i = 1; i <= vertex; i++) {
            int color = in.nextInt();
            if (color == 0) numRed++;
            else numBlack++;
            vertexColorMap.put(i, color);
            if (!found) {
                firstRed = i;
                found = true;
            }
        }

        for (int i = 1; i <= vertex; i++) {
            HashMap<Integer, Integer> newMap = new HashMap<Integer, Integer>();
            edgeMap.put(i, newMap);
        }
        numDegrees = new int[vertex + 1];
        for (int i = 0; i < vertex - 1; i++) {
            int source = in.nextInt();
            int dest = in.nextInt();
            int weight = in.nextInt();
            numDegrees[source] = numDegrees[source] + 1;
            numDegrees[dest] = numDegrees[dest] + 1;            
            HashMap<Integer, Integer> sourceMap = edgeMap.get(source);
            sourceMap.put(dest, weight);
            edgeMap.put(source, sourceMap);
            HashMap<Integer, Integer> destMap = edgeMap.get(dest);
            destMap.put(source, weight);
            edgeMap.put(dest, destMap);
        }
        int start = 0;
        for (int i = 1; i < vertex + 1; i++) {
            if (numDegrees[i] == 1) {
                start = i;
                break;
            }
        }
        for (int i = 1; i < vertex + 1; i++) {
            if (numDegrees[i] == 1) {
                start = i;
                break;
            }       
        }
        blackNodeCount = new int[vertex + 1];
        redNodeCount = new int[vertex + 1];
        int[] seenArray = new int[vertex + 1];
        seenArray[start] = 1;
        determineSubTreeNodeCount(start, seenArray);
        traverse(start);
    }
}
