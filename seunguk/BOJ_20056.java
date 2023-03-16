package day02;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Stack;
import java.util.StringTokenizer;

public class BOJ_20056 {
	static int[] dy = { -1, -1, 0, 1, 1, 1, 0, -1 };
	static int[] dx = { 0, 1, 1, 1, 0, -1, -1, -1 };
	static int N;
	static int M;
	static int K;
	static fireBall[] balls;

	public static void main(String[] args) throws IOException {
		BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st = new StringTokenizer(in.readLine());

		N = Integer.parseInt(st.nextToken()); // 격자 크기
		M = Integer.parseInt(st.nextToken()); // 파이어볼의 개수
		K = Integer.parseInt(st.nextToken()); // 이동 횟수

		balls = new fireBall[M];

		for (int i = 0; i < M; i++) {
			// ball의 정보를 넣어준다.
			st = new StringTokenizer(in.readLine());
			balls[i] = new fireBall(Integer.parseInt(st.nextToken()), Integer.parseInt(st.nextToken()),
					Integer.parseInt(st.nextToken()), Integer.parseInt(st.nextToken()),
					Integer.parseInt(st.nextToken()));
		}
		int index = 0;
		while (index < K) {
			move();
			merge();

			index += 1;
		}

		int answer = 0;
		for (int i = 0; i < balls.length; i++) {
			answer += balls[i].mass;
		}

		System.out.println(answer);
	}

	private static void move() {
		for (int i = 0; i < balls.length; i++) {
			int y = balls[i].y;
			int x = balls[i].x;
			int speed = balls[i].speed;
			int dir = balls[i].dir;

			y += (dy[dir] * speed);
			x += (dx[dir] * speed);

			if (y < 1) {
				y = y + N;

			} else if (y > N) {
				y = y % N;
			}

			if (x < 1) {
				x = x + N;

			} else if (x > N) {
				x = x % N;

			}
			// 바꿔줘야하는건 위치 정보 말고는 없다.
			balls[i].x = x;
			balls[i].y = y;
		}

	}

	private static void merge() {
		boolean[] visited = new boolean[balls.length];
		Stack<post> stc = new Stack<>();
		ArrayList<fireBall> clone = new ArrayList<>();
		for (int i = 0; i < balls.length; i++) {
			boolean[] isSelected = new boolean[balls.length];
			if (visited[i] == true) {
				continue;
			}
			stc.add(new post(balls[i].y, balls[i].x));
			visited[i] = true;
			isSelected[i] = true;
			for (int j = i + 1; j < balls.length; j++) {
				if (j == balls.length) {
					break;
				}
				if (stc.peek().y == balls[j].y && stc.peek().x == balls[j].x) {
					stc.add(new post(balls[j].y, balls[j].x));
					visited[j] = true;
					isSelected[j] = true;
				}

			}

			if (stc.size() > 1) {
				// 중복된 것이 있다는 의미
				int massSum = 0;
				int speedSum = 0;
				int count = 0; // 같은 좌표에 있는 파이어볼이 몇개인지
				int check = 0; // 좌표의 방향 값
				for (int t = 0; t < balls.length; t++) {
					if (isSelected[t] == true) {
						massSum += balls[t].mass;
						speedSum += balls[t].speed;
						count += 1;
						check += (balls[t].dir % 2);
					}
				}

				if (massSum / 5 > 0) {

					if (check == 0 || check == count) {
						// 방향이 0,2,4,6
						clone.add(new fireBall(stc.peek().y, stc.peek().x, massSum / 5, speedSum / count, 0));
						clone.add(new fireBall(stc.peek().y, stc.peek().x, massSum / 5, speedSum / count, 2));
						clone.add(new fireBall(stc.peek().y, stc.peek().x, massSum / 5, speedSum / count, 4));
						clone.add(new fireBall(stc.peek().y, stc.peek().x, massSum / 5, speedSum / count, 6));
					} else {
						// 방향이 1,3,5,7
						clone.add(new fireBall(stc.peek().y, stc.peek().x, massSum / 5, speedSum / count, 1));
						clone.add(new fireBall(stc.peek().y, stc.peek().x, massSum / 5, speedSum / count, 3));
						clone.add(new fireBall(stc.peek().y, stc.peek().x, massSum / 5, speedSum / count, 5));
						clone.add(new fireBall(stc.peek().y, stc.peek().x, massSum / 5, speedSum / count, 7));
					}
				}

			} else if (stc.size() == 1) {
				clone.add(new fireBall(balls[i].y, balls[i].x, balls[i].mass, balls[i].speed, balls[i].dir));
			}
			stc.clear();
		}

		balls = new fireBall[clone.size()];
		for (int i = 0; i < clone.size(); i++) {
			balls[i] = clone.get(i);
		}

	}

}

class fireBall {
	int y;
	int x;
	int mass;
	int speed;
	int dir;

	public fireBall(int y, int x, int mass, int speed, int dir) {
		super();
		this.y = y;
		this.x = x;
		this.mass = mass;
		this.speed = speed;
		this.dir = dir;
	}

}

class post {
	int y;
	int x;

	public post(int y, int x) {
		super();
		this.y = y;
		this.x = x;
	}

}
