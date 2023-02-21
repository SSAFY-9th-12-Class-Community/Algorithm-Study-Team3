import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;

// SW 역량테스트
// 홍 방범 서비스
public class SW_2117 {
    static int[][] map;
    static int[][] homes;
    static int homeCount;
    static int maxHome;
    static int n, m, k;


    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringBuilder sb = new StringBuilder();

        int TC = Integer.parseInt(br.readLine());

        for (int t = 1; t <= TC; t++) {
            int[] nm = Arrays.stream(br.readLine().split(" ")).mapToInt(Integer::parseInt).toArray();

            n = nm[0];
            m = nm[1];
            k = n + 2;

            map = new int[n][n];
            homes = new int[n * n][2];
            homeCount = 0;
            maxHome = 0;

            for (int i = 0; i < n; i++) {
                map[i] = Arrays.stream(br.readLine().split(" ")).mapToInt(Integer::parseInt).toArray();
            }

            // 집 위치 저장
            for (int i = 0; i < n; i++) {
                for (int j = 0; j < n; j++) {
                    if (map[i][j] == 1) {
                        homes[homeCount][0] = i;
                        homes[homeCount][1] = j;
                        homeCount++;
                    }
                }
            }

            while (k-- > 0) {
                for (int i = 0; i < n; i++) {
                    for (int j = 0; j < n; j++) {
                        int count = 0;

                        for (int c = 0; c < homeCount; c++) {
                            // 집 맨해튼 거리가 k보다 작을 경우
                            if (Math.abs(i - homes[c][0]) + Math.abs(j - homes[c][1]) < k) {
                                count++;
                            }
                        }
                        // 안손해일 경우
                        if (k * k + (k - 1) * (k - 1) <= count * m && maxHome < count) {
                            maxHome = count;
                        }

                    }
                }

                if (maxHome > 0) break;
            }

            sb.append("#").append(t).append(" ").append(maxHome).append("\n");
        }

        System.out.println(sb);
    }


}
