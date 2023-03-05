import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.StringTokenizer;

public class BOJ_14500 {
	static int[][] map;
	static int N, M;
	static boolean[][] visited;
	static int answer = 0;
	static int[] dx = {-1, 1, 0, 0};
	static int[] dy = {0, 0, -1, 1};
	static int[][][] castle = {{{0, -1}, {0, 1}, {-1, 0}}, {{-1, 0}, {0, 1}, {1, 0}}, {{-1, 0}, {0, -1}, {1, 0}}, {{0, -1}, {1, 0}, {0, 1}}}; // 성 모양은 따로 체

	public static void main(String[] args) throws Exception {
		inputValues();
		bruteForce();
	}
	
	private static void inputValues() throws Exception {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st = new StringTokenizer(br.readLine());
		
		N = Integer.parseInt(st.nextToken());
		M = Integer.parseInt(st.nextToken());
		map = new int[N][];
		
		for(int i = 0; i < N; i++) {
			map[i] = Arrays.stream(br.readLine().split(" ")).mapToInt(Integer::parseInt).toArray();
		}
		
		visited = new boolean[N][M];
	}
	
	private static void bruteForce() {
		for(int i = 0; i < N; i++) {
			for(int j = 0; j < M; j++) {
				visited[i][j] = true;
				dfs(i, j, 0, map[i][j]);
				visited[i][j] = false;
				castleBfs(i, j, map[i][j]);
			}
		}
		System.out.println(answer);
	}
	
	private static void dfs(int x, int y, int cnt, int sum) {
		if(cnt == 3) { // 기저 조건 
			answer = Math.max(answer, sum);
			return;
		}
		
		for(int d = 0; d < 4; d++) {
			int nx = x + dx[d], ny = y + dy[d];
			
			if(!isInBoundary(nx, ny)|| visited[nx][ny]) continue;
			visited[nx][ny] = true;
			dfs(nx, ny, cnt + 1, sum + map[nx][ny]);
			visited[nx][ny] = false;
		}
	}
	
	private static void castleBfs(int x, int y, int sum) {
		for(int[][] arr : castle) {	
			int nx1 = x + arr[0][0], ny1 = y + arr[0][1], nx2 = x + arr[1][0], ny2 = y + arr[1][1], nx3 = x + arr[2][0], ny3 = y + arr[2][1];
			if(isInBoundary(nx1, ny1) && isInBoundary(nx2, ny2) && isInBoundary(nx3, ny3)) {
				answer = Math.max(answer, sum + map[nx1][ny1] + map[nx2][ny2] + map[nx3][ny3]);
				
			}
		}
	}
	
	private static boolean isInBoundary(int x, int y) {
		if(x < 0 || x >= N || y < 0 || y >= M)  return false;
		return true;
	}
}