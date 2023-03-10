import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.Queue;
import java.util.StringTokenizer;

public class BOJ_1525 {
	static String answer = "123456780";
	static String input;
	static int[] dx = {-1, 1, 0, 0};
	static int[] dy = {0, 0, -1, 1};
	static Map<String, Integer> visited = new HashMap<>();
	
	public static void main(String[] args) throws Exception {
		inputValues();
		System.out.println(bfs());
	}
	
	private static void inputValues() throws Exception {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st;
		StringBuilder sb = new StringBuilder();
		for(int i = 0; i < 3; i++) {
			st = new StringTokenizer(br.readLine());
			for(int j = 0; j < 3; j++) {
				sb.append(st.nextToken());
			}
		}
		input = sb.toString();
	}
	
	
	private static int bfs() {
		Queue<String> queue = new LinkedList<>();
		visited.put(input, 0);
		queue.add(input);
		
		while(!queue.isEmpty()) {
			String arr = queue.poll();
			
			if(arr.equals(answer)) return visited.get(arr);
			
			int zeroIdx = arr.indexOf("0");
			int x = zeroIdx / 3;
			int y = zeroIdx % 3;
			
			for(int d = 0; d < 4; d++) {
				int nx = x + dx[d];
				int ny = y + dy[d];
				
				if(nx < 0 || nx > 2 || ny < 0 || ny > 2) continue;
				int newIdx = (nx * 3) + ny;
				char value = arr.charAt(newIdx);
				String newArr = arr.replace(value, ' ');
				newArr = newArr.replace('0', value);
				newArr = newArr.replace(' ', '0');
				
				if(visited.containsKey(newArr)) continue;
				visited.put(newArr, visited.get(arr) + 1);
				queue.add(newArr);
			}
		}
		return -1;
	}
}
