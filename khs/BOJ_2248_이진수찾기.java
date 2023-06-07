import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.StringTokenizer;

/*
  https://www.acmicpc.net/problem/2248
  이진수 찾기
  DP, 파스칼 삼각형 응용 문제 
  --- 
  삽질한 것 :  최대 N = 31,  L= 31 이므로,  I의 최대범위는 문제에서 나올수 있는 모든 경우의 수를 커버할 수 있는 (31C0 + 31C1 ... + 31C31) long으로 선택되어야한다.
  (런타임 에러 (NumberFormat)) 원인을 못 잡고 있었는데 해당 문제였음 
*/
public class BOJ_2248_이진수찾기 {

    private static int N, L;
    private static Long I;
    private static int[][] dp = new int[32][32];

    // N 자리의 이진수에서 L개 이하의 비트가 1인 것을 크기순으로 나열 할 때 순번이 I번째인 이진수
    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        N = Integer.parseInt(st.nextToken());
        L = Integer.parseInt(st.nextToken());
        I = Long.parseLong(st.nextToken());
        br.close();

        for(int i=0; i<32; ++i) Arrays.fill(dp[i], -1);

        System.out.println(getAnswer("", 0, L, I));
    }
  
    // 정답이 될 이진수 String, 이진수의 맨 앞자리부터 시작하는 인덱스,  아직 선택할 수 있는 1의 갯수,  remain 아직 남은 경우의 수 
    private static String getAnswer(String answer, int idx, int r, long remain) {
        if(answer.length() == N) return answer;
        
        /*
          현재 자리수를 1로 선택한다고 가정하면, 다음 자리수에서 선택 가능한 모든 1을 선택하는 경우의 수 만큼이 오름차순으로 추가되는 효과가 있다.
          
          ex) 5 3 19에서 3개 이하의 비트가 1인 숫자들을 오름차순으로 정렬해보자.
          
          00000
          00001
          00010
          00011
          00100
          
          00101
          00110
          00111
          01000
          01001
          
          01010
          01011
          01100
          01101
          01110
          
          10000    (16번째)
          10001
          10010
          10011
          
          이때 16번째 수인 10000를 관찰해보자. 해당 숫자는 왜 16번째 일까?
          
          -> 해당 숫자보다 작으면서 1인 비트가 L개 이하인 수는 다음 숫자들의 합이다.
          
          첫 번째 자리가 0이면서 남은 4자리 숫자 중에서 1를 3개 선택하는 모든 경우의 수  -> 00111, 01011, 01101, 01110
          첫 번째 자리가 0이면서 남은 4자리 숫자 중에서 1를 2개 선택하는 모든 경우의 수  -> 00011, 00101, 00110, 01001, 01010, 01100
          첫 번째 자리가 0이면서 남은 4자리 숫자 중에서 1를 1개 선택하는 모든 경우의 수  -> 00001, 00010, 00100, 01000
          첫 번째 자리가 0이면서 남은 4자리 숫자 중에서 1를 0개 선택하는 모든 경우의 수  -> 00000
          
          따라서 현재 자리수를 1로 선택한다는 것은 
          
          (전체 길이 - 현재 자리수 - 1) Combination R  (R <= 현재 1로 바꿀 수 있는 갯수, R>=0) 의 모든 합 
          만큼 경우의 수를 소모하는 것과 동일하다. 
          
          문제를 이렇게 바꾸면 결국 문제는 파스칼 삼각형을 DP로 구하는 문제로 변하게 된다. 
          
        */
        long caseCnt = 0;
        for (int i = r; i >= 0; --i) caseCnt += calcCombination(N-idx-1, i);

        if(remain > caseCnt) {
            return getAnswer(answer+1, idx+1, r-1, remain-caseCnt);
        } else if(remain < caseCnt) {
            return getAnswer(answer+0, idx+1, r, remain);
        } else {
            answer += 0;
            StringBuilder answerBuilder = new StringBuilder(answer);
            while(answerBuilder.length() < N) {
                if(r-->0) answerBuilder.append(1);
                else answerBuilder.append(0);
            }
            answer = answerBuilder.toString();
            return answer;
        }
    }

    private static int calcCombination(int n, int r) {
        if(n<r) return 0;
        if(n==r || r == 0) return dp[n][r] = 1;
        if(dp[n][r] != -1) return dp[n][r];
        return dp[n][r] = calcCombination(n-1, r-1) + calcCombination(n-1, r);
    }
}
