package org.example.tree.data;

public record Node<T>(T value, Node<T> right, Node<T> left) {

    public static <T> Node<T> init(T value) {
        return new Node<>(value, null, null);
    }

}
