package study;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.StringTokenizer;

public class BOJ_1477 {
	static int mid;
	// 휴게소 세우기

	public static void main(String[] args) throws IOException {
		BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st = new StringTokenizer(in.readLine());

		int nowRest = Integer.parseInt(st.nextToken()); // 현재 휴게소의 개수
		int newRest = Integer.parseInt(st.nextToken()); // 새로 지을 휴게소의 개수
		int roadLen = Integer.parseInt(st.nextToken()); // 도로의 길이

		int[] array = new int[nowRest + 2]; // 0 과 roadLen을 넣어줘야 한다.
		st = new StringTokenizer(in.readLine());
		array[0] = 0; // 0을 넣어준다.
		array[nowRest + 1] = roadLen; // roadLen을 넣어준다
		for (int i = 1; i <= nowRest; i++) {
			// 나머지 휴게소의 위치들을 넣어준다.
			array[i] = Integer.parseInt(st.nextToken());
		}
		Arrays.sort(array); // 휴게소 위치를 오름차순으로 정렬해준다.
		// 이제 정렬이 전부 되었으므로 이분탐색을 통해 답을 구해본다.

		int start = 1; // 처음 휴게소의 위치는 1이니까
		int end = roadLen;

		while (start <= end) {

			mid = (start + end) / 2;

			int sum = 0;

			for (int i = 0; i <= nowRest; i++) {
				sum += ((array[i + 1] - array[i] - 1) / mid);
				// -1 하는 이유는 아마 210 /70의 경우 2개가 되어야 하는데 3개가 되니까 이런식으로 처리를 해주는 것 같다.
			}

			if (sum > newRest) {
				// 휴게소가 너무 많으니까 사이 거리를 넓혀서 휴게소의 개수를 감소시켜본다. sum=newRest일 때도 멈추지 않고 극한까지 몰아붙여본다.
				start = mid + 1;
			} else {
				// 휴게소가 너무 적으니까 사이 거리를 줄여서 휴게소의 개수를 증가시켜본다.
				end = mid - 1;
			}

		}

		System.out.println(start); // 왜 start를 해야할까?

	}

}
