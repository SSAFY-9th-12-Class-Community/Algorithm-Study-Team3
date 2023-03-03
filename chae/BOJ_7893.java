import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.Comparator;
import java.util.PriorityQueue;

// 골드 5
// 내일 할거야
public class BOJ_7893 {
    static int n;
    static int currentDay;
    static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    static PriorityQueue<Task> pq = new PriorityQueue<>(new Comparator<Task>() {
        @Override
        public int compare(Task o1, Task o2) {
            return o2.t - o1.t;
        }
    });

    public static void main(String[] args) throws IOException {

        n = Integer.parseInt(br.readLine());

        for (int i = 0; i < n; i++) {
            pq.add(new Task(Arrays.stream(br.readLine().split(" ")).mapToInt(Integer::parseInt).toArray()));
        }

        currentDay = pq.peek().t;

        for (int i = 0; i < n; i++) {
            Task task = pq.poll();
            currentDay = Math.min(currentDay, task.t);
            currentDay -= task.d;
        }
        System.out.println(currentDay);
    }

}

class Task {
    int d;
    int t;

    public Task(int[] dt) {
        this.d = dt[0];
        this.t = dt[1];
    }
}
