import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.StringTokenizer;

public class Main {
	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringBuilder sb = new StringBuilder();
		StringTokenizer st = null;
		
		int N = Integer.parseInt(br.readLine());
		int[] arr = new int[N];
		st = new StringTokenizer(br.readLine());
		for(int i = 0; i < N; i++) {
			arr[i] = Integer.parseInt(st.nextToken());
		}
		Arrays.sort(arr);
		N = Integer.parseInt(br.readLine());
		st = new StringTokenizer(br.readLine()); 
		for(int i = 0; i < N; i++) {
			int num = Integer.parseInt(st.nextToken());
			if(Arrays.binarySearch(arr, num) >= 0) sb.append("1\n");
			else sb.append("0\n");
		}
		System.out.println(sb);
	}
}
