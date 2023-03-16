import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;

public class BOJ_2805_나무자르기 {

    private static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    private static int[] nm;
    
    public static void main(String[] args) throws IOException {
        nm = read();
        int[] arr = read();
        System.out.println(binarySearch(arr));
    }

    private static int binarySearch(int[] arr) {
        Arrays.sort(arr);
        int left = 0, right = arr[arr.length-1];

        while(left<right) {
            int mid = (left+right) / 2;
            long cnt = 0;
            for(int n: arr) {
            	if(n > mid) cnt += (n-mid);
            }
            
            if(cnt >= nm[1]) left = mid+1;
            else right = mid;
        }
        return left-1;
    }

    private static int[] read() throws IOException {
        return Arrays.stream(br.readLine().split(" ")).mapToInt(Integer::parseInt).toArray();
    }
}
