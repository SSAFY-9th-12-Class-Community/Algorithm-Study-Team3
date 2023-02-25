import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.StringTokenizer;

public class SW_5656_벽돌깨기 {

    private static final BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    private static final StringBuilder sb = new StringBuilder();
    private static int[][] dirs = { {-1, 0}, {0, 1}, {1, 0}, {0, -1} };
    private static int N, W, H;   // N: 구슬 쏘는 횟수, W: 너비, H: 높이

    public static void main(String[] args) throws Exception {
        int T = Integer.parseInt(br.readLine());
        
        for(int tc=1; tc<=T; ++tc) {
            StringTokenizer st = new StringTokenizer(br.readLine());
            N = Integer.parseInt(st.nextToken());
            W = Integer.parseInt(st.nextToken());
            H = Integer.parseInt(st.nextToken());

            int[][] originGrid = new int[H][];
            for(int i=0; i<H; ++i) originGrid[i] = read();

            int answer = 0;
            for(int i=0; i<H; ++i) {
                for(int j=0; j<W; ++j) {
                    if(originGrid[i][j] != 0) answer++;
                }
            }

            answer -= getMaxRemoveCnt(originGrid,0);
            sb.append('#').append(tc).append(' ').append(answer).append('\n');
        }

        System.out.println(sb.toString());
        br.close();
    }

    private static int getMaxRemoveCnt(int[][] grid, int idx) {
        if(idx == N) return 0;

        int ret = 0;
        for(int i=0; i<W; ++i) {
            int startHeight = findHeight(grid, i);
            if(startHeight == -1) continue;

            int currentStepRemoveCnt = 0;
            int[][] copyGrid = getCopyGridValueOf(grid);
            currentStepRemoveCnt += removeBlock(copyGrid, startHeight, i);

            removeFragment(copyGrid);
            ret = Math.max(ret, currentStepRemoveCnt+getMaxRemoveCnt(copyGrid, idx+1));
        }
        return ret;
    }

    private static int findHeight(int[][] grid, int w) {
        for(int i=0; i<H; ++i) if(grid[i][w] != 0) return i;
        return -1;
    }

    private static boolean isValid(int y, int x) {
        return (y>=0 && y<H) && (x>=0 && x<W);
    }

    private static int removeBlock(int[][] grid, int y, int x) {
        int ret = 1;
        int range = grid[y][x]-1;
        grid[y][x] = 0;     // 현재 위치의 블록을 제거했으므로, 재귀 호출 시 더 이상 추가적인 함수 호출이 일어나지 않는다. 

        for(int i=1; i<=range; ++i) {
            for(int[] dir: dirs) {
                int ny = y+(dir[0]*i);
                int nx = x+(dir[1]*i);

                if(!isValid(ny, nx) || grid[ny][nx] == 0) continue;
                if(grid[ny][nx] == 1) {
                    ret++;  // 크기가 1인 경우, 블록제거를 전파하지 않아도 되므로 현재 위치에서 제거한다. 
                    grid[ny][nx] = 0;
                } else {
                    ret += removeBlock(grid, ny, nx); //크기가 1보다 큰 블록인 경우 재귀적으로 블록 제거 함수를 호출한다.
                }
            }
        }
        return ret;
    }

    private static void removeFragment(int[][] grid) {
        for(int i=0; i<W; ++i) {
            int cnt = 0;
            for(int j=H-1; j>=0; --j) {
                if(grid[j][i] == 0) {
                    ++cnt;
                    continue;
                }

                if(cnt != 0) {
                    grid[j][i] ^= grid[j+cnt][i];
                    grid[j+cnt][i] ^= grid[j][i];
                    grid[j][i] ^= grid[j+cnt][i];
                }
            }
        }
    }

    private static int[][] getCopyGridValueOf(int[][] origin) {
       int[][] copyGrid = new int[H][];
       for(int i=0; i<H; ++i) copyGrid[i] = origin[i].clone();
       return copyGrid;
    }

    private static int[] read() throws IOException {
        return Arrays.stream(br.readLine().split(" ")).mapToInt(Integer::parseInt).toArray();
    }
}
