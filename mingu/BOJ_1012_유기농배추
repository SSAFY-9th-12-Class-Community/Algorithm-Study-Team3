import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class BOJ_1012_유기농배추 {
	static int[] dx = new int[] {1,0,-1,0};
	static int[] dy = new int[] {0,1,0,-1};
	static int[][] arr;
	static int cnt;
	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st = null;
		
		int T = Integer.parseInt(br.readLine());
		
		for(int i = 0; i < T; i++) {
			st = new StringTokenizer(br.readLine());
			int M = Integer.parseInt(st.nextToken());
			int N = Integer.parseInt(st.nextToken());
			int K = Integer.parseInt(st.nextToken());
			arr = new int [M][N];
			for(int j = 0; j < K; j++) {
				st = new StringTokenizer(br.readLine());
				arr[Integer.parseInt(st.nextToken())][Integer.parseInt(st.nextToken())] =  1;
			}
			cnt = 0;
			CountWorms();
			System.out.println(cnt);
		}
		br.close();
	}
	
	public static void CountWorms() {
		for(int i = 0; i < arr.length; i++) {
			for(int j = 0; j < arr[i].length; j++) {
				if(arr[i][j] == 1) {
					arr[i][j] = 0;
					Move4way(i,j);
					cnt++;
				}
			}
		}
	}
	
	public static void Move4way(int x, int y) {
		for(int i = 0; i < 4; i++) {
			int xx = x+dx[i];
			int yy = y+dy[i];
			if(xx < 0 || xx >= arr.length || yy < 0 || yy >= arr[0].length) continue;
			if(arr[xx][yy] == 1) {
				arr[xx][yy] = 0;
				Move4way(xx, yy);
			}
		}
	}
}
