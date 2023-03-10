package alg;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class BOJ_15486 {
	public static void main(String[] args) throws NumberFormatException, IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st = null;
		int N = Integer.parseInt(br.readLine());
		int[] dp = new int[N + 2];
		int max = 0;
		int time = 0;
		int pay = 0;
		for (int i = 1; i < N + 2; i++) {
			if (dp[i] > max) {
				max = dp[i];
			}
			if (i == N + 1) {
				time = 0;
				pay = 0;
			} else {
				st = new StringTokenizer(br.readLine());
				time = Integer.parseInt(st.nextToken());
				pay = Integer.parseInt(st.nextToken());
			}
			if (i + time < N + 2) {
				dp[i + time] = Math.max(dp[i + time], max + pay);
			}
		}
		System.out.println(max);
	}
}
