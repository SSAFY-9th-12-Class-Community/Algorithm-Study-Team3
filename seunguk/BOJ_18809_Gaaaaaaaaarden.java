package day03;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;
import java.util.StringTokenizer;

/*
 * 0은 호수, 1은 배양액 불가능, 2는 배양액 쌉가능
 * 첫줄 N(행),M(열),초록,빨강
 * 1. 입력 받으면서 배양액 쌉가능 칸의 수를 세고 조합을 한다.
 * 2. 각 경우에 대해 꽃의 최대 개수를 구하고 갱신해준다.
 * 
 * 
 */
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;
import java.util.StringTokenizer;

/*
 * 0은 호수, 1은 배양액 불가능, 2는 배양액 쌉가능
 * 첫줄 N(행),M(열),초록,빨강
 * 1. 입력 받으면서 배양액 쌉가능 칸의 수를 세고 조합을 한다.
 * 2. 각 경우에 대해 꽃의 최대 개수를 구하고 갱신해준다.
 * 
 * 
 */
public class BOJ_18809_Gaaaaaaaaarden {
	static int N, M, G, R;
	static int[][] map;
	static int count = 0; // 배양액을 뿌릴 수 있는 칸의 개수
	static ArrayList<Possible> ground = new ArrayList<>();
	static Pos[][] visited;
	static int[] groundC; // 배양액을 뿌릴 수 있는 모든 땅에 어떤 배양액을 뿌릴지를 넣어주는 배열
	static int Greenidx = 0;
	static int Redidx = 0;
	static Queue<Pos> que = new LinkedList<>();
	static int dx[] = { 0, 1, 0, -1 };
	static int dy[] = { 1, 0, -1, 0 };
	static int answer = 0;

	public static void main(String[] args) throws IOException {
		BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st = new StringTokenizer(in.readLine());

		N = Integer.parseInt(st.nextToken());
		M = Integer.parseInt(st.nextToken());
		G = Integer.parseInt(st.nextToken());
		R = Integer.parseInt(st.nextToken());

		map = new int[N][M];

		for (int i = 0; i < N; i++) {
			st = new StringTokenizer(in.readLine());
			for (int j = 0; j < M; j++) {
				map[i][j] = Integer.parseInt(st.nextToken());
				if (map[i][j] == 2) {

					ground.add(new Possible(count, i, j)); // count 랑 index랑 동일
					count += 1;
				}
			}
		}

		groundC = new int[count];
		comb(0);

		System.out.println(answer);
	}

	private static void getMin(int[] arr) {
		visited = new Pos[N][M];
		for (int i = 0; i < arr.length; i++) {
			if (arr[i] == 1) {
				// Green
				que.offer(new Pos(ground.get(i).y, ground.get(i).x, 0, 'G'));
				visited[ground.get(i).y][ground.get(i).x] = new Pos(ground.get(i).y, ground.get(i).x, 0, 'G');
			} else if (arr[i] == 2) {
				// Red
				que.offer(new Pos(ground.get(i).y, ground.get(i).x, 0, 'R'));
				visited[ground.get(i).y][ground.get(i).x] = new Pos(ground.get(i).y, ground.get(i).x, 0, 'R');
			}
		}
		int flower = 0;
		while (!que.isEmpty()) {
			Pos now = que.poll();
			int time = visited[now.y][now.x].time;
			char color = visited[now.y][now.x].color;

			if (visited[now.y][now.x].color == 'F') {
				continue;
			}
			for (int t = 0; t < 4; t++) {
				int nexty = now.y + dy[t];
				int nextx = now.x + dx[t];

				if (nextx < 0 || nexty < 0 || nextx >= M || nexty >= N || map[nexty][nextx] == 0) {
					continue;
				}
				if (visited[nexty][nextx] == null) {
					visited[nexty][nextx] = new Pos(nexty, nextx, time + 1, color);
					que.add(new Pos(nexty, nextx, time + 1, color));
				} else if (visited[nexty][nextx].color == 'G') {
					if (color == 'R' && visited[nexty][nextx].time == time + 1) {
						flower += 1;
						visited[nexty][nextx].color = 'F';
					}
				} else if (visited[nexty][nextx].color == 'R') {
					if (color == 'G' && visited[nexty][nextx].time == time + 1) {
						flower += 1;
						visited[nexty][nextx].color = 'F';
					}

				}

			}

		}
		answer = Math.max(answer, flower);

	}

	private static void comb(int cnt) {
		if (Greenidx > G || Redidx > R) {
			return;
		}

		if (cnt == count) {
			if (Redidx == R && Greenidx == G) {
//				for (int i = 0; i < count; i++) {
//					System.out.print(groundC[i] + " ");
//				}
//				System.out.println();
				getMin(groundC);
				return;
				// 이제 groundC가 만들어졌으므로 최대 꽃을 구하는 과정만 구현하면 된다.

			} else {
				return;
			}

		}

		for (int i = 0; i <= 2; i++) {
			if (i == 0) {
				groundC[cnt] = i; // 안 넣어
				comb(cnt + 1);
			} else if (i == 1) {
				Greenidx += 1;
				groundC[cnt] = i; // 초록
				comb(cnt + 1);
				Greenidx -= 1;
			} else if (i == 2) {
				Redidx += 1;
				groundC[cnt] = i; // 빨강
				comb(cnt + 1);
				Redidx -= 1;
			}

		}
	}

	static class Possible {
		int idx;
		int y;
		int x;

		public Possible(int idx, int y, int x) {
			super();
			this.idx = idx;
			this.y = y;
			this.x = x;
		}

	}

	static class Pos {
		int y;
		int x;
		int time;
		char color;

		public Pos(int y, int x, int time, char color) {
			super();
			this.y = y;
			this.x = x;
			this.time = time;
			this.color = color;
		}

	}

}
