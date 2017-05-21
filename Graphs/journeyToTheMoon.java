import java.io.*;
import java.util.*;

public class Solution {
    public static HashMap<Integer, HashSet<Integer>> edgeMap = new HashMap<Integer, HashSet<Integer>>();
    public static HashSet<Integer> notSeenVertexSet = new HashSet<Integer>();
    
    public static void BFS(int numVertex) {
        int current = 0;
        int numVertexSeen = 1;
        int numConnectedComponent = 0;
        int numVertexSeenInCurrentCC = 1;
        int combinationSum = 0;
        int weightedCCCountSum = 0;
        int[] vertexSeenArray = new int[numVertex];
        vertexSeenArray[0] = 1;
        notSeenVertexSet.remove(0);
        Queue<Integer> queue = new PriorityQueue<Integer>();
        while (numVertexSeen < numVertex) {
            HashSet<Integer> neighborSet = edgeMap.get(current);
            Iterator<Integer> iter = neighborSet.iterator();
            while (iter.hasNext()) {
                int neighbor = iter.next();
                if (vertexSeenArray[neighbor] == 0) {
                    queue.add(neighbor);
                    numVertexSeen++;
                    numVertexSeenInCurrentCC++;
                    vertexSeenArray[neighbor] = 1;
                    notSeenVertexSet.remove(neighbor);
                }
            }
            if (queue.size() == 0) {
                int toAdd = weightedCCCountSum * numVertexSeenInCurrentCC;
                weightedCCCountSum = weightedCCCountSum + numVertexSeenInCurrentCC;
                combinationSum = combinationSum + toAdd;
                numVertexSeenInCurrentCC = 0;
                numConnectedComponent++;
                current = (int) notSeenVertexSet.toArray()[0];
                vertexSeenArray[current] = 1;
                numVertexSeenInCurrentCC++;
                numVertexSeen++;
                notSeenVertexSet.remove(current);
            }
            else {
                current = queue.poll();
            }
            if (numVertexSeen == numVertex) {
                int toAdd = weightedCCCountSum * numVertexSeenInCurrentCC;
                weightedCCCountSum = weightedCCCountSum + numVertexSeenInCurrentCC;
                combinationSum = combinationSum + toAdd;
                numVertexSeenInCurrentCC++;
                numConnectedComponent++;
                break;
            }
        }
        System.out.println(combinationSum);
    }

    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        int numPeople = in.nextInt();
        int k = in.nextInt();
        for (int i = 0; i < numPeople; i++) {
            HashSet<Integer> set = new HashSet<Integer>();
            edgeMap.put(i, set);
            notSeenVertexSet.add(i);
        }
        for (int i = 0; i < k; i++) {
            int source = in.nextInt();
            int dest = in.nextInt();
            HashSet<Integer> neighborSet = edgeMap.get(source);
            neighborSet.add(dest);
            edgeMap.put(source, neighborSet);
            neighborSet = edgeMap.get(dest);
            neighborSet.add(source);
            edgeMap.put(dest, neighborSet);
        }
        BFS(numPeople);
    }
}
