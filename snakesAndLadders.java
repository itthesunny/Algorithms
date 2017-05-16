import java.io.*;
import java.util.*;
import java.text.*;
import java.math.*;
import java.util.regex.*;

public class Solution {
    static HashMap<Integer, HashSet<Integer>> edgeMap = new HashMap<Integer, HashSet<Integer>>();
    static HashMap<Integer, Integer> snakeMap = new HashMap<Integer, Integer>();
    public static void BFS() {
        int[] seenArray = new int[101];
        int[] fromArray = new int[101];
        seenArray[1] = 1;
        fromArray[1] = 1;
        int current = 1;
        int dest = 100;
        Queue<Integer> bfsQueue = new LinkedList<Integer>();
        while (true) {
            Object[] neighborArray = edgeMap.get(current).toArray();
            Arrays.sort(neighborArray);
            if (snakeMap.containsKey(current)) {
                int next = snakeMap.get(current);
                if (seenArray[next] == 0) {
                    seenArray[next] = 1;
                    fromArray[next] = current;
                }
                current = next;
                continue;
            }
            for (int i = neighborArray.length - 1; i >= 0; i--) {
                int neighbor = (int) neighborArray[i];
                if (seenArray[neighbor] == 0) {
                    seenArray[neighbor] = 1;
                    fromArray[neighbor] = current;
                    bfsQueue.add(neighbor);
                    if (neighbor == 99) break;
                }
            }
            current = bfsQueue.poll();
            if (current == dest) break;
        }
        current = dest;
        int count = 0;
        while (true) {
            int next = fromArray[current];
            if (Math.abs(current - next) < 7) {
            count++;
            }
            current = next;
            if (current == 1) break; 
        }
        System.out.println(count);
    }

    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        int numTestCase = in.nextInt();
        for (int k = 0; k < numTestCase; k++) {
            int numVertex = 99;
            for (int i = 1; i <= numVertex; i++) {
                HashSet<Integer> neighborSet = new HashSet<Integer>();
                for (int j = i + 1; j < i + 7; j++) {
                    if (j == 101) break;
                    neighborSet.add(j);
                }
                edgeMap.put(i, neighborSet);
            }
            int numLadder = in.nextInt();
            for (int i = 0; i < numLadder; i++) {
                int source = in.nextInt();
                int dest = in.nextInt();
                HashSet<Integer> neighborSet = edgeMap.get(source);
                neighborSet.add(dest);
                edgeMap.put(source, neighborSet);
            }
            int numSnakes = in.nextInt();
            for (int i = 0; i < numSnakes; i++) {
                int source = in.nextInt();
                int dest = in.nextInt();
                snakeMap.put(source, dest);
            }
            boolean reachable = false;
            for (int i = 94; i <= 99; i++) {
                if (!snakeMap.containsKey(i)) {
                    reachable = true;
                }
            }
            if (reachable) BFS();
            else System.out.println("-1");
            edgeMap = new HashMap<Integer, HashSet<Integer>>();
            snakeMap = new HashMap<Integer, Integer>();
        }
    }
}
