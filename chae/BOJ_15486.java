import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;

// 골드 5
// 퇴사 2
public class BOJ_15486 {
    static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    static int[] dp;
    static int t, p;
    static int answer;

    public static void main(String[] args) throws IOException {
        int n = Integer.parseInt(br.readLine());

        dp = new int[n+1];

        for (int i = 0; i < n; i++) {
            int[] tp = Arrays.stream(br.readLine().split(" ")).mapToInt(Integer::parseInt).toArray();
            t = tp[0];
            p = tp[1];

            answer = Math.max(answer, dp[i]);

            int nextDay = i + t;

            if (nextDay < n+1)
                dp[nextDay] = Math.max(dp[nextDay], answer + p);

        }

        System.out.println(Math.max(answer, dp[n]));

    }
}
