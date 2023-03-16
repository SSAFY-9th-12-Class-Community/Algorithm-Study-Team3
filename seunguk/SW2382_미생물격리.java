package day02;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Stack;
import java.util.StringTokenizer;

public class SW2382_미생물격리 {
	static info[] map;
	static int N;
	static int M;
	static int K;

	public static void main(String[] args) throws IOException {
		BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st = new StringTokenizer(in.readLine());

		int tc = Integer.parseInt(st.nextToken());

		for (int test_case = 1; test_case <= tc; test_case++) {
			st = new StringTokenizer(in.readLine());

			N = Integer.parseInt(st.nextToken()); // 한 변의 셀의 개수
			M = Integer.parseInt(st.nextToken()); // 격리 시간
			K = Integer.parseInt(st.nextToken()); // 미생물 군집의 개수
			map = new info[K];
			int index = 0;

			for (int i = 0; i < K; i++) {
				// map에 특정 자리에 info 값들을 넣어준다.
				st = new StringTokenizer(in.readLine());
				int y = Integer.parseInt(st.nextToken());
				int x = Integer.parseInt(st.nextToken());
				int count = Integer.parseInt(st.nextToken());
				int dir = Integer.parseInt(st.nextToken());
				map[i] = new info(y, x, count, dir);

			}
			while (index < M) {
				// M번 반복한다.
				move(); // 단순하게 움직여주기
				merge(); // 움직인 이후 병합하기
				index += 1;
			}
			int answer = 0;
			for (int i = 0; i < map.length; i++) {
				answer += map[i].count;
			}

			System.out.println("#" + test_case + " " + answer);
		}

	}

	private static void move() {
		// 단순하게 움직여주는 과정
		// 간결하게 바꿔야함
		for (int i = 0; i < map.length; i++) {
			int y = map[i].y;
			int x = map[i].x;
			int count = map[i].count;
			int dir = map[i].dir;

			if (dir == 1) {
				// 상
				y -= 1;
				if (y == 0) {
					count /= 2;
					dir = 2;
				}
				map[i].y = y;
				map[i].count = count;
				map[i].dir = dir;
			} else if (dir == 2) {
				// 하
				y += 1;
				if (y == N - 1) {
					count /= 2;
					dir = 1;
				}
				map[i].y = y;
				map[i].count = count;
				map[i].dir = dir;

			} else if (dir == 3) {
				// 좌
				x -= 1;
				if (x == 0) {
					count /= 2;
					dir = 4;
				}
				map[i].x = x;
				map[i].count = count;
				map[i].dir = dir;

			} else if (dir == 4) {
				// 우
				x += 1;
				if (x == N - 1) {
					count /= 2;
					dir = 3;
				}
				map[i].x = x;
				map[i].count = count;
				map[i].dir = dir;

			}

		}

	}

	private static void merge() {
		Stack<locationt> stc = new Stack<>();
		boolean visited[] = new boolean[map.length];

		ArrayList<info> clone = new ArrayList<>();
		for (int i = 0; i < map.length; i++) {
			boolean isSelected[] = new boolean[map.length];
			if (visited[i] == true) {
				continue;
			}
			stc.add(new locationt(map[i].y, map[i].x)); // 스택에 y,x 값을 넣어주고, 해당 y,x 의 값과 같은 값들이 존재하는지 비교를 한다.
			visited[i] = true;
			isSelected[i] = true;
			for (int j = i + 1; j < map.length; j++) {
				if (j == map.length) {
					break;
				}
				if (map[j].y == stc.peek().y && map[j].x == stc.peek().x && visited[j] == false) {
					// 만약에 stack에 들어있는 좌표 값과 같은 위치에 있다면 + 방문하지 않았다면
					stc.add(new locationt(map[j].y, map[j].x));
					visited[j] = true;
					isSelected[j] = true;
				}
			}

			if (stc.size() > 1) {
				int compare = 0;
				int infoIndex = 0;
				int sum = 0;
				for (int t = 0; t < map.length; t++) {
					// 가장 최댓값과 그 최댓값을 가지고 있는 인덱스를 저장한다.
					if (isSelected[t] == true) {
						sum += map[t].count;
						if (compare <= map[t].count) {
							compare = map[t].count;
							infoIndex = t;
						}
					}
				}
				clone.add(new info(map[infoIndex].y, map[infoIndex].x, sum, map[infoIndex].dir));

			} else if (stc.size() == 1) {
				clone.add(new info(map[i].y, map[i].x, map[i].count, map[i].dir));
			}
			stc.clear();
		}
		map = new info[clone.size()];
		for (int i = 0; i < clone.size(); i++) {
			map[i] = clone.get(i);
		}
	}

}

class info {
	int y;
	int x;
	int count;
	int dir;

	public info(int y, int x, int count, int dir) {
		super();
		this.y = y;
		this.x = x;
		this.count = count;
		this.dir = dir;
	}

}

class locationt {
	int y;
	int x;

	public locationt(int y, int x) {
		super();
		this.y = y;
		this.x = x;
	}

}
