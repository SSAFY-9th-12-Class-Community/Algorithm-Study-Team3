import java.io.BufferedReader;				//			0
import java.io.IOException;					//		4	1	5
import java.io.InputStreamReader;			//			2
import java.util.StringTokenizer;			//			3

public class Main {
	static int[] dice = new int[] {2,1,5,6,4,3};
	static int score = 0;
	static int A = 6; // 주사위 아래
	static int B = 0; // 맵 바닥
	static int C = 0; // 연속으로 가는 바닥 갯수
	static int dir = 0;
	static int x = 0;
	static int y = 0;
	static int dir_x[] = new int[] {0,1,0,-1};
	static int dir_y[] = new int[] {1,0,-1,0}; 
	static int[][] map = null;
	
	public static void resetMap() {
		for(int i = 0; i < map.length; i++) {
			for(int j = 0; j < map[0].length; j++) {
				if(map[i][j] == 0) map[i][j] = B;
			}
		}
	}
	public static void moveDice() {
		int xx = x + dir_x[dir];
		int yy = y + dir_y[dir];
		if(xx < 0 || xx >= map.length || yy < 0 || yy >= map[0].length) {
			dir += 2;
			if(dir >= 4) dir %= 4;
			//dir = (dir + 2) >= 4 ? (dir + 2) % 4 : dir + 2; // NULL값 체크
		}
		x += dir_x[dir];
		y += dir_y[dir];
		replaceDice();
		C = 0;
		calculateScore(x, y);
		score += B * C;
		resetMap();
	}
	public static void replaceDice() {
		int temp;
		switch(dir) {	// 동 : 0, 남 : 1, 서 : 2, 북 : 3 
		case 0:	temp = dice[4]; dice[4] = dice[3]; dice[3] = dice[5]; dice[5] = dice[1]; dice[1] = temp; break;
		case 1: temp = dice[0]; dice[0] = dice[3]; dice[3] = dice[2]; dice[2] = dice[1]; dice[1] = temp; break;
		case 2: temp = dice[4]; dice[4] = dice[1]; dice[1] = dice[5]; dice[5] = dice[3]; dice[3] = temp; break;
		case 3: temp = dice[0]; dice[0] = dice[1]; dice[1] = dice[2]; dice[2] = dice[3]; dice[3] = temp;
		}
		A = dice[3]; // 이동 방향 결정
		B = map[x][y];
		if(A > B) {
			dir++;
			if(dir == 4) dir = 0; //dir = ((dir+1) == 4) ? 0 : dir++;
		}
		else if(A < B) {
			dir--;
			if(dir == -1) dir = 3; //dir = ((dir-1) == -1) ? 3 : dir--;
		}
	}
	public static void calculateScore(int a, int b) { // BFS
		C++;
		map[a][b] = 0;
		for(int i = 0; i < 4; i++){
			int aa = a + dir_x[i]; 
			int bb = b + dir_y[i];
			if(aa < 0 || aa >= map.length || bb < 0 || bb >= map[0].length) continue;
			if(map[aa][bb] == B) {
				//map[aa][bb] = 0;
				calculateScore(aa, bb);
			}
		}
	}
	public static void main(String[] args) throws IOException {
		BufferedReader bf = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st = new StringTokenizer(bf.readLine());
		int N = Integer.parseInt(st.nextToken()); // 세로
		int M = Integer.parseInt(st.nextToken()); // 가로
		int K = Integer.parseInt(st.nextToken()); // 명령 개수
		
		map = new int[N][M];
		for(int i = 0; i < N; i++) {
			st = new StringTokenizer(bf.readLine());
			for(int j = 0; j < M; j++) {
				map[i][j] = Integer.parseInt(st.nextToken());
			}
		}
		for(int i = 0; i < K; i++) {
			moveDice();
		}
	
		System.out.println(score);
	}
}
