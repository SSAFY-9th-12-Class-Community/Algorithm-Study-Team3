package day02;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.StringTokenizer;

public class BOJ_2461_대표선수 {
	static int Ban;
	static int Students;
	static int[][] howMany;

	public static void main(String[] args) throws IOException {

		BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st = new StringTokenizer(in.readLine());

		Ban = Integer.parseInt(st.nextToken()); // 몇개의 반?
		Students = Integer.parseInt(st.nextToken()); // 각 반에 있는 학생들의 능력치
		howMany = new int[Ban][Students];

		for (int i = 0; i < Ban; i++) {
			st = new StringTokenizer(in.readLine());
			for (int j = 0; j < Students; j++) {
				howMany[i][j] = Integer.parseInt(st.nextToken());
			}
		}

		for (int i = 0; i < Ban; i++) {
			// 각 행을 정렬해준다. => 각 반의 학생들의 능력치를 오름차순으로 정렬해준다.
			Arrays.sort(howMany[i]);
		}
		int[] temp = new int[Ban];
		int answer = Integer.MAX_VALUE;
		int minIndex = 0; // 최솟값을 가지고 있는 반을 나타내는 인덱스
		while (true) {

			int max = Integer.MIN_VALUE;
			int min = Integer.MAX_VALUE;

			for (int i = 0; i < Ban; i++) {
				// 최솟값
				if (howMany[i][temp[i]] < min) {
					min = howMany[i][temp[i]];
					minIndex = i;
				}
				// 최댓값
				if (howMany[i][temp[i]] > max) {
					max = howMany[i][temp[i]];
				}
			}

			answer = Math.min(answer, max - min);
			temp[minIndex]++;
			if (temp[minIndex] == Students)
				break;

		}
		System.out.println(answer);

	}

}
