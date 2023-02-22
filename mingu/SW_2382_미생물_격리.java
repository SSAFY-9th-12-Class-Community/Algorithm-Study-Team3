import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Stack;
import java.util.StringTokenizer;

public class Solution {
	static int N, M, K;
	static Group g[];
	public static class Group{
		int row;
		int col;
		int num;
		int dir; 
		public Group(int row, int col, int num, int dir) {
			this.row = row;
			this.col = col;
			this.num = num;
			this.dir = dir;
		}
	}
	public static void main(String[] args) throws NumberFormatException, IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringBuilder sb = new StringBuilder();
		StringTokenizer st = null;
		int T = Integer.parseInt(br.readLine());
		for(int Tc = 1; Tc <= T; Tc++) {
			st = new StringTokenizer(br.readLine());
			N = Integer.parseInt(st.nextToken());
			M = Integer.parseInt(st.nextToken());
			K = Integer.parseInt(st.nextToken());
			g = new Group[K];
			for(int i = 0; i < K; i++) {
				st = new StringTokenizer(br.readLine());
				g[i] = new Group(Integer.parseInt(st.nextToken()), Integer.parseInt(st.nextToken()), Integer.parseInt(st.nextToken()), Integer.parseInt(st.nextToken()));
			}
			for(int i = 0; i < M; i++) {
				move();
			}
			int sum = 0;
			for(int i = 0; i < K; i++) {
				sum += g[i].num;
			}
			sb.append("#"+Tc+" "+sum+"\n");
		}
		System.out.println(sb);
	}
	
	public static void move() {
		for(int i = 0; i < K; i++) {
			if(g[i].num == 0) continue; // 미생물이 없거나 합쳐진 군집은 패스
			switch(g[i].dir) { // 1 : 상, 2 : 하, 3 : 좌, 4 : 우
			case 1:g[i].row--; checkDamaged(i); break;
			case 2:g[i].row++; checkDamaged(i); break;
			case 3:g[i].col--; checkDamaged(i); break;
			case 4:g[i].col++; checkDamaged(i);
			}
		}
		checkMerge();
	}
	
	public static void checkMerge() {
        Stack<Integer> s = new Stack<Integer>();
        
        for(int i = 0; i < K; i++) {
            if(g[i].num == 0) continue; // 미생물이 없거나 합쳐진 군집은 패스
            
            int maxIdx = i;
            for(int j = i+1; j < K; j++) {
                if(g[j].num == 0) continue;
                
                if((g[maxIdx].row == g[j].row) && (g[maxIdx].col == g[j].col)) {
                    if(g[maxIdx].num < g[j].num) {
                        s.push(maxIdx);
                        maxIdx = j;
                    } else {
                        s.push(j);
                    }
                }       
            }
            
            while(!s.isEmpty()) {
                int idx = s.pop();
                g[maxIdx].num += g[idx].num;
                g[idx].num = 0;
            }
        }
    }
	
	public static void checkDamaged(int idx) { // 미생물이 약품이 칠해진 셀에 도착했는지 검사 & 계산
		if(g[idx].col == 0) {
			g[idx].dir++;
			g[idx].num /= 2;
		}
		else if(g[idx].col == N-1) {
			g[idx].dir--;
			g[idx].num /= 2;
		}
		else if(g[idx].row == 0) {
			g[idx].dir++;
			g[idx].num /= 2;
		}
		else if(g[idx].row == N-1) {
			g[idx].dir--;
			g[idx].num /= 2;
		}
	}
}
