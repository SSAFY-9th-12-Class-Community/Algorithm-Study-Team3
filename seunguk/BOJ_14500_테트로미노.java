package day03;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class BOJ_14500_테트로미노 {
	static int sero, garo;
	static int[][] map;
	static int dy[] = { -1, 0, 1, 0 };
	static int dx[] = { 0, 1, 0, -1 };
	static int answer = Integer.MIN_VALUE;

	public static void main(String[] args) throws IOException {
		BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st = new StringTokenizer(in.readLine());
		sero = Integer.parseInt(st.nextToken());
		garo = Integer.parseInt(st.nextToken());
		map = new int[sero][garo];
		boolean visited[][] = new boolean[sero][garo];
		for (int i = 0; i < sero; i++) {
			st = new StringTokenizer(in.readLine());
			for (int j = 0; j < garo; j++) {
				map[i][j] = Integer.parseInt(st.nextToken());
			}
		}
		for (int i = 0; i < sero; i++) {
			for (int j = 0; j < garo; j++) {
				visited[i][j] = true;
				dfs(1, i, j, map[i][j], visited);
				visited[i][j] = false;
			}
		}
		System.out.println(answer);

	}

	public static void dfs(int cnt, int y, int x, int sum, boolean[][] visited) {

		if (cnt == 4) {
			if (sum >= answer) {
				answer = sum;
			}
			return;
		}
		if (cnt == 2) {
			for (int i = 0; i < 4; i++) {
				int nexty = y + dy[i];
				int nextx = x + dx[i];
				if (nextx < 0 || nexty < 0 || nextx >= garo || nexty >= sero || visited[nexty][nextx]) {
					continue;
				}
				visited[nexty][nextx] = true;
				dfs(cnt + 1, y, x, sum + map[nexty][nextx], visited);
				visited[nexty][nextx] = false;
			}
		}

		for (int i = 0; i < 4; i++) {
			int nexty = y + dy[i];
			int nextx = x + dx[i];

			if (nextx < 0 || nexty < 0 || nextx >= garo || nexty >= sero || visited[nexty][nextx]) {
				continue;
			}
			visited[nexty][nextx] = true;
			dfs(cnt + 1, nexty, nextx, sum + map[nexty][nextx], visited);
			visited[nexty][nextx] = false;
		}

	}

}
