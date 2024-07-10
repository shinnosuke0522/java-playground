package org.example.graph;

import org.example.graph.data.Edge;
import org.example.graph.data.Vertex;

import java.util.ArrayList;
import java.util.List;

public class AdjacencyMatrixGraph {
    private final List<Vertex> vertices;
    private final boolean[][] matrix;

    public AdjacencyMatrixGraph(List<Vertex> vertices, List<Edge> edges) {
        this.vertices = vertices;
        this.matrix = new boolean[vertices.size()][vertices.size()];
        edges.forEach(edge -> {
            if (!vertices.contains(edge.source()) || !vertices.contains(edge.destination())) {
                throw new IllegalArgumentException();
            }
            var sourceIndex = vertices.indexOf(edge.source());
            var destinationIndex = vertices.indexOf(edge.destination());
            matrix[sourceIndex][destinationIndex] = true;
        });
    }
    public void prints() {
        System.out.println("Adjacency Matrix");

        System.out.println(" " + String.join("", vertices.stream().map(Vertex::value).toList()));
        for (int i = 0; i < vertices.size(); i++) {
            var sb = new StringBuilder();
            sb.append(vertices.get(i).value());
            for (int j = 0; j < vertices.size(); j++) {
                if (matrix[i][j]) {
                    sb.append("1");
                } else {
                    sb.append("0");
                }
            }
            System.out.println(sb);
        }
    }

    public static void main(String[] args) {
        // グラフのエッジを定義
        Vertex vA = new Vertex("A");
        Vertex vB = new Vertex("B");
        Vertex vC = new Vertex("C");
        Vertex vD = new Vertex("D");
        Vertex vE = new Vertex("E");
        List<Vertex> vertices = List.of(vA, vB, vC, vD, vE);

        List<Edge> edges = new ArrayList<>();
        edges.add(new Edge(vA, vB));
        edges.add(new Edge(vB, vC));
        edges.add(new Edge(vB, vE));
        edges.add(new Edge(vC, vD));
        edges.add(new Edge(vC, vE));
        edges.add(new Edge(vE, vA));
        edges.add(new Edge(vE, vC));

        var matrix = new AdjacencyMatrixGraph(vertices, edges);
        matrix.prints();
    }
}
