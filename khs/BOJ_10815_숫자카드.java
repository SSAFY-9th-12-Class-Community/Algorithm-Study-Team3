import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class BOJ_10815_숫자카드 {

    private static final BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

    public static void main(String[] args) throws IOException {
        int n = Integer.parseInt(br.readLine());

        int[] nums = Arrays.stream(br.readLine().split(" ")).mapToInt(Integer::parseInt).toArray();
        Map<Integer, Boolean> mp = new HashMap<>();
        for(int num: nums) mp.put(num, true);

        int m = Integer.parseInt(br.readLine());
        nums = Arrays.stream(br.readLine().split(" ")).mapToInt(Integer::parseInt).toArray();

        StringBuffer sb = new StringBuffer();
        for(int num: nums) {
            int answer = mp.get(num) == null ? 0 : 1;
            sb.append(answer).append(' ');
        }

        System.out.println(sb.toString());
        br.close();
    }
}
