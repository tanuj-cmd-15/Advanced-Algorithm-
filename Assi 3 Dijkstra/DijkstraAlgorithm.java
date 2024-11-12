import java.util.*;

class Vertex {
    int d; 
    Vertex pi; 
    String color; 
    String name; 
    
    public Vertex(String name, int d, Vertex pi, String color) {
        this.name = name;
        this.d = d;
        this.pi = pi;
        this.color = color;
    }
    
    @Override
    public String toString() {
        String parentName = (pi != null) ? pi.name : "null";
        return "(" + d + "," + parentName + "," + color + ")";
    }
}

class Edge {
    String destination; 
    int wt; 
    
    public Edge(String destination, int wt) {
        this.destination = destination;
        this.wt = wt;
    }
    
    @Override
    public String toString() {
        return "(" + destination + "," + wt + ")";
    }
}

class Graph {
    Map<String, Vertex> v; 
    Map<String, List<Edge>> adj; 
    boolean isDirected;

    public Graph(List<String> vertices, List<String[]> edges, boolean isDirected) {
        this.adj = new HashMap<>();
        this.v = new HashMap<>();
        this.isDirected = isDirected;

        for (String vertexName : vertices) {
            v.put(vertexName, new Vertex(vertexName, Integer.MAX_VALUE, null, "white"));
            adj.put(vertexName, new ArrayList<>());
        }

        for (String[] edge : edges) {
            addEdge(edge[0], edge[1], Integer.parseInt(edge[2]));
        }
    }

    public void addEdge(String start, String end, int weight) {
        adj.get(start).add(new Edge(end, weight));
        if (!isDirected) {
            adj.get(end).add(new Edge(start, weight)); 
        }
    }

    public void initializeSingleSource(String s) {
        for (Vertex vertex : v.values()) {
            vertex.d = Integer.MAX_VALUE;
            vertex.pi = null;
            vertex.color = "white";
        }
        v.get(s).d = 0;
    }

    public boolean relax(String u, String v, int w) {
        if (this.v.get(v).d > this.v.get(u).d + w) {
            this.v.get(v).d = this.v.get(u).d + w;
            this.v.get(v).pi = this.v.get(u);
            return true;
        }
        return false;
    }

    public void dijkstra(String s) {
        initializeSingleSource(s);
        
        PriorityQueue<String> pq = new PriorityQueue<>(Comparator.comparingInt(v -> this.v.get(v).d));
        pq.add(s);
        
        while (!pq.isEmpty()) {
            String u = pq.poll();
            this.v.get(u).color = "black";
            
            for (Edge edge : adj.get(u)) {
                String neighbor = edge.destination;
                int weight = edge.wt;
                
                if (relax(u, neighbor, weight)) {
                    pq.add(neighbor);
                }
            }
        }
    }

    public void printGraph() {
        for (String vertex : v.keySet()) {
            System.out.println(vertex + " -> " + v.get(vertex));
        }
    }
}

public class DijkstraAlgorithm {
    public static void main(String[] args) {
        List<String> vertices = Arrays.asList("A", "B", "C", "D", "E");
        
        List<String[]> edges = Arrays.asList(
            new String[] {"A", "B", "4"},
            new String[] {"A", "C", "2"},
            new String[] {"B", "C", "5"},
            new String[] {"B", "D", "10"},
            new String[] {"C", "E", "3"},
            new String[] {"E", "D", "2"},
            new String[] {"D", "E", "1"}
        );
        
        Graph g = new Graph(vertices, edges, true);
    
        g.dijkstra("A");
        
        g.printGraph();
    }
}
