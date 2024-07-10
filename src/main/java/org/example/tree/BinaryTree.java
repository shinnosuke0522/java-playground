package org.example.tree;

import lombok.NonNull;
import org.example.tree.data.Node;

public class BinaryTree {
    private final Node<Integer> nodeTreeRoot;

    public BinaryTree(@NonNull Node<Integer> nodeTreeRoot) {
        assert nodeTreeRoot.value() != null;
        this.nodeTreeRoot = nodeTreeRoot;
    }

    public BinaryTree insert(int val) {
        if (nodeTreeRoot.value() == val) {
            throw new IllegalArgumentException();
        }
        var nodes =  insertNode(nodeTreeRoot, val);
        return new BinaryTree(nodes);
    }
    private Node<Integer> insertNode(
            Node<Integer> node1,
            @NonNull Integer val
    ) {
        if (node1 == null) {
            return Node.init(val);
        }
        if (node1.value() < val) {
            return new Node<>(node1.value(), insertNode(node1.right(), val), node1.left());
        }
        if (val < node1.value()) {
            return new Node<>(node1.value(), node1.right(), insertNode(node1.left(), val));
        }
        throw new IllegalArgumentException();
    }

    public boolean search(int val) {
        return searchNode(nodeTreeRoot, val);
    }

    public boolean searchNode(Node<Integer> n, int target) {
        if (n == null) return false;
        if (n.value() == target) return true;
        if (n.value() < target) return searchNode(n.right(), target);
        return searchNode(n.left(), target);
    }

    public String print() {
        return printHelper(nodeTreeRoot);
    }

    private String printHelper(Node<Integer> n) {
        var sb = new StringBuilder();
        if (n == null) {
            return sb.toString();
        }
        sb.append(printHelper(n.left()));
        sb.append(n.value());
        sb.append(printHelper(n.right()));
        return sb.toString();
    }

    public static void main(String[] args) {
        var tree =
                new BinaryTree(Node.init(5))
                        .insert(1)
                        .insert(9)
                        .insert(2)
                        .insert(7)
                        .insert(3)
                        .insert(6)
                        .insert(4);
        System.out.println(tree.print());
        System.out.println(tree.search(6));
        System.out.println(tree.search(10));
    }


}
