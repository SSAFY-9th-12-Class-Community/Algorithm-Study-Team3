package study;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.LinkedList;
import java.util.Queue;
import java.util.StringTokenizer;

public class BOJ_1012 {
	static boolean[][] maze;
	static int garo;
	static int sero;
	static int count;
	static int[] dx = { 0, 1, 0, -1 };
	static int[] dy = { 1, 0, -1, 0 };

	public static void main(String[] args) throws IOException {
		BufferedReader in = new BufferedReader(new InputStreamReader(System.in));

		int test_case = Integer.parseInt(in.readLine());

		for (int i = 0; i < test_case; i++) {
			StringTokenizer st = new StringTokenizer(in.readLine());
			garo = Integer.parseInt(st.nextToken());
			sero = Integer.parseInt(st.nextToken());
			count = 0;
			int howMany = Integer.parseInt(st.nextToken());

			maze = new boolean[sero][garo];

			for (int j = 0; j < howMany; j++) {
				st = new StringTokenizer(in.readLine());
				int k = Integer.parseInt(st.nextToken());
				int f = Integer.parseInt(st.nextToken());
				maze[f][k] = true;
			}

			for (int l = 0; l < sero; l++) {
				for (int m = 0; m < garo; m++) {
					if (maze[l][m] == true) {
						count += 1;

						bfs(l, m);
					}
				}
			}

			System.out.println(count);

		}

	}

	static public void bfs(int ny, int nx) {
		Queue<Point> que = new LinkedList<>();
		que.add(new Point(ny, nx));
		maze[ny][nx] = false;
		while (!que.isEmpty()) {
			Point tmp = que.poll();
			// maze[tmp.y][tmp.x] = false;
			for (int i = 0; i < 4; i++) {
				int nexty = tmp.y + dy[i];
				int nextx = tmp.x + dx[i];
				if (nextx < 0 || nexty < 0 || nexty >= sero || nextx >= garo || maze[nexty][nextx] == false) {
					continue;
				}
				if (maze[nexty][nextx] == true) {
					que.add(new Point(nexty, nextx));
					maze[nexty][nextx] = false;
				}
			}

		}

	}

}

class Point {
	public int y, x;

	Point(int y, int x) {

		this.y = y;
		this.x = x;
	}
}
