import java.util.*;

class Graph {
    static class Edge {
        String start, end;
        int weight;

        Edge(String start, String end, int weight) {
            this.start = start;
            this.end = end;
            this.weight = weight;
        }
    }

    private Map<String, Integer> vertexMap;
    private List<Edge> edges;
    private Map<String, Integer> distance;
    private Map<String, String> predecessor;

    public Graph(List<String> vertices, List<Edge> edges) {
        this.vertexMap = new HashMap<>();
        this.edges = edges;
        this.distance = new HashMap<>();
        this.predecessor = new HashMap<>();

        for (int i = 0; i < vertices.size(); i++) {
            vertexMap.put(vertices.get(i), i);
            distance.put(vertices.get(i), Integer.MAX_VALUE);
            predecessor.put(vertices.get(i), "NIL");
        }
    }

    public boolean bellmanFord(String source) {
        distance.put(source, 0); 

        for (int i = 1; i < vertexMap.size(); i++) {
            for (Edge edge : edges) {
                if (distance.get(edge.start) != Integer.MAX_VALUE &&
                    distance.get(edge.start) + edge.weight < distance.get(edge.end)) {
                    distance.put(edge.end, distance.get(edge.start) + edge.weight);
                    predecessor.put(edge.end, edge.start);
                }
            }
        }

        for (Edge edge : edges) {
            if (distance.get(edge.start) != Integer.MAX_VALUE &&
                distance.get(edge.start) + edge.weight < distance.get(edge.end)) {
                return false; 
            }
        }

        return true;
    }

  
    public void printShortestPaths() {
        for (Map.Entry<String, Integer> entry : distance.entrySet()) {
            String vertex = entry.getKey();
            int dist = entry.getValue();
            String pred = predecessor.get(vertex);
            System.out.println(vertex + " (distance=" + (dist == Integer.MAX_VALUE ? "∞" : dist) +
                               ", predecessor=" + (pred.equals("NIL") ? "NIL" : pred) + ")");
        }
    }

   
    public static void main(String[] args) {
        List<String> vertices = Arrays.asList( "A", "B", "C", "D", "E");

        List<Edge> edges = Arrays.asList(
            new Edge("A", "B", 4), 
            new Edge("A", "C", 2),
            new Edge("B", "C", 5),
            new Edge("C", "E", 3),
            new Edge("B", "D", 10),
            new Edge("E", "D", 2),
            new Edge("D", "E", -1)    
        );

        Graph graph = new Graph(vertices, edges);

        System.out.println("Running Bellman-Ford Algorithm from source A:");
        if (graph.bellmanFord("A")) {
            graph.printShortestPaths();
        } else {
            System.out.println("Graph contains a negative weight cycle.");
        }
    }
}
