import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.StringTokenizer;

public class BOJ_14719_빗물 {
    static int H, W;
    static int[] rainDrops;
    static int[] visited;
    public static void main(String[] args) throws Exception {
        inputValues(); // 입력 값 받기
        twoPointer();
        System.out.println(Arrays.stream(visited).sum());
    }


    public static void inputValues() throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());

        H = Integer.parseInt(st.nextToken());
        W = Integer.parseInt(st.nextToken());

        rainDrops = new int[W];
        visited = new int[W];
        st = new StringTokenizer(br.readLine());

        for(int i = 0; i < W; i++) {
            rainDrops[i] = Integer.parseInt(st.nextToken());
        }
    }

    public static void twoPointer() {
        int start = 0, end = 0;
        int standard;
        while(start <= end) {
            end++;

            if(end == rainDrops.length) { // 종료 조건
                break;
            }

            if(rainDrops[end - 1] < rainDrops[end]) { // 갱신
                standard = Math.min(rainDrops[end], rainDrops[start]);
                getSpaceCount(standard, start, end);
            }


            if(rainDrops[start] <= rainDrops[end]) { // end부분이 start보다 높거나 같으면
                getSpaceCount(rainDrops[start], start, end);
                start = end;
            }
        }
    }

    public static void getSpaceCount(int standard, int start, int end) {
        for(int i = start; i < end; i++) {
            int value = standard - rainDrops[i];
            if(value < 0) value = 0;
            visited[i] = Math.max(visited[i], value);
        }
    }
}
