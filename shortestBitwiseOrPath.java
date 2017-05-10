import java.io.*;
import java.util.*;

public class Solution {
    public static HashSet<Integer> vertexSet = new HashSet<Integer>();
    public static HashMap<Integer, HashMap<Integer, HashSet<Integer>>> edgeMap = new HashMap<Integer, HashMap<Integer, HashSet<Integer>>>();
    public static int vertexCount = 0;
    // My Standard Binary Heap Implementation with decrease Key
    public static class BinaryMinHeapImpl<V, Key extends Comparable<Key>> {
	private int size = 1;
	private ArrayList<Entry<V, Key>> heap = new ArrayList<Entry<V, Key>>();
	private HashMap<V, Entry<V, Key>> map = new HashMap<V, Entry<V, Key>>();

    public int size() {
        return size - 1;
    }

    public boolean isEmpty() {
    	if (size == 1) return true;
    	else return false;
    }

    public boolean containsValue(V value) {
       if (map.containsKey(value)) return true;
       else return false;
        
    }   
    private void swim(int currentPos) {
    	 boolean correctPlace = false;
         while(!correctPlace && currentPos > 0) {
           int index = 0; 
      	   if (currentPos % 2 == 0) {
      		   index = currentPos / 2;
      		   if (index == 0) break;
      	   }
      	   else {
      		 index = (currentPos - 1) / 2;
      		 if (index == 0) break;
      	   }
      	   Key toComp =  heap.get(index).getKey();
      	   Key current = heap.get(currentPos).getKey();
      	   if (toComp.compareTo(current) == 1) {   
      		  Entry first =  heap.get(index);
      		  Entry second =  heap.get(currentPos);
      		  heap.remove(index);
      		  heap.add(index, second);
      		  second.setPos(index);
      		  heap.remove(currentPos);
      		  heap.add(currentPos, first);
      		  first.setPos(currentPos);
      		  currentPos = index;
      	   }
      	   else correctPlace = true;
         }
    }

    public void add(V value, Key key) {
    	if (size == 1) heap.add(0, null);
    	if (key == null) {
    		throw new IllegalArgumentException("");
    	}
    	Entry toAdd = new Entry(value, key);
    	toAdd.setPos(size);
    	heap.add(size, toAdd);
    	map.put(value, toAdd);
    	swim(size);
    	size++;
    }

    public void decreaseKey(V value, Key newKey) {
    	if (!map.containsKey(value)) {
    		throw new NoSuchElementException("");
    	}
        Entry current = map.get(value);
        Key currKey =  (Key) current.getKey();
        if (newKey.compareTo(currKey) == 1) {
        	throw new IllegalArgumentException("");
        }
        int pos = current.getPos();
        Entry newEntry = new Entry(value, newKey);
        newEntry.setPos(pos);
        map.put(value, newEntry);
        heap.remove(pos);
        heap.add(pos, newEntry);
        swim(pos);
    }

    public V peek() {       
        return heap.get(1).value;
    }
    
    public Key topVal() {   	
    	return heap.get(1).key;
    }
    
    private void sink() {
    	boolean found = false;
    	int tracker = 1;
    	Entry top = heap.get(1);
    	Key comp = (Key) top.getKey();
    	while (!found) {
    	if (2 * tracker >= size) {
    		found = true;
    		}
    	else if (2 * tracker + 1 >= size) {
    	Key k1 = heap.get(2 * tracker).key;	
    	if (comp.compareTo(k1) == 1) {
			Entry smaller = heap.get(2 * tracker);
			heap.remove(2 * tracker);
			top.setPos(2 * tracker);
			heap.add(2 * tracker, top);
			smaller.setPos(tracker);
			heap.remove(tracker);
			heap.add(tracker, smaller);
			tracker = 2 * tracker;   			
			}
    	else found = true;
    	}
    	else {
    		Key k1 = heap.get(2 * tracker).key;
    		Key k2 = heap.get(2 * tracker + 1).key;
    		if (comp.compareTo(k1) == 1 && comp.compareTo(k2) == 1) {
    			int dest = 0;
    			if (k1.compareTo(k2) == 1) {
    				dest = 2 * tracker + 1;
    				}
    			else dest = 2 * tracker;
    			Entry smaller = heap.get(dest);
    			heap.remove(dest);
    			top.setPos(dest);
    			heap.add(dest, top);
    			smaller.setPos(tracker);
    			heap.remove(tracker);
    			heap.add(tracker, smaller);
    			tracker = dest;  	   			
    			}
    		else if (comp.compareTo(k1) == 1) {
    			Entry smaller = heap.get(2 * tracker);
    			heap.remove(2 * tracker);
    			top.setPos(2 * tracker);
    			heap.add(2 * tracker, top);
    			smaller.setPos(tracker);
    			heap.remove(tracker);
    			heap.add(tracker, smaller);
    			tracker = 2 * tracker;   			
    			}
    		else if (comp.compareTo(k2) == 1) {
    			Entry smaller = heap.get(2 * tracker + 1);
    			heap.remove(2 * tracker + 1);
    			top.setPos(2 * tracker + 1);
    			heap.add(2 * tracker + 1, top);
    			smaller.setPos(tracker);
    			heap.remove(tracker);
    			heap.add(tracker, smaller);
    			tracker = 2 * tracker + 1;   	
    			}
    		else found = true;
    		}
    	}
    }

    public V extractMin() {
       if (size == 1) {
    	   throw new NoSuchElementException("");
       }
       if (size == 2) {
    	   Entry min = heap.get(1);
    	   heap.remove(1);
    	   map.remove(min.value);
    	   size = size - 1;
    	   return (V) min.value;
       }
       Entry last = heap.get(size - 1);
       Entry min = heap.get(1);
       V val = (V) min.getValue();
       map.remove(val);
       heap.remove(1);
       heap.add(1, last);
       heap.remove(size - 1);
       size--;
       sink();
       
       return val;
    }

    public Set<V> values() {
       Set<V> setz = new HashSet<V>();
       int last = size - 1;
       while (last > 0) {
    	   Entry ent = heap.get(last);
    	   V val = (V) ent.value;
    	   setz.add(val);
    	   last--;
       }
        return Collections.unmodifiableSet(setz);
    }

    class Entry<A, B> {
        private A value;
        private B key;
        private int pos;

        public Entry(A value, B key) {
            this.value = value;
            this.key = key;
        }
        public A getValue() {
            return this.value;
        }
        public B getKey() {
            return this.key;
        }    
        public int getPos() {
        	return pos;
        }
        public void setPos(int newpos) {
        	pos = newpos;
        }  
        public B setKey(B key) {
            B oldKey = this.key;
            this.key = key;
            return oldKey;
        }
    }
}
    
    public static int dijkstraWithOrCostFunction(int sourceVertex, int destinationVertex) {
        int[] edgeFromArray = new int[vertexCount + 1];
        boolean[] vertexSeenArray = new boolean[vertexCount + 1];
        int[] distanceToArray = new int[vertexCount + 1];
        for (int i = 0; i <= vertexCount; i++) {
            distanceToArray[i] = Integer.MAX_VALUE;
        }
        distanceToArray[sourceVertex] = 0;
        boolean destinationReached = false;
        int currentVertex = sourceVertex;
        vertexSeenArray[sourceVertex] = true;
        BinaryMinHeapImpl heap = new BinaryMinHeapImpl();
        while (!destinationReached) {
            HashMap<Integer, HashSet<Integer>> neighborMap = edgeMap.get(currentVertex);
            Set<Integer> neighborSet = neighborMap.keySet();
            Iterator<Integer> neighborIterator = neighborSet.iterator();
            while (neighborIterator.hasNext()) {
                int neighbor = neighborIterator.next();
                int currentMin = Integer.MAX_VALUE;
                Set<Integer> weightSet = neighborMap.get(neighbor);
                Iterator<Integer> weightIterator = weightSet.iterator();
                while (weightIterator.hasNext()) {
                    int weight = weightIterator.next();
                    if ((distanceToArray[currentVertex] | weight) < currentMin) {
                        currentMin = (distanceToArray[currentVertex] | weight);
                    }
                }
                if (!vertexSeenArray[neighbor]) {
                    heap.add(neighbor, currentMin);
                    distanceToArray[neighbor] = currentMin;
                    vertexSeenArray[neighbor] = true;     
                    edgeFromArray[neighbor] = currentVertex;
                }
                else  {
                    if (distanceToArray[neighbor] > currentMin) {
                        distanceToArray[neighbor] = currentMin;
                        heap.add(neighbor, currentMin);
                        edgeFromArray[neighbor] = currentVertex;
                    }
                }
            }
            if (heap.size() == 0) break;
            currentVertex = (int) heap.extractMin();
            if (currentVertex == destinationVertex) break;
        }
        if (distanceToArray[destinationVertex] == Integer.MAX_VALUE) return -1;
        return distanceToArray[destinationVertex];
    }
    
    public static boolean checkBits(int source, int targetBit) {
        while (targetBit > 1) {
            source = source >> 1;
            targetBit--;
        }
        if ((source & 1) == 1) return true;
        else return false;
    }
    
    public static boolean isConnectedWithMaximimumEdgeBounded(int targetBit, int sourceVertex, int targetVertex) {
        //Map modification to get rid of all edges larger than specified weight
        HashMap<Integer, HashMap<Integer, HashSet<Integer>>> tempEdgeMap = new  HashMap<Integer,             HashMap<Integer, HashSet<Integer>>>();
        Set<Integer> keySet = edgeMap.keySet();
        Iterator<Integer> keyIterator = keySet.iterator();
        while (keyIterator.hasNext()) {
            int currentSource = keyIterator.next();
            HashMap<Integer, HashSet<Integer>> sourceEdgeMap = edgeMap.get(currentSource);
            HashMap<Integer, HashSet<Integer>> newSourceEdgeMap = new  HashMap<Integer, HashSet<Integer>>();
            Set<Integer> destinationSet = sourceEdgeMap.keySet();
            Iterator<Integer> destinationIterator = destinationSet.iterator();
            while (destinationIterator.hasNext()) {
                int currentDestination = destinationIterator.next();
                HashSet<Integer> weightSet = sourceEdgeMap.get(currentDestination);
                HashSet<Integer> newWeightSet =  new HashSet<Integer>();
                Iterator<Integer> weightIterator = weightSet.iterator();
                while (weightIterator.hasNext()) {
                    int currentWeight = weightIterator.next();
                    if (!checkBits(currentWeight, targetBit)) {
                        newWeightSet.add(currentWeight);
                    }
                }
                newSourceEdgeMap.put(currentDestination, newWeightSet);
            }
            tempEdgeMap.put(currentSource, newSourceEdgeMap);
        }

        //BFS Search for connectivity
        int[] edgeFromArray = new int[vertexCount + 1];
        boolean[] vertexSeenArray = new boolean[vertexCount + 1];
        boolean destinationFound = false;
        int currentVertex = sourceVertex;
        Queue<Integer> vertexQueue = new ArrayDeque<Integer>();
        while (!destinationFound) {
            HashMap<Integer, HashSet<Integer>> neighborMap = tempEdgeMap.get(currentVertex);
            Set<Integer> neighborSet = neighborMap.keySet();
            Iterator<Integer> neighborIterator = neighborSet.iterator();
            while (neighborIterator.hasNext()) {
                int neighbor = neighborIterator.next();
                if (neighbor == targetVertex && neighborMap.get(neighbor).size() != 0) {
                    destinationFound = true;
                    break;
                }
                if (!vertexSeenArray[neighbor] && neighborMap.get(neighbor).size() != 0) {
                    vertexSeenArray[neighbor] = true;
                    edgeFromArray[neighbor] = currentVertex;
                    vertexQueue.add(neighbor);
                }
            }
            if (vertexQueue.size() == 0) break;
            currentVertex = vertexQueue.poll();
        }
        if (destinationFound) edgeMap = tempEdgeMap;
        return destinationFound;
    }
    
    
    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        vertexCount = in.nextInt();
        int edgeCount = in.nextInt();
        int maximumWeight = 0;
        for (int i = 1; i <= vertexCount; i++) {
            vertexSet.add(i);
            HashMap<Integer, HashSet<Integer>> map = new  HashMap<Integer, HashSet<Integer>>();
            edgeMap.put(i, map);
        }
        for (int i = 0; i < edgeCount; i++) {
            int sourceVertex = in.nextInt();
            int destinationVertex = in.nextInt();
            int weight = in.nextInt();
            if (weight > maximumWeight) {
                maximumWeight = weight;
            }
            HashMap<Integer, HashSet<Integer>> sourceEdgeMap = edgeMap.get(sourceVertex);
            HashSet<Integer> sourceToDestinationWeightSet;
            if (sourceEdgeMap.containsKey(destinationVertex)) {
                 sourceToDestinationWeightSet = sourceEdgeMap.get(destinationVertex);
            }
            else {
                sourceToDestinationWeightSet = new HashSet<Integer>();
            }
            sourceToDestinationWeightSet.add(weight);
            sourceEdgeMap.put(destinationVertex, sourceToDestinationWeightSet);
            edgeMap.put(sourceVertex, sourceEdgeMap);
            
            HashMap<Integer, HashSet<Integer>> destinationEdgeMap = edgeMap.get(destinationVertex);
            HashSet<Integer> destinationToSourceWeightSet;
            if (destinationEdgeMap.containsKey(sourceVertex)) {
                 destinationToSourceWeightSet = destinationEdgeMap.get(sourceVertex);
            }
            else {
                destinationToSourceWeightSet = new HashSet<Integer>();
            }
            destinationToSourceWeightSet.add(weight);
            destinationEdgeMap.put(sourceVertex, destinationToSourceWeightSet);
            edgeMap.put(destinationVertex, destinationEdgeMap);
        }
        int sourceVertex = in.nextInt();
        int destinationVertex = in.nextInt();
        int firstTrial = 1;
        boolean minimumValueFound = false;
        int var = 1;
        while (var < maximumWeight) {
            firstTrial++;
            var = var * 2;
        }
        while (firstTrial > 0) {
            isConnectedWithMaximimumEdgeBounded(firstTrial, sourceVertex, destinationVertex);
            firstTrial--;
        }
        System.out.println(dijkstraWithOrCostFunction(sourceVertex, destinationVertex));
    }
}
