import java.util.*;

public class App {
    private static double Infinity = Double.POSITIVE_INFINITY;

    private static HashMap<Character, List<Pair<Double, Character>>> makeGraph() {
        HashMap<Character, List<Pair<Double, Character>>> graph = new HashMap<>();
        graph.put('S', Arrays.asList(new Pair<>(8.0, 'E'), new Pair<>(10.0, 'A')));
        graph.put('A', Collections.singletonList(new Pair<>(2.0, 'C')));
        graph.put('B', Collections.singletonList(new Pair<>(1.0, 'A')));
        graph.put('C', Collections.singletonList(new Pair<>(-2.0, 'B')));
        graph.put('D', Arrays.asList(new Pair<>(-4.0, 'A'), new Pair<>(-1.0, 'C')));
        graph.put('E', Collections.singletonList(new Pair<>(1.0, 'D')));
        return graph;
    }

    private static HashMap<Character, List<Pair<Double, Character>>> makeGraphWithNegativeCycle() {
        HashMap<Character, List<Pair<Double, Character>>> graph = new HashMap<>();
        graph.put('S', Arrays.asList(new Pair<>(8.0, 'E'), new Pair<>(10.0, 'A')));
        graph.put('A', Collections.singletonList(new Pair<>(-2.0, 'C')));
        graph.put('B', Collections.singletonList(new Pair<>(1.0, 'A')));
        graph.put('C', Collections.singletonList(new Pair<>(-2.0, 'B')));
        graph.put('D', Arrays.asList(new Pair<>(-4.0, 'A'), new Pair<>(-1.0, 'C')));
        graph.put('E', Collections.singletonList(new Pair<>(1.0, 'D')));
        return graph;
    }

    private static HashMap<Character, Double> bellmanFord(HashMap<Character, List<Pair<Double, Character>>> graph, char start) {
        HashMap<Character, Double> shortestPaths = new HashMap<>();

        for (char node : graph.keySet()) {
            shortestPaths.put(node, Infinity);
        }

        shortestPaths.put(start, 0.0);
        int size = graph.size();

        for (int i = 0; i < size - 1; i++) {
            for (char node : graph.keySet()) {
                for (Pair<Double, Character> edge : graph.get(node)) {
                    double cost = edge.getKey();
                    char toNode = edge.getValue();
                    if (shortestPaths.get(node) + cost < shortestPaths.get(toNode)) {
                        shortestPaths.put(toNode, shortestPaths.get(node) + cost);
                    }
                }
            }
        }

        // Iterate once more and check for negative cycle
        for (char node : graph.keySet()) {
            for (Pair<Double, Character> edge : graph.get(node)) {
                double cost = edge.getKey();
                char toNode = edge.getValue();
                if (shortestPaths.get(node) + cost < shortestPaths.get(toNode)) {
                    throw new RuntimeException("INVALID - negative cycle detected");
                }
            }
        }

        return shortestPaths;
    }

    public static void main(String[] args) {
        char start = 'S';

        HashMap<Character, List<Pair<Double, Character>>> graph = makeGraph();
        HashMap<Character, Double> shortestPaths = bellmanFord(graph, start);
        System.out.println("Shortest path from " + start + ": " + shortestPaths);

        // HashMap<Character, List<Pair<Double, Character>>> graphWithNegativeCycle = makeGraphWithNegativeCycle();
        // try {
        //     HashMap<Character, Double> negativeCycle = bellmanFord(graphWithNegativeCycle, start);
        //     System.out.println("Shortest path from " + start + ": " + negativeCycle);
        // } catch (RuntimeException ex) {
        //     System.out.println(ex.getMessage());
        // }
    }

    private static class Pair<K, V> {
        private final K key;
        private final V value;

        public Pair(K key, V value) {
            this.key = key;
            this.value = value;
        }

        public K getKey() {
            return key;
        }

        public V getValue() {
            return value;
        }
    }
}
