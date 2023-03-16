import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.*;

public class SW_2383_점심식사시간 {

    private static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    private static StringBuilder sb = new StringBuilder();
    private static int[][] room;
    private static final Stair[] stairs = new Stair[2];
    private static final int[][] peoples = new int[10][2];
    private static final int[] selectedStairNumber = new int[10];
    private static int N, peopleCnt, stairCnt, answer;

    public static void main(String[] args) throws Exception {
        int T;
        T = Integer.parseInt(br.readLine());

        for(int tc=1; tc<=T; ++tc) {
            N = Integer.parseInt(br.readLine());
            peopleCnt = stairCnt = 0;
            room = new int[N][N];
            input();

            answer = Integer.MAX_VALUE;
            selectStairAndSimulation(0);
            sb.append('#').append(tc).append(' ').append(answer).append('\n');
        }

        System.out.println(sb.toString());
        br.close();
    }

    private static void selectStairAndSimulation(int cnt) {
        if(cnt == peopleCnt) {
            answer = Math.min(answer, simulation());
            return;
        }

        selectedStairNumber[cnt] = 0;
        selectStairAndSimulation(cnt+1);
        selectedStairNumber[cnt] = 1;
        selectStairAndSimulation(cnt+1);
    }

    private static int simulation() {
        int ret = 0;
        // 각 사람을 자기 자신이 선택한 계단에 위치시킨다. (도착시간 + 대기시간 1분)
        for(int i=0; i<peopleCnt; ++i) {
            Stair s = stairs[selectedStairNumber[i]];
            s.insertWaitingQueue(getManhattanDistance(s.y, s.x, peoples[i][0], peoples[i][1])+1);
        }

        // 각 큐에 존재하는 모든 인원을 제거할 때까지 작업을 진행한다.
        for(int i=0; i<stairCnt; ++i) {
            ret = Math.max(ret, stairs[i].consume());
        }
        return ret;
    }


    private static int getManhattanDistance(int y1, int x1, int y2, int x2) {
        return Math.abs(y1-y2) + Math.abs(x1-x2);
    }

    private static void input() throws Exception {
        StringTokenizer st;
        for(int i=0; i<N; ++i) {
            st = new StringTokenizer(br.readLine());
            for(int j=0; j<N; ++j) {
                room[i][j] = Integer.parseInt(st.nextToken());

                if(room[i][j] > 1) {
                    stairs[stairCnt++] = new Stair(i, j, room[i][j]);
                } else if(room[i][j] == 1) {
                    peoples[peopleCnt][0] = i;
                    peoples[peopleCnt++][1] = j;
                }
            }
        }
    }

    static class Stair {
        int y, x, k;
        PriorityQueue<Integer> waitingQueue;  // [도착 시간]  
        Deque<Integer> jobQueue; // [작업의 종료시간]

        Stair(int y, int x, int k) {
            this.y = y;
            this.x = x;
            this.k = k;
            this.waitingQueue = new PriorityQueue<>();
            this.jobQueue = new ArrayDeque<>();
        }

        void insertWaitingQueue(int arrivalTime) {
            waitingQueue.add(arrivalTime);
        }

        public int consume() {
            if(waitingQueue.isEmpty()) return 0;

            while(!waitingQueue.isEmpty() && jobQueue.size() < 3) {
                jobQueue.add(k+waitingQueue.poll());
            }

            while (!waitingQueue.isEmpty() && !jobQueue.isEmpty()) {
                int endTime = jobQueue.poll();
                if(waitingQueue.isEmpty()) continue;
                
                // 계단을 다 내려간 사람이 생길 때, 대기 중인 사람이 존재한다면 작업큐에 넣어준다.
                // 만일 작업 완료 이전에 들어온 사람인 경우, 계단을 내려가기 시작한 시간이 작업이 완료된 시점이므로 작업완료시간 + K가 해당 사람이 계단을 다 내려간 시점이다.
                int arrivalTime = waitingQueue.poll();
                if(arrivalTime > endTime) jobQueue.add(k+arrivalTime);
                else jobQueue.add(k+endTime);
            }
            
            int ret;
            ret = jobQueue.peekLast();
            jobQueue.clear();
            return ret;
        }
    }
}
