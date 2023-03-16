package study;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class BOJ_2248_이진수찾기 {
	static int N, L;
	static long I;
	static long[][] DP;

	public static void main(String[] args) throws IOException {
		BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st = new StringTokenizer(in.readLine());

		N = Integer.parseInt(st.nextToken());
		L = Integer.parseInt(st.nextToken());
		I = Long.parseLong(st.nextToken());
		DP = new long[N + 1][N + 1];
		DP[0][0] = 1;
		DP[1][0] = 1;
		DP[1][1] = 1;
		// DP완성하고
		for (int i = 2; i <= N; i++) {
			for (int j = 0; j <= i; j++) {
				if (j == 0) {
					DP[i][j] = 1;
					continue;
				}
				if (j == i) {
					DP[i][j] = 1;
					continue;
				}
				DP[i][j] = DP[i - 1][j - 1] + DP[i - 1][j];
			}
		}
		int idx = N - 1;
		int[] answer = new int[N];
		int k = 0;
		while (idx >= 0) {
			long sum = 0;
			for (int i = 0; i <= L; i++) {
				sum += DP[idx][i];
			}
			if (sum < I) {
				// 1을 추가한다
				I -= sum;
				L -= 1;
				answer[k] = 1;
			} else {
				answer[k] = 0;
			}
			k += 1;
			idx -= 1;
		}

		for (int i = 0; i < N; i++) {
			System.out.print(answer[i]);

		}
	}

}
