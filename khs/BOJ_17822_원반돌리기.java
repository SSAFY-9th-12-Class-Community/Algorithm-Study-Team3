import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.StringTokenizer;

public class BOJ_17822_원반돌리기 {

    private static final BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    private static int N, M, T;
    private static List<Integer>[] disks; // 각 디스크별 숫자들을 저장한다.

    public static void main(String[] args) throws Exception {
        StringTokenizer st = new StringTokenizer(br.readLine());
        N = Integer.parseInt(st.nextToken());
        M = Integer.parseInt(st.nextToken());
        T = Integer.parseInt(st.nextToken());

        disks = new List[N + 1];
        for (int i = 1; i <= N; ++i) {
            disks[i] = new ArrayList<>();
            st = new StringTokenizer(br.readLine());
            for (int j = 0; j < M; ++j) {
                disks[i].add(Integer.parseInt(st.nextToken()));
            }
        }

        for (int i = 0; i < T; ++i) {
            st = new StringTokenizer(br.readLine());
            int x = Integer.parseInt(st.nextToken());
            int d = Integer.parseInt(st.nextToken());
            int rotateAmount = Integer.parseInt(st.nextToken());
            if (d == 1) rotateAmount *= -1;

            // 회전
            for (int j = x; j <= N; j += x) Collections.rotate(disks[j], rotateAmount);

            // 회전 후 인접 노드에 동일값이 존재하는 경우 해당 숫자값들 제거
            int deleteNumberCnt = 0;
            for(int j=1; j<=N; ++j) {
                for(int k=0; k<M; ++k) {
                    if(disks[j].get(k) == Integer.MIN_VALUE) continue;
                    deleteNumberCnt += removeSameValueAdjNumber(j, k);
                }
            }

            // 만일 제거한 노드가 단 하나도 존재하지 않는다면 평균값을 구해서 평균보다 작으면 +1, 크면 -1 해준다.
            if(deleteNumberCnt == 0) {
                double totalSum = 0.0;
                int cnt = 0;

                for(int j=1; j<=N; ++j) {
                    for(int k=0; k<M; ++k) {
                        int currentNum = disks[j].get(k);
                        if(currentNum == Integer.MIN_VALUE) continue;
                        totalSum += currentNum; //현재 원판에서 삭제되지 않은 숫자들을 모두 구한다.
                        cnt += 1;
                    }
                }

                double avg = totalSum / cnt;
                for(int j=1; j<=N; ++j) {
                    for(int k=0; k<M; ++k) {
                        int currentNum = disks[j].get(k);
                        if(currentNum == Integer.MIN_VALUE) continue;

                        if(avg > currentNum) {
                            disks[j].set(k, currentNum+1);
                        } else if(avg < currentNum) {
                            disks[j].set(k, currentNum-1);
                        }
                    }
                }
            }
        }

        int answer = 0;
        for (int i = 1; i <= N; ++i) {
            answer += disks[i].stream().filter(e -> e != Integer.MIN_VALUE).reduce(0, Integer::sum);
        }
        System.out.println(answer);
    }

    private static int removeSameValueAdjNumber(int y, int x) {
        int originNumber = disks[y].get(x);
        Queue<int[]> q = new LinkedList<>();
        int ret = checkSameDisk(q, y, x, originNumber);

        while(!q.isEmpty()) {
            int[] i = q.poll();
            ret += checkSameDisk(q, i[0], i[1], originNumber);
            ret += checkAdjDisk(q, i[0], i[1], originNumber);
        }

        if(ret == 1) {
            //현재 검사중인 원판의 자리와 인접한 자리에서, 자기 자신과 동일한 숫자가 없다면 원상복귀해준다.
            disks[y].set(x, originNumber);
            --ret;
        }
        return ret;
    }

    // 현재 위치인 (y, x)를 기준으로 양 옆을 확인하며 인접한 자리 중 자신과 동일한 값이 있는 지 검사한다.
    private static int checkSameDisk(Queue<int[]> q, int y, int x, int originNumber) {
        if(disks[y].get(x) == Integer.MIN_VALUE) return 0;

        int ret = 1;
        disks[y].set(x, Integer.MIN_VALUE);
        q.add(new int[] {y, x});

        int left = (x == 0) ? M-1 : x-1;
        if(disks[y].get(left) == originNumber) {
            ret += checkSameDisk(q, y, left, originNumber);
        }

        int right = (x == M-1) ? 0 : x+1;
        if(disks[y].get(right) == originNumber) {
            ret += checkSameDisk(q, y, right, originNumber);
        }
        return ret;
    }

    // 현재 위치에서 인접한 다른 원판들 중 자기 자신의 값과 동일한 것이 있는지 검사한다. 
    private static int checkAdjDisk(Queue<int[]> q, int y, int x, int originNumber) {
        int ret = 0;
        int prevDepth = Math.max(y-1, 1);
        int nextDepth = Math.min(y+1, N);

        if(disks[prevDepth].get(x) == originNumber) {
            ret++;
            q.add(new int[] {prevDepth, x});
        }

        if(disks[nextDepth].get(x) == originNumber) {
            ret++;
            q.add(new int[] {nextDepth, x});
        }
        return ret;
    }
}
