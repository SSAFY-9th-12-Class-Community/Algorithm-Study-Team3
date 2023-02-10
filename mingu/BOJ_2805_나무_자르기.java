import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.StringTokenizer;

public class Main {
	static int N;
	static int M;
	static ArrayList<Integer> trees;
	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st = new StringTokenizer(br.readLine());
		N = Integer.parseInt(st.nextToken());
		M = Integer.parseInt(st.nextToken());
		trees = new ArrayList<>();
		st = new StringTokenizer(br.readLine());
		for(int i = 0; i < N; i++) {
			trees.add(Integer.parseInt(st.nextToken()));
		}
		int end = Collections.max(trees); // max값
		System.out.println(BinarySearch(end));
	}
	
	public static int BinarySearch(int end) {
		int start = 0;
		long m_Now; // 현재 나무 절단량 .... 나무높이가 최대 20억이라 long...
		// start는 조건을 만족하는 최대값에 수렴, end는 조건을 만족하지 않는 최소값에 수렴
		// start(절단기)가 1의 높이만 높아져도 조건에 만족하지 않을 때를 찾기 (최대값)
		while(end - 1 > start) { // start(절단기) 와 end가 1의 높이차를 둘때까지 반복
			int mid = (start + end) / 2; // 현재 절단기
			m_Now = 0;
			for(int i = 0; i < N; i++) {
				if(mid < trees.get(i)) m_Now += trees.get(i) - mid;
			}
//			System.out.printf("절단기 %d높이에서 나무량 : %d #### end : %d, start : %d%n", mid, m_Now, end, start);
			if(m_Now < M) { // 나무가 부족하면
				end = mid; // 현재 절단기 높이를 end값(조건을 만족하지 않는 최소값)에 대입
			}
			else if(m_Now >= M) { // 나무가 충분하면
				start = mid; // 현재 절단기 높이를 start값(조건을 만족하는 최대값)에 대입
			}
		}
		return start;
	}
}
