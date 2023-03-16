package study;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Scanner;

public class BOJ_1300 {
	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);

		int a = sc.nextInt();
		int count = sc.nextInt();
		long answer = 0;
		// count-1개가 앞에 있는 것을 구하면 된다.


		long start = 1;
		long end = count; // 처음에는 a*a를 했었다.
		long temp = 0;
		while (start <= end) {

			long sum = 0;
			long mid = (start + end) / 2;

			for (long i = 1; i <= a; i++) {
				if (mid / i >= a) {
					sum += a;
				} else {
					sum += (mid / i);
				}
			}

			if (sum >= count) {
				// 수가 너무 큰 경우
				temp = mid;
				end = mid - 1;
			} else if (sum < count) {
				// 수가 너무 적은 경우
				start = mid + 1;
			}

		}
		System.out.println(temp);
	}

}
