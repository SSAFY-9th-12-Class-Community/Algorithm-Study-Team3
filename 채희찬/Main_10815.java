package BeakJoon;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.Arrays;

// 실버 5
// 숫자 카드
public class Main_10815 {
  private static final BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
  private static final BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));

  public static void main(String[] args) throws IOException {
    BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

    int n = Integer.parseInt(br.readLine());
    int[] ns = Arrays.stream(br.readLine().split(" ")).mapToInt(Integer::parseInt).toArray();

    int m = Integer.parseInt(br.readLine());
    int[] ms = Arrays.stream(br.readLine().split(" ")).mapToInt(Integer::parseInt).toArray();
    StringBuilder sb = new StringBuilder();

    Arrays.sort(ns);

    for (int data : ms) {
      sb.append(binarySearch(ns, n, data) + " ");
    }

    bw.write(sb.toString());
    bw.close();
  }

  public static int binarySearch(int[] ns, int n, int data) {
    int left = 0;
    int right = n - 1;
    int mid;

    while (left <= right) {
      mid = (left + right) / 2;

      if (ns[mid] == data) {
        return 1;
      }

      if (ns[mid] < data) {
        left = mid + 1;
      } else {
        right = mid - 1;
      }

    }

    return 0;
  }
}
