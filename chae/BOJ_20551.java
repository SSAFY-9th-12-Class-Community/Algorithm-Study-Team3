import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

// 실버 4
// Sort 마스터 배지훈의 후계자
public class BOJ_20551 {
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringBuilder sb = new StringBuilder();

        int[] nm = Arrays.stream(br.readLine().split(" ")).mapToInt(Integer::parseInt).toArray();

        int n = nm[0];
        int m = nm[1];

        int[] arr = new int[n];
        for (int i =0; i < n; i++){
            arr[i] = Integer.parseInt(br.readLine());
        }
        Arrays.sort(arr);

        Map<Integer, Integer> map = new HashMap<>();
        for(int i = 0; i < n; i++){
            if(!map.containsKey(arr[i])){
                map.put(arr[i], i);
            }
        }

        while(m-- > 0){
            int x = Integer.parseInt(br.readLine());
            if (map.containsKey(x))
                sb.append(map.get(x)).append("\n");
            else
                sb.append(-1).append("\n");
        }

        System.out.println(sb);

    }

//    public static void main(String[] args) throws IOException {
//        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
//        StringBuilder sb = new StringBuilder();
//
//        int[] nm = Arrays.stream(br.readLine().split(" ")).mapToInt(Integer::parseInt).toArray();
//
//        int n = nm[0];
//        int m = nm[1];
//
//        int[] arr = new int[n];
//        for (int i = 0; i < n; i++) {
//            arr[i] = Integer.parseInt(br.readLine());
//        }
//        Arrays.sort(arr);
//
//        int mid  = 0;
//
//        while (m-- > 0) {
//            int x = Integer.parseInt(br.readLine());
//            int left = 0, right = n - 1;
//
//            while (left <= right) {
//                mid = (left + right);
//                if (arr[mid] < x) {
//                    left = mid + 1;
//                } else if (arr[mid] > x) {
//                    right = mid - 1;
//                } else {
//                    if (right == mid) break;
//                    right = mid;
//                }
//            }
//            if (arr[mid] == x){
//                sb.append(mid).append("\n");
//            }else{
//                sb.append(-1).append("\n");
//            }
//
//        }
//        System.out.println(sb);
//
//    }


}