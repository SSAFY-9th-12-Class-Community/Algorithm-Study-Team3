import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class BOJ_14719_빗물 {

    static int h, w;
    static int[][] blocks;  // 주어진 높이를 이용해 실제 블록과, 빗물이 쌓인 모양을 2차원 배열로 생성한다.

    public static void main(String[] args) throws Exception {
        input();

        int answer = 0;
        // 각 행에 모든 열을 확인한다. 만약 열의 값이 0이라면, 해당 count값을 증가시키며 누적해나간다. 
        // 만약 열의 값이 1이라면, 해당 열에는 블록이 설치되어있는 상태이다. 이 때 현재 열의 번호 - (count+1)의 값이 1이라면, 블록에 둘러 쌓여있는 형태이므로 빗물이 count값 만큼 쌓인다.
        for(int i=h-1; i>=0; --i) {
            int cnt = 0;

            for(int j=0; j<w; ++j) {
                if(blocks[i][j] == 0) {
                    cnt++;
                    continue;
                }

                if(j>=cnt+1 && blocks[i][j-cnt-1] == 1) answer += cnt;
                cnt = 0;
            }
        }
        System.out.println(answer);
    }

    private static void input() throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());

        h = Integer.parseInt(st.nextToken()); w = Integer.parseInt(st.nextToken());

        blocks = new int[h][w];
        st = new StringTokenizer(br.readLine());
        for(int i=0; i<w; ++i) {
            int height = Integer.parseInt(st.nextToken());
            for(int j=h-1; j>=h-height; --j) {
                blocks[j][i] = 1;
            }
        }
        br.close();
    }
}
