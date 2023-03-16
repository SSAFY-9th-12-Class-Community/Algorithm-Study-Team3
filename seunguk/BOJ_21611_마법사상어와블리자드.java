package day03;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.StringTokenizer;

public class BOJ_21611_마법사상어와블리자드 {
	static int len, blizzardCount;
	static int[][] map;
	static int[] dy = { 0, -1, 1, 0, 0 }; // 0번째는 필요없어서 임의로 0을 넣음
	static int[] dx = { 0, 0, 0, -1, 1 }; // 0번째는 필요없어서 임의로 0을 넣음
	static int sharkY, sharkX;
	static int bomb;
	static int[] bombList;

	public static void main(String[] args) throws IOException {
		BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st = new StringTokenizer(in.readLine());

		len = Integer.parseInt(st.nextToken()); // map의 한 변의 길이
		sharkY = (len + 1) / 2;
		sharkX = (len + 1) / 2;
		blizzardCount = Integer.parseInt(st.nextToken()); // 블리자드를 쓰는 횟수
		map = new int[len + 1][len + 1];
		bombList = new int[4]; // 1, 2, 3을 표현하기 위해서
		for (int i = 1; i <= len; i++) {
			st = new StringTokenizer(in.readLine());
			for (int j = 1; j <= len; j++) {
				map[i][j] = Integer.parseInt(st.nextToken());
			}
		}

		for (int i = 0; i < blizzardCount; i++) {
			st = new StringTokenizer(in.readLine());
			int dir = Integer.parseInt(st.nextToken());
			int power = Integer.parseInt(st.nextToken());
			blizzard(dir, power);
		}

		int answer = 1 * bombList[1] + 2 * bombList[2] + 3 * bombList[3];
		System.out.println(answer);
	}

	static void blizzard(int dir, int power) {
		int p = 0;
		int nowy = sharkY;
		int nowx = sharkX;
		// 블리자드를 쓴 직후의 맵의 모습
		while (p < power) {
			nowy += dy[dir];
			nowx += dx[dir];
			if (nowy < 1 || nowx < 1 || nowx > len || nowy > len) {
				break;
			}
			map[nowy][nowx] = 0;
			p += 1;
		}
		makeString();
	}

	static void makeString() {
		// 문자열로 바꿔주는 함수
		String temp = "";
		int nowy = sharkY;
		int nowx = sharkX;
		int index = 1;
		boolean flag = true;
		// 문자열로 바꿔주는 과정
		while (true) {
			for (int i = 0; i < index; i++) {
				nowx -= 1;
				if (nowy == 1 && nowx == 1) {
					// 1행 1열까지 확인하면 이제 나가야한다.
					temp += map[nowy][nowx];
					flag = false;
					break;
				}
				temp += map[nowy][nowx];
			}
			if (!flag) {
				// 1행 1열을 확인했으므로 이제 나가야 한다.
				break;
			}
			for (int i = 0; i < index; i++) {
				nowy += 1;
				temp += map[nowy][nowx];
			}
			index += 1;
			for (int i = 0; i < index; i++) {
				nowx += 1;
				temp += map[nowy][nowx];
			}
			for (int i = 0; i < index; i++) {
				nowy -= 1;
				temp += map[nowy][nowx];
			}
			index += 1;
		}
		bomb = 1;
		char[] check = temp.toCharArray(); // 현재 temp는 사이에 블리자드로 인한 0들이 들어있는 문자열이다.
		zeroExclude(check); // 사이에 있는 0을 1회 지워준다.
		while (bomb != 0) {
			explosion(check); // 사이에 같은 숫자들을 폭발시킨다.
			zeroExclude(check); // 사이에 있는 0들을 지워준다.
		}
		lengthen(check);
	}

	static void zeroExclude(char[] check) {
		// 사이에 있는 0들을 지워준다.
		LinkedList<Character> list = new LinkedList<>();
		for (int i = 0; i < check.length; i++) {
			list.add(check[i]);
		}
//		for (int i = 0; i < check.length - 1; i++) {
//			if (check[i] == '0') {
//				for (int j = i; j < check.length - 1; j++) {
//					check[j] = check[j + 1];
//				}
//				check[check.length - 1] = '9';
//				i -= 1;
//			}
//		}
		for (int i = 0; i < list.size(); i++) {
			if (list.get(i) == '0') {
				list.remove(i);
				list.add('9');
				i -= 1;
			}
		}
		for (int i = 0; i < check.length; i++) {
			check[i] = list.get(i);
		}
	}

	static void explosion(char[] check) {
		int same = 0;
		bomb = 0; // bomb 는 폭발이 계속 일어나고 있는지 여부를 확인하기 위한 용도이다. => while을 돌리기 위해서
		for (int i = 0; i < check.length - 1; i++) {
			if (check[i] == check[i + 1] && check[i] != '9') {
				same += 1;
			} else {
				// 앞뒤가 다른 경우
				if (same >= 3) {
					if (check[i] == '1') {
						// 값이 1인데 폭발이 일어나는 경우
						bombList[1] += (same + 1);
						for (int j = i - same; j <= i; j++) {
							check[j] = '0';
						}
						bomb = 1;
						same = 0;

					} else if (check[i] == '2') {
						// 값이 2인데 폭발이 일어나는 경우
						bombList[2] += (same + 1);
						for (int j = i - same; j <= i; j++) {
							check[j] = '0';
						}
						bomb = 1;
						same = 0;

					} else if (check[i] == '3') {
						// 값이 3인데 폭발이 일어나는 경우
						bombList[3] += (same + 1);
						for (int j = i - same; j <= i; j++) {
							check[j] = '0';
						}
						bomb = 1;
						same = 0;

					}
					// 이게 폭발이 일어나는 경우에 해당한다.
				} else {
					same = 0;
				}
			}
		}
	}

	static void lengthen(char[] check) {
		// 연쇄폭발이 끝난 이후 map에 들어갈 문자열을 만들어주는 함수
		String temp = "";
		int idx = 0;
		int same = 0;
		while (check[idx] != '9') {
			if (check[idx] == check[idx + 1]) {
				same += 1;
				idx += 1;
			} else {
				temp += (same + 1);
				temp += check[idx];
				same = 0;
				idx += 1;
			}
		}
		int t = temp.length();
		if (temp.length() < len * len) {
			for (int i = 1; i < (len * len) - t; i++) {
				temp += '0';
			}
		}
		char[] check1 = temp.toCharArray();
		makeMap(check1);
	}

	static void makeMap(char[] check) {
		// lengthen으로 인해 만들어진 문자열을 map에 넣어주는 함수
		int nowy = sharkY;
		int nowx = sharkX;
		int index = 1;
		int sd = 0;
		boolean flag = true;
		while (true) {
			for (int i = 0; i < index; i++) {
				nowx -= 1;
				if (nowx == 1 && nowy == 1) {
					map[nowy][nowx] = check[sd++] - 48;
					flag = false;
					break;
				}
				map[nowy][nowx] = check[sd++] - 48;
			}
			if (flag == false) {
				break;
			}

			for (int i = 0; i < index; i++) {
				nowy += 1;
				map[nowy][nowx] = check[sd++] - 48;
			}
			index += 1;
			for (int i = 0; i < index; i++) {
				nowx += 1;
				map[nowy][nowx] = check[sd++] - 48;
			}
			for (int i = 0; i < index; i++) {
				nowy -= 1;
				map[nowy][nowx] = check[sd++] - 48;
			}
			index += 1;
		}
	}
}