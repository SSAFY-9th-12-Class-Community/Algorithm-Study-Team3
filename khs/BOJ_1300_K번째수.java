import java.util.Scanner;

public class BOJ_1300_K번째수 {
	
	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);
		int n = sc.nextInt(), k = sc.nextInt();
		sc.close();
		
		long left = 1, right = Math.min(1000000000, n*n);
		while(left<=right) {
			long mid = (left+right)/2;
			long cnt = 0;
			for(int i=1; i<=n; ++i) {
				cnt += Math.min(n, mid/i);
			}
			
			if(cnt >= k) { 
				right = mid-1;
			} else {
				left = mid+1;
			}
		}
		System.out.println(left);
	}
}
