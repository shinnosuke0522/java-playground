package org.example.atcoder;

import java.util.HashSet;
import java.util.List;
import java.util.Scanner;
import java.util.Set;
import java.util.Stack;

/**
 * https://atcoder.jp/contests/arc031/tasks/arc031_2
 */
public class DFS {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        char[][] map = new char[10][10];
        for (int i = 0; i < 10; i++) {
            map[i] = sc.nextLine().toCharArray();
        }
        var dfs = new DFS(map);
        dfs.ans();
    }

    record Point(int x, int y) {}

    private static final List<Point> DIRECTIONS = List.of(
            new Point(0, -1),  // 上
            new Point(0, 1),   // 下
            new Point(-1, 0),  // 左
            new Point(1, 0)    // 右
    );
    private static final int MAP_HEIGHT = 10;
    private static final int MAP_WIDTH = 10;
    private final char[][] map;
    private final int landCount;

    public DFS(char[][] map) {
        this.map = map;

        var tmpLandCount = 0;
        for (char[] row : map) {
            for (char col : row) {
                if (col == 'o') {
                    tmpLandCount++;
                }
            }
        }
        this.landCount = tmpLandCount;
    }

    public void ans() {
        for (int y = 0; y < MAP_HEIGHT; y++) {
            for (int x = 0; x < MAP_WIDTH; x++) {
                // 陸地を作成
                char[][] copyMap = copyMap(this.map);
                if (copyMap[y][x] == 'o') {
                    continue;
                }
                copyMap[y][x] = 'o';

                //陸地の数を計算
                var tmpLandCount = countLand(copyMap);
                if (tmpLandCount == landCount + 1) {
                    System.out.println("YES");
                    return;
                }
            }
        }
        System.out.println("NO");
    }

    private char[][] copyMap(char[][] map) {
        var copyMap = new char[MAP_HEIGHT][MAP_WIDTH];
        for (int i = 0; i < map.length; i++) {
            copyMap[i] = map[i].clone();
        }
        return copyMap;
    }

    private int countLand(char[][] map) {
        Point land = findLand(map);
        if (land == null) {
            return 0;
        }
        return countLandWithDfs(land, map);
    }

    private Point findLand(char[][] map) {
        for (int i = 0; i < MAP_HEIGHT; i++) {
            for (int j = 0; j < MAP_WIDTH; j++) {
                if (map[i][j] == 'o') {
                    return new Point(j, i);
                }
            }
        }
        return null;
    }

    private int countLandWithDfs(Point point, char[][] map) {
        Set<Point> visited = new HashSet<>();
        visited.add(point);

        Stack<Point> stack = new Stack<>();
        stack.push(point);
        while (!stack.isEmpty()) {
            Point current = stack.pop();
            for (Point direction : DIRECTIONS) {
                Point neighbor = new Point(current.x + direction.x, current.y + direction.y);
                // 探索済みか
                if (visited.contains(neighbor)) {
                    continue;
                }
                // マップの外か
                if (neighbor.x < 0 || MAP_WIDTH <= neighbor.x
                        || neighbor.y < 0 || MAP_HEIGHT <= neighbor.y) {
                    continue;
                }
                // 海かどうか
                if (map[neighbor.y][neighbor.x] != 'o') {
                    continue;
                }
                visited.add(neighbor);
                stack.push(neighbor);
            }
        }
        return visited.size();
    }
}
