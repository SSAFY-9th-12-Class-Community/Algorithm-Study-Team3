
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Stack;
import java.util.StringTokenizer;

// 실행시간 : 132ms
public class BOJ_14719 {
	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st = new StringTokenizer(br.readLine());
		int H = Integer.parseInt(st.nextToken()); // 2차원 세계의 세로 길이 H
		int W = Integer.parseInt(st.nextToken()); // 2차원 세계의 가로 길이 W
		int totalRain = 0; // 빗물 양
		int curBlock = 0; // 현재 고려중인 블록
		int highestBlock = -1; // 가장 높은 블록
		Stack<Integer> s = new Stack<Integer>();
		st = new StringTokenizer(br.readLine());
		// 정방향으로 탐색하며 더 높은 블록이 나올 때만 빗물 양 계산
		for(int i = 0; i < W; i++) { 
			curBlock = Integer.parseInt(st.nextToken()); // 현재 반복문이 가리키는 블록
			if(highestBlock <= curBlock) { // 
				while(!s.isEmpty() && highestBlock >= 0) { // 스택이 빌때까지
					totalRain += highestBlock - s.pop(); // 이전에 가장 높았던 블록에서 스택에 있는 블록 높이를 전부 빼서 빗물 양 계산
				}
				highestBlock = curBlock; // 가장 높은 블록 갱신
			}
			s.push(curBlock);
		}
		// 고려되지 않은 블록들 pop()하며 역방향으로 빗물 양 계산
		int higherBlock = 0; // 역방향 계산동안 가장 높은 블록
		int sumBlock = 0; // pop()한 블록들의 합 
		int count = 0; // pop()한 횟수
		while(!s.isEmpty()) { // 스택이 빌때까지 (정방향때 계산되지 않은 나머지 블록),(최고 높이 블록이 마지막으로 갱신된 이후의 블럭들)
			curBlock = s.pop();
			if(higherBlock <= curBlock) {
				totalRain += (higherBlock * count) - sumBlock;
				higherBlock = curBlock;
				count = 0;
				sumBlock = 0;
			}
			else {
				count++;
				sumBlock += curBlock;
			}
		}
		totalRain += (curBlock * count) - sumBlock;
		System.out.println(totalRain);
		
		br.close();
	}
}
