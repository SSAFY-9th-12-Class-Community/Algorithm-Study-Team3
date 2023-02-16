import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.Queue;

public class BOJ_23288_주사위굴리기2 {

    private static Dice dice = new Dice();
    private static int[][] board = null;
    private static final int[][] dirs = {{0, 1},{1, 0}, {0, -1}, {-1, 0}};
    private static int n, m, k;

    public static void main(String[] args) throws IOException {
        int answer = 0;

        init();
        for(int i=0; i<k; ++i) {
            int ny = dice.y + dirs[dice.moveDir][0];
            int nx = dice.x + dirs[dice.moveDir][1];
            if(!isValid(ny, nx)) dice.moveDir = (dice.moveDir+2) % 4;
            dice.move();

            answer += getScore(dice.y, dice.x);

            int diff = dice.getBottomValue() - board[dice.y][dice.x];
            if(diff > 0) {
                dice.moveDir = (dice.moveDir+1) % 4;
            } else if(diff < 0) {
                dice.moveDir = (4+dice.moveDir-1) % 4;
            }
        }

        System.out.println(answer);
    }

    private static void init() throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int[] tmp = Arrays.stream(br.readLine().split(" ")).mapToInt(Integer::parseInt).toArray();
        n = tmp[0]; m = tmp[1]; k = tmp[2];
        board = new int[n][];

        for(int i=0; i<n; ++i) {
            board[i] = Arrays.stream(br.readLine().split(" ")).mapToInt(Integer::parseInt).toArray();
        }
        br.close();
    }

    private static boolean isValid(int y, int x) {
        return (y>=0 && y<n) && (x>=0 && x<m);
    }

    private static int getScore(int y, int x) {
        int nodeCnt = 1, baseScore = board[y][x];

        Queue<Pair> q = new LinkedList<>();
        boolean[][] isVisit = new boolean[n][m];
        isVisit[y][x] = true;
        q.add(new Pair(y, x));

        while(!q.isEmpty()) {
            Pair p = q.poll();

            for(int i=0; i<4; ++i) {
                int ny = p.first + dirs[i][0];
                int nx = p.second + dirs[i][1];
                if(!isValid(ny, nx) || isVisit[ny][nx] || board[ny][nx] != baseScore) continue;
                isVisit[ny][nx] = true;
                q.add(new Pair(ny, nx));
                nodeCnt++;
            }
        }

        return nodeCnt * baseScore;
    }

    static class Pair {
        int first, second;
        public Pair(int y, int x) {this.first= y; this.second= x;}
    }

    static class Dice {
        int y = 0, x = 0;
        int[] plane = {4, 3, 2, 1, 5, 6}; // (서쪽, 동쪽), (뒷면, 윗면, 앞면, 아랫면)
        int moveDir = 0; //0-> 오른쪽, 1-> 아래쪽, 2-> 왼쪽, 3-> 위쪽

        public void move() {
            y += dirs[moveDir][0];
            x += dirs[moveDir][1];

            if(moveDir % 2 == 0) {
                int[] tmp = { plane[0], plane[3], plane[1], plane[5]};
                rotate(tmp, dirs[moveDir][1]);
                plane[0] = tmp[0];
                plane[3] = tmp[1];
                plane[1] = tmp[2];
                plane[5] = tmp[3];
            } else {
                int[] tmp = Arrays.copyOfRange(plane, 2, 6);
                rotate(tmp, dirs[moveDir][0]);
                System.arraycopy(tmp, 0, plane, 2, 4);
            }
        }

        private void rotate(int[] subArray, int addValue) {
            int idx = (addValue == -1) ? 3 : 0;
            int prev = subArray[idx];
            for(int i=0; i<4; ++i) {
                idx = (4+idx+addValue) % 4;
                int tmp = subArray[idx];
                subArray[idx] = prev;
                prev = tmp;
            }
        }

        public int getBottomValue() {
            return this.plane[5];
        }
    }
}
