import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.StringTokenizer;

public class Main {
	static int N, M, K;
	static ArrayList<FireBall> list = new ArrayList<FireBall>();
	static int dr[][] = {{-1,0}, {-1,1}, {0,1}, {1,1}, {1,0}, {1,-1}, {0,-1}, {-1,-1}};
	public static class FireBall{
		int row;
		int col;
		int mass;
		int speed;
		int dir;
		public FireBall(int row, int col, int mass, int speed, int dir) {
			this.row = row;
			this.col = col;
			this.mass = mass;
			this.speed = speed;
			this.dir = dir;
		}
	}
	
	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st = new StringTokenizer(br.readLine());
		N = Integer.parseInt(st.nextToken());
		M = Integer.parseInt(st.nextToken());
		K = Integer.parseInt(st.nextToken());
		int massSum = 0;
		for(int i = 0; i < M; i++) {
			st = new StringTokenizer(br.readLine());
			list.add(new FireBall(Integer.parseInt(st.nextToken())-1, Integer.parseInt(st.nextToken())-1, Integer.parseInt(st.nextToken()), Integer.parseInt(st.nextToken()), Integer.parseInt(st.nextToken())));
		}
		for(int i = 0; i < K; i++) {
			move();
			checkEdge();
			mergeAndSplit();
		}
		for(int i = 0; i < list.size(); i++) {
			massSum += list.get(i).mass;
		}
		System.out.println(massSum);
	}
	
	public static void move() {
		for(int i = 0; i < list.size(); i++) {
			list.get(i).row += dr[list.get(i).dir][0] * list.get(i).speed;
			list.get(i).col += dr[list.get(i).dir][1] * list.get(i).speed;
		}
	}
	
	public static void checkEdge() {
		for(int i = 0; i < list.size(); i++) {
			int curRow = list.get(i).row;
			int curCol = list.get(i).col;
			if(curRow < 0 || curRow >= N) list.get(i).row = (curRow + N*1000) % (N);
			if(curCol < 0 || curCol >= N) list.get(i).col = (curCol + N*1000) % (N);
		}
	}
	
	public static void mergeAndSplit() {
		ArrayList<FireBall> temp = new ArrayList<FireBall>();
		for(int i = 0; i < list.size(); i++) {
			ArrayList<FireBall> mergeList = new ArrayList<FireBall>();
			if(list.get(i).mass == 0) continue;
			boolean isSame; // 홀수 : false, 짝수 : true
			boolean tri = true;
			if(list.get(i).dir % 2 == 0) isSame = true;
			else isSame = false;
			int massSum = 0;
			int speedSum = 0;
			int rowTemp = list.get(i).row;
			int colTemp = list.get(i).col;
			mergeList.add(list.get(i));
			for(int j = i+1; j < list.size(); j++) {
				if(list.get(j).mass == 0) continue;
				if(mergeList.get(0).row == list.get(j).row && mergeList.get(0).col == list.get(j).col) {
					mergeList.add(list.get(j));
					list.remove(j);
				}
			}
			if(mergeList.size() > 1) {
				for(int j = 0; j < mergeList.size(); j++) {
					massSum += mergeList.get(j).mass;
					speedSum += mergeList.get(j).speed;
					if(isSame != (mergeList.get(j).dir % 2 == 0)) {
						tri = false;
					}
				}
				int dirTemp = 0;
				if(tri == false) dirTemp = 1;
				for(int j = 0; j < 4; j++) {
					temp.add(new FireBall(rowTemp, colTemp, massSum/5, speedSum/mergeList.size(), j*2+dirTemp));
				}
				list.remove(i--);
			}
		}
		list.addAll(temp);
	}
}
