
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

// 최대 N = 8
// 행 -> 뒤집는다 or 안뒤집는다
// 열 -> 뒤집는다 or 안뒤집는다
// 4^8 = 2^16 = 65,536
public class BOJ_23058 {
	static int N;
	static int map[];
	static int reverse = 0;
	public static void main(String[] args) throws NumberFormatException, IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		N = Integer.parseInt(br.readLine());
		map = new int[N];
		for(int i = 0; i < N; i++) { // 비트마스킹 위해 input값을 10진수로 변환하여 저장
			map[i] = Integer.parseInt(br.readLine().replace(" ", ""), 2);
		}
		reverse = (1<<N)-1; // 한 행을 비트마스킹으로 뒤집기 위한 고정 변수
		
		System.out.println(getMinTime(0,0));
	}
	
	public static int getMinTime(int n, int time) { // 모든 행과 열에 대해 뒤집을 경우의 수를 따져 최소 시간을 구하는 메소드
		if(n == N) { // 모든 행과 열에 대해 선택을 마쳤다면
			int count = 0;
			for(int i = 0; i < N; i++) {
				for(int j = 0; j < N; j++) {
					if((map[i] & (1<<j)) == 0) count++; // 0인 카드의 개수를 센다 (1이 아닌 카드의 개수를 센다)
				}
			}
			return time+Math.min(N*N-count, count); // 행과 열을 뒤집은 횟수(time)에 낱개로 뒤집을 숫자 중 개수가 적은 것(곧, 최소 시간)을 더해 리턴
		}
		
		// 재귀
		int min = getMinTime(n+1, time); // 아무것도 선택하지 않고 다음 행과 열을 따짐
		
		map[n] ^= reverse; // XOR연산으로 행을 뒤집는다
		min = Math.min(min, getMinTime(n+1, time+1)); // 행을 뒤집은 경우에 다음 행과 열을 따짐
		
		reverseColumn(n); // 열을 뒤집는다
		min = Math.min(min, getMinTime(n+1, time+2)); // 행과 열을 모두 뒤집은 경우에 다음 행과 열을 따짐
		
		map[n] ^= reverse; // 행을 다시 뒤집는다, 열만 뒤집은 상태
		min = Math.min(min, getMinTime(n+1, time+1)); // 열만 뒤집은 경우에 다음 행과 열을 따짐
		
		reverseColumn(n); // 열을 다시 뒤집는다, 아무것도 뒤집지 않은 상태
		
		return min; 
	}

	public static void reverseColumn(int colNum) {
		for (int i = 0; i < N; i++) { // 모든 10진수에서 colNum만큼 shift해준 이진수값을 빼준다(해당 열만 반전)
			map[i] ^= (1 << (N - 1 - colNum));
		}
	}
}
