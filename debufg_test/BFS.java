package debufg_test;

import java.util.*;

public class BFS {
    public static void bfs(int[][] graph, int start) {
        boolean[] visited = new boolean[graph.length];
        Queue<Integer> queue = new LinkedList<>();
        
        visited[start] = true;
        queue.add(start);
        
        System.out.print("BFS Traversal: ");
        
        while (!queue.isEmpty()) {
            int v = queue.poll();
            System.out.print(v + " ");
            
            for (int i = 0; i < graph[v].length; i++) {
                if (graph[v][i] == 1 && !visited[i]) {
                    visited[i] = true;
                    queue.add(i);
                }
            }
        }
    }
    

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        System.out.print("Enter the number of vertices: ");
        int n = sc.nextInt();
        
        int[][] graph = new int[n][n];
        System.out.println("Enter the adjacency matrix:");
        for (int i = 0; i < n; i++)
            for (int j = 0; j < n; j++)
                graph[i][j] = sc.nextInt();
        
        System.out.print("Enter the starting vertex: ");
        int start = sc.nextInt();
        
        bfs(graph, start);
    }
}
/*
entrada de consola es Enter the number of vertices: 4
Enter the adjacency matrix:
0 1 0 1
1 0 1 0
0 1 0 1
1 0 1 0
Enter the starting vertex: 0
BFS Traversal: 0 1 3 2
 */