import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.PriorityQueue;
import java.util.StringTokenizer;

// n개의 과제, 각 과제 i는 di일이 걸리고 ti일 안에 끝내야함.
class HomeWork implements Comparable<HomeWork> {
    int day;
    int until;

    public HomeWork(int day, int until) {
        this.day = day;
        this.until = until;
    }

    @Override
    public int compareTo(HomeWork h) {
        if (this.until > h.until) {
            return -1;
        } else if (this.until < h.until) {
            return 1;
        }
        return 0;
    }
}
public class BOJ_7983_내일_할거야 {
    static int N;
    static PriorityQueue<HomeWork> pq = new PriorityQueue<>();
    public static void main(String[] args) throws Exception {
        inputValues();
        int endDay = 1000000001;


        while (!pq.isEmpty()) {
            HomeWork h = pq.poll();
            if (endDay <= h.until) {
                h.until = endDay - 1;
            }
            endDay = h.until - h.day + 1;
        }


        System.out.println(endDay - 1);
    }

    public static void inputValues() throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st;
        N = Integer.parseInt(br.readLine());

        for (int i = 0; i < N; i++) {
            st = new StringTokenizer(br.readLine());
            int day = Integer.parseInt(st.nextToken());
            int until = Integer.parseInt(st.nextToken());
            pq.add(new HomeWork(day, until));
        }
    }
}