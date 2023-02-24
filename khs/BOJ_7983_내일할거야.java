import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.StringTokenizer;

public class BOJ_7983_내일할거야 {
	
	private static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
	private static Assignment[] assignments;
	
	public static void main(String[] args) throws Exception {
		int N = Integer.parseInt(br.readLine());
		assignments = new Assignment[N];
		for(int i=0; i<N; ++i) {
			StringTokenizer st = new StringTokenizer(br.readLine());
			assignments[i] = new Assignment(Integer.parseInt(st.nextToken()), Integer.parseInt(st.nextToken()));
		}
		Arrays.sort(assignments, (a1, a2)->a2.deadLine-a1.deadLine);
		
		int prevJobStartTime = Integer.MAX_VALUE;
		for(int i=0; i<N; ++i) {
			if(prevJobStartTime <= assignments[i].deadLine) {
				assignments[i].deadLine = prevJobStartTime-1;
			}
			prevJobStartTime = assignments[i].deadLine - assignments[i].durationTime + 1;
		}
		
		System.out.println(prevJobStartTime-1);
	}
	
	static class Assignment {
		int durationTime, deadLine;
		
		Assignment(int durationTime, int deadLine) {
			this.durationTime = durationTime;
			this.deadLine = deadLine;
		}
	}
}



/*
초기 접근 이분탐색 
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.StringTokenizer;

public class BOJ_7983_내일할거야 {
	
	private static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
	private static Assignment[] assignments;
	
	public static void main(String[] args) throws Exception {
		int N = Integer.parseInt(br.readLine());
		assignments = new Assignment[N];
		for(int i=0; i<N; ++i) {
			StringTokenizer st = new StringTokenizer(br.readLine());
			assignments[i] = new Assignment(Integer.parseInt(st.nextToken()), Integer.parseInt(st.nextToken()));
		}
		Arrays.sort(assignments, (a1, a2)->a1.minStartTime-a2.minStartTime);
		
		int left = 0, right = assignments[0].minStartTime;
		
		while(left<=right) {
			int mid = left+((right-left)/2);
			int cnt = 0;
			
			long total = mid;
			for(int i=0; i<N; ++i) {
				total += assignments[i].durationTime;
				if(total-1 > assignments[i].deadLine) break;
				++cnt;
			}
			
			if(cnt < N) right = mid-1;
			else left = mid+1;
		}
		System.out.println(left-2);
	}
	
	static class Assignment {
		int durationTime, deadLine;
		int minStartTime;
		
		Assignment(int durationTime, int deadLine) {
			this.durationTime = durationTime;
			this.deadLine = deadLine;
			this.minStartTime = deadLine-durationTime+1;
		}
	}
}
 */
