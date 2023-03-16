import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.Arrays;

public class SW_4014_활주로건설 {
	
	private static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
	private static BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
	private static int[][] arr = null;
	private static int n, len;
	
	public static void main(String[] args) throws NumberFormatException, IOException {
		int testCase = Integer.parseInt(br.readLine());
		
		for(int tc=1; tc<=testCase; ++tc) {
			input();
			
			int answer = 0;
			for(int i=0; i<n; ++i) {
				if(isValidRow(i)) ++answer;
				if(isValidColumn(i)) ++ answer;
			}
			bw.write("#"+tc+' '+answer+'\n');
		}
		bw.flush();
		bw.close();
		br.close();
	}
	
	private static boolean isBuildableRow(boolean[] isVisit, int row, int col, int diff) {
		int startIdx = diff > 0 ? col : col-1;
		int range = diff > 0 ? isVisit.length : -1;
		int cnt = 0;
		int startValue = arr[row][startIdx];
		
		boolean ret = false;
		for(int i = startIdx; i!=range; i+=diff) {
			if(isVisit[i] || arr[row][i] != startValue) break;
			if(++cnt == len) {
				ret = true;
				break;
			}
		}
		
		if(ret) {
			while(cnt-- > 0) {
				isVisit[startIdx] = true;
				startIdx+=diff;
			}
		}
		return ret;
	}

	private static boolean isValidRow(int target) {
		boolean[] isVisit = new boolean[arr.length];
		
		int prev = arr[target][0];
		for(int i=1; i<arr.length; ++i) {
			int diff = prev-arr[target][i];
			if(diff == 0) continue;
			if(Math.abs(diff) > 1 || !isBuildableRow(isVisit, target, i, diff)) return false;
			
			prev = arr[target][i];
			if(diff > 0) i = i+len-1;
		}
		return true;
	}
	
	private static boolean isBuildableColumn(boolean[] isVisit, int row, int col, int diff) {
		int startIdx = diff > 0 ? row : row-1;
		int range = diff > 0 ? isVisit.length : -1;
		int cnt = 0;
		int startValue = arr[startIdx][col];
		
		boolean ret = false;
		for(int i = startIdx; i!=range; i+=diff) {
			if(isVisit[i] || arr[i][col] != startValue) break;
			if(++cnt == len) {
				ret = true;
				break;
			}
		}
		
		if(ret) {
			while(cnt-- > 0) {
				isVisit[startIdx] = true;
				startIdx+=diff;
			}
		}
		return ret;
	}
	
	private static boolean isValidColumn(int target) {
		boolean[] isVisit = new boolean[arr.length];
		
		int prev = arr[0][target];
		for(int i=1; i<arr.length; ++i) {
			int diff = prev-arr[i][target];
			if(diff == 0) continue;
			if(Math.abs(diff) > 1 || !isBuildableColumn(isVisit, i, target, diff)) return false;
			
			prev = arr[i][target];
			if(diff > 0) i = i+len-1;
		}
		return true;
	}
	
	private static void input() throws IOException {
		int[] nx = read();
		n = nx[0];
		len = nx[1];
		
		arr = new int[n][];
		for(int i=0; i<n; ++i) arr[i] = read();
	}

	private static int[] read() throws IOException {
		return Arrays.stream(br.readLine().split(" ")).mapToInt(Integer::parseInt).toArray();
	}
}
