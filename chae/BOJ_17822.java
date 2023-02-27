import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

// 골드 2
// 원판 돌리기
public class BOJ_17822 {
    static int n, m, t;
    static int[][] circles;
    static boolean check = false;
    static boolean[][] visited;
    static Queue<Integer> q = new LinkedList<>();

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        int[] nmt = Arrays.stream(br.readLine().split(" ")).mapToInt(Integer::parseInt).toArray();

        n = nmt[0];     // 원판 개수
        m = nmt[1];     // 원판에 숫자 개수
        t = nmt[2];     // 회전 수

        // 원판 생성 후 입력 받아 저장
        circles = new int[n][m];
        for (int i = 0; i < n; i++) {
            circles[i] = Arrays.stream(br.readLine().split(" ")).mapToInt(Integer::parseInt).toArray();
        }

        // 회전정보 입력 받아 rotate함수에 돌리기
        for (int i = 0; i < t; i++) {
            int[] data = Arrays.stream(br.readLine().split(" ")).mapToInt(Integer::parseInt).toArray();
            for (int k = 1; (k * data[0]) - 1 < n; k++) {
                rotate((k * data[0]) - 1, data[1], data[2]);
            }

            check = false;

            // 같은 수 찾아서 모두 지우기
            for (int r = 0; r < n; r++) {
                for (int c = 0; c < m; c++) {
                    visited = new boolean[n][m];
                    findSameNumber(r, c);

                    // 큐에 들어있는 인덱스의 값 0으로 바꾸기
                    while (!q.isEmpty()) {
                        int index = q.poll();
                        int a = index / m;
                        int b = index % m;

                        circles[a][b] = 0;
                    }
                }
            }

            // 같은 수가 하나도 없을 경우
            if (!check) {
                // 전체의 평균을 구해서 평균보다 크면 -1 작으면 +1
                handleByAvg();
            }

        }
        System.out.println(sumAllNumbers());
    }

    // 회전정보 파라미터로 받아 회전 시키기
    private static void rotate(int circle, int dir, int point) {
        if (dir == 0) {     // 시계방향일 경우
            for (int r = 0; r < point % m; r++) {
                int temp = circles[circle][m - 1];
                for (int i = m - 1; i > 0; i--) {
                    circles[circle][i] = circles[circle][i - 1];
                }
                circles[circle][0] = temp;

            }
        } else if (dir == 1) {     // 반시계방향일 경우
            for (int r = 0; r < point % m; r++) {
                int temp = circles[circle][0];
                for (int i = 0; i < m - 1; i++) {
                    circles[circle][i] = circles[circle][i + 1];
                }
                circles[circle][m - 1] = temp;

            }
        }
    }

    // 모든 원판을 돌며 같은 수 찾아 처리하기
    // 같은 원판의 시작값과 끝값 비교하기
    private static void findSameNumber(int r, int c) {
        int[] dr = {0, 0, 1, -1};
        int[] dc = {1, -1, 0, 0};

        if (circles[r][c] == 0) return;

        for (int d = 0; d < 4; d++) {
            int nextR = r + dr[d];
            int nextC = c + dc[d];

            // 좌우 연결
            if (nextC == -1) nextC = m-1;
            else if (nextC == m) nextC = 0;

            // 다음 값과 같으면 큐에 추가
            if (isPossibleToCompare(nextR, nextC)) {
                if (circles[r][c] == circles[nextR][nextC]) {
                    check = true;
                    visited[r][c] = true;
                    q.add(r*m + c);
                    findSameNumber(nextR, nextC);
                    q.add(nextR*m + nextC);
                }

            }
        }
    }

    // 비교가능한 인덱스 인지 확인
    private static boolean isPossibleToCompare(int r, int c) {
        if (r >= 0 && r < n && c >= 0 && c < m && circles[r][c] != 0 && !visited[r][c]) {
            return true;
        }
        return false;
    }

    // 평균 값 구해서 수 처리하기
    private static void handleByAvg() {
        double avg = 0.0;
        int zeroCount = 0;
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < m; j++) {
                if (circles[i][j] == 0) {
                    zeroCount++;
                    continue;
                }
                avg += circles[i][j];
            }
        }
        avg /= (m*n - zeroCount);

        // 평균보다 크면 -1, 작으면 +1
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < m; j++) {
                if (circles[i][j] != 0) {
                    if (circles[i][j] > avg) {  // 수가 평균보다 클 경우
                        circles[i][j]--;
                    }else if (circles[i][j] < avg) {    // 수가 평균보다 작을 경우
                        circles[i][j]++;
                    }
                }
            }
        }
    }

    // 수 합 구하기
    private static int sumAllNumbers() {
        int sum = 0;
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < m; j++) {
                sum += circles[i][j];
            }
        }
        return sum;
    }

}
