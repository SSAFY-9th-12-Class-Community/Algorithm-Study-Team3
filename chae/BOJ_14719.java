import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;

// 골드 5
// 빗물
public class BOJ_14719 {
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        int[] hw = Arrays.stream(br.readLine().split(" ")).mapToInt(Integer::parseInt).toArray();

        int w = hw[1];

        int sum = 0;

        int[] arr = Arrays.stream(br.readLine().split(" ")).mapToInt(Integer::parseInt).toArray();

        int left = 0;
        for (int i = 0; i < w; i++) {
            int right = 0;

            if (left < arr[i]) {    // 현재 값이 left보다 클 경우
                left = arr[i];  // left에 저장
            }

            for (int j = i + 1; j < w; j++){    // 오른쪽으로 탐색하며 가장 큰 right 값 찾아서 저장
                if (right < arr[j]) {
                    right = arr[j];
                }
            }

            int value = Math.min(left, right) - arr[i]; // left, right 중 최소값 - 현재 값
            if (value > 0) {
                sum += value;
            }
        }

        System.out.println(sum);

    }
}
