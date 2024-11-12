import java.util.*;

class Vertex {
    String name;
    int discoveryTime;
    int finishingTime;
    Vertex predecessor;
    String color;

    public Vertex(String name) {
        this.name = name;
        this.discoveryTime = -1;
        this.finishingTime = -1;
        this.predecessor = null;
        this.color = "WHITE";
    }

    @Override
    public String toString() {
        return "Vertex{" +
                "name='" + name + '\'' +
                ", discoveryTime=" + discoveryTime +
                ", finishingTime=" + finishingTime +
                ", predecessor=" + (predecessor != null ? predecessor.name : "NIL") +
                ", color='" + color + '\'' +
                '}';
    }
}

class Edge {
    Vertex start;
    Vertex end;

    public Edge(Vertex start, Vertex end) {
        this.start = start;
        this.end = end;
    }

    @Override
    public String toString() {
        return "Edge{" +
                "start=" + start.name +
                ", end=" + end.name +
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
            addEdge(edge[0], edge[1]);
        }
    }

    public void addEdge(String startName, String endName) {
        Vertex start = vertices.get(startName);
        Vertex end = vertices.get(endName);

        if (start != null && end != null) {
            adjList.get(start).add(new Edge(start, end));
            if (!isDirected) {
                adjList.get(end).add(new Edge(end, start));
            }
        }
    }

    public void dfs() {
        for (Vertex v : vertices.values()) {
            v.color = "WHITE";
            v.discoveryTime = -1;
            v.finishingTime = -1;
            v.predecessor = null;
        }

        int[] time = {0}; 

        for (Vertex u : vertices.values()) {
            if (u.color.equals("WHITE")) {
                dfsVisit(u, time);
            }
        }

        System.out.println("\nDFS Traversal Result:");
        for (Vertex v : vertices.values()) {
            System.out.println(v);
        }
    }

    private void dfsVisit(Vertex u, int[] time) {
        time[0]++;
        u.discoveryTime = time[0];
        u.color = "GRAY";
        System.out.println("Visiting: " + u.name + " at time " + u.discoveryTime);

        for (Edge edge : adjList.get(u)) {
            Vertex v = edge.end;
            if (v.color.equals("WHITE")) {
                v.predecessor = u;
                dfsVisit(v, time);
            }
        }

        u.color = "BLACK";
        time[0]++;
        u.finishingTime = time[0];
        System.out.println("Finishing: " + u.name + " at time " + u.finishingTime);
    }

    public void printGraph() {
        System.out.println("\nGraph adjacency list:");
        for (Vertex vertex : adjList.keySet()) {
            System.out.print(vertex.name + " -> ");
            for (Edge edge : adjList.get(vertex)) {
                System.out.print(edge.end.name + " ");
            }
            System.out.println();
        }
    }

    public static void main(String[] args) {
        List<String> vertexNames = Arrays.asList("A", "B", "C", "D", "E","F","I","J");
        List<String[]> edges = Arrays.asList(
            new String[]{"A", "B"},
            new String[]{"B", "C"},
            new String[]{"D", "C"},
            new String[]{"B", "D"},
            new String[]{"E", "B"},
            new String[]{"E", "A"},
            new String[]{"D", "E"},
            new String[]{"C", "F"},
            new String[]{"I", "J"},
            new String[]{"I", "A"} 
        );

        Graph graph = new Graph(vertexNames, edges, true);  

        graph.printGraph();
        graph.dfs();  
    }
}
