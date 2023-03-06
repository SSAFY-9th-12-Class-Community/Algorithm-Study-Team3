import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.StringTokenizer;

/*
 * 점화식 day :  걸리는 시간, cost : 받을 수 있는 금액
 * DP[i] = max(DP[i], DP[i - 1])
 * DP[i + day - 1] = max(DP[i + day - 1], DP[i - 1] + cost) 
 * */
public class BOJ_15486 {
	static int N;
	static int[] dp;
	
	public static void main(String[] args) throws Exception {
		inputValues();
	}
	
	private static void inputValues() throws Exception {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
		StringBuilder sb = new StringBuilder();
		StringTokenizer st;
		N = Integer.parseInt(br.readLine());
		dp = new int[N + 1];
		
		for(int i = 1; i < N + 1; i++) {
			 st = new StringTokenizer(br.readLine());
			 int day = Integer.parseInt(st.nextToken()), cost = Integer.parseInt(st.nextToken());
			 dp[i] = Math.max(dp[i], dp[i - 1]);
			 if(i + day - 1 > N) continue;
			 dp[i + day - 1] = Math.max(dp[i + day - 1], dp[i - 1] + cost);
		}
		sb.append(dp[N]);
		bw.write(sb.toString());
		bw.flush();
		bw.close();
		br.close();
	}
}
