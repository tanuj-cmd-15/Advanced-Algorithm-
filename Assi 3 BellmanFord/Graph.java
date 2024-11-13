import java.util.*;

class Vertex {
    String name;
    Vertex predecessor;
    int distance;

    public Vertex(String name) {
        this.name = name;
        this.predecessor = null;
        this.distance = Integer.MAX_VALUE; // Initialize distance to infinity
    }

    @Override
    public String toString() {
        return "Vertex{" +
                "name='" + name + '\'' +
                ", distance=" + (distance == Integer.MAX_VALUE ? "∞" : distance) +
                ", predecessor=" + (predecessor != null ? predecessor.name : "NIL") +
                '}';
    }
}

class Edge {
    Vertex start;
    Vertex end;
    int weight;

    public Edge(Vertex start, Vertex end, int weight) {
        this.start = start;
        this.end = end;
        this.weight = weight;
    }

    @Override
    public String toString() {
        return "Edge{" +
                "start=" + start.name +
                ", end=" + end.name +
                ", weight=" + weight +
                '}';
    }
}

class Graph {
    private Map<String, Vertex> vertices;
    private List<Edge> edges;

    public Graph(List<String> vertexNames, List<String[]> edgesData) {
        vertices = new HashMap<>();
        edges = new ArrayList<>();

        for (String name : vertexNames) {
            vertices.put(name, new Vertex(name));
        }

        for (String[] edgeData : edgesData) {
            addEdge(edgeData[0], edgeData[1], Integer.parseInt(edgeData[2]));
        }
    }

    public void addEdge(String startName, String endName, int weight) {
        Vertex start = vertices.get(startName);
        Vertex end = vertices.get(endName);

        if (start != null && end != null) {
            edges.add(new Edge(start, end, weight));
        }
    }

    private void initializeSingleSource(Vertex source) {
        for (Vertex vertex : vertices.values()) {
            vertex.distance = Integer.MAX_VALUE;
            vertex.predecessor = null;
        }
        source.distance = 0;
    }

    private boolean relax(Vertex u, Vertex v, int weight) {
        if (v.distance > u.distance + weight) {
            v.distance = u.distance + weight;
            v.predecessor = u;
            return true;
        }
        return false;
    }

    public boolean bellmanFord(String sourceName) {
        Vertex source = vertices.get(sourceName);
        if (source == null) return false;

        initializeSingleSource(source);

        for (int i = 1; i < vertices.size(); i++) {
            for (Edge edge : edges) {
                relax(edge.start, edge.end, edge.weight);
            }
        }

        for (Edge edge : edges) {
            if (edge.end.distance > edge.start.distance + edge.weight) {
                return false; // Negative weight cycle detected
            }
        }

        return true;
    }

    public void printShortestPaths() {
        for (Vertex vertex : vertices.values()) {
            System.out.println(vertex);
        }
    }

    public static void main(String[] args) {
        List<String> vertexNames = Arrays.asList("A", "B", "C", "D", "E");

        List<String[]> edgesData = Arrays.asList(
            new String[]{"A", "B", "4"},
            new String[]{"A", "C", "2"},
            new String[]{"B", "C", "5"},
            new String[]{"B", "D", "10"},
            new String[]{"C", "E", "3"},
            new String[]{"E", "D", "2"},
            new String[]{"D", "E", "-1"}
        );

        Graph graph = new Graph(vertexNames, edgesData);

        System.out.println("Running Bellman-Ford Algorithm from source A:");
        if (graph.bellmanFord("A")) {
            graph.printShortestPaths();
        } else {
            System.out.println("Graph contains a negative weight cycle.");
        }
    }
}
