package day03;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.Queue;
import java.util.StringTokenizer;

public class BOJ_1525_퍼즐 {
	static int[] dy = { -1, 0, 1, 0 };
	static int[] dx = { 0, 1, 0, -1 };
	static String answer = "123456780";
	static Map<String, Integer> map = new HashMap<>();

	public static void main(String[] args) throws IOException {
		BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st;

		String start = "";
		for (int i = 0; i < 3; i++) {
			st = new StringTokenizer(in.readLine());
			for (int j = 0; j < 3; j++) {
				int a = Integer.parseInt(st.nextToken());
				start += a;
			}
		}
		map.put(start, 0);// 문자열과 움직인 횟수를 표현한다.

		System.out.println(bfs(start));
	}

	public static int bfs(String start) {
		Queue<String> que = new LinkedList<>();
		que.add(start);
		while (!que.isEmpty()) {
			String standard = que.poll();
			int move = map.get(standard);
			int getLoc = standard.indexOf('0'); // 문자열 상에서 0이 속한 위치를 알아낸다.
			int y = getLoc / 3;
			int x = getLoc % 3;

			if (standard.equals(answer)) {
				return move;
			}
			for (int i = 0; i < 4; i++) {
				int nexty = y + dy[i];
				int nextx = x + dx[i];
				if (nextx < 0 || nexty < 0 || nextx > 2 || nexty > 2) {
					// 범위 밖으로 나가게 된다면?
					continue;
				}
				// 그 외의 경우에는 순서를 바꿔줘야한다.
				int chasing = 3 * nexty + nextx; // 현재 기준이 되는 위치에 있는 값과 0의 값을 바꿔줘야 한다.
				char ch = standard.charAt(chasing);

				String next = standard.replace(ch, 'c');
				String next1 = next.replace('0', ch);
				String next2 = next1.replace('c','0');

				if (!map.containsKey(next2)) {
					que.add(next2);
					map.put(next2, move + 1);
				}

			}
		}
		return -1;
	}

}