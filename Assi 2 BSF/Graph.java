import java.util.*;

class Vertex {
    String name;
    int distance;
    Vertex predecessor;
    String color;

    public Vertex(String name) {
        this.name = name;
        this.distance = Integer.MAX_VALUE;
        this.predecessor = null;
        this.color = "WHITE";
    }

    @Override
    public String toString() {
        return "Vertex{" +
                "name='" + name + '\'' +
                ", distance=" + distance +
                ", predecessor=" + (predecessor != null ? predecessor.name : "NIL") +
                ", color='" + color + '\'' +
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
    private Map<Vertex, List<Edge>> adjList;
    private boolean isDirected;

    public Graph(List<String> vertexNames, List<String[]> edges, boolean isDirected) {
        this.isDirected = isDirected;
        vertices = new HashMap<>();
        adjList = new HashMap<>();

        for (String name : vertexNames) {
            Vertex vertex = new Vertex(name);
            vertices.put(name, vertex);
            adjList.put(vertex, new ArrayList<>());
        }

        for (String[] edge : edges) {
            addEdge(edge[0], edge[1], 1); // default weight as 1 for unweighted graph
        }
    }

    public void addEdge(String startName, String endName, int weight) {
        Vertex start = vertices.get(startName);
        Vertex end = vertices.get(endName);

        if (start != null && end != null) {
            adjList.get(start).add(new Edge(start, end, weight));
            if (!isDirected) {
                adjList.get(end).add(new Edge(end, start, weight));
            }
        }
    }

    public void bfs(String startName) {
        if (!vertices.containsKey(startName)) {
            System.out.println("Vertex not found");
            return;
        }

        // Initialize vertices
        for (Vertex v : vertices.values()) {
            v.color = "WHITE";
            v.distance = Integer.MAX_VALUE;
            v.predecessor = null;
        }

        Vertex start = vertices.get(startName);
        start.color = "GRAY";
        start.distance = 0;
        start.predecessor = null;

        Queue<Vertex> queue = new LinkedList<>();
        queue.add(start);

        System.out.println("Starting BFS from vertex: " + start.name);

        while (!queue.isEmpty()) {
            Vertex u = queue.poll();
            System.out.println("Visiting: " + u.name);

            for (Edge edge : adjList.get(u)) {
                Vertex v = edge.end;
                if (v.color.equals("WHITE")) {
                    v.color = "GRAY";
                    v.distance = u.distance + 1;
                    v.predecessor = u;
                    queue.add(v);
                }
            }
            u.color = "BLACK";
        }

        System.out.println("\nBFS Traversal Result:");
        for (Vertex v : vertices.values()) {
            System.out.println(v);
        }
    }

    public void printGraph() {
        System.out.println("\nGraph adjacency list:");
        for (Vertex vertex : adjList.keySet()) {
            System.out.print(vertex.name + " -> ");
            for (Edge edge : adjList.get(vertex)) {
                System.out.print(edge.end.name + "(" + edge.weight + ") ");
            }
            System.out.println();
        }
    }

    public static void main(String[] args) {
        List<String> vertexNames = Arrays.asList("A", "B", "C", "D", "E","S","N");
        List<String[]> edges = Arrays.asList(
            new String[]{"A", "B"},
            new String[]{"A", "S"},
            new String[]{"B", "C"},
            new String[]{"D", "C"},
            new String[]{"S", "N"},
            new String[]{"B", "D"},
            new String[]{"E", "B"},
            new String[]{"E", "A"},
            new String[]{"D", "E"}
        );

        Graph graph = new Graph(vertexNames, edges, false);

        graph.printGraph();

        graph.bfs("A");
    }
}
