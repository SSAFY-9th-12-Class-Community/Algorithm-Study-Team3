import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;

public class BOJ_20551_Sort마스터배지훈의후계자 {

    static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    static StringBuilder sb = new StringBuilder();
    static int nm[] = null;

    public static void main(String[] args) throws IOException {
        nm = Arrays.stream(br.readLine().split(" ")).mapToInt(Integer::parseInt).toArray();
        int[] arr = new int[nm[0]];
        for(int i=0; i<nm[0]; ++i) arr[i] = Integer.parseInt(br.readLine());

        Arrays.sort(arr);
        for(int i=0; i<nm[1]; ++i) {
            sb.append(binarySearch(arr, Integer.parseInt(br.readLine()))).append('\n');
        }
        System.out.println(sb.toString());
    }

    public static int binarySearch(int[] arr, int n) {
        int left = 0, right = arr.length-1;

        while(left < right) {
            int mid = (left+right) / 2;
            if(arr[mid] >= n) right = mid; 
            else left = mid+1;
        }
        return arr[right] == n ? right : -1;
    }
}
