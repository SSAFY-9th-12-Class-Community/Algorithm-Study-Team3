import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class BOJ_1520_내리막길 {
	
	private static int M, N;
	private static int[][] grid, dp;
	private static int[][] dirs = {{-1, 0}, {0, 1}, {1, 0}, {0, -1}};
	
	
	public static void main(String[] args) throws Exception {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st = new StringTokenizer(br.readLine());
		
		M = Integer.parseInt(st.nextToken());
		N = Integer.parseInt(st.nextToken());
		grid = new int[M][N];
		dp = new int[M][N];
		
		for(int i=0; i<M; ++i) {
			st = new StringTokenizer(br.readLine());
			for(int j=0; j<N; ++j) {
				grid[i][j] = Integer.parseInt(st.nextToken());
				dp[i][j] = -1;
			}
		}
		System.out.println(getAnswer(M-1, N-1));
	}
	
	private static boolean isValid(int row, int col) {
		return (row>=0 && row < M) && (col >=0 && col < N);
	}
	
	private static int getAnswer(int row, int col) {
		if(row == 0 && col == 0) return 1;
		if(dp[row][col] != -1) return dp[row][col];
		
		dp[row][col] = 0;
		for(int[] dir: dirs) {
			int ny = row + dir[0];
			int nx = col + dir[1];
			
			if(!isValid(ny, nx) || grid[row][col] >= grid[ny][nx]) continue;
			dp[row][col] += getAnswer(ny, nx);
		}
		return dp[row][col];
	}
}
