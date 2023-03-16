package day03;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.LinkedList;
import java.util.Queue;
import java.util.StringTokenizer;

public class SW_17822_원판돌리기 {
	static int N;
	static int M;
	static int T;
	static int[][] map;
	static boolean[][] visited;
	static boolean[][] isSelected;
	static int[] dy = { -1, 0, 1, 0 };
	static int[] dx = { 0, 1, 0, -1 };
	static int count;
	static int temp;
	static int answer;

	public static void main(String[] args) throws IOException {
		BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st = new StringTokenizer(in.readLine());

		N = Integer.parseInt(st.nextToken()); // 반지름의 길이
		M = Integer.parseInt(st.nextToken()); // 각 원판에 적힌 정수의 개수
		T = Integer.parseInt(st.nextToken()); // T번 회전
		answer = 0;
		map = new int[N + 1][M + 1];// map을 채워넣는다.

		for (int i = 1; i <= N; i++) {
			st = new StringTokenizer(in.readLine());
			for (int j = 1; j <= M; j++) {
				map[i][j] = Integer.parseInt(st.nextToken());
			}
		}
		for (int i = 0; i < T; i++) {
			visited = new boolean[N + 1][M + 1];// map을 채워넣는다.
			st = new StringTokenizer(in.readLine());
			int x = Integer.parseInt(st.nextToken()); // 배수
			int d = Integer.parseInt(st.nextToken()); // 방향
			int k = Integer.parseInt(st.nextToken()); // 몇번?
			rotate(x, d, k);
//			for (int f = 1; f <= N; f++) {
//				for (int z = 1; z <= M; z++) {
//					System.out.print(map[f][z] + " ");
//				}
//				System.out.println();
//			}

			merge();
//			for (int f = 1; f <= N; f++) {
//				for (int z = 1; z <= M; z++) {
//					System.out.print(map[f][z] + " ");
//				}
//				System.out.println();
//			}

		}
		for (int i = 1; i <= N; i++) {
			for (int j = 1; j <= M; j++) {
				if (map[i][j] != -9) {
					answer += map[i][j];
				}
			}
		}
		System.out.println(answer);

	}

	// x는 배수, d 는 방향, k 는 횟수
	public static void rotate(int x, int d, int k) {
		for (int i = 1; i <= N; i++) {
			if (i % x == 0) {
				// x의 배수인 경우에만 돌린다.
				if (d == 0) {
					// 시계 방향으로 돌린다
					for (int j = 0; j < k; j++) {
						// k번 반복한다
						int temp = map[i][M];
						for (int t = M - 1; t >= 1; t--) {
							map[i][t + 1] = map[i][t];
						}
						map[i][1] = temp;

					}

				} else if (d == 1) {
					// 반시계 방향으로 돌린다.
					for (int j = 0; j < k; j++) {
						// k번 반복한다.
						int temp = map[i][1];
						for (int t = 2; t <= M; t++) {
							map[i][t - 1] = map[i][t];
						}
						map[i][M] = temp;

					}
				}
			}
		}

	}

	public static void merge() {
		count = 0;
		for (int i = 1; i <= N; i++) {
			for (int j = 1; j <= M; j++) {
				isSelected = new boolean[N + 1][M + 1];
				temp = 0;
				bfs(i, j);
				if (temp > 1) {
					// bfs가 작동했을 경우
					for (int k = 1; k <= N; k++) {
						for (int m = 1; m <= M; m++) {
							if (isSelected[k][m] == true) {
								map[k][m] = -9;
							}
						}
					}
				}

			}

		}
		if (count == 0) {
			int sum = 0;
			int counting = 0;
			for (int i = 1; i <= N; i++) {
				for (int j = 1; j <= M; j++) {
					if (map[i][j] != -9) {
						sum += map[i][j];
						counting += 1;
					}
				}
			}
			for (int i = 1; i <= N; i++) {
				for (int j = 1; j <= M; j++) {
					if (map[i][j] != -9) {
						if (map[i][j] > (double) sum / counting) {
							map[i][j] -= 1;
						} else if (map[i][j] < (double) sum / counting) {
							map[i][j] += 1;
						}
					}
				}
			}
		}

	}

	public static void bfs(int y, int x) {
		if (visited[y][x] == true || map[y][x] == -9) {
			return;
		}
		Queue<index> que = new LinkedList<>();

		visited[y][x] = true;
		isSelected[y][x] = true;
		temp += 1; // isSelect 된 개수
		que.add(new index(y, x));
		while (!que.isEmpty()) {
			index id = que.poll();
			for (int i = 0; i < 4; i++) {
				int nexty = id.y + dy[i];
				int nextx = id.x + dx[i];

				if (nexty < 1 || nexty > N) {
					continue;
				}
				if (nextx < 1) {
					nextx = M;
				}
				if (nextx > M) {
					nextx = 1;
				}

				if (map[id.y][id.x] == map[nexty][nextx] && isSelected[nexty][nextx] == false) {
					// 값이 만약에 동일하다면?
					que.add(new index(nexty, nextx));
					visited[nexty][nextx] = true;
					isSelected[nexty][nextx] = true;
					count += 1;
					temp += 1;
				}
			}
		}

	}

	public static class index {
		int y;
		int x;

		public index(int y, int x) {
			super();
			this.y = y;
			this.x = x;
		}

	}
}
