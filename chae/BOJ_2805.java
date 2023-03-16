import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.StringTokenizer;

// 실버 2
// 나무 자르기
public class BOJ_2805 {
    static int n;
    static int m;
    static int[] trees;
    static long max = 0;

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());

        n = Integer.parseInt(st.nextToken());
        m = Integer.parseInt(st.nextToken());

        trees = Arrays.stream(br.readLine().split(" ")).mapToInt(Integer::parseInt).toArray();
        max = Arrays.stream(trees).max().getAsInt();

        long start = 0;
        long end = max;

        while (start <= end) {
            long mid = (start + end) / 2;
            long sum = 0;

            for (int tree : trees) {
                if (tree > mid) {
                    sum += tree - mid;
                }
            }

            if (sum >= m) {
                start = mid + 1;
            }else {
                end = mid - 1;
            }
        }
        System.out.println(end);
    }

//    시간 초과
//    static int[] trees;
//    public static void main(String[] args) throws IOException {
//        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
//
//        StringTokenizer st = new StringTokenizer(br.readLine());
//        int n = Integer.parseInt(st.nextToken());
//        int m = Integer.parseInt(st.nextToken());
//
//        trees = Arrays.stream(br.readLine().split(" ")).mapToInt(Integer::parseInt).toArray();
//
//        binarySearch(m, 1, Arrays.stream(trees).max().getAsInt());
//
//    }
//
//    public static void binarySearch(int target, int start, int end){
//        if (start == end) {
//            System.out.println(start);
//            return;
//        }
//        int mid = (end + start) / 2;
//
//        int tree = cutTrees(mid);
//
//        if (tree < target){
//            binarySearch(target, start, mid - 1);
//        } else if (tree > target) {
//            binarySearch(target, mid + 1, end);
//        }else {
//            System.out.println(mid);
//        }
//    }
//    public static int cutTrees(int height) {
//        int sum = 0;
//        for (int i = 0; i < trees.length; i++){
//            if (trees[i] > height){
//                sum += trees[i] - height;
//            }
//        }
//
//        return sum;
//    }
}
