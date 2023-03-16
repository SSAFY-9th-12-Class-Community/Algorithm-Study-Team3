package study;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

/*
 * DFS로 풀면 될 듯
 * 
 */
public class BOJ_1520_내리막길 {
	static int sero, garo;
	static int[][] map;
	static boolean[][] visited;
	static int dy[] = { -1, 0, 1, 0 };
	static int dx[] = { 0, 1, 0, -1 };
	static int answer = 0;
	static int[][] DP;

	public static void main(String[] args) throws IOException {
		BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st = new StringTokenizer(in.readLine());
		sero = Integer.parseInt(st.nextToken());
		garo = Integer.parseInt(st.nextToken());

		// map을 완성해준다.
		map = new int[sero][garo];
		DP = new int[sero][garo];

		visited = new boolean[sero][garo];
		visited[0][0] = true;
		for (int i = 0; i < sero; i++) {
			st = new StringTokenizer(in.readLine());
			for (int j = 0; j < garo; j++) {
				map[i][j] = Integer.parseInt(st.nextToken());
			}
		}
		for (int i = 0; i < sero; i++) {
			for (int j = 0; j < garo; j++) {
				DP[i][j] = -1;
			}
		}
		DFS(0, 0);
		System.out.println(DP[0][0]);

	}

	public static int DFS(int y, int x) {
		// y,x에서 도착점으로 가는 개수
		if (y == sero - 1 && x == garo - 1) {

			return 1;
		}
		if (DP[y][x] != -1) {
			// 만약에 이미 갈 수 있다는게 증명된 상황이라면?

			return DP[y][x];

		}
		DP[y][x] = 0;

		for (int i = 0; i < 4; i++) {
			int nexty = y + dy[i];
			int nextx = x + dx[i];

			if (nexty < 0 || nextx < 0 || nexty >= sero || nextx >= garo) {
				// 범위 밖을 나가거나 이미 방문을 했다면 더이상 신경쓰지 않는다.
				continue;
			}
			if (map[nexty][nextx] < map[y][x]) {
				DP[y][x] += DFS(nexty, nextx);
			}

		}
		return DP[y][x];
	}

}