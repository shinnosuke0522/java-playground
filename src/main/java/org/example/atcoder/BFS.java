package org.example.atcoder;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.Scanner;
import java.util.Set;

public class BFS {
    record Point(int x, int y) {}

    private static final List<Point> DIRECTIONS = List.of(
            new Point(0, -1),  // 上
            new Point(0, 1),   // 下
            new Point(-1, 0),  // 左
            new Point(1, 0)    // 右
    );

    private final int mapHeight;
    private final int mapWidth;
    private final Point src;
    private final Point dst;
    private final char[][] map;

    public BFS(Point src, Point dst, char[][] map) {
        this.src = src;
        this.dst = dst;
        this.map = map;
        this.mapHeight = map.length;
        this.mapWidth = map[0].length;
    }

    public int ans() {
        Set<Point> visited = new HashSet<>();
        Queue<Point> queue = new LinkedList<>(); // 探索対象を保管する
        Queue<Integer> depthQueue = new LinkedList<>(); // 探索対象の深さを記録

        // start
        visited.add(src);
        queue.add(src);
        depthQueue.add(0);

        while (!queue.isEmpty()) {
            var current = queue.poll();
            var depth = depthQueue.poll();
            for(Point direction: DIRECTIONS) {
                var neighbours = new Point(current.x + direction.x, current.y + direction.y);
                // バリデーション
                if (neighbours.x < 0 || mapWidth <= neighbours.x
                    || neighbours.y < 0 || mapHeight <= neighbours.y) {
                    continue;
                }
                if (visited.contains(neighbours)) {
                    continue;
                }
                if (map[neighbours.y][neighbours.x] == '#') {
                    continue;
                }

                // ゴールなら終わり
                if (neighbours.equals(dst)) {
                    return depth + 1;
                }

                visited.add(neighbours);
                queue.add(neighbours);
                depthQueue.add(depth + 1);
            }
        }
        return 0;
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        var input1 = sc.nextLine().split(" ");
        var mapHeight = Integer.parseInt(input1[0]);
        var mapWidth = Integer.parseInt(input1[1]);

        var input2 = sc.nextLine().split(" ");
        var srcY = Integer.parseInt(input2[0]) - 1;
        var srcX = Integer.parseInt(input2[1]) - 1;
        var src = new Point(srcX, srcY);

        var input3 = sc.nextLine().split(" ");
        var dstY = Integer.parseInt(input3[0]) - 1;
        var dstX = Integer.parseInt(input3[1]) - 1;
        var dst = new Point(dstX, dstY);

        char[][] map = new char[mapHeight][mapWidth];
        for (int i = 0; i < mapHeight; i++) {
            map[i] = sc.nextLine().toCharArray();
        }

        var ans = new BFS(src, dst, map).ans();
        System.out.println(ans);
    }
}
