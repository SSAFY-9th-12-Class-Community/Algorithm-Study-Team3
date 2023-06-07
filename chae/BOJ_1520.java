import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;

// 골드3
// 내리막 길
public class BOJ_1520 {
    static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    static int[][] tabla;
    static int[][] dp;
    static int n, m;
    static int r, c;

    static int[] dr = {0, 0, 1, -1};
    static int[] dc = {1, -1, 0, 0};

    public static void main(String[] args) throws IOException {
        int[] nm = Arrays.stream(br.readLine().split(" ")).mapToInt(Integer::parseInt).toArray();
        n = nm[0];      // row
        m = nm[1];      // col
        tabla = new int[n][m];
        dp = new int[n][m];
        // 지도 저장
        for (int i = 0; i < n; i++) {
            tabla[i] = Arrays.stream(br.readLine().split(" ")).mapToInt(Integer::parseInt).toArray();
        }

        // 가본적 없는 곳은 -1
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < m; j++) {
                dp[i][j] = -1;
            }
        }

        dfs(0, 0);

        System.out.println(dp[0][0]);
    }

    private static int dfs(int x, int y) {
        if (x == n - 1 && y == m - 1){
            return 1;
        }

        // 가본 곳이면 경로 수 return
        if (dp[x][y] != -1) {
            return dp[x][y];
        }

        // 가본곳은 바로 0으로 체크
        dp[x][y] = 0;
        for (int d = 0; d < 4; d++) {
            int nextR = x + dr[d];
            int nextC = y + dc[d];

            if (isPossibleToMove(nextR, nextC, tabla[x][y])) {
                dp[x][y] += dfs(nextR, nextC);
            }
        }

        return dp[x][y];
    }

    // 유효한 인덱스이고, 현재값보다 작은곳일 경우 true
    private static boolean isPossibleToMove(int x, int y, int valor) {
        if (x >= 0 && x < n && y >= 0 && y < m && valor > tabla[x][y]) {
            return true;
        }
        return false;
    }
}
