import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.StringTokenizer;

public class BOJ_18809_Gaaaaaaaaaarden {

    private static final BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    private static int N, M, G, R, LEN, answer;
    private static Grid[][] grids;
    private static Grid[][] copyGrid; 
    private static List<int[]> availableGrid = new ArrayList<>(); // 배양액을 뿌릴 수 있는 땅의 좌표를 저장하는 리스트 
 
    private static int[] selectedRed = new int[5], selectedGreen = new int[5];  // 빨간색 배양액을 뿌릴 땅과 초록색 배양액을 뿌릴 수 있는 땅의 위치를 저장하는 배열  availableGrid의 인덱스 값을 저장
    private static int[][] dirs = { {-1, 0}, {0, 1}, {1, 0}, {0, -1} };
    
    public static void main(String[] args) throws Exception{
        int[] tmp = read();
        N = tmp[0]; M = tmp[1]; G = tmp[2]; R = tmp[3];
        grids = new Grid[N][M];
        copyGrid = new Grid[N][M];

        for(int i=0; i<N; ++i) {
            StringTokenizer st = new StringTokenizer(br.readLine());
            for(int j=0; j<M; ++j) {
                int gridType = Integer.parseInt(st.nextToken());
                grids[i][j] = new Grid(gridType);
                if(gridType == 2) availableGrid.add(new int[] {i, j});
            }
        }
        
        LEN = availableGrid.size();
        selectGridAndSimulation(0, 0, 0);
        System.out.println(answer);
    }

    private static boolean isValid(int y, int x) {
        return (y>=0 && y<N) && (x>=0 && x<M);
    }

    private static int bfs() {
      // 각 시뮬레이션이 다른 시뮬레이션에게 영향을 미치지 않도록 매 시도마다 Grid 배열의 초기 상태를 복사 생성함
    	for(int i=0; i<N; ++i) {
    		for(int j=0; j<M; ++j) {
    			copyGrid[i][j] = new Grid(grids[i][j]);
    		}
    	}
    	
        int ret = 0;
        Queue<int[]> q = new LinkedList<>(); // [y좌표, x좌표, 도달 시간, 타입] 
        
        // 빨간색 배양액이 뿌려진 땅의 좌표, 해당 땅에 도달하는데 걸린 시간, 타입을 bfs를 통해 탐색하기 위해 큐에 넣음 
        for(int i=0; i<R; ++i) {
            int[] coordinate = availableGrid.get(selectedRed[i]);
            copyGrid[coordinate[0]][coordinate[1]].gridType = 3;
            q.add(new int[] {coordinate[0], coordinate[1], 0, 3});
        }
      
      // 초록색 배양액이 뿌려진 땅의 좌표, 해당 땅에 도달하는데 걸린 시간, 타입을 bfs를 통해 탐색하기 위해 큐에 넣음 
        for(int i=0; i<G; ++i) {
            int[] coordinate = availableGrid.get(selectedGreen[i]);
            copyGrid[coordinate[0]][coordinate[1]].gridType = 4;
            q.add(new int[] {coordinate[0], coordinate[1], 0, 4});
        }

        while (!q.isEmpty()) {
            int[] info = q.poll();
            if(copyGrid[info[0]][info[1]].gridType == 5) continue;  // 이미 해당 땅에 꽃이 핀 상태라면 해당 용액은 더 이상 전파가 불가능한 상태이므로 진행하지 않는다.
            
            for(int i=0; i<4; ++i) {
                int ny = info[0] + dirs[i][0];
                int nx = info[1] + dirs[i][1];
              
              // 좌표가 index 범위를 벗어나는 경우, 해당 위치가 호수인 경우, 해당 위치에 이미 꽃이 핀 경우, 해당 위치에 이미 동일한 타입의 배양액이 존재하는 경우 
                if(!isValid(ny, nx) || copyGrid[ny][nx].gridType == 0 || copyGrid[ny][nx].gridType == 5
                		|| copyGrid[ny][nx].gridType == info[3] ) continue;
              
              // 아직 배양액이 뿌려지지 않은 땅이라면 현재 배양액이 해당 땅으로 전파됨 
                if(copyGrid[ny][nx].gridType == 1 || copyGrid[ny][nx].gridType == 2) {
                	copyGrid[ny][nx].gridType = info[3];
                	copyGrid[ny][nx].time = info[2]+1;
                	q.add(new int[] {ny, nx, info[2]+1, info[3]});
                } else {
                // 자기 자신의 색깔에 반대되는 배양액인 경우에만 해당 조건식으로 들어오게됨
                // 만약 색깔이 반대되는 배양액이 존재하는 땅에 도달하게 되면, 도달 가능한 시간을 비교하여 꽃이 필 수 있는 지 여부를 판단함 
                	if(copyGrid[ny][nx].time == info[2]+1) {
                		copyGrid[ny][nx].gridType = 5;
                		++ret;
                	}
                }
            }
        }
        return ret;
    }
    
  // 빨간색 배양액, 초록색 배양액 선택을 조합을 통해 중복되는 경우를 최대한 줄임 
    private static void selectGridAndSimulation(int idx, int aCnt, int gCnt) {
        if(aCnt == R && gCnt == G) {
            answer = Math.max(answer, bfs());
            return;
        }
        if(LEN-idx < (R-aCnt) + (G-gCnt)) return;

        for(int i=idx; i<availableGrid.size(); ++i) {
            if(aCnt < R) {
                selectedRed[aCnt] = i;
                selectGridAndSimulation(i+1, aCnt+1, gCnt);
            }

            if(gCnt < G) {
                selectedGreen[gCnt] = i;
                selectGridAndSimulation(i+1, aCnt, gCnt+1);
            }
        }
    }

    private static int[] read() throws IOException {
        return Arrays.stream(br.readLine().split(" ")).mapToInt(Integer::parseInt).toArray();
    }

    static class Grid {
// 격자에 위치한 각 타입  0: 호수,  1: 배양액을 뿌릴 수 있는 땅, 2: 배양액을 뿌릴 수 없는 땅, 3 : 빨간색 배양액이 뿌려진 땅, 4: 녹색 배양액이 뿌려진 땅, 5: 꽃
        int gridType;
        int time; //격자에 도달한 시간 

        Grid(int type) {
            this.gridType = type;
            this.time = 0;
        }
        
      //객체의 복사생성을 위한 생성자
        Grid(Grid g) {
            this.gridType = g.gridType;
            this.time = g.time;
        }
    }
}
