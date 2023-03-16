package alg;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Queue;
import java.util.StringTokenizer;

public class BOJ_1525 {
	static int[] dr = {-1,1,0,0};
	static int[] dc = {0,0,-1,1};
	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st = null;
		int start = 0;
		for(int i = 0; i < 3; i++) {
			st = new StringTokenizer(br.readLine());
			for(int j = 0; j < 3; j++) {
				int a = Integer.parseInt(st.nextToken());
				if(a == 0) a = 9;
				start = start * 10 + a;
			}
		}
		Queue<Integer> q = new LinkedList<>();
		HashMap<Integer, Integer> m = new HashMap<>();
		m.put(start, 0);
		q.add(start);
		
		while(!q.isEmpty()) {
			int n = q.poll();
			String now = String.valueOf(n);
			int empty = now.indexOf("9");
			int row = empty / 3;
			int col = empty % 3;
			for(int i = 0; i < 4; i++) {
				int nRow = row + dr[i];
				int nCol = col + dc[i];
				int move = nRow*3+nCol;
				if(nRow >= 0 && nRow <3 && nCol >= 0 && nCol < 3) {
					StringBuilder sb = new StringBuilder(now);
					char temp = sb.charAt(move);
					sb.setCharAt(move, '9');
					sb.setCharAt(empty, temp);
					int n2 = Integer.parseInt(sb.toString());
					if(!m.containsKey(n2)) {
						m.put(n2, m.get(n)+1);
						q.add(n2);
					}
				}
			}
		}
		if(m.containsKey(123456789)) {
			System.out.println(m.get(123456789));
		}
		else {
			System.out.println(-1);
		}
	}
}
