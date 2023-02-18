import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

public class SW_2382_미생물격리 {

    private static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    private static StringBuilder sb = new StringBuilder();

    private static int[][] dirs = {{-1, 0}, {1, 0}, {0, -1}, {0, 1}};
    private static List<Community> communities = new ArrayList<>();
    private static int n, m, k;

    public static void main(String[] args) throws Exception {
        int testCase = Integer.parseInt(br.readLine());

        for(int tc=1; tc<=testCase; ++tc) {
            StringTokenizer st = new StringTokenizer(br.readLine());
            n = Integer.parseInt(st.nextToken());
            m = Integer.parseInt(st.nextToken());
            k = Integer.parseInt(st.nextToken());

            communities.clear();
            for(int i=0; i<k; ++i) {
                int[] tmp = read();
                communities.add(new Community(tmp[0], tmp[1], tmp[2], tmp[3]-1));
            }

            int answer = 0;
            for(int i=0; i<m; ++i) {
                int len = communities.size();
                answer = 0;
                if(len == 0) break;

                // move
                communities.forEach(Community::move);
                // merge
                int[][][] cells = new int[n][n][2];
                for(int j=0; j<len; ++j) {
                    Community c = communities.get(j);
                    if(cells[c.y][c.x][1] != 0) {
                        int prevMaxIdx = cells[c.y][c.x][0];
                        if(communities.get(prevMaxIdx).amount < c.amount) cells[c.y][c.x][0] = j;
                    } else {
                        cells[c.y][c.x][0] = j;
                    }

                    cells[c.y][c.x][1] += c.amount;
                }

                List<Community> tmp = new ArrayList<>();
                for(int j=0; j<len; ++j) {
                    Community c = communities.get(j);
                    if(cells[c.y][c.x][0] != j) continue;
                    c.amount = cells[c.y][c.x][1];
                    answer += c.amount;
                    if(c.amount != 0) tmp.add(c);
                }

                communities = tmp;
            }

            sb.append('#').append(tc).append(' ').append(answer).append('\n');
        }

        System.out.println(sb.toString());
        br.close();
    }

    private static int[] read() throws IOException {
        return Arrays.stream(br.readLine().split(" ")).mapToInt(Integer::parseInt).toArray();
    }

    private static class Community {
        int y, x, amount, dir;

        Community(int y, int x, int amount, int dir) {
            this.y = y;
            this.x = x;
            this.amount = amount;
            this.dir = dir;
        }

        void move() {
            this.y += dirs[this.dir][0];
            this.x += dirs[this.dir][1];

            if(this.y == 0 || this.x == 0 || this.y == n-1 || this.x == n-1) {
                if(this.dir % 2 == 0) this.dir += 1;
                else this.dir -= 1;
                this.amount /= 2;
            }
        }
    }
}
