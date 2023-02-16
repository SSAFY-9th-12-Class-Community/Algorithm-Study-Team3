package study;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.StringTokenizer;

public class BOJ_2805_나무자르기_김승욱 {
	// 나무 자르기
	public static void main(String[] args) throws IOException {
		BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st = new StringTokenizer(in.readLine());
		//나무를 위에서부터 자른다는 것을 주의
		int namuCnt = Integer.parseInt(st.nextToken()); // 나무의 수
		int need = Integer.parseInt(st.nextToken()); // 필요한 나무 길이
		long total = 0;
		long tree[] = new long[namuCnt];
		st = new StringTokenizer(in.readLine());
		for (int i = 0; i < namuCnt; i++) {
			tree[i] = Long.parseLong(st.nextToken());
			total += tree[i];
		}

		Arrays.sort(tree); // 나무 길이를 정렬한다
		// 4 26 40 42 46
		boolean caseC = true;

		if (namuCnt == 1) {
			//나무가 1그루만 있을 때
			System.out.println(tree[0] - need);
		} else if (namuCnt == 2) {
			//나무가 2그루 있을 때
			if (tree[1] - tree[0] < need) {
				System.out.println(((tree[0] + tree[1] - need) / 2));
			} else {
				System.out.println(tree[1] - need);
			}
		} else {
			//나무가 3그루 이상 있을 때
			int x = namuCnt - 2;
			int count = 1;
			long sum = 0;
			while (true) {
				sum += count * (tree[x + 1] - tree[x]);
				if(sum>need) {
					break;
				}
				
				count += 1;
				x -= 1;
				if (x < 0) {
					//나무들끼리 차이가 거의 없는 경우를 의미한다.
					caseC = false;
					break;
				}

			}
			if (caseC) {
				//나무들끼리 차이가 거의 없는 경우
				x += 1;
				long answer = 0;
				long summing = 0;
				for (int i = namuCnt - 1; i >= x; i--) {
					summing += (tree[i] - tree[x]);
				}
				if ((need - summing) % count == 0) {
					answer = (need - summing) / count;
				} else {
					answer = ((need - summing) / count) + 1;
				}

				System.out.println(tree[x] - answer);

			} else {
				// 제일 작은 부분이 포함된다!
				// 제일 처음 요소도 포함되는 경우

				System.out.println((total - need) / namuCnt);

			}

		}

	}

}

