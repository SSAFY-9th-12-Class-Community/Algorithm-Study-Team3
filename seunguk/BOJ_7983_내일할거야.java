package day03;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.StringTokenizer;

public class BOJ_7983_내일할거야 {

	public static void main(String[] args) throws NumberFormatException, IOException {
		BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st = new StringTokenizer(in.readLine());
		int N = Integer.parseInt(st.nextToken());
		ArrayList<Work> list = new ArrayList<>();
		for (int i = 0; i < N; i++) {
			st = new StringTokenizer(in.readLine());
			int a = Integer.parseInt(st.nextToken());
			int b = Integer.parseInt(st.nextToken());
			list.add(new Work(a, b));
		}
		Collections.sort(list);

		int start = list.get(N - 1).dueDate;
		for (int i = N - 1; i >= 0; i--) {
			for (int k = 0; k < list.get(i).days; k++) {
				start -= 1;
			}
			if (i == 0) {
				break;
			}

			if (start > list.get(i - 1).dueDate) {
				start = list.get(i - 1).dueDate;
			}

		}
		System.out.println(start);
	}

	static public class Work implements Comparable<Work> {
		int days; // 며칠 걸리는지
		int dueDate; // 언제까지 끝내야하는지

		@Override
		public String toString() {
			return "Work [days=" + days + ", dueDate=" + dueDate + "]";
		}

		public Work(int days, int dueDate) {
			super();
			this.days = days;
			this.dueDate = dueDate;
		}

		@Override
		public int compareTo(Work o) {
			if (o.dueDate < dueDate) {
				return 1;
			} else if (o.dueDate > dueDate) {
				return -1;
			}
			return 0;
		}

	}
}
