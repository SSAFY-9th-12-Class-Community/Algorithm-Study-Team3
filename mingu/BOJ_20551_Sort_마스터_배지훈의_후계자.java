// 제한시간 = 2000ms
// 제출 = 2588ms (자바 추가시간) (https://www.acmicpc.net/help/language)

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.StringTokenizer;

public class Main {
	static StringBuilder sb = new StringBuilder();
	static int[] A;
	static int N, M;
	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st = new StringTokenizer(br.readLine());
		N = Integer.parseInt(st.nextToken()); // 배열 A의 원소의 개수
		M = Integer.parseInt(st.nextToken()); // 질문의 개수
		A = new int[N];
		for(int i = 0; i < N; i++) { // 배열 입력
			A[i] = Integer.parseInt(br.readLine());
		}
		Arrays.sort(A); // 배열 오름차순 정렬
		for(int i = 0; i < M; i++) {
			int D = Integer.parseInt(br.readLine());
			sb.append(BinarySearch(D) + "\n");
		}
		System.out.println(sb);
	}
	public static int BinarySearch(int D) {
		int start = 0;
		int end = N-1;
		int pivot = (start + end) / 2;
		while(end-start >= 0) { // 한값으로 수렴하게끔
			if(A[pivot] > D) end = pivot-1; // 피봇 값이 조건보다 크면 
			else if(A[pivot] < D) start = pivot+1; // 피봇값 보다 조건이 크면
			else { // 조건 값을 찾았다면
				while(true) {
					if(pivot == 0) return 0; // pivot index가 0일때는 NullPointException 발생하므로 0 리턴
					if(A[pivot-1] != D) return pivot; // 가장 빠른 값을 찾기 위해 조건과 다른 값이 나올때까지 탐색후 가장 빠른 인덱스 번호(pivot) 리턴
					pivot--;
				}
			}
			pivot = (start + end) / 2; // 새로운 조건으로 다시 이분탐색
		}
		return -1; // 값 없음 -1리턴
	}
}// -1
