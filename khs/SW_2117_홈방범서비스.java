import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;

public class SW_2117_홈방범서비스 {

    private static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    private static StringBuilder sb = new StringBuilder();
    private static final int EMPTY_SPACE = 0;
    private static int[] nm;
    private static int[][] city;

    public static void main(String[] args) throws Exception {
        int testCase = Integer.parseInt(br.readLine());
        for(int tc=1; tc<=testCase; ++tc) {
            nm = read();
            city = new int[nm[0]][];
            for(int i=0; i<nm[0]; ++i) city[i] = read();

            int answer = 0;
            for(int i=0; i<nm[0]; ++i) {
                for(int j=0; j<nm[0]; ++j) {  
                    // n * n 격자 모두를 포함하기 위해서는, 격자의 중심에서 맨하탄 거리가 n만큼 되는 범위를 모두 포함해야한다.
                    // 서비스 영역이 k일때, 서비스를 시작하는 좌표로 부터 맨하탄거리가 k-1만큼인 영역을 확인하므로 dist의 최댓값은 nm[0]+1만큼 되야한다. 
                    for(int dist=1; dist<=nm[0]+1; ++dist) {
                        int[] ret = getProfit(i, j, dist);
                        if(ret[1] >= 0) {
                            answer = Math.max(answer, ret[0]);
                        }
                    }
                }
            }
            sb.append('#').append(tc).append(' ').append(answer).append('\n');
        }

        System.out.println(sb.toString());
        br.close();
    }

    private static int getManhattanDist(int y1, int y2, int x1, int x2) {
        return Math.abs(y1-y2) + Math.abs(x1-x2);
    }

    private static int[] getProfit(int y, int x, int k) {
        int cnt = 0;
        int ret = -(k*k + (k-1)*(k-1));

        for(int i=y-(k-1); i<y+k; ++i) {
            for(int j=x-(k-1); j<x+k; ++j) {
                if(!isValid(i, j) || city[i][j] == EMPTY_SPACE) continue;

                if(getManhattanDist(y, i, x, j) <= k-1) {
                    cnt += 1;
                    ret += nm[1];
                }
            }
        }

        return new int[] {cnt, ret};
    }

    private static boolean isValid(int y, int x) {
        return (y>=0 && y<nm[0]) && (x>=0 && x<nm[0]);
    }

    private static int[] read() throws IOException {
        return Arrays.stream(br.readLine().split(" ")).mapToInt(Integer::parseInt).toArray();
    }
}
