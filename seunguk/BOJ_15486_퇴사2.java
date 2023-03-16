package day03;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class BOJ_15486_퇴사2 {
	static int len;
	static int[] Time, Pay;
	static int[] DP;
	static int max = Integer.MIN_VALUE;

	public static void main(String[] args) throws IOException {
		BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st = new StringTokenizer(in.readLine());

		len = Integer.parseInt(st.nextToken());
		Time = new int[len + 2];
		Pay = new int[len + 2];
		for (int i = 1; i <= len; i++) {
			st = new StringTokenizer(in.readLine());
			Time[i] = Integer.parseInt(st.nextToken());
			Pay[i] = Integer.parseInt(st.nextToken());

		}
		DP = new int[len + 2];

		for (int i = 1; i <= len + 1; i++) {
			if (max < DP[i]) {
				max = DP[i];
			}
			int day = i + Time[i];
			if (day <= len + 1) {
				DP[day] = Math.max(DP[day], max + Pay[i]);
			}
		}
		System.out.println(max);
	}
}
