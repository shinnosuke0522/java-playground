package org.example.graph;

import lombok.NonNull;

import java.util.Stack;
import java.util.Vector;

public class DepthFirstSearch {

    public void solutionWithStack(@NonNull TreeNode<Integer> root) {
        Stack<TreeNode<Integer>> stack = new Stack<>();
        stack.push(root);

        while (!stack.isEmpty()) {
            TreeNode<Integer> current = stack.pop();
            stack.push(current.left);
            if (current.right != null) {
                stack.push(current.right);
            }
            if (current.left != null) {
                stack.push(current.left);
            }
        }
    }

    public void solutionWithRecursion(@NonNull TreeNode<Integer> root) {

    }


    public record TreeNode<T>(
            @NonNull T current,
            DepthFirstSearch.TreeNode<T> right,
            DepthFirstSearch.TreeNode<T> left
    ) {
    }
}
