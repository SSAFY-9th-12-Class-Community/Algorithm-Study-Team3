import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.*;

// 4 <= N <= 50 0 <= M <= N^2
public class BOJ_20056_마법사_상어와_파이어볼 {
    static int N, M, K;
    static int[][] graph;
    static Map<Integer, List<int[]>> visited = new HashMap<>();

    // 0: 상 1: 오른쪽 위 대각선 2: 오른쪽 3: 오른쪽 아래 대각선 4: 아래 5: 왼쪽 아래 대각선 6: 왼쪽 7: 왼쪽 위 대각선
    static int[] dx = {-1, -1, 0, 1, 1, 1, 0, -1};
    static int[] dy = {0, 1, 1, 1, 0, -1, -1, -1};
    static int answer = 0;
    public static void main(String[] args) throws Exception {
        inputValues();
        for (int i = 0; i < K; i++) {
            orders();
        }

        for (int key : visited.keySet()) {
            for (int[] fireball : visited.get(key)) {
                answer += fireball[2];
            }
        }
        System.out.println(answer);
    }

    private static void orders() {
        Map<Integer, List<int[]>> newMap = new HashMap<>();
        int i = 0;
        int index = 0;
        int[] keySet = new int[visited.keySet().size()];

        for (int key : visited.keySet()) {
            keySet[index++] = key;
        }
        for (int key : keySet) {
            for (int[] fireball : visited.get(key)) {
                move(fireball, newMap);
            }
            visited.remove(key);
        }

        // 이동 모두 끝나고 2개 이상의 파이어볼 있는 칸에서 합치고 분산 작업하기
        checkFireballOverTwo(newMap);
        visited = newMap;
    }

    private static void move(int[] fireball, Map<Integer, List<int[]>> newMap) {
        int d = fireball[4]; // 방향
        int s = fireball[3]; // 속력
        int x = fireball[0] - 1, y = fireball[1] - 1;
        List<int[]> temp;
        for (int i = 0; i < s; i++) { // 방향으로 s칸 만큼 이동
            x = x + dx[d];
            y = y + dy[d];
            if(x < 0 && y < 0) { // x와 y의 범위가 배열 범위보다 작아진다면
                x = N - 1;
                y = N - 1;
            }else if(x < 0 && y >= N) { // x는 배열 범위보다 작아지는데 y는 배열범위보다 커진다면
                x = N - 1;
                y = 0;
            }else if(x >= N && y < 0) { // x는 배열 범위보다 커지는데 y는 배열범위보다 작아진다면
                x = 0;
                y = N - 1;
            }else if(x >= N && y >= N) { //x와 y의 범위가 배열 범위보다 커진다면
                x = 0;
                y = 0;
            }else if(x < 0) { //x는 배열 범위보다 작은데 y는 정상범위일 경우
                x = N - 1;
            }else if(x >= N) { //x는 배열 범위를 초과하는데 y는 정상범위일 경우
                x = 0;
            }else if(y < 0) { //x는 정상범위인데 y가 배열 범위보다 작은경우
                y = N - 1;
            }else if(y >= N) {
                y = 0;//x는 정상 범위인데 y가 배열 범위보다 큰 경우
            }
        }

        if(!newMap.containsKey(graph[x][y])) {
            temp = new LinkedList<>();
            temp.add(new int[]{x + 1, y + 1, fireball[2], s, d});
            newMap.put(graph[x][y], temp);
        }else { // 이미 해당 맵에 키가 존재한다면
            temp = newMap.get(graph[x][y]);
            temp.add(new int[]{x + 1, y + 1, fireball[2], s, d});
        }
    }
    private static void checkFireballOverTwo(Map<Integer, List<int[]>> newMap) {
        for (int key : newMap.keySet()) {
            if (newMap.get(key).size() >= 2) {
                sumAndDivide(newMap.get(key));
            }
        }
    }

    private static void sumAndDivide(List<int[]> temp) {
        int sumM = 0, cnt = 0, sumS = 0;
        boolean isAllEvenOrOdd = true;
        int mod = temp.get(0)[4] % 2;
        int[] directions, fireball = new int[0];
        int weight = 0, speed = 0;

        while(temp.size() > 0) {
            fireball = temp.remove(0);
            if((fireball[4] % 2) != mod) isAllEvenOrOdd = false;
            sumM += fireball[2];
            sumS += fireball[3];
            cnt++;
        }

        if(isAllEvenOrOdd) directions = new int[]{0, 2, 4, 6};
        else directions = new int[]{1, 3, 5, 7};

        weight += sumM / 5;
        speed += sumS / cnt;
        if(weight == 0) return;
        for (int i = 0; i < 4; i++) {
            temp.add(new int[]{fireball[0], fireball[1], weight, speed, directions[i]});
        }
    }

    private static void inputValues() throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        List<int[]> temp;
        N = Integer.parseInt(st.nextToken());
        M = Integer.parseInt(st.nextToken());
        K = Integer.parseInt(st.nextToken());

        graph = new int[N][N];
        int start = 0;
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                graph[i][j] = start++;
            }
        }
        // ri, ci, mi, si, di
        for (int i = 0; i < M; i++) {
            int [] fireball = Arrays.stream(br.readLine().split(" "))
                    .mapToInt(Integer::parseInt)
                    .toArray();
            int r = fireball[0] - 1;
            int c = fireball[1] - 1;

            temp = new LinkedList<>();
            temp.add(fireball);
            visited.put(graph[r][c], temp);
        }
    }
}
