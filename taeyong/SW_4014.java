import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.StringTokenizer;
 
 
 
public class Solution {
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
 
    public static int getCountWidthAirstrip() {
        for (int i = 0; i < N; i++) {
            int start = 0,  end; // 투포인터 활용
            int prevHeight = graph[i][start];
            
            for (end = 1; end <= N; end++) {
                if (end == N) { // 끝에 도착했다면?
                    cnt++;
                    break;
                }
                if (prevHeight == graph[i][end]) { // 이전과 다음 높이가 같다면?
                    continue;
                }
                if (Math.abs(graph[i][end] - prevHeight) >= 2) { // 높이 차이가 2 이상 난다면?
                    break;
                }
 
                if(prevHeight < graph[i][end]) { // end부분 높이가 1 높다면?
                    if (end - X >= start && canMakeBridgeWidthUp(end - X, i)) { // 다리를 놓을 수 있다면
                        start = end;
                        prevHeight = graph[i][end];
                        continue;
                    }
                    break;
                }
 
                if(prevHeight > graph[i][end]) { // start 부분 높이가 1 높다면?
                    if(canMakeBridgeWidthDown(end, i)) {
                        prevHeight = graph[i][end];
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
            int prevHeight = graph[start][i];
                     
            for (end = 1; end <= N; end++) {
                if (end == N) { // 끝에 도착했다면?
                    cnt++;
                    break;
                }
                if (prevHeight == graph[end][i]) { // 이전과 다음 높이가 같다면?
                    continue;
                }
                if (Math.abs(graph[end][i] - prevHeight) >= 2) { // 높이 차이가 2 이상 난다면?
                    break;
                }
 
                if(prevHeight < graph[end][i]) { // end부분 높이가 1 높다면?
                    if (end - X >= start && canMakeBridgeLengthUp(end - X, i)) { // 다리를 놓을 수 있다면
                        prevHeight =  graph[end][i];
                        start = end;
                        continue;
                    }
                    break;
                }
 
                if(prevHeight > graph[end][i]) { // start 부분 높이가 1 높다면?
                    if(canMakeBridgeLengthDown(end, i)) {
                        prevHeight =  graph[end][i];
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