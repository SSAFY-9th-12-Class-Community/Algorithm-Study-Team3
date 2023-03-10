import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;

// 골드 4
// 테트로미노
public class BOJ_14500 {
    static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    static int[][] map;
    static boolean[][] visited;
    static int n, m;
    static int answer;

    static int[] dr = {-1, 1, 0, 0};    // 상 하 좌 우
    static int[] dc = {0, 0, -1, 1};

    public static void main(String[] args) throws IOException {
        int[] nm = Arrays.stream(br.readLine().split(" ")).mapToInt(Integer::parseInt).toArray();

        n = nm[0];
        m = nm[1];
        map = new int[n][m];
        visited = new boolean[n][m];

        for (int i = 0; i < n; i++) {
            map[i] = Arrays.stream(br.readLine().split(" ")).mapToInt(Integer::parseInt).toArray();
        }

        // 모든 좌표 돌면서 탐색
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < m; j++) {
                visited[i][j] = true;
                search(i, j, map[i][j], 1);
                visited[i][j] = false;
            }
        }

        System.out.println(answer);
    }

    // dfs로 모든 방향의 테트로미노 찾기
    private static void search(int r, int c, int sum, int count) {
        // 테트로미노 완성되면 끝내기
        if (count == 4) {
            answer = Math.max(answer, sum);
            return;
        }

        // 4방 탐색
        for (int d = 0; d < 4; d++) {
            int nextR = r + dr[d];
            int nextC = c + dc[d];

            // 이동 할 수 없는 곳이면 continue
            if (!isPossibleToSearch(nextR, nextC)) continue;

            // ㅗ모양 탐색
            if (count == 2) {
                visited[nextR][nextC] = true;
                // 현재 위치에서 한번 더 탐색
                search(r, c, sum + map[nextR][nextC], count + 1);
                visited[nextR][nextC] = false;
            }

            visited[nextR][nextC] = true;
            search(nextR, nextC, sum + map[nextR][nextC], count + 1);
            visited[nextR][nextC] = false;

        }
    }

    // map 유효성 체크
    private static boolean isPossibleToSearch(int r, int c) {
        if (r >= 0 && r < n && c >= 0 && c < m && !visited[r][c]) {
            return true;
        }
        return false;
    }
}
