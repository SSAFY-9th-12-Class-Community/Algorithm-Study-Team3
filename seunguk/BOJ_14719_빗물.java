package day02;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class BOJ_14719_빗물 {

	public static void main(String[] args) throws IOException {
		BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st = new StringTokenizer(in.readLine());

		int sero = Integer.parseInt(st.nextToken()); // 세로
		int garo = Integer.parseInt(st.nextToken()); // 가로
		int[] array = new int[garo];
		int[] leftArray = new int[garo];
		int[] rightArray = new int[garo];
		int[] finalArray = new int[garo];
		int max = Integer.MIN_VALUE;
		int answer = 0;

		st = new StringTokenizer(in.readLine());
		for (int i = 0; i < garo; i++) {
			// array에 높이를 넣어준다.
			array[i] = Integer.parseInt(st.nextToken());
			max = Math.max(array[i], max); // 왼쪽부터 하나씩 가기 시작하면서 최댓값을 넣어준다.
			leftArray[i] = max;

		}
		// 왼쪽부터 한 번 돌았으니까 이제는 오른쪽부터 한바퀴 돌아야 한다.
		max = Integer.MIN_VALUE;
		for (int i = garo - 1; i >= 0; i--) {
			max = Math.max(array[i], max);
			rightArray[i] = max;
		}

		for (int i = 0; i < garo; i++) {
			finalArray[i] = Math.min(leftArray[i], rightArray[i]);
		}

		for (int i = 0; i < garo; i++) {
			answer += (finalArray[i] - array[i]);

		}

		System.out.println(answer);

	}

}
