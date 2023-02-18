import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class Solution {
	static int N;
	static int X;
	static int[][] map;
	static int countFlight;
	public static void main(String[] args) throws NumberFormatException, IOException {
		// 공통 : 경사로 설치 시 카운트 초기화
		// 1. X개 이상 연속되다가 현재보다 1 높은 수 O
		// 2. 현재보다 1 낮은 수가 나오면 최소 X개 이상 연속되어야 O
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringBuilder sb = new StringBuilder();
		StringTokenizer st = null;
		int T = Integer.parseInt(br.readLine());
		for(int t = 1; t <= T; t++) {
			st = new StringTokenizer(br.readLine());
			N = Integer.parseInt(st.nextToken());
			X = Integer.parseInt(st.nextToken());
			map = new int[N][N];
			for(int i = 0; i < N; i++) {
				st = new StringTokenizer(br.readLine());
				for(int j = 0; j < N; j++) {
					map[i][j] = Integer.parseInt(st.nextToken()); 
				}
			}
			countFlight = 0;
			buildFlight();
			System.out.printf("#%d %d\n", t, countFlight);
		}
	}
	
	static void buildFlight() {
		for(int i = 0; i < N; i++) { // 행 검사
			int isCntDown = 0; // 내리막 연속 검사 트리거
			int wayCnt = 1;
			for(int j = 0; j < N - 1; j++) {
				if(map[i][j] - map[i][j+1] == -1) { // 오르막
					if(isCntDown == 1) break;
					if(wayCnt < X) break; // 조건 만족 X
					wayCnt = 1;
				}
				else if(map[i][j] - map[i][j+1] == 1) { // 내리막
					if(isCntDown == 1) break;
					isCntDown = 1;
					wayCnt = 1;
				}
				else if(map[i][j] - map[i][j+1] == 0) { // 평지
					wayCnt++;
					if(isCntDown == 1 && wayCnt >= X) {
						isCntDown = 0; // 내리막 검사 끝
						wayCnt = 0;
					}
				}
				else break;
				if(j == N-2 && isCntDown == 0) {
					countFlight++;
//					System.out.printf("%d행 활주로 건설가능, 총 활주로 : %d\n", i + 1, countFlight);
				}
			}
		}
		for(int i = 0; i < N; i++) { // 열 검사
			int wayCnt = 1;
			int isCntDown = 0;
			for(int j = 0; j < N - 1; j++) {
				if(map[j][i] - map[j+1][i] == -1) { // 오르막
					if(isCntDown == 1) break;
					if(wayCnt < X) break; // 조건 만족 X
					wayCnt = 1;
				}
				else if(map[j][i] - map[j+1][i] == 1) { // 내리막
					if(isCntDown == 1) break;
					isCntDown = 1;
					wayCnt = 1;
				}
				else if(map[j][i] - map[j+1][i] == 0) { // 평지
					wayCnt++;
					if(isCntDown == 1 && wayCnt >= X) {
						isCntDown = 0; // 내리막 검사 끝
						wayCnt = 0;
					}
				}
				else break;
				if(j == N-2 && isCntDown == 0) {
					countFlight++;
//					System.out.printf("%d열 활주로 건설가능, 총 활주로 : %d\n", i + 1, countFlight);
				}
			}
		}
	}
}
