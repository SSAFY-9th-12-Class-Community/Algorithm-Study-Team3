
import java.io.InputStreamReader;
import java.util.LinkedList;
import java.util.Queue;
import java.util.StringTokenizer;
import java.io.BufferedReader;
import java.io.IOException;

public class Main {
	static LinkedList<LinkedList<Integer>> list = new LinkedList<LinkedList<Integer>>();
	static LinkedList<LinkedList<Integer>> temp = new LinkedList<LinkedList<Integer>>();
	static int N, M, T;
	static int target, dir, num; // target배수인 원판을 dir방향으로 num칸 회전
	static int cnt;
	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st = new StringTokenizer(br.readLine());
		N = Integer.parseInt(st.nextToken());
		M = Integer.parseInt(st.nextToken());
		T = Integer.parseInt(st.nextToken());
		for(int i = 0; i < N; i++) {
			st = new StringTokenizer(br.readLine());
			list.add(new LinkedList<Integer>());
			while(st.hasMoreTokens()) {
				int value = Integer.parseInt(st.nextToken());
				list.get(i).add(value);
			}
		}
		for(int i = 0; i < T; i++) {
			st = new StringTokenizer(br.readLine());
			target = Integer.parseInt(st.nextToken());
			dir = Integer.parseInt(st.nextToken());
			num = Integer.parseInt(st.nextToken());
			cnt = 0;
			spin();
			calculate();
		}
		System.out.println((int)result());
	}
	public static void spin() {
		int idx = 0;
		for(int i = 1; i <= N/target; i++) {
			for(int j = 0; j < num; j++) {
				if (dir == 1) {
					idx = list.get(target * i - 1).poll();
					list.get(target * i - 1).add(idx);
				} else {
					idx = list.get(target * i - 1).pollLast();
					list.get(target * i - 1).push(idx);
				}
			}
		}
		temp.clear();
		for(int i = 0; i < N; i++) {
			temp.add(new LinkedList<Integer>());
			for(int j = 0; j < M; j++) {
				temp.get(i).add(list.get(i).get(j));
			}
		}
	}
	public static void calculate() {
		boolean tri = false;
		for(int i = 0; i < N; i++) {
			for(int j = 0; j < M; j++) {
				int value = temp.get(i).get(j);
				if(value == 0) continue;
				if(temp.get(i).get((j+1)%M) == value) {
					list.get(i).set((j+1)%M, 0); // 양옆
					tri = true;
				}
				if(temp.get(i).get((j+M-1)%M) == value) {
					list.get(i).set((j+M-1)%M, 0);
					tri = true;
				}
				if(i != 0 && temp.get(i-1).get(j) == value) {
					list.get(i-1).set(j, 0); // 상하
					tri = true;
				}
				if(i != N-1 && temp.get(i+1).get(j) == value) {
					list.get(i+1).set(j, 0);
					tri = true;
				}
			}
		}
		if(!tri) {
			double avg = result() / cnt;
			for(int i = 0; i < N; i++) {
				for(int j = 0; j < M; j++) {
					if(list.get(i).get(j) != 0) {
						if(list.get(i).get(j) < avg) list.get(i).set(j, list.get(i).get(j)+1);
						else if(list.get(i).get(j) > avg) list.get(i).set(j, list.get(i).get(j)-1);
					}
				}
			}
		}
	}
	public static double result() {
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
