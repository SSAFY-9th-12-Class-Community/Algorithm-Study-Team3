import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.*;

public class BOJ_17822 {
    static int N, M ,T;
    static List<Integer>[] disks;
    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        inputValues(br);
        start(br);
        System.out.println(getSum());
    }

    private static int getSum() {
        int sum = 0;
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < M; j++) {
                sum += disks[i].get(j);
            }
        }
        return sum;
    }

    private static int getCount() {
        int cnt = 0;
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < M; j++) {
                if(disks[i].get(j) > 0) cnt++;
            }
        }
        return cnt;
    }

    private static void changeByAverage(double avg) {
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < M; j++) {
                int num = disks[i].get(j);
                if (num != 0 && num > avg) {
                    disks[i].set(j,  num - 1);
                }else if(num != 0 && num < avg) {
                    disks[i].set(j, num + 1);
                }
            }
        }
    }

    public static void start(BufferedReader br) throws Exception {
        int x, d, k;
        int[] input;

        for (int i = 0; i < T; i++) {
            input = Arrays.stream(br.readLine().split(" ")).mapToInt(Integer::parseInt).toArray();
            x = input[0];
            d = input[1];
            k = input[2];

            rotate(x, d, k); // 1번: 회전
            if(!eraseNumbers()) { // 2번: 원판에 수가 남아있고 인접하면서 수가 같은 것을 모두 찾는다.
                int sum = getSum();
                int count = getCount();
                double avg = (sum * 1.0) / count;
                changeByAverage(avg);
            }
        }
    }

    public static boolean eraseNumbers() {
        List<Integer>[] newDisks = new ArrayList[N];
        boolean isPossible = false;
        for (int i = 0; i < N; i++) {
            List<Integer> newArr = new ArrayList<>();

            for (int j = 0; j < M; j++) {
                newArr.add(0); // 0으로 초기화
            }

            for (int k = 0; k < M; k++) { // 인접에 대한 조건 모두 작성 해당 원소가 0이라는 것은 삭제되었다는 의미
                if(i == 0 && disks[i].get(k) != 0 && Objects.equals(disks[i].get(k), disks[i + 1].get(k))) {
                    isPossible = true;
                    continue;
                } else if(i == N - 1 && disks[i].get(k) != 0 && Objects.equals(disks[i].get(k), disks[i - 1].get(k))) {
                    isPossible = true;
                    continue;
                } else if(k == 0 && disks[i].get(k) != 0 && Objects.equals(disks[i].get(k), disks[i].get(k + 1))) {
                    isPossible = true;
                    continue;
                } else if (k == 0 && disks[i].get(k) != 0 && Objects.equals(disks[i].get(k), disks[i].get(M - 1))) {
                    isPossible = true;
                    continue;
                } else if (k == M - 1 && disks[i].get(k) != 0 && Objects.equals(disks[i].get(k), disks[i].get(k - 1))) {
                    isPossible = true;
                    continue;
                } else if (k == M - 1 && disks[i].get(k) != 0 && Objects.equals(disks[i].get(k), disks[i].get(0))) {
                    isPossible = true;
                    continue;
                } else if (1 <= k && k <= M - 2 && disks[i].get(k) != 0 && Objects.equals(disks[i].get(k), disks[i].get(k - 1))) {
                    isPossible = true;
                    continue;
                } else if (1 <= k && k <= M - 2 && disks[i].get(k) != 0 && Objects.equals(disks[i].get(k), disks[i].get(k + 1))) {
                    isPossible = true;
                    continue;
                } else if (1 <= i && i <= N - 2 && disks[i].get(k) != 0 && Objects.equals(disks[i].get(k), disks[i - 1].get(k))) {
                    isPossible = true;
                    continue;
                } else if (1 <= i && i <= N - 2 && disks[i].get(k) != 0 && Objects.equals(disks[i].get(k), disks[i + 1].get(k))) {
                    isPossible = true;
                    continue;
                }
                newArr.set(k, disks[i].get(k));
            }
            newDisks[i] = newArr;
        }
        disks = newDisks;
        return isPossible;
    }

    private static void rotate(int x, int d, int k) {
        for (int i = x; i <= N; i = i + x) {
            if(d == 0) { // 시계 방향
                Collections.rotate(disks[i - 1], k);
            } else if (d == 1) {
                Collections.rotate(disks[i - 1], -k);
            }
        }
    }

    public static void inputValues(BufferedReader br) throws Exception {
        String[] input = br.readLine().split(" ");
        StringTokenizer st;
        N = Integer.parseInt(input[0]);
        M = Integer.parseInt(input[1]);
        T = Integer.parseInt(input[2]);

        disks = new ArrayList[N];

        for (int i = 0; i < N; i++) { // 원판 ArrayList로 초기화
            disks[i] = new ArrayList<>();
        }

        for (int i = 0; i < N; i++) {
            st = new StringTokenizer(br.readLine());
            while (st.hasMoreTokens()) {
                disks[i].add(Integer.parseInt(st.nextToken()));
            }
        }
    }
}
