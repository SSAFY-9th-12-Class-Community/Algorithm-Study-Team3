import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

public class BOJ_20056_마법사상어와파이어볼 {

    private static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    private static int[][] dirs = { {-1, 0}, {-1, 1}, {0, 1}, {1, 1},
                                    {1, 0}, {1, -1}, {0, -1}, {-1, -1} };
    private static int n, m, k;
    private static Queue<FireBall>[][] grid;
    private static List<FireBall> fireBalls = new LinkedList<>();

    public static void main(String[] args) throws Exception {
        int[] tmp = read();
        n = tmp[0]; m = tmp[1]; k = tmp[2];

        grid = new Queue[n][n];
        for(int i=0; i<n; ++i) {
            for(int j=0; j<n; ++j) {
                grid[i][j] = new LinkedList<>();
            }
        }

        for(int i=0; i<m; ++i) {
            tmp = read();
            fireBalls.add(new FireBall(tmp[0]-1, tmp[1]-1, tmp[2], tmp[3], tmp[4]));
        }

        for(int i=0; i<k; ++i) {
            if(fireBalls.size() == 0) break;
            fireBalls.forEach(FireBall::move);
            mergeAndDivide();
        }

        int answer = fireBalls.stream().mapToInt(e->e.m).sum();
        System.out.println(answer);
    }

    private static void mergeAndDivide() {
        for(int i=0; i<n; ++i) {
            for(int j=0; j<n; ++j) {
                int size = grid[i][j].size();
                // 2개 이상의 파이어볼이 있는 칸에서만 분할이 일어난다. 
                if(size < 2) {
                    grid[i][j].clear();
                    continue;
                }

                int totalMass = 0, totalSpeed = 0;
                int odd = 0, even = 0;
                while(!grid[i][j].isEmpty()) {
                    FireBall fb = grid[i][j].poll();
                    fireBalls.remove(fb);

                    totalMass += fb.m;
                    totalSpeed += fb.s;
                    
                    if(fb.d % 2 == 0) even++;
                    else odd++;
                }

                int avgMass = totalMass / 5;
                int avgSpeed = totalSpeed / size;
                if(avgMass == 0) continue;

                int dir = (odd == 0 || even == 0) ? 0 : 1;
                for(;dir<8; dir+=2) {
                   fireBalls.add(new FireBall(i, j, avgMass, avgSpeed, dir));
                }
            }
        }
    }

    private static int[] read() throws IOException {
        return Arrays.stream(br.readLine().split(" ")).mapToInt(Integer::parseInt).toArray();
    }

    static class FireBall {
        int r, c, m, d, s;

        FireBall(int r, int c, int m, int s, int d) {
            this.r = r;
            this.c = c;
            this.m = m;
            this.s = s;
            this.d = d;
        }

        void move() {
            this.r = (n + this.r + dirs[this.d][0] * (this.s % n)) % n;
            this.c = (n + this.c + dirs[this.d][1] * (this.s % n)) % n;

            grid[this.r][this.c].add(this);
        }
    }
}
