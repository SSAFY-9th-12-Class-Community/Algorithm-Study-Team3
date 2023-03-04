import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.*;

public class BOJ_21611_마법사상어와블리자드 {

    private static int N, M;
    private static Grid[][] grids;
    private static int[][] dirs = { {-1, 0}, {1, 0}, {0, -1}, {0, 1} };
    private static int[] answer = new int[3];

    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());

        N = Integer.parseInt(st.nextToken());
        M = Integer.parseInt(st.nextToken());
        grids = new Grid[N][N];
        for(int i=0; i<N; ++i) {
            st = new StringTokenizer(br.readLine());
            for(int j=0; j<N; ++j) {
                grids[i][j] = new Grid(i, j, Integer.parseInt(st.nextToken()));
            }
        }
        initGraph();

        for(int i=0; i<M; ++i) {
            st = new StringTokenizer(br.readLine());
            int d = Integer.parseInt(st.nextToken())-1, s = Integer.parseInt(st.nextToken());

            int y = N/2, x = N/2;
            for(int j=0; j<s; ++j) {
                y+=dirs[d][0];
                x+=dirs[d][1];
                grids[y][x].type = 0;
            }
            removeFragment();
            while(destroyAdjNode(grids[N/2][N/2-1], 0) != 0) removeFragment();
            convertMarble();
        }
        System.out.println(answer[0]+answer[2]+((answer[1]+answer[2])<<1));
    }

    static void convertMarble() {
        List<Integer> newMarbles = getGroupInfo();
        clearGridType();

        Grid grid = grids[N/2][N/2-1];
        for(Integer marble: newMarbles) {
            if(grid == null) return;
            grid.type = marble;
            grid = grid.getNextGrid();
        }
    }

    static List<Integer> getGroupInfo() {
        List<Integer> ret = new ArrayList<>();
        Grid grid = grids[N/2][N/2-1];
        int type = grid.type, cnt = 1;

        while(type != 0) {
            grid = grid.getNextGrid();
            if(grid.type != type) {
                ret.add(cnt);
                ret.add(type);
                type = grid.type;
                cnt = 1;
            } else cnt++;
        }
        return ret;
    }

    static int destroyAdjNode(Grid grid, int cost) {
        if(grid == null || grid.type == 0) return cost;

        int cnt = getSameMarbleCnt(grid);
        Grid nextGrid = grid;

        for(int i=0; i<cnt; ++i) {
            if(cnt >= 4) {
                answer[nextGrid.type-1]++;
                nextGrid.type = 0;
            }
            nextGrid = nextGrid.getNextGrid();
        }
        if(cnt >= 4) cost+=cnt;
        return destroyAdjNode(nextGrid, cost);
    }

    private static int getSameMarbleCnt(Grid grid) {
        int ret = 1;

        Grid nextGrid = grid.getNextGrid();
        while(nextGrid != null) {
            if(grid.type != nextGrid.type) break;
            nextGrid = nextGrid.getNextGrid();
            ret++;
        }
        return ret;
    }

    static void removeFragment() {
        Queue<Integer> q = new LinkedList<>();

        Grid grid = grids[N/2][N/2-1];
        while (grid != null) {
            if(grid.type != 0) q.add(grid.type);
            grid = grid.getNextGrid();
        }

        clearGridType();

        grid = grids[N/2][N/2-1];
        while(!q.isEmpty()) {
            grid.type = q.poll();
            grid = grid.getNextGrid();
        }
    }

    private static void clearGridType() {
        for(int i=0; i<N; ++i) {
            for(int j=0; j<N; ++j) {
                grids[i][j].type = 0;
            }
        }
    }

    static void initGraph() {
        int[] dIdx = {2, 1, 3, 0};
        grids[N/2][N/2].outGoing = dIdx[0];
        int ny = N/2, nx = N/2-1, outGoing = 1;
        int moveAmount = 1, cnt = 1;

        while(ny != 0 || nx != 0) {
            grids[ny][nx].outGoing = dIdx[outGoing];

            ny+=dirs[dIdx[outGoing]][0];
            nx+=dirs[dIdx[outGoing]][1];

            if(--cnt == 0) {
                outGoing = (outGoing+1) % 4;
                if(outGoing % 2 == 0) moveAmount+=1;
                cnt = moveAmount;
            }
        }
        grids[ny][nx].outGoing = -1;
    }

    static class Grid {
        int y, x;
        int outGoing, type;
        Grid(int y, int x, int type) {
            this.y = y;
            this.x = x;
            this.type = type;
        }

        public Grid getNextGrid() {
            if(this.outGoing == -1) return null;
            return grids[y+dirs[outGoing][0]][x+dirs[outGoing][1]];
        }
    }
}
