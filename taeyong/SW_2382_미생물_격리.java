import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.*;

public class SW_2382_미생물_격리 {
    static int[][] graph;
    static int N, M, K;
    static int[] dx = {0, -1, 1, 0, 0};
    static int[] dy = {0, 0, 0, -1, 1};
    static Map<Integer, int[]> visited;

    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringBuilder sb = new StringBuilder();
        int T = Integer.parseInt(br.readLine());

        for (int test_case = 1; test_case <= T; test_case++) {
            inputValues(br);
            moveByTime();
            sb.append("#").append(test_case).append(" ").append(getAllSumResult()).append("\n");
        }
        System.out.print(sb);
    }

    private static int getAllSumResult() {
        int answer = 0;
        for (int key : visited.keySet()) {
            answer += visited.get(key)[2];
        }
        return answer;
    }

    private static void moveByTime() {
        for (int i = 0; i < M; i++) {
            move();
        }
    }

    private static void move() {
        Map<Integer, List<int[]>> newMap = new HashMap<>();

        for (int key : visited.keySet()) {
            goByDirection(visited.get(key), newMap);
        }
        visited = checkConsistsOfTwo(newMap);
    }

    private static Map<Integer, int[]> checkConsistsOfTwo(Map<Integer, List<int[]>> newMap) {
        Map<Integer, int[]> newVisited = new HashMap<>();
        int[] association;
        for (int key : newMap.keySet()) {
            if(newMap.get(key).size() == 1) newVisited.put(key, newMap.get(key).get(0));
            else if(newMap.get(key).size() >= 2) {
                association = sumAssociations(newMap.get(key));
                newVisited.put(key, association);
            }
        }
        return newVisited;
    }

    private static int[] sumAssociations(List<int[]> associations) {
        int sumW = 0;
        int direct = 0;
        int maxSum = 0;
        int[] association = new int[0];
        while (associations.size() > 0) {
            association = associations.remove(0);

            sumW += association[2];
            if (association[2] > maxSum) {
                maxSum = association[2];
                direct = association[3];
            }
        }

        return new int[] {association[0], association[1], sumW, direct};
    }

    private static void goByDirection(int[] association, Map<Integer, List<int[]>> newMap) {
        List<int[]> temp;

        association[0] += dx[association[3]];
        association[1] += dy[association[3]];

        if (association[0] == 0 || association[0] == N - 1 || association[1] == 0 || association[1] == N - 1) { // 약품이 칠해진 곳이라면
            association[2] = association[2] / 2;
            if(association[2] == 0) return;

            if(association[3] == 1) association[3] = 2;
            else if(association[3] == 2) association[3] = 1;
            else if(association[3] == 3) association[3] = 4;
            else if(association[3] == 4) association[3] = 3;
        }

        if(newMap.containsKey(graph[association[0]][association[1]])) {
            temp = newMap.get(graph[association[0]][association[1]]);
            temp.add(association);
        }else {
            temp = new LinkedList<>();
            temp.add(association);
            newMap.put(graph[association[0]][association[1]], temp);
        }
    }

    private static void inputValues(BufferedReader br) throws Exception {
        StringTokenizer st = new StringTokenizer(br.readLine());
        N = Integer.parseInt(st.nextToken());
        M = Integer.parseInt(st.nextToken());
        K = Integer.parseInt(st.nextToken());
        graph = new int[N][N];
        visited = new HashMap<>();
        int[] association;

        initializeGraph();

        for (int i = 0; i < K; i++) { // 해시맵으로 군집 저장
            association = Arrays.stream(br.readLine().split(" "))
                    .mapToInt(Integer::parseInt)
                    .toArray();


            visited.put(graph[association[0]][association[1]], association);
        }
    }

    private static void initializeGraph() {
        int index = 0;
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                graph[i][j] = index++;
            }
        }
    }
}