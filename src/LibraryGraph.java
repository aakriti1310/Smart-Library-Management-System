import java.util.*;

/** Demonstrates graph representation, BFS, DFS and Dijkstra's algorithm. */
public class LibraryGraph {
    private final Map<String, List<Edge>> graph = new LinkedHashMap<>();

    private static class Edge {
        String to; int weight;
        Edge(String to, int weight) { this.to = to; this.weight = weight; }
    }

    public LibraryGraph() {
        addUndirected("Fiction", "Mystery", 2);
        addUndirected("Fiction", "Romance", 3);
        addUndirected("Technology", "Education", 2);
        addUndirected("Education", "Biography", 4);
        addUndirected("Biography", "Fiction", 5);
    }

    private void addUndirected(String a, String b, int weight) {
        graph.computeIfAbsent(a, k -> new ArrayList<>()).add(new Edge(b, weight));
        graph.computeIfAbsent(b, k -> new ArrayList<>()).add(new Edge(a, weight));
    }

    public void breadthFirst(String start) {
        if (!graph.containsKey(start)) { System.out.println("Category not found."); return; }
        Set<String> visited = new LinkedHashSet<>();
        Queue<String> queue = new ArrayDeque<>();
        queue.add(start); visited.add(start);
        while (!queue.isEmpty()) {
            String current = queue.remove();
            System.out.print(current + " ");
            for (Edge e : graph.get(current))
                if (visited.add(e.to)) queue.add(e.to);
        }
        System.out.println();
    }

    public void depthFirst(String start) {
        if (!graph.containsKey(start)) { System.out.println("Category not found."); return; }
        Set<String> visited = new LinkedHashSet<>();
        dfs(start, visited);
        System.out.println();
    }

    private void dfs(String node, Set<String> visited) {
        visited.add(node);
        System.out.print(node + " ");
        for (Edge e : graph.get(node))
            if (!visited.contains(e.to)) dfs(e.to, visited);
    }

    public void shortestPaths(String start) {
        if (!graph.containsKey(start)) { System.out.println("Category not found."); return; }
        Map<String, Integer> distance = new HashMap<>();
        for (String v : graph.keySet()) distance.put(v, Integer.MAX_VALUE);
        distance.put(start, 0);
        PriorityQueue<String> pq = new PriorityQueue<>(Comparator.comparingInt(distance::get));
        pq.add(start);
        Set<String> done = new HashSet<>();
        while (!pq.isEmpty()) {
            String u = pq.poll();
            if (!done.add(u)) continue;
            for (Edge e : graph.get(u)) {
                if (distance.get(u) != Integer.MAX_VALUE && distance.get(u) + e.weight < distance.get(e.to)) {
                    distance.put(e.to, distance.get(u) + e.weight);
                    pq.add(e.to);
                }
            }
        }
        System.out.println("Minimum category-link distances from " + start + ":");
        for (String v : graph.keySet())
            System.out.println("  " + v + " : " + (distance.get(v) == Integer.MAX_VALUE ? "unreachable" : distance.get(v)));
    }
}
