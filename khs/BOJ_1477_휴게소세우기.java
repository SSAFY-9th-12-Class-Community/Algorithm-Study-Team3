import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.StringTokenizer;

public class BOJ_1477_휴게소세우기 {
	
	static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
	static int n, m, l;
	static List<Integer> distances = new ArrayList<>();
	
	public static void main(String[] args) throws IOException {
		input();
		calcDistance();
		
		int left = 1, right = l/2+1;
		while(left <= right) {
			int cnt = 0;
			int mid = (left+right)/2;
			
			for(int distance: distances) {
				cnt += (distance/mid);
				if(distance % mid == 0) --cnt;
			}
			
			if(cnt <= m) right = mid-1;
			else left = mid+1;
		}
		
		System.out.println(left);
	}
	
	private static void calcDistance() {
		if(n == 0) {
			distances.add(l);
			return;
		}
		
		int len = distances.size();
		Collections.sort(distances);
		int prev = distances.get(0);
		for(int i=1; i<len; ++i) {
			int tmp = distances.get(i);
			distances.set(i, distances.get(i)-prev);
			prev = tmp;
		}
		distances.add(l-prev);
	}
	
	private static void input() throws IOException {
		StringTokenizer st = new StringTokenizer(br.readLine());
		n = Integer.parseInt(st.nextToken());
		m = Integer.parseInt(st.nextToken());
		l = Integer.parseInt(st.nextToken());
		
		if(n!=0) Arrays.stream(br.readLine().split(" ")).mapToInt(Integer::parseInt).forEach(e->distances.add(e));
		br.close();
	}
}
