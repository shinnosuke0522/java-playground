package org.example.graph;

import org.example.graph.data.Vertex;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class AdjacencyListGraphWithLinkedList {
    private final List<LinkedList<Vertex>> edges;

    public AdjacencyListGraphWithLinkedList(final List<LinkedList<Vertex>> edges) {
        this.edges = edges;
    }

    public void print() {
        System.out.println("Adjacency List:");
        edges.forEach(edge -> {
            var sb = new StringBuilder();
            edge.forEach(vertex -> {
                sb.append(vertex.value()).append("=>");
            });
            System.out.println(sb.toString());
        });
    }

    public static void main(String[] args) {
        // グラフのエッジを定義
        Vertex vA = new Vertex("A");
        Vertex vB = new Vertex("B");
        Vertex vC = new Vertex("C");
        Vertex vD = new Vertex("D");
        Vertex vE = new Vertex("E");

        List<LinkedList<Vertex>> edges = new ArrayList<>();
        var edge1 = new LinkedList<Vertex>();
        edge1.add(vA);
        edge1.add(vB);
        edges.add(edge1);

        var edge2 = new LinkedList<Vertex>();
        edge2.add(vB);
        edge2.add(vC);
        edge2.add(vE);
        edges.add(edge2);

        var edge3 = new LinkedList<Vertex>();
        edge3.add(vC);
        edge3.add(vD);
        edge3.add(vE);
        edges.add(edge3);

        var edge4 = new LinkedList<Vertex>();
        edge4.add(vE);
        edge4.add(vC);
        edge4.add(vA);
        edges.add(edge4);

        // 隣接リストのグラフを作成
        var graph = new AdjacencyListGraphWithLinkedList(edges);

        // 隣接リストを出力
        System.out.println("Adjacency List:");
        graph.print();

    }

}
