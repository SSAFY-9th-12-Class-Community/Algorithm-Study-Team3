import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.StringTokenizer;

public class BOJ_2461_대표선수 {
	
	private static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
	
	// [반 번호, 학생의 능력치]를 저장하는 배열
	private static List<int[]> students = new ArrayList<>();
	private static int n, m;
	
	public static void main(String[] args) throws Exception {
		input();
		// 학생의 능력치별 오름차순으로 정렬
		Collections.sort(students, (s1, s2)->{
			return s1[1] - s2[1];
		});
		System.out.println(twoPointerApproach());
	}
	
	private static int twoPointerApproach() {
		int answer = Integer.MAX_VALUE;
		
		int left = 0, right = 0; 
		int[] rangeCnt = new int[n]; // left ~ right 내의 각 학급별 학생이 얼마나 있는지 세는 배열
		rangeCnt[students.get(0)[0]] += 1; 
		
		int flag = n-1; // left~right 구간안에 학생이 1명도 없는 반의 숫자를 세는 flag 변수
		while(true) {
			while(flag != 0 && right < n*m) {
				right++;
				// 모든 구간을 다 탐색해도, 더 이상 모든 학급을 포함하는 구간을 만들어 낼 수 없는 경우 종료한다.
				if(right == n*m) return answer; 
				// 학생이 1명도 없던 학급의 학생이 탐색 구간에 추가되는 경우 flag값을 감소시킨다. 
				if (rangeCnt[students.get(right)[0]]++ == 0) flag--; 
			}
			
			while(flag == 0 && left <= right) {
				answer = Math.min(answer, students.get(right)[1] - students.get(left)[1]);
				// 탐색 구간내에 특정 학급의 학생이 0 이 되는 경우 flag 값을 증가시킨다. 
				if(--rangeCnt[students.get(left)[0]] == 0) flag++;
				left++;
			}
		}
	}
	
	private static void input() throws Exception {
		StringTokenizer st = new StringTokenizer(br.readLine());
		n = Integer.parseInt(st.nextToken());
		m = Integer.parseInt(st.nextToken());

		for(int i=0; i<n; ++i) {
			st = new StringTokenizer(br.readLine());
			for(int j=0; j<m; ++j) {
				int stats = Integer.parseInt(st.nextToken());
				students.add(new int[] {i, stats});
			}
		}
	}
}
