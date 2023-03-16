package study;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class BOJ_17398_통신망분할 {
	static int N, M, Q;
	static int[] parents;
	static int[][] edge;
	static int[] order;
	static boolean[] check;

	public static void main(String[] args) throws IOException {
		BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st = new StringTokenizer(in.readLine());

		N = Integer.parseInt(st.nextToken()); // 통신탑의 개수
		M = Integer.parseInt(st.nextToken()); // 통신탑 사이의 연결의 개수
		Q = Integer.parseInt(st.nextToken()); // 통신망 연결 분할 횟수
		parents = new int[N + 1];
		edge = new int[M + 1][2];
		order = new int[Q + 1];
		check = new boolean[M + 1];
		for (int i = 1; i <= N; i++) {
			parents[i] = -1;
		}
		for (int i = 1; i <= M; i++) {
			st = new StringTokenizer(in.readLine());
			int a = Integer.parseInt(st.nextToken());
			int b = Integer.parseInt(st.nextToken());

			edge[i][0] = a;
			edge[i][1] = b;
		}
		for (int i = 1; i <= Q; i++) {
			st = new StringTokenizer(in.readLine());
			int k = Integer.parseInt(st.nextToken());
			order[i] = k;
			check[k] = true;
		}
		for (int i = 1; i <= M; i++) {
			// 나중에 지워야할 부분이 아닌 경우는 전부 연결해준다.=> 전체 연결 중 나중에 지우는 부분이 아닌 경우를 연결
			if (check[i])
				continue;
			union(edge[i][0], edge[i][1]);
		}
		long ans = 0;
		for (int i = Q; i >= 1; i--) {
			int idx = order[i]; // 역순으로 하나씩 더해준다.
			if (find(edge[idx][0]) == find(edge[idx][1])) {
				union(edge[idx][0], edge[idx][1]);
			} else {
				ans += (long) parents[find(edge[idx][0])] * (long) parents[find(edge[idx][1])];
				union(edge[idx][0], edge[idx][1]);
			}
		}
		System.out.println(ans);

	}

	static int find(int x) {
		if (parents[x] < 0)
			return x;
		return parents[x] = find(parents[x]);
	}

	static void union(int x, int y) {
		int px = find(x);
		int py = find(y);
		if (px == py)
			return;
		parents[px] += parents[py];
		parents[py] = px;

	}

}
