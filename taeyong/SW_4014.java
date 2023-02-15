import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.StringTokenizer;
 
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.StringTokenizer;


/**
 * 개선하다가 망한 코드;
 */
public class SW_4014 {
    static int[][] graph;
    static int N, X;
    static int cnt;
    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        int T = Integer.parseInt(br.readLine());

        for (int i = 1; i <= T; i++) {
            initializeGraph(br); // 입력값 받고 그래프 초기화

            // 가로 확인
            cnt = 0;
            int width = getCountWidthAirstrip();
            cnt = 0;
            int length = getCountLengthAirstrip();
            System.out.printf("#%d %d\n", i, width + length);
        }
    }

    public static void initializeGraph(BufferedReader br) throws Exception {
        StringTokenizer st;
        st = new StringTokenizer(br.readLine());
        N = Integer.parseInt(st.nextToken());
        X = Integer.parseInt(st.nextToken());

        graph = new int[N][N];
        for (int i = 0; i < N; i++) {
            st = new StringTokenizer(br.readLine());
            for (int j = 0; j < N; j++) {
                graph[i][j] = Integer.parseInt(st.nextToken());
            }
        }
    }

    // 1. 경사로는 높이 차이가 1이어야함
    // 2. 높이 차이가 1인 구간이 X와 동일해야 함
    public static int getCountWidthAirstrip() {
        for (int i = 0; i < N; i++) {
            int start = 0,  end; // 투포인터 활용
            for (end = 1; end <= N; end++) {
                if (end == N) { // 끝에 도착했다면?
                    cnt++;
                    break;
                }
                if (graph[i][start] == graph[i][end]) { // 이전과 다음 높이가 같다면?
                    continue;
                }
                if (Math.abs(graph[i][end] - graph[i][start]) >= 2) { // 높이 차이가 2 이상 난다면?
                    break;
                }

                if(graph[i][start] < graph[i][end]) { // end부분 높이가 1 높다면?
                    if (end - X >= start && canMakeBridgeWidthUp(end - X, i)) { // 다리를 놓을 수 있다면
                        start = end;
                        continue;
                    }
                    break;
                }

                if(graph[i][start] > graph[i][end]) { // start 부분 높이가 1 높다면?
                    if(canMakeBridgeWidthDown(end, i)) {
                        end = end + X - 1;
                        start = end + 1;
                        if(start == N - 1) start--;
                        continue;
                    }
                    break;
                }
            }
        }
        return cnt;
    }

    public static int getCountLengthAirstrip() {
        for (int i = 0; i < N; i++) {
            int start = 0,  end; // 투포인터 활용
            for (end = 1; end <= N; end++) {
                if (end == N) { // 끝에 도착했다면?
                    cnt++;
                    break;
                }
                if (graph[start][i] == graph[end][i]) { // 이전과 다음 높이가 같다면?
                    continue;
                }
                if (Math.abs(graph[end][i] - graph[start][i]) >= 2) { // 높이 차이가 2 이상 난다면?
                    break;
                }

                if(graph[start][i] < graph[end][i]) { // end부분 높이가 1 높다면?
                    if (end - X >= start && canMakeBridgeLengthUp(end - X, i)) { // 다리를 놓을 수 있다면
                        start = end;
                        continue;
                    }
                    break;
                }

                if(graph[start][i] > graph[end][i]) { // start 부분 높이가 1 높다면?
                    if(canMakeBridgeLengthDown(end, i)) {
                        end = end + X - 1;
                        start = end + 1;
                        if(start == N - 1) start--;
                        continue;
                    }
                    break;
                }
            }
        }
        return cnt;
    }

    public static boolean canMakeBridgeWidthUp(int start, int i) {
        for (int j = start; j < start + X; j++) {
            if(graph[i][start] != graph[i][j]) {
                return false;
            }
        }
        return true;
    }

    public static boolean canMakeBridgeLengthUp(int start, int i) {
        for (int j = start; j < start + X; j++) {
            if (graph[start][i] != graph[j][i]) {
                return false;
            }
        }
        return true;
    }

    public static boolean canMakeBridgeWidthDown(int end, int i) {
        for (int j = end; j < end + X; j++) {
            if(j >= N || graph[i][end] != graph[i][j]) { // Index 범위 벗어난다면?
                return false;
            }
        }
        return true;
    }

    public static boolean canMakeBridgeLengthDown(int end, int i) {
        for (int j = end; j < end + X; j++) {
            if(j >= N || graph[end][i] != graph[j][i]) { // Index 범위 벗어난다면?
                return false;
            }
        }
        return true;
    }
}

/**
 * 처음에 실패한 코드 테케 40개 -> 겹치는 부분을 해결하지 못함
 * 
 */
// public class Solution {
//     static int[][] graph;
//     static int N, X;
//     static int cnt;
//     public static void main(String[] args) throws Exception {
//         BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
 
//         int T = Integer.parseInt(br.readLine());
 
//         for (int i = 1; i <= T; i++) {
//             initializeGraph(br); // 입력값 받고 그래프 초기화
 
//             // 가로 확인
//             cnt = 0;
//             int width = getCountWidthAirstrip();
//             cnt = 0;
//             int length = getCountLengthAirstrip();
//             System.out.printf("#%d %d\n", i, width + length);
//         }
//     }
 
//     public static void initializeGraph(BufferedReader br) throws Exception {
//         StringTokenizer st;
//         st = new StringTokenizer(br.readLine());
//         N = Integer.parseInt(st.nextToken());
//         X = Integer.parseInt(st.nextToken());
 
//         graph = new int[N][N];
//         for (int i = 0; i < N; i++) {
//             st = new StringTokenizer(br.readLine());
//             for (int j = 0; j < N; j++) {
//                 graph[i][j] = Integer.parseInt(st.nextToken());
//             }
//         }
//     }
 
//     // 1. 경사로는 높이 차이가 1이어야함
//     // 2. 높이 차이가 1인 구간이 X와 동일해야 함
//     public static int getCountWidthAirstrip() {
//         for (int i = 0; i < N; i++) {
//             int start = 0,  end = 0; // 투포인터 활용
//             while(start <= end) {
//                 end++;
//                 if(end == N) { // 종료 조건
//                     cnt++;
//                     break;
//                 }
//                 if(graph[i][end] == graph[i][start]) { // end를 한 칸 움직였을 때 start와 같은 높이라면? continue
//                     continue;
//                 }
//                 if (Math.abs(graph[i][end] - graph[i][start]) >= 2) { // 높이 차이가 2이상이라면?
//                     break;
//                 }
//                 if(graph[i][start] < graph[i][end]) { // 높이 차가 발생했는데 end부분이 더 높다면?
//                     if(canMakeBridgeWidthUp(start, i)) {
//                         start = end;
//                         continue;
//                     }
//                     break;
//                 }else if(graph[i][start] > graph[i][end]) { // 높이 차가 발생했는데 start부분이 더 높다면?
//                     if(canMakeBridgeWidthDown(end, i)) {
//                         end = end + X - 1;
//                         start = end;
//                         continue;
//                     }
//                     break;
//                 }
//             }
//         }
//         return cnt;
//     }
 
//     public static int getCountLengthAirstrip() {
//         for (int i = 0; i < N; i++) {
//             int start = 0,  end = 0; // 투포인터 활용
//             while(start <= end) {
//                 end++;
//                 if(end == N) { // 종료 조건
//                     cnt++;
//                     break;
//                 }
//                 if(graph[end][i] == graph[start][i]) { // end를 한 칸 움직였을 때 start와 같은 높이라면? continue
//                     continue;
//                 }
//                 if (Math.abs(graph[end][i] - graph[start][i]) >= 2) { // 높이 차이가 2이상이라면?
//                     break;
//                 }
//                 if(graph[start][i] < graph[end][i]) { // 높이 차가 발생했는데 end부분이 더 높다면?
//                     if(canMakeBridgeLengthUp(start, i)) {
//                         start = end;
//                         continue;
//                     }
//                     break;
//                 }else if(graph[start][i] > graph[end][i]) { // 높이 차가 발생했는데 start부분이 더 높다면?
//                     if(canMakeBridgeLengthDown(end, i)) {
//                         end = end + X - 1;
//                         start = end;
//                         continue;
//                     }
//                     break;
//                 }
//             }
//         }
//         return cnt;
//     }
//     public static boolean canMakeBridgeWidthUp(int start, int i) {
//         for (int j = start; j < start + X; j++) {
//             if(graph[i][start] != graph[i][j]) {
//                 return false;
//             }
//         }
//         return true;
//     }
 
//     public static boolean canMakeBridgeWidthDown(int end, int i) {
//         for (int j = end; j < end + X; j++) {
//             if(j >= N || graph[i][end] != graph[i][j]) { // Index 범위 벗어난다면?
//                 return false;
//             }
//         }
//         return true;
//     }
 
//     public static boolean canMakeBridgeLengthUp(int start, int i) {
//         for (int j = start; j < start + X; j++) {
//             if(graph[start][i] != graph[j][i]) {
//                 return false;
//             }
//         }
//         return true;
//     }
 
//     public static boolean canMakeBridgeLengthDown(int end, int i) {
//         for (int j = end; j < end + X; j++) {
//             if(j >= N || graph[end][i] != graph[j][i]) { // Index 범위 벗어난다면?
//                 return false;
//             }
//         }
//         return true;
//     }
// }