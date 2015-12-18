import java.util.List;
import java.util.Map;
import java.util.HashSet;
import java.util.PriorityQueue;
import java.util.ArrayList;
import java.util.HashMap;
import java.io.File;
import java.util.Scanner;
import java.io.FileNotFoundException;
import java.util.Set;
import java.io.PrintWriter;
/**
 * Your implementations of various graph search algorithms.
 *
 * @author Liubov Nikolenko
 * @version 1.0
 */
public class GraphSearch {
    //private static List path;
    /**
     * Find the shortest distance between the start node and the goal node
     * given a weighted graph in the form of an adjacency list where the
     * edges only have positive weights. If there are no adjacent nodes for
     * a node, then an empty list is present.
     *
     * Return the aforementioned shortest distance if there exists a path
     * between the start and goal, -1 otherwise.
     *
     * There are guaranteed to be no negative edge weights in the graph.
     *
     * You may import/use {@code java.util.PriorityQueue}.
     *
     * @throws IllegalArgumentException if any input is null, or if
     * {@code start} or {@code goal} doesn't exist in the graph
     * @param start the object representing the node you are starting at.
     * @param adjList the adjacency list that represents the graph we are
     *        searching.
     * @param goal the object representing the node we are trying to reach.
     * @param <T> the data type representing the nodes in the graph.
     * @return the shortest distance between the start and the goal node
     */
    public static <T> VertexDistancePair<T> dijkstraShortestPathAlgorithm(T start,
            Map<T, List<VertexDistancePair<T>>> adjList, T goal) {
        if (start == null || adjList == null || goal == null) {
            throw new IllegalArgumentException("One or more agruments are"
            + "null");
        }
        if (!adjList.containsKey(start)) {
            throw new IllegalArgumentException("The starting vertex is not"
            + "in the graph");
        }
        if (!adjList.containsKey(goal)) {
            throw new IllegalArgumentException("The goal vertex is not in"
            + "the graph");
        }
        HashSet<T> visited = new HashSet<>();
        PriorityQueue<VertexDistancePair<T>> struct = new PriorityQueue<>();
        struct.add(new VertexDistancePair<T>(start, 0));
        while (!struct.isEmpty()) {
            //pick out the vertex with minimum distance
            VertexDistancePair<T> vertex = struct.poll();
            if (vertex.getVertex().equals(goal)) {
                //path = new ArrayList<>();
                //VertexDistancePair<T> current = vertex;
                //back-tracking to find the path
                /**
                while (current != null) {
                    path.add(0, current.getVertex());
                    current = current.getPrevious();
                }
                */
                return vertex;
            }
            if (!visited.contains(vertex.getVertex())) {
                int distance = vertex.getDistance();
                visited.add(vertex.getVertex());
                List<VertexDistancePair<T>> neighbors =
                    adjList.get(vertex.getVertex());
                for (VertexDistancePair<T> neighbor: neighbors) {
                    if (!visited.contains(neighbor.getVertex())) {
                        T newVertex = neighbor.getVertex();
                        int weight = neighbor.getDistance();
                        struct.add(new VertexDistancePair<T>(newVertex,
                            distance + weight, vertex));

                    }

                }

            }

        }
        return new VertexDistancePair<T>(null, -1);

    }
    public static void main(String[] args) throws FileNotFoundException {
        //recording the graph data from the txt filre to the memory
        HashMap<Integer, List<VertexDistancePair<Integer>>> adjList =
            new HashMap<>();
        for (int i = 0; i < 1600; i++) {
            adjList.put(i, new ArrayList<VertexDistancePair<Integer>>());
        }
        File file = new File("nikki.txt");
        Scanner sc = new Scanner(file);
        sc.nextLine();
        while (sc.hasNext()) {
            String input = sc.nextLine();
            String [] arr = input.split(" ");
            Integer vertex = new Integer(arr[0]);
            Integer endVertex = new Integer(arr[1]);
            Integer distance = new Integer(arr[2]);
            VertexDistancePair<Integer> pair =
                new VertexDistancePair<>(endVertex, distance);
            if (!adjList.containsKey(vertex)) {
                List<VertexDistancePair<Integer>> list =
                    new ArrayList<>();
                list.add(pair);
                adjList.put(vertex, list);
            } else {
                List<VertexDistancePair<Integer>> list =
                    adjList.get(vertex);
                list.add(pair);
                adjList.put(vertex, list);
            }
        }
        //System.out.println(adjList.get(3));
        //find the maximum weight of the paths
        int root  = 1;
        Set<Integer> set = adjList.keySet();
        int max = 0;
        int vert = 1;
        List path1 = null;
        for (int vertex: set) {
            int result = dijkstraShortestPathAlgorithm(root, adjList, vertex)
                .getDistance();
            if (result > max) {
                max = result;
            }
        }
        //output all the paths that have the maximum value into the cmd
        //and txt file
        PrintWriter writer = new PrintWriter("results.txt");
        for (int vertex: set) {
            VertexDistancePair<Integer> res =
                dijkstraShortestPathAlgorithm(root, adjList, vertex);
            int result = res.getDistance();
            List<Integer> path = new ArrayList<>();
            if (result == max) {
                System.out.println("distance " + max);
                System.out.println("vertex " + vertex);
                writer.println("vertex " + vertex);
                writer.println("distance " + max);
                VertexDistancePair<Integer> current = res;
                while (current != null) {
                    path.add(0, current.getVertex());
                    current = current.getPrevious();
                }
                System.out.println(path);
                writer.println(path);
            }
        }
        writer.close();

    }

}
