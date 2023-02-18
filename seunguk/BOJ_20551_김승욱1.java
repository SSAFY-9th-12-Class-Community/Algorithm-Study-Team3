package study;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.StringTokenizer;

public class BOJ_20551_김승욱1 {
	public static void main(String[] args) throws IOException {
		BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st = new StringTokenizer(in.readLine());

		int N = Integer.parseInt(st.nextToken()); // 배열의 길이
		int M = Integer.parseInt(st.nextToken()); // 질문의 개수

		int[] array = new int[N];

		for (int i = 0; i < N; i++) {
			// N개의 값을 배열에 넣는다.
			st = new StringTokenizer(in.readLine());
			array[i] = Integer.parseInt(st.nextToken());
		}
		Arrays.sort(array); // 정렬을 한번 해준다.

		for (int i = 0; i < M; i++) {
			int start = 0;
			int end = N - 1;
			int answer = -1;
			st = new StringTokenizer(in.readLine());
			int question = Integer.parseInt(st.nextToken()); // -1, 10, 5, 9, 0

			while (start < end) {
				
				int mid = (start + end) / 2;
				if (array[mid] < question) {
					start = mid + 1;
				} else if (array[mid] >= question) {
					end = mid;
				}

			}
			if(array[start]==question) {
				System.out.println(start);
			}
			else {
				System.out.println("-1");
			}

		}
	}

}

