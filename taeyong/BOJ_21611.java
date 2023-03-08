import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.StringTokenizer;

// 마법사 상어는 블리자드를 총 M번 시전했다. 시전한 마법의 정보가 주어졌을 때
// 1 * (폭발한 1번 구슬의 개수) + 2 * 폭발한 2번 구슬의 개수) + 3 * (폭발한 3번 구슬의 개수)
public class BOJ_21611 {
	static int N, M;
	static int[][] map;
	static Queue<int[]> orders = new LinkedList<>();
	static List<Integer> numbers = new ArrayList<>();
	// 상, 하 , 좌, 우
	static int[] dx = {-1, 1 ,0, 0};
	static int[] dy = {0, 0, -1, 1};
	static int sharkX, sharkY, total;
	static int answer = 0;
	
	public static void main(String[] args) throws Exception {
		inputValues();
		
		while(!orders.isEmpty()) {
			destroy();
			explode();
			change();
			reset();
		}
		System.out.println(answer);
	}
	
	private static void inputValues() throws Exception {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st = new StringTokenizer(br.readLine());
		N = Integer.parseInt(st.nextToken());
		M = Integer.parseInt(st.nextToken());
		
		map = new int[N][N];
		
		//상어 위치 
		sharkX = N / 2;
		sharkY = N / 2;
		
		for(int i = 0; i < N ; i++) {// 맵 정보 초기화
			st = new StringTokenizer(br.readLine());
			for(int j = 0; j < N ; j++) {
				int num = Integer.parseInt(st.nextToken());
				map[i][j] = num;
				if(num > 0) total++;
			}
		}
		
		for(int i = 0; i < M; i++) { // 블리자드 마법의 방향 di와 거리 si
			orders.add(Arrays.stream(br.readLine().split(" ")).mapToInt(Integer::parseInt).toArray());
		}
	}
	
	
	// 마법의 방향과 거리만큼 파괴하는 메서드
	private static void destroy() {
		int[] order = orders.poll();
		
		int direction = order[0] - 1; // 방
		int distance = order[1]; // 거리 
		int nx = sharkX, ny = sharkY;
		
		for(int i = 0; i < distance; i++) {
			nx = nx + dx[direction]; 
			ny = ny + dy[direction];
			
			if(map[nx][ny] == 0) continue; // 범위 벗어나거나 이미 0인 경우 
			map[nx][ny] = 0; // 해당 구슬 파괴
			total--;
		}
	}
	
	// 작업 후 맵에 재배치 
	private static void reset() {
		map = new int[N][N];
		int[] direction = new int[]{2, 1, 3, 0};
		int dir = 0 , move = 1;
		int x = sharkX, y = sharkY;
		int cnt = 0;
		while(cnt != numbers.size()) {
			for(int i = 0; i < 2; i++) {
				for(int j = 0; j < move; j++) {
					x += dx[direction[dir]];
					y += dy[direction[dir]];
					map[x][y] = numbers.get(cnt++);
					
					if(cnt == numbers.size()) return;
				}
				dir = (dir + 1) % 4;
			}
			move++;
		}
	}
	
	//처음으로 공백이 생긴 좌표 구하는 메서드 
	private static void find() {
		int move = 1;
		int cnt = 0;
		int[] direction = new int[]{2, 1, 3, 0}; // 좌, 우, 상, 하 로 dx와 dy배열을 순회한다. 달팽이 모양으로 
		int x = sharkX, y = sharkY;
		int dir = 0;
		
		numbers.clear();
		while(cnt != total) {
			for(int i = 0; i < 2; i++) {
				for(int j = 0; j < move; j++) {
					x += dx[direction[dir]];
					y += dy[direction[dir]];
					if(map[x][y] != 0) {
						numbers.add(map[x][y]);
						cnt++;
					}
					
					if(cnt == total) return;
				}
				dir = (dir + 1) % 4;
			}
			move++;
		}
	}
	
	private static void explode() {
		find();
		boolean isExplode = true;
		List<Integer> newNumbers;
		
		while(isExplode) {
			newNumbers = new ArrayList<>();
			isExplode = false;
			for(int i = 0; i < numbers.size(); i++) { // 투 포인터 활용해서 4개가 연속되는지 확인하기 
				
				int end = i;
				
				while(end < numbers.size() && numbers.get(i) == numbers.get(end)) end++;
				
				if((end - i) >= 4) {
					answer += numbers.get(i) * (end - i);
					isExplode = true;
				}else {
					for(int j = i; j < end; j++) {
						newNumbers.add(numbers.get(j));
					}
				}
				i = end - 1;
			}
			numbers = newNumbers;
		}
	}
	
	private static void change() {
		List<Integer> newNumbers = new ArrayList<>();
		
		for(int i = 0; i < numbers.size(); i++) {
			if(newNumbers.size() >= N * N) break;
			
			int end = i;
			
			while(end < numbers.size() && numbers.get(i) == numbers.get(end)) end++;
			
			newNumbers.add(end - i);
			newNumbers.add(numbers.get(i));
			
			i = end - 1;
		}
		
		for(int i = newNumbers.size(); i >= N * N ; i--) {
			newNumbers.remove(i - 1);
		}
		numbers = newNumbers;
		total = numbers.size();
	}
}
