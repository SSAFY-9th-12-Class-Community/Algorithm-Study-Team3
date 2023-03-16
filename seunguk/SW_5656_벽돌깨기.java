package study;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.LinkedList;
import java.util.Queue;
import java.util.StringTokenizer;

public class SW_5656_벽돌깨기 {
	static int N, W, H;
	static int[][] map;
	static int[] drop;
	static int answer;

	public static void main(String[] args) throws IOException {
		BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st = new StringTokenizer(in.readLine());

		int tc = Integer.parseInt(st.nextToken()); // test_case
		for (int test_case = 1; test_case <= tc; test_case++) {
			st = new StringTokenizer(in.readLine());
			N = Integer.parseInt(st.nextToken()); // 떨어뜨릴 공의 개수
			W = Integer.parseInt(st.nextToken()); // 가로 길이
			H = Integer.parseInt(st.nextToken()); // 세로 길이
			answer = Integer.MAX_VALUE;
			map = new int[H][W];

			drop = new int[N];

			// map을 완성시킨다.
			for (int i = 0; i < H; i++) {
				st = new StringTokenizer(in.readLine());
				for (int j = 0; j < W; j++) {
					map[i][j] = Integer.parseInt(st.nextToken());
				}
			}
			// 이제 어디에 공을 떨어뜨릴 것인지 조합을 구하자
			Choose(0);
			System.out.println("#" + test_case + " " + answer);

		}
	}

	public static void Choose(int cnt) {
		// 어디에 공을 떨어뜨릴지 정해야함
		if (cnt == N) {
			// 이제 여기서 공을 떨어뜨리면 일어나는 일을 넣어줘야 한다.
//			for (int i = 0; i < N; i++) {
//				System.out.print(drop[i] + " ");
//			}
//			System.out.println();

			Remove(drop);
			return;
		}

		for (int i = 0; i < W; i++) {
			drop[cnt] = i;
			Choose(cnt + 1);
		}
	}

	private static void Remove(int[] arr) {
		// 배열을 넘겨 받았을 때 해당 배열을 바탕으로 일어나는 일을 구성
		// map을 복사해야한다.
		int[][] Clone = new int[H][W];
		
		// Clone map완성
		for (int i = 0; i < H; i++) {
			for (int j = 0; j < W; j++) {
				Clone[i][j] = map[i][j];
			}
		}
		for (int i = 0; i < arr.length; i++) {
			// arr.length = 공을 떨어뜨리는 횟수 = N과 동일하다
			int dropPosition = arr[i]; // 공을 어디에 떨어뜨리는지
			boolean[][] visited = new boolean[H][W];
			int SH = -9;
			for (int j = 0; j < H; j++) {
				if (Clone[j][arr[i]] != 0) {
					SH = j;
					break;
				}
			}
			if (SH == -9) {
				// 해당 칸에 더이상 벽돌이 없음을 의미한다.
				continue;
			}
			Queue<index> que = new LinkedList<>();
			que.add(new index(SH, dropPosition, Clone[SH][dropPosition]));
			while (!que.isEmpty()) {
				index id = que.poll();
				Clone[id.y][id.x] = 0; // 해당값을 0으로 바꾼다 (즉, 깨뜨린다.)
				visited[id.y][id.x] = true;
				// 좌, 우, 하 에 해당하는 벽돌들을 que에 넣어줘야 한다.
				// 좌 먼저 시작한다
				for (int t = id.x - 1; t >= id.x - id.value + 1; t--) {
					if (t < 0) {
						break;
					}
					if (visited[id.y][t] == false) {
						que.add(new index(id.y, t, Clone[id.y][t]));
						visited[id.y][t] = true;
					}

				}
				// 우
				for (int t = id.x + 1; t <= id.x + id.value - 1; t++) {
					if (t >= W) {
						break;
					}

					if (visited[id.y][t] == false) {
						que.add(new index(id.y, t, Clone[id.y][t]));
						visited[id.y][t] = true;
					}

				}
				// 하
				for (int t = id.y + 1; t <= id.y + id.value - 1; t++) {
					if (t >= H) {
						break;
					}
					if (visited[t][id.x] == false) {
						que.add(new index(t, id.x, Clone[t][id.x]));
						visited[t][id.x] = true;
					}

				}
				// 상

				for (int t = id.y - 1; t >= id.y - id.value + 1; t--) {
					if (t < 0) {
						break;
					}

					if (visited[t][id.x] == false) {
						que.add(new index(t, id.x, Clone[t][id.x]));
						visited[t][id.x] = true;
					}

				}

			}
//			System.out.println(i + "번 공을 떨어뜨린 후");
//			for (int z = 0; z < H; z++) {
//				for (int f = 0; f < W; f++) {
//					System.out.print(Clone[z][f] + " ");
//				}
//				System.out.println();
//			}
//			System.out.println();
			// 이제 해당 과정을 전부 완료했으면 0인 부분은 채워줘야 한다.
			for (int c = 0; c < W; ++c) {

				for (int r = H - 1; r >= 0; --r) {
					if (Clone[r][c] == 0) {
						int nr = r;

						while (nr > 0 && Clone[nr][c] == 0) {
							nr--;
						}

						Clone[r][c] = Clone[nr][c];
						Clone[nr][c] = 0;
					}
				}
			}
//			System.out.println(i + "번 공을 떨어뜨린 후 밑으로 모으기");
//			for (int z = 0; z < H; z++) {
//				for (int f = 0; f < W; f++) {
//					System.out.print(Clone[z][f] + " ");
//				}
//				System.out.println();
//			}
//			System.out.println();

		}
		int sum = 0;
		for (int i = 0; i < H; i++) {
			for (int j = 0; j < W; j++) {
				if (Clone[i][j] == 0) {
					sum += 1;
				}
			}
		}
		if (answer >= H * W - sum) {
			answer = H * W - sum;
		}

	}

	static class index {
		int y;
		int x;
		int value;

		public index(int y, int x, int value) {
			super();
			this.y = y;
			this.x = x;
			this.value = value;
		}

	}

}
