package org.example.graph;

import org.example.graph.data.Edge;
import org.example.graph.data.Vertex;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AdjacencyListGraphWithMap {
    private final Map<Vertex, List<Vertex>> adjacencyMap;

    public AdjacencyListGraphWithMap(final Collection<Edge> edges, final boolean isDirected) {
        var adjacencyMap = new HashMap<Vertex, List<Vertex>>();
        edges.forEach(edge -> {
            adjacencyMap.putIfAbsent(edge.source(), new ArrayList<>());
            adjacencyMap.putIfAbsent(edge.destination(), new ArrayList<>());
            adjacencyMap.get(edge.source()).add(edge.destination());

            // 無向グラフの場合、逆からも辿れるはずなので追加する
            if (!isDirected) {
                adjacencyMap.get(edge.destination()).add(edge.source());
            }
        });
        this.adjacencyMap = adjacencyMap;
    }

    public void print() {
        for (Map.Entry<Vertex, List<Vertex>> entry : adjacencyMap.entrySet()) {
            var sb = new StringBuilder();
            sb.append(entry.getKey().value());
            sb.append(" => ");
            for (Vertex vertex : entry.getValue()) {
                sb.append(vertex.value());
                sb.append(" => ");
            }
            System.out.println(sb.toString());
        }
    }

    public static void main(String[] args) {
        // グラフのエッジを定義
        Vertex vA = new Vertex("A");
        Vertex vB = new Vertex("B");
        Vertex vC = new Vertex("C");
        Vertex vD = new Vertex("D");
        Vertex vE = new Vertex("E");

        List<Edge> edges = new ArrayList<>();
        edges.add(new Edge(vA, vB));
        edges.add(new Edge(vB, vC));
        edges.add(new Edge(vB, vE));
        edges.add(new Edge(vC, vD));
        edges.add(new Edge(vC, vE));
        edges.add(new Edge(vE, vA));
        edges.add(new Edge(vE, vC));

        // 隣接リストのグラフを作成
        AdjacencyListGraphWithMap graph = new AdjacencyListGraphWithMap(edges, true);

        // 隣接リストを出力
        System.out.println("Adjacency List:");
        graph.print();
    }
}
