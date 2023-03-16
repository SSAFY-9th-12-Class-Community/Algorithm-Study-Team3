
import java.io.InputStreamReader;
import java.util.LinkedList;
import java.util.Queue;
import java.util.StringTokenizer;
import java.io.BufferedReader;
import java.io.IOException;

public class Main {
	static LinkedList<LinkedList<Integer>> list = new LinkedList<LinkedList<Integer>>(); // 원판
	static LinkedList<LinkedList<Integer>> temp = new LinkedList<LinkedList<Integer>>(); // 원판 연산을 위한 temp원판
	static int N, M, T;
	static int target, dir, num; // target배수인 원판을 dir방향으로 num칸 회전
	static int cnt;
	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st = new StringTokenizer(br.readLine());
		N = Integer.parseInt(st.nextToken());
		M = Integer.parseInt(st.nextToken());
		T = Integer.parseInt(st.nextToken());
		for(int i = 0; i < N; i++) { // 입력
			st = new StringTokenizer(br.readLine());
			list.add(new LinkedList<Integer>());
			while(st.hasMoreTokens()) {
				int value = Integer.parseInt(st.nextToken());
				list.get(i).add(value);
			}
		}
		for(int i = 0; i < T; i++) { // T번만큼 회전
			st = new StringTokenizer(br.readLine());
			target = Integer.parseInt(st.nextToken());
			dir = Integer.parseInt(st.nextToken());
			num = Integer.parseInt(st.nextToken());
			cnt = 0; // 원판 내 남은 숫자 개수 초기화
			spin(); // target, dir, num을 통한 원판 회전 및 temp원판 갱신
			calculate(); // 회전 후 연산메소드
		}
		System.out.println((int)result()); // 출력
	}
	public static void spin() {
		int idx = 0;
		for(int i = 1; i <= N/target; i++) { // 배수가 target인 원판 개수만큼 반복
			for(int j = 0; j < num; j++) { // num칸 회전
				if (dir == 1) { // 반시계 방향, poll해서 뒤쪽으로 add
					idx = list.get(target * i - 1).poll(); 
					list.get(target * i - 1).add(idx);
				} else { // 시계 방향, pollLast해서 앞쪽으로 push
					idx = list.get(target * i - 1).pollLast();
					list.get(target * i - 1).push(idx);
				}
			}
		}
		temp.clear(); // temp원판 초기화
		for(int i = 0; i < N; i++) {
			temp.add(new LinkedList<Integer>());
			for(int j = 0; j < M; j++) { // temp원판 갱신
				temp.get(i).add(list.get(i).get(j));
			}
		}
	}
	public static void calculate() {
		boolean tri = false; // 인접하면서 수가 같은 것이 '단한개라도' 있는지 체크하기 위한 트리거
		for(int i = 0; i < N; i++) {
			for(int j = 0; j < M; j++) {
				int value = temp.get(i).get(j); // 해당하는 원판 숫자
				if(value == 0) continue; // 원판 숫자가 0 == X(숫자 없음)
				if(temp.get(i).get((j+1)%M) == value) { // 같은 원판에서 양옆에 인접한 숫자
					list.get(i).set((j+1)%M, 0);
					tri = true;
				}
				if(temp.get(i).get((j+M-1)%M) == value) { // 같은 원판에서 양옆에 인접한 숫자
					list.get(i).set((j+M-1)%M, 0);
					tri = true;
				}
				if(i != 0 && temp.get(i-1).get(j) == value) { // 위, 아래 있는 원판에서 인접한 숫자
					list.get(i-1).set(j, 0);
					tri = true;
				}
				if(i != N-1 && temp.get(i+1).get(j) == value) { // 위, 아래 있는 원판에서 인접한 숫자
					list.get(i+1).set(j, 0);
					tri = true;
				}
			}
		}
		if(!tri) { // 인접하면서 수가 같은 것이 단한개도 없다면
			double avg = result() / cnt; // 평균
			for(int i = 0; i < N; i++) {
				for(int j = 0; j < M; j++) {
					if(list.get(i).get(j) != 0) { // 평균보다 큰 수는 1을 빼고, 작은 수는 1을 더함
						if(list.get(i).get(j) < avg) list.get(i).set(j, list.get(i).get(j)+1);
						else if(list.get(i).get(j) > avg) list.get(i).set(j, list.get(i).get(j)-1);
					}
				}
			}
		}
	}
	public static double result() { // 원판에 적힌 수의 합 
		double sum = 0;
		for(int i = 0; i < N; i++) {
			for(int j = 0; j < M; j++) {
				if(list.get(i).get(j) != 0) {
					sum += list.get(i).get(j);
					cnt++;
				}
			}
		}
		return sum;
	}
}
