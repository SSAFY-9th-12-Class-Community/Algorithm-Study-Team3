package study;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.StringTokenizer;

public class BOJ_2805 {
	// 나무 자르기
	public static void main(String[] args) throws IOException {
		BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st = new StringTokenizer(in.readLine());

		int namuCnt = Integer.parseInt(st.nextToken());
		int need = Integer.parseInt(st.nextToken());
		long total = 0;
		long tree[] = new long[namuCnt];
		st = new StringTokenizer(in.readLine());
		for (int i = 0; i < namuCnt; i++) {
			tree[i] = Long.parseLong(st.nextToken());
			total += tree[i];
		}

		Arrays.sort(tree); // 나무를 정렬한다
		// 4 26 40 42 46
		boolean caseC = true;

		if (namuCnt == 1) {
			System.out.println(tree[0] - need);
		} else if (namuCnt == 2) {
			if (tree[1] - tree[0] < need) {
				System.out.println(((tree[0] + tree[1] - need) / 2));
			} else {
				System.out.println(tree[1] - need);
			}
		} else {
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
					caseC = false;
					break;
					// 나머지는 존나 짧고 몇개만 긴경우 => 0 0인덱스를 만들고 그 안에 값을 넣은다음 나머지를 전부 오른쪽으로 밀어버리면 된다.
				}

				// System.out.println(x+" "+count);

			}
			//System.out.println(x+" "+count);
			if (caseC) {
				x += 1;
				

				// System.out.println(x + " " + count);
				long answer = 0;
				long summing = 0;
				for (int i = namuCnt - 1; i >= x; i--) {
					summing += (tree[i] - tree[x]);
				}
				// System.out.println("summing:" + summing);
				if ((need - summing) % count == 0) {
					answer = (need - summing) / count;
				} else {
					// System.out.println("실행!");
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
