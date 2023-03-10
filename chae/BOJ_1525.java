import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

// 골드 2
// 퍼즐
public class BOJ_1525 {
    static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    static StringTokenizer st;
    static StringBuilder start = new StringBuilder();

    static Queue<String> q = new LinkedList<>();
    static Map<String, Integer> map = new HashMap<>();
    static final String answer = "123456780";

    public static void main(String[] args) throws IOException {

        for (int i = 0; i < 3; i++) {
            st = new StringTokenizer(br.readLine());
            for (int j = 0; j < 3; j++) {
                String token = st.nextToken();
                start.append(token);
            }
        }

        puzzle();

        if (map.containsKey(answer))
            System.out.println(map.get(answer));
        else System.out.println(-1);

        return;
    }

    private static void puzzle() {
        int[] dr = {-1, 1, 0, 0};    // 상 하 좌 우
        int[] dc = {0, 0, -1, 1};

        // 초기 설정
        q.offer(start.toString());
        map.put(start.toString(), 0);

        while (!q.isEmpty()) {
            String current = q.poll();
            int zeroIndex = current.indexOf("0");
            int r = zeroIndex / 3;    // 0의 좌표
            int c = zeroIndex % 3;

            for (int d = 0; d < 4; d++) {
                int nextR = r + dr[d];
                int nextC = c + dc[d];
                int nextIndex = nextR * 3 + nextC;

                if (isPossibleToSwap(nextR, nextC)) {
                    StringBuilder next = new StringBuilder(current);
                    char nextChar = next.charAt(nextIndex);
                    // 교환
                    next.setCharAt(zeroIndex, nextChar);
                    next.setCharAt(nextIndex, '0');
                    // map에 없을 경우 추가
                    if (!map.containsKey(next.toString())) {
                        q.offer(next.toString());
                        map.put(next.toString(), map.get(current) + 1);
                    }

                }
            }
        }
    }

    private static boolean isPossibleToSwap(int x, int y) {
        if (x >= 0 && x < 3 && y >= 0 && y < 3) {
            return true;
        }
        return false;
    }
}