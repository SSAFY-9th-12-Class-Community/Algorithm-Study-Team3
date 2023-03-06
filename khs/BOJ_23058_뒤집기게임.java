import java.io.BufferedReader;
import java.io.InputStreamReader;

public class BOJ_23058_뒤집기게임 {
	
	private static int N;
	private static int[] grid;
	private static int REVERSE = 0;
	
	public static void main(String[] args) throws Exception {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		N = Integer.parseInt(br.readLine());
		grid = new int[N];
		for(int i=0; i<N; ++i) {
			grid[i] = Integer.parseInt(br.readLine().replace(" ", ""), 2);
		}
		REVERSE = (1<<N)-1;
		
		System.out.println(getMinValue(0, 0));
	}
	
	private static int getMinValue(int idx, int cost) {
		if(idx == N) {
			int cnt = 0;
			for(int i=0; i<N; ++i) {
				for(int j=0; j<N; ++j) {
					if((grid[i] & (1<<j)) != 0) ++cnt;
				}
			}
			return cost+Math.min(N*N-cnt, cnt);
		}
		
		// 아무 행, 열도 선택하지 않는 경우 
		int ret = getMinValue(idx+1, cost);
		grid[idx] ^= REVERSE;
		
		// 현재 행만 선택하는 경우 
		ret = Math.min(ret, getMinValue(idx+1, cost+1));
		reverseColumn(idx);
		
		// 현재 행열을 다 선택하는 경우
		ret = Math.min(ret, getMinValue(idx+1, cost+2));
		grid[idx] ^= REVERSE;
		
		// 현재 행만 선택하는 경우
		ret = Math.min(ret, getMinValue(idx+1, cost+1));
		reverseColumn(idx);
		return ret;
	}
	
	private static void reverseColumn(int columnNumber) {
		for(int i=0; i<N; ++i) grid[i] ^= (1<<(N-1-columnNumber));
	}
}
