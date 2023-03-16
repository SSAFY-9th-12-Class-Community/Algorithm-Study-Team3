package day02;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.StringTokenizer;

public class SW2117_홈방범서비스 {
	static int[][] map;
	static int N;
	static int M;
	static int count1;
	static ArrayList<index> list = new ArrayList<>();

	public static void main(String[] args) throws IOException {
		BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st = new StringTokenizer(in.readLine());

		int tc = Integer.parseInt(st.nextToken()); // 10

		for (int test_case = 1; test_case <= tc; test_case++) {

			st = new StringTokenizer(in.readLine());

			N = Integer.parseInt(st.nextToken()); // 한 변의 길이
			M = Integer.parseInt(st.nextToken()); // 한 집당 받을 수 있는 돈

			map = new int[N][N]; // 맵을 만들어준다.
			count1 = 0;
			for (int i = 0; i < N; i++) {
				// 맵 완성
				st = new StringTokenizer(in.readLine());
				for (int j = 0; j < N; j++) {
					map[i][j] = Integer.parseInt(st.nextToken());
					if (map[i][j] == 1) {
						list.add(new index(i, j));
						count1 += 1; // 1인 개수를 더해준다.
					}
				}
			}

			int fAnswer = 0;

			for (int size = 1; size <= N + 1; size++) {
				int cost = size * size + (size - 1) * (size - 1);
				if (count1 * M < cost) {
					break;
				}
				if (0 <= MaxCount(size) * M - cost) {
					if (fAnswer <= MaxCount(size)) {
						fAnswer = MaxCount(size);
					}
				}

			}

			System.out.println("#" + test_case + " " + fAnswer);
			list.clear();
		}
	}

	private static int MaxCount(int length) {
		int answer = 0;
		for (int i = 0; i < N; i++) {

			for (int j = 0; j < N; j++) {
				int temp = 0;
				// 각 좌표에서
				for (int k = 0; k < list.size(); k++) {
					// if(거리가 충족된다면?)=> answer+=1;을 해라
					if (Math.abs(i - list.get(k).y) + Math.abs(j - list.get(k).x) <= (length - 1)) {
						temp += 1;
					}
				}
				if (answer <= temp) {
					answer = temp;
				}
			}
		}
		return answer;
	}
}

class index {
	int y;
	int x;

	public index(int y, int x) {
		super();
		this.y = y;
		this.x = x;
	}

}
