package study;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class Swexpert4014 {
	public static void main(String[] args) throws NumberFormatException, IOException {
		BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st;
		int tc = Integer.parseInt(in.readLine());

		for (int test_case = 1; test_case <= tc; test_case++) {
			st = new StringTokenizer(in.readLine());

			int length = Integer.parseInt(st.nextToken()); // 6입력
			int needLength = Integer.parseInt(st.nextToken()); // 2입력

			int[][] map = new int[length][length]; // 지도 만들고

			for (int i = 0; i < length; i++) {
				st = new StringTokenizer(in.readLine());
				for (int j = 0; j < length; j++) {
					map[i][j] = Integer.parseInt(st.nextToken());
				}
			}

			// 여기서부터 한 줄을 읽으면서 가능 불가능 여부를 판단
			// 먼저 가로를 판단해보자
			int answer = 0; // 정답 출력용
			int te = 0;
			for (int i = 0; i < length; i++) {
				int up = 3;// 시작은 3
				int temp = 1;
				int count = 0; // 출발 지점인지를 확인용으로 추가
				boolean roop = true;
				for (int j = 0; j < length - 1; j++) {
					if (map[i][j] == map[i][j + 1]) {
						// System.out.println("양옆이 같다");

						temp += 1;
					} else if (map[i][j] != map[i][j + 1]) {
						// 양 옆의 길이가 다르다면?
						if (map[i][j] > map[i][j + 1]) {
							// System.out.println("내려간다");
							up = 1; // 내려가는 경우
							if (map[i][j] - map[i][j + 1] == 1) {
								if (count == 0) {
									// System.out.println("첫 하강");
									temp = 1;
									count += 1;
								} else {
									if (temp < needLength) {
										// System.out.println("나와1");
										// 모은 길이가 경사로를 만들기에 불충분한 경우에는 break를 한다
										roop = false;
										break;
									} else {
										// System.out.println("잘내려왔어");
										// 이제 스무스하게 내려가면서 경사로의 길이를 1로 초기화한다
										temp = 1;
									}
								}
							} else {
								// System.out.println("이런일은 없지?2");
								// 이건 어차피 안되는거니까
								roop = false;
								break;
							}

						} else if (map[i][j] < map[i][j + 1]) {
							// System.out.println("올라간다");

							if (up == 1) {
								// 이건 결국 내려왔다가 올라가는 경우를 의미한다고 볼 수 있다.
								if (temp < 2 * needLength) {
									// 이 경우에는 골짜기 모양을 채우기 부족하다
									roop = false;
									break;
								} else {
									count = 0; // 이미 한번 올라온 상황이기에 그냥 내려가도 노상관
									up = 2;
									temp = 1;

								}
							} else {
								up = 2;
								if (map[i][j + 1] - map[i][j] == 1) {
									if (temp < needLength) {
										// System.out.println("나와2");
										// 모은 길이가 경사로를 만들기에 불충분한 경우에는 break를 한다.
										roop = false;
										break;
									} else {
										// System.out.println("잘 올라갔어");
										temp = 1;
										// 만약에 이렇게 끝나는 경우?
									}

								} else {
									// System.out.println("이런일은 없지?2");
									roop = false;
									break;
								}
							}

						}

					}

				}

				if (roop) {
					// 여기에는 어떤 경우에 answer +=1이 되는지를 추가해줘야한다.
					if (up == 3) {
						// System.out.println("3인 경우 더한다");
						answer += 1; // 고저가 전혀 바뀌지 않은 경우
					} else if (up == 1 && temp >= needLength) {
						// System.out.println("1인 경우 더한다");
						answer += 1;
					} else if (up == 2) {
						// System.out.println("2인 경우 더한다");
						answer += 1;
					}
				}

			}
			// System.out.println("현재 가로끝나고:" + answer);
			te = answer;
			// System.out.println("가로가 끝난이후 answer:" + answer);
			// 이제 세로를 세보자

			for (int j = 0; j < length; j++) {
				int up = 3;// 시작은 3
				int temp = 1;
				int count = 0; // 출발 지점인지를 확인용으로 추가
				boolean roop = true;
				for (int i = 0; i < length - 1; i++) {
					if (map[i][j] == map[i + 1][j]) {
						// System.out.println("위 아래가 같다");

						temp += 1;
					} else if (map[i][j] != map[i + 1][j]) {
						// 위 아래의 길이가 다르다면?
						if (map[i][j] > map[i + 1][j]) {
							// System.out.println("내려간다");
							up = 1; // 내려가는 경우
							if (map[i][j] - map[i + 1][j] == 1) {
								if (count == 0) {
									// System.out.println("첫 하강");
									temp = 1;
									count += 1;
								} else {
									if (temp < needLength) {
										// System.out.println("나와1");
										// 모은 길이가 경사로를 만들기에 불충분한 경우에는 break를 한다
										roop = false;
										break;
									} else {
										// System.out.println("잘내려왔어");
										// 이제 스무스하게 내려가면서 경사로의 길이를 1로 초기화한다
										temp = 1;
									}
								}
							} else {
								// System.out.println("이런일은 없지?1");
								// 이건 어차피 안되는거니까
								roop = false;
								break;
							}

						} else if (map[i][j] < map[i + 1][j]) {
							// System.out.println("올라간다");

							if (up == 1) {
								// 이건 결국 내려왔다가 올라가는 경우를 의미한다고 볼 수 있다.
								if (temp < 2 * needLength) {
									// 이 경우에는 골짜기 모양을 채우기 부족하다
									roop = false;
									break;
								} else {
									count = 0; // 이미 한번 올라온 상황이기에 그냥 내려가도 노상관
									up = 2;
									temp = 1;

								}
							} else {
								up = 2; // 올라가는 경우
								if (map[i + 1][j] - map[i][j] == 1) {
									if (temp < needLength) {
										// System.out.println("나와2");
										// 모은 길이가 경사로를 만들기에 불충분한 경우에는 break를 한다.
										roop = false;
										break;
									} else {
										// System.out.println("잘 올라갔어");

										temp = 1;
										// 만약에 이렇게 끝나는 경우?
									}

								} else {
									// System.out.println("이런일은 없지?2");
									roop = false;
									break;
								}

							}

						}

					}

				}

				if (roop) {
					// 여기에는 어떤 경우에 answer +=1이 되는지를 추가해줘야한다.
					if (up == 3) {
						answer += 1; // 고저가 전혀 바뀌지 않은 경우
					} else if (up == 1 && temp >= needLength) {
						answer += 1;
					} else if (up == 2) {
						answer += 1;
					}
				}

			}
			// System.out.println("세로만: " + (answer - te));
			System.out.println("#" + test_case + " " + answer);

		}
	}

}