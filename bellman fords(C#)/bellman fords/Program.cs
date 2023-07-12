using System;
using System.Collections.Generic;
using System.Linq;


namespace bellman_fords
{
    public class Graph
    {
        private static double Infinity = double.PositiveInfinity;

        private static Dictionary<char, List<(double, char)>> MakeGraph()
        {
            return new Dictionary<char, List<(double, char)>>
            {
                { 'S', new List<(double, char)> { (8, 'E'), (10, 'A') } },
                { 'A', new List<(double, char)> { (2, 'C') } },
                { 'B', new List<(double, char)> { (1, 'A') } },
                { 'C', new List<(double, char)> { (-2, 'B') } },
                { 'D', new List<(double, char)> { (-4, 'A'), (-1, 'C') } },
                { 'E', new List<(double, char)> { (1, 'D') } }
            };
        }

        private static Dictionary<char, List<(double, char)>> MakeGraphWithNegativeCycle()
        {
            return new Dictionary<char, List<(double, char)>>
            {
                { 'S', new List<(double, char)> { (8, 'E'), (10, 'A') } },
                { 'A', new List<(double, char)> { (-2, 'C') } },
                { 'B', new List<(double, char)> { (1, 'A') } },
                { 'C', new List<(double, char)> { (-2, 'B') } },
                { 'D', new List<(double, char)> { (-4, 'A'), (-1, 'C') } },
                { 'E', new List<(double, char)> { (1, 'D') } }
            };
        }

        private static Dictionary<char, double> BellmanFord(Dictionary<char, List<(double, char)>> graph, char start)
        {
            var shortestPaths = new Dictionary<char, double>();

            foreach (char node in graph.Keys)
            {
                shortestPaths[node] = Infinity;
            }

            shortestPaths[start] = 0;
            int size = graph.Count;

            for (int i = 0; i < size - 1; i++)
            {
                foreach (char node in graph.Keys)
                {
                    foreach ((double cost, char toNode) in graph[node])
                    {
                        if (shortestPaths[node] + cost < shortestPaths[toNode])
                        {
                            shortestPaths[toNode] = shortestPaths[node] + cost;
                        }
                    }
                }
            }

            // Iterate once more and check for negative cycle
            foreach (char node in graph.Keys)
            {
                foreach ((double cost, char toNode) in graph[node])
                {
                    if (shortestPaths[node] + cost < shortestPaths[toNode])
                    {
                        throw new Exception("INVALID - negative cycle detected");
                    }
                }
            }

            return shortestPaths;
        }

        public static void Main()
        {
            char start = 'S';

            var graph = MakeGraph();
            var shortestPaths = BellmanFord(graph, start);
            Console.WriteLine($"Shortest path from {start}: {string.Join(", ", shortestPaths)}");

            //var graphWithNegativeCycle = MakeGraphWithNegativeCycle();
            //try
            //{
            //    var negativeCycle = BellmanFord(graphWithNegativeCycle, start);
            //    Console.WriteLine($"Shortest path from {start}: {string.Join(", ", negativeCycle)}");
            //}
            //catch (Exception ex)
            //{
            //    Console.WriteLine(ex.Message);
            //}

            Console.ReadLine();
        }
    }
}