import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class BOJ_14500_테트로미노 {

    private static int N, M;
    private static int[][] dirs = {{-1, 0}, {0, 1}, {1, 0}, {0, -1}};
    private static int[][] grid;
    private static boolean[][] isVisited;

    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());

        N = Integer.parseInt(st.nextToken());
        M = Integer.parseInt(st.nextToken());
        grid = new int[N][M];
        isVisited = new boolean[N][M];

        for(int i=0; i<N; ++i) {
            st = new StringTokenizer(br.readLine());
            for(int j=0; j<M; ++j) {
                grid[i][j] = Integer.parseInt(st.nextToken());
            }
        }

        int answer = 0;
        for(int i=0; i<N; ++i) {
            for(int j=0; j<M; ++j) {
                isVisited[i][j] = true;
                answer = Math.max(answer, getMaxValue(i, j, 1, grid[i][j]));
                isVisited[i][j] = false;
                answer = Math.max(answer, getMaxNonContinuousBlock(i, j));
            }
        }
        System.out.println(answer);
    }

    private static boolean isValid(int y, int x) {
        return (y>=0 && y<N) && (x>=0 && x<M);
    }

    private static int getMaxValue(int y, int x, int cnt, int cost) {
        if(cnt == 4) return cost;
        int ret = 0;
        for(int[] dir: dirs) {
            int ny = y+dir[0];
            int nx = x+dir[1];
            if(!isValid(ny, nx) || isVisited[ny][nx]) continue;
            isVisited[ny][nx] = true;
            ret = Math.max(ret, getMaxValue(ny, nx, cnt+1, cost+grid[ny][nx]));
            isVisited[ny][nx] = false;
        }
        return ret;
    }

    private static int getMaxNonContinuousBlock(int y, int x) {
        int ret = 0;

        if(isValid(y-1, x-1) && isValid(y-1, x+1)) {
            ret = Math.max(ret, grid[y][x]+grid[y-1][x-1]+grid[y-1][x]+grid[y-1][x+1]);
        }

        if(isValid(y+1, x-1) && isValid(y+1, x+1)) {
            ret = Math.max(ret, grid[y][x]+grid[y+1][x-1]+grid[y+1][x]+grid[y+1][x+1]);
        }

        if(isValid(y-1, x-1) && isValid(y+1, x-1)) {
            ret = Math.max(ret, grid[y][x]+grid[y-1][x-1]+grid[y][x-1]+grid[y+1][x-1]);
        }

        if(isValid(y-1, x+1) && isValid(y+1, x+1)) {
            ret = Math.max(ret, grid[y][x]+grid[y-1][x+1]+grid[y][x+1]+grid[y+1][x+1]);
        }
        return ret;
    }
}
