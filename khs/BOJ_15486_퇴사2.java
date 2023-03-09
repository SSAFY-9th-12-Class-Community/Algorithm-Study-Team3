import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class BOJ_15486_퇴사2 {

    private static int N;

    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st;

        N = Integer.parseInt(br.readLine());
        int[][] consultants = new int[N+1][2];
        for(int i=1; i<=N; ++i) {
            st = new StringTokenizer(br.readLine());
            consultants[i][0] = Integer.parseInt(st.nextToken()); // 상담하는데 소요되는 시간
            consultants[i][1] = Integer.parseInt(st.nextToken()); // 상담하고 얻는 이득
        }

        int[] dp = new int[N+2];  // dp[i] : 선택의 시작일을 i일로 고정했을 때, 상담을 통해 얻을 수 있는 최대 이득 
        if(consultants[N][0] == 1) dp[N] = consultants[N][1];  // 마지막 상담에 걸리는 시간이 하루이면, 시작일이 N번째 날일 때 얻을 수 있는 이득을 갱신한다. 

        for(int i=N-1; i>=1; --i) {
            dp[i] = dp[i+1];
            int currentJobEndTime = (i+consultants[i][0]-1);
            // 만일 현재 상담을 선택할 수 있다면, 시작일을 i+1번째로 했을 경우와, 현재 상담을 선택하는 경우를 비교해서 이득이 더 큰 것을 선택한다. 
            if(currentJobEndTime <= N) dp[i] = Math.max(dp[i], dp[currentJobEndTime+1]+consultants[i][1]);
        }

        System.out.println(dp[1]);
    }
}





/*
초기 재귀함수를 통한 구현 
자바로 하면 이상하게 시간 초과 발생   -> 위의 반복문을 통한 bottom up 방식과 완전히 동일한 로직임 
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.StringTokenizer;

public class BOJ_15486_퇴사2 {

    private static int N;

    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st;

        N = Integer.parseInt(br.readLine());
        int[][] consultants = new int[N+1][2];
        for(int i=1; i<=N; ++i) {
            st = new StringTokenizer(br.readLine());
            consultants[i][0] = Integer.parseInt(st.nextToken());
            consultants[i][1] = Integer.parseInt(st.nextToken());
        }

        int[] dp = new int[N+1];

        Arrays.fill(dp, -1);
        if(consultants[N][0] == 1) dp[N] = consultants[N][1];

        System.out.println(getMaxProfit(consultants, dp, 1));
    }

    static int getMaxProfit(int[][] consultants, int[] dp, int day) {
        if(day > N) return 0;
        if(dp[day] != -1) return dp[day];
        dp[day] = Math.max(0, getMaxProfit(consultants, dp, day+1));
        int nextDay = day + consultants[day][0];
        if(nextDay-1 <= N) dp[day] = Math.max(dp[day], getMaxProfit(consultants, dp, nextDay)+ consultants[day][1]);
        return dp[day];
    }
}

// 동일 코드를 c언어로 제출 시에는 통과 
#include <bits/stdc++.h>

using namespace std;

int N;
vector<int> dp;
vector<vector<int>> consult;

int get_max_profit(int day) {
    if(day > N) return 0;
    if(dp[day] != -1) return dp[day];
    dp[day] = max(0, get_max_profit(day+1));
    int nextDay = day + consult[day][0];
    if(nextDay-1 <= N) dp[day] = max(dp[day], get_max_profit(nextDay)+ consult[day][1]);
    return dp[day];
}

int main() {
    cin.tie(0);
    cout.tie(0);
    ios::sync_with_stdio(false);
    
    cin >> N;
    consult = vector<vector<int>>(N+1, vector<int>(2));

    for(int i=1; i<=N; ++i) {
        cin >> consult[i][0] >> consult[i][1];
    }

    dp = vector<int>(N+1, -1);
    if(consult[N][0] == 1) dp[N] = consult[N][1];

    cout << get_max_profit(1);
    return 0;
}
*/
