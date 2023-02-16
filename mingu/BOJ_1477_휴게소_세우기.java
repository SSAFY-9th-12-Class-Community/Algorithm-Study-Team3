import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.StringTokenizer;

public class Main {
	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st = new StringTokenizer(br.readLine());
		int N = Integer.parseInt(st.nextToken()); // 현재 휴게소의 개수
		int M = Integer.parseInt(st.nextToken()); // 더 지으려고 하는 휴게소의 개수
		int L = Integer.parseInt(st.nextToken()); // 고속도로의 길이
		ArrayList<Integer> list = new ArrayList<>();
		st = new StringTokenizer(br.readLine());
		list.add(0);
		for (int i = 0; i < N; i++) {
			list.add(Integer.parseInt(st.nextToken()));
		}
		list.add(L);
		Collections.sort(list); // 오름차순 정렬
		
		int left = 1;
		int right = L-1;
		
		while(left <= right) {
			int mid = (left+right) / 2;
			int sum = 0;
			
			for(int i = 1; i < list.size(); i++) {
				sum += (list.get(i) - list.get(i-1) - 1) / mid;
			}
			
			if(sum > M) left = mid + 1;
			else right = mid - 1;
		}
		System.out.println(left);
	}
}
