import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.StringTokenizer;
 
//운영 비용 = k * k + (k - 1) * (k - 1)
public class SW_2117_홈_방범_서비스 {
    static int N, M;
    static int K;
    static int[][] graph;
    static List<int[]> houses;
     
    public static void main(String args[]) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringBuilder sb = new StringBuilder();
        int T = Integer.parseInt(br.readLine());
         
        for(int test_case = 1; test_case <= T; test_case++) {
            houses = new LinkedList<>();
            inputValues(br);
            getHouseLocations();
            sb.append("#").append(test_case).append(" ").append(getMaxHouseCnt()).append("\n");
        }
        System.out.print(sb);
    }
     
    private static void inputValues(BufferedReader br) throws Exception {
        StringTokenizer st = new StringTokenizer(br.readLine());
        N = Integer.parseInt(st.nextToken());
        M = Integer.parseInt(st.nextToken());
         
        graph = new int[N][];
        K = N + 1;
        for(int i = 0; i < N; i++) {
            graph[i] = Arrays.stream(br.readLine().split(" "))
                    .mapToInt(Integer::parseInt)
                    .toArray();
        }
    }
     
    private static void getHouseLocations() {
        for(int i = 0; i < N; i++) {
            for(int j = 0; j < N; j++) {
                if(graph[i][j] == 1) houses.add(new int[] {i, j});
            }
        }
    }
     
    private static int getMaxHouseCnt() {
        int answer = 0;
        for(int i = 0; i < N; i++) {
            for(int j = 0; j < N; j++) {
                for(int k = K; k > 0; k--) {
                    answer = Math.max(answer, getManhattanDistance(i, j, k));
                }
            }
        }
        return answer;
    }
     
    private static int getManhattanDistance(int x, int y, int k) {
        int companyPrice = k * k + (k - 1) * (k - 1);
        int distance, housePrice;
        int cnt = 0;
        for(int[] location : houses) {
            distance = Math.abs(x - location[0]) + Math.abs(y - location[1]);
            if(distance < k) cnt++;
        }
         
        housePrice = M * cnt;
        if((housePrice - companyPrice) >= 0) return cnt;
        return 0;
    }
}