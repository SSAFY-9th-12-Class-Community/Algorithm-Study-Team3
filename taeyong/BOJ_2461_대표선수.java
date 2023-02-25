import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.*;

public class BOJ_2461_대표선수 {
    static Map<Integer, Integer> map = new HashMap<>();
    static List<Integer> arr = new ArrayList<>();
    public static int[] visited;
    static int N, M;
    static int answer = Integer.MAX_VALUE;
    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st;
        int num;

        st = new StringTokenizer(br.readLine());
        N = Integer.parseInt(st.nextToken());
        M = Integer.parseInt(st.nextToken());

        visited = new int[N];

        for (int i = 0; i < N; i++) {
            st = new StringTokenizer(br.readLine());
            for (int j = 0; j < M; j++) {
                num = Integer.parseInt(st.nextToken());
                map.put(num, i); // 학급을 구분
                arr.add(num);
            }
        }

        // 모든 학생들이 저마다 다른 능력을 가지고 있기 때문에 오름차순 정렬
        Collections.sort(arr);

        twoPointer();
        System.out.println(answer);
    }

    public static void twoPointer() {
        int start = 0, end = 0;

        while (start < arr.size() && end < arr.size()) {
            while (end < arr.size()) {
                visited[map.get(arr.get(end))]++;
                end++;
                if (includeAllClass()) { // 모든 학급을 포함하고 있다면
                    break;
                }
            }

            while(visited[map.get(arr.get(start))] > 1) {
                visited[map.get(arr.get(start))]--;
                start++;
            }

            if (includeAllClass()) {
                int min = Integer.MAX_VALUE;
                int max = Integer.MIN_VALUE;

                for (int i = start; i < end; i++) {
                    min = Math.min(min, arr.get(i));
                    max = Math.max(max, arr.get(i));
                }
                answer = Math.min(answer, max - min);
            }
            visited[map.get(arr.get(start))]--;
            start++;
        }
    }

    public static boolean includeAllClass() {
        for (int i = 0; i < N; i++) {
            if(visited[i] == 0) return false;
        }
        return true;
    }
}
