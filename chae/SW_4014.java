import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;

// SW 역량테스트
// 활주로 건설
public class SW_4014 {
    static int[][] arr;
    static int n, count;
    static int[][] visited;
    static int check;
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringBuilder sb = new StringBuilder();

        int TC = Integer.parseInt(br.readLine());

        for (int t = 1; t <= TC; t++) {
            int[] nx = Arrays.stream(br.readLine().split(" ")).mapToInt(Integer::parseInt).toArray();

            n = nx[0];
            int x = nx[1];
            count = 0;

            arr = new int[n][n];
            for (int i = 0; i < n; i++) {
                arr[i] = Arrays.stream(br.readLine().split(" ")).mapToInt(Integer::parseInt).toArray();
            }

            // [0][0]에서 [0][n-2]까지 돌면서 위아래로 탐색
            for (int i = 0; i < n; i++) {
                check = 0;
                visited = new int[n][n];
                for (int j = 0; j < n - 1; j++) {
                    // 다음 거랑 비교
                    // 높이가 올라가면 내 뒤랑 높이가 같은지
                    // 높이가 낮아지면 그 앞이랑 높이가 같은지
                    if (arr[i][j] == arr[i][j+1]) {     // 높이가 같을 경우
                        continue;
                    } else if (arr[i][j] < arr[i][j+1]) {   // 높이가 올라갈 경우 -> 이전칸과 비교
                        if (isPossibleToInstallationLamp(x, i, j, 1, visited)){
                            continue;
                        }
//                        if (j-1 >= 0 && arr[i][j] == arr[i][j-1] && visited[j] == 0 && visited[j-1] == 0) {     // j-1이 0보다 크거나 같고, 이전 칸과 같은 높이일 경우
//                            visited[j] = 1;
//                            visited[j-1] = 1;
//                            continue;
//                        }
                        else {     // 이전 칸이 없거나 높이가 다를 경우
                            check = 1;
                            break;
                        }
                    } else if (arr[i][j] > arr[i][j+1]) {      // 높이가 내려갈 경우 -> 다음칸과 그 다음칸을 비교
                        if (isPossibleToInstallationLamp(x, i, j+1, 0, visited)){
                            continue;
                        }
//                        if (j+2 <= n-1 && arr[i][j+1] == arr[i][j+2] && visited[j+1] == 0 && visited[j+2] == 0) {     // 그 다음칸이 마지막칸보다 작거나 같고, 다음칸과 같은 높이일 경우
//                            visited[j+1] = 1;
//                            visited[j+2] = 1;
//                            continue;
//                        }
                        else {     // 그 다음 칸이 없거나 높이가 다를 경우
                            check = 1;
                            break;
                        }
                    }

                }
                if (check == 0) {
                    count++;
                }
            }

            // [0][0]에서 [n-2][0]까지 돌면서 좌우로 탐색
            for (int j = 0; j < n; j++) {
                check = 0;
                visited = new int[n][n];
                for (int i = 0; i < n - 1; i++) {
                    // 높이가 올라가면 내 뒤랑 높이가 같은지
                    // 높이가 낮아지면 그 앞이랑 높이가 같은지
                    if (arr[i][j] == arr[i+1][j]) {     // 높이가 같을 경우
                        continue;
                    }else if (arr[i][j] < arr[i+1][j]) {    // 높이가 올라갈 경우 -> 이전칸과 비교
                        if (isPossibleToInstallationLamp(x, i, j, 3, visited)){
                            continue;
                        }
//                        if (i-1 >= 0 && arr[i][j] == arr[i-1][j] && visited[i] == 0 && visited[i-1] == 0) {     // j-1이 0보다 크거나 같고, 이전 칸과 같은 높이일 경우
//                            visited[i] = 1;
//                            visited[i-1] = 1;
//                            continue;
//                        }
                        else {     // 이전 칸이 없거나 높이가 다를 경우
                            check = 1;
                            break;
                        }
                    }else if (arr[i][j] > arr[i+1][j]) {      // 높이가 내려갈 경우 -> 다음칸과 그 다음칸을 비교
                        if (isPossibleToInstallationLamp(x, i+1, j, 2, visited)){
                            continue;
                        }
//                        if (i+2 <= n-1 && arr[i+1][j] == arr[i+2][j] && visited[i+1] == 0 && visited[i+2] == 0) {     // 그 다음칸이 마지막칸보다 작거나 같고, 다음칸과 같은 높이일 경우
//                            visited[i+1] = 1;
//                            visited[i+2] = 1;
//                            continue;
//                        }
                        else {     // 그 다음 칸이 없거나 높이가 다를 경우
                            check = 1;
                            break;
                        }
                    }

                }
                if (check == 0) {
                    count++;
                }
            }

            sb.append("#").append(t).append(" ").append(count).append("\n");

        }
        System.out.println(sb);
    }

    private static boolean isPossibleToInstallationLamp(int count, int r, int c, int dir, int[][] visited) {
        int[] dr = {0, 0, 1, -1};   // 우 좌, 하, 상
        int[] dc = {1, -1, 0, 0};

        while (count-- > 1){
            if ((r+dr[dir]) < 0 || (r+dr[dir]) >= n
                    || (c+dc[dir]) < 0 || (c+dc[dir]) >= n
                    || arr[r][c] != arr[r+dr[dir]][c+dc[dir]]
                    || visited[r+dr[dir]][c+dc[dir]] == 1
                    || visited[r][c] == 1) {

                return false;
            }
            visited[r][c] = 1;
            r = r+dr[dir];
            c = c+dc[dir];
        }
        return true;
    }


}
