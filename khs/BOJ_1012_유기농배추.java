import java.util.LinkedList;
import java.util.Queue;
import java.util.Scanner;

public class BOJ_1012_유기농배추 {
    private static Scanner sc = new Scanner(System.in);
    private static final int[][] dirs = {{-1, 0}, {0, 1}, {1, 0}, {0, -1}};

    public static void main(String[] args) {
        int t = sc.nextInt();

        for(int tc=0; tc<t; ++tc) {
            System.out.println(solve(sc.nextInt(), sc.nextInt(), sc.nextInt()));
        }
        sc.close();
    }

    private static int solve(int m, int n, int k) {
        int ret = 0;
        int[][] board = new int[m][n];
        boolean[][] isVisited = new boolean[m][n];

        for(int i=0; i<k; ++i) {
            int y = sc.nextInt(), x = sc.nextInt();
            board[y][x] = 1;
        }

        for(int i=0; i<m; ++i) {
            for(int j=0; j<n; ++j) {
                if(board[i][j] == 1 && !isVisited[i][j]) {
                    bfs(board, isVisited, i, j);
                    ret++;
                }
            }
        }
        return ret;
    }

    private static void bfs(int[][] board, boolean[][] isVisited, int y, int x) {
        Queue<Pair> q = new LinkedList<>();
        q.add(new Pair(y, x));
        isVisited[y][x] = true;

        while(!q.isEmpty()) {
            Pair p = q.poll();

            for(int[] dir: dirs) {
                int ny = p.first + dir[0];
                int nx = p.second + dir[1];
                boolean isValid = (ny >=0 && ny < board.length) && (nx>=0 && nx<board[0].length);

                if(!isValid || isVisited[ny][nx] || board[ny][nx] != 1) continue;
                isVisited[ny][nx] = true;
                q.add(new Pair(ny, nx));
            }
        }
    }

    static class Pair{
        int first, second;
        public Pair() {}
        public Pair(int y, int x) {this.first=y; this.second=x;}
    }
}
