import java.io.*;
import java.util.*;
//Algorithm: Iteratively get rid of all leaves in the tree till a straight line is left. Key is keeping an array that records degrees of each vertex initialized while input stream is read. Traverse through array to find all such with degree of 1. Continue this process iteratively. If a vertex is deleted just decrement degree's of neighbors and check if they became a leaf to prevent multiple array traversal. Total of this is done in O(V+E). While doing so identify if the leaf deleted have to traveled or not.If so add its weight twice. Additionally keep the longestPathArray to identify the largest simple Path taken to a vertex.

//After being left with a line, clear all nodes we dont have to use then add all weights that must be used. Now only additional step is to correct the total sum which is slightly wrong since we added each leaf twice. Whena start and a finish is possible two heaviest simple paths will only be traveled once thus we adjust for that.

//Passes all hackerrank tests with a run time of O(V+E)
public class Solution {
    public static HashSet<Integer> leafSet = new HashSet<Integer>();
    public static HashMap<Integer,HashMap<Integer,Integer>> edgeMap = new HashMap<Integer,HashMap<Integer,Integer>>();
    public static HashSet<Integer> targetSet = new HashSet<Integer>();
    public static int sum = 0;
    public static int linePathCost = 0;
    public static int vertexNum;
    public static int terminalVertexNum;
    public static int[] neighborCount;
    public static HashSet<Integer> haveToIncludeSet = new HashSet<Integer>();
    public static HashSet<Integer> doubleMarkedSet = new HashSet<Integer>();
    public static int leftEnd = -1;
    public static int rightEnd = -1;
    public static int numVertexWithMoreThan2Degree = 0;
    public static boolean firstTime = true;
    public static int[] longestPathArray;
    public static int[] secondLongestPathArray;
    

    
    public static void trimPath() {
        sum = sum - longestPathArray[leftEnd] - longestPathArray[rightEnd];
        System.out.println(sum);              
    }

    public static void calculateCost() {
        int current = leftEnd;
        HashSet<Integer> visited = new HashSet<Integer>();
        while (true) {
            visited.add(current);            
            Object[] neighborArray = edgeMap.get(current).keySet().toArray();
            int first = (int) neighborArray[0];
            int neighbor;
            if (neighborArray.length > 1) {
                if (visited.contains(first)) {
                    neighbor = (int) neighborArray[1];
                }
                else neighbor = first;
            }
            else neighbor = first;
            sum = sum + edgeMap.get(current).get(neighbor);
            linePathCost += edgeMap.get(current).get(neighbor);
            current = neighbor;
            if (current == rightEnd) break;
        }
    }

    public static void simplifyLinearGraph() {
        Iterator<Integer> iter = leafSet.iterator();
        while (iter.hasNext()) {
            int end = iter.next();
            int current = end;
            while (true) {
                if (haveToIncludeSet.contains(current) || targetSet.contains(current)) break;
                HashMap<Integer, Integer> currentMap = edgeMap.get(current);
                int neighbor = (int) currentMap.keySet().toArray()[0];
                HashMap<Integer, Integer> neighborMap = edgeMap.get(neighbor);
                neighborMap.remove(current);
                edgeMap.put(neighbor, neighborMap);
                edgeMap.remove(current);
                current = neighbor;
            }   
            if (rightEnd == -1) {
                rightEnd = current;
            }
            else leftEnd = current;
        }
    }  
       
    public static boolean simplifyGraph() {
        Iterator<Integer> leafIterator = leafSet.iterator();
        HashSet<Integer> newEndSet = new HashSet<Integer>();     
        while (leafIterator.hasNext()) {
            int current = leafIterator.next();
            Object[] arr = edgeMap.get(current).keySet().toArray();
            int neighbor = (int) arr[0]; 
            if (targetSet.contains(current) || haveToIncludeSet.contains(current)) {
                sum = sum + 2 * edgeMap.get(current).get(neighbor);
                doubleMarkedSet.add(current);
                haveToIncludeSet.add(neighbor);
                int newCost = longestPathArray[current] + edgeMap.get(current).get(neighbor);
                if (newCost > longestPathArray[neighbor]) {      
                    int candidate1 = secondLongestPathArray[current] + edgeMap.get(current).get(neighbor);
                    if (candidate1 == newCost) candidate1 = 0;                    
                    secondLongestPathArray[neighbor] = Math.max(longestPathArray[neighbor], candidate1);                                                                              
                    longestPathArray[neighbor] = newCost; 
                }
                else if (newCost > secondLongestPathArray[neighbor]) {
                  secondLongestPathArray[neighbor] = newCost;                   
                }
            }
            edgeMap.remove(current);
            HashMap<Integer, Integer> newMap = edgeMap.get(neighbor);
            newMap.remove(current);
            edgeMap.put(neighbor, newMap);
            neighborCount[neighbor]--;
            if (neighborCount[neighbor] == 1) {
                newEndSet.add(neighbor);
            }  
            if (neighborCount[neighbor] == 2) {
                numVertexWithMoreThan2Degree--;
            } 
        }
        leafSet = newEndSet;
        firstTime = false;
        return(numVertexWithMoreThan2Degree == 0);
    }
    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        vertexNum = in.nextInt();
        neighborCount = new int[vertexNum + 1];
        longestPathArray = new int[vertexNum + 1];      
        secondLongestPathArray = new int[vertexNum + 1];               
        terminalVertexNum = in.nextInt();
        for (int i = 1; i < vertexNum + 1; i++) {
            HashMap<Integer, Integer> newMap = new HashMap<Integer, Integer>();
            edgeMap.put(i, newMap);
        }
        for (int i = 0; i < terminalVertexNum; i++) {
            int newTarget = in.nextInt();
            targetSet.add(newTarget);
        }
        for (int i = 0; i < vertexNum - 1; i++) {
            int source = in.nextInt();
            int dest = in.nextInt();
            int weight = in.nextInt();
            HashMap<Integer, Integer> sourceMap = edgeMap.get(source);
            sourceMap.put(dest, weight);
            edgeMap.put(source, sourceMap);
            sourceMap = edgeMap.get(dest);
            sourceMap.put(source, weight);
            edgeMap.put(dest, sourceMap);
            neighborCount[source]++;
            neighborCount[dest]++;
        }
        for (int i = 1; i < vertexNum + 1; i++) {
            if (neighborCount[i] == 1) {
                leafSet.add(i);
            }
            if (neighborCount[i] > 2) {
               numVertexWithMoreThan2Degree++; 
            }
        }
        //Completely Linearize the graph
        while(true) {
            if (simplifyGraph()) break;
        }
        simplifyLinearGraph();
        calculateCost();
        trimPath();
        for (int i = 0; i < longestPathArray.length; i++) {
           // System.out.println(longestPathArray[i] - secondLongestPathArray[i]);
        }


    }
}
