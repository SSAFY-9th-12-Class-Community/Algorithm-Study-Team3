package study;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.StringTokenizer;

public class BOJ_10815 {

	public static void main(String[] args) throws NumberFormatException, IOException {

		BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
		int mine = Integer.parseInt(in.readLine());

		int[] myValue = new int[mine];
		StringTokenizer st = new StringTokenizer(in.readLine());
		for (int i = 0; i < mine; i++) {
			myValue[i] = Integer.parseInt(st.nextToken());
		}

		Arrays.sort(myValue);
		int compare = Integer.parseInt(in.readLine());
		int[] answer = new int[compare];
		Pair[] comPairing = new Pair[compare];

		st = new StringTokenizer(in.readLine());
		for (int i = 0; i < compare; i++) {
			int k = Integer.parseInt(st.nextToken());
			comPairing[i] = new Pair(i, k);
		}

		int my_index = 0;
		int compare_index = 0;

		Arrays.sort(comPairing);

		while (compare_index != compare || my_index != mine) {
			if (myValue[my_index] == comPairing[compare_index].value) {
				
				answer[comPairing[compare_index].index]=1;
				if(my_index +1>=mine) {
					break;
				}
				else {
					my_index += 1;
				}
				if(compare_index + 1>=compare) {
					break;
				}
				else {
					compare_index += 1;
				}

			} else if (myValue[my_index] > comPairing[compare_index].value) {
				if(compare_index + 1>=compare) {
					break;
				}
				else {
					compare_index += 1;
				}
			} else if (myValue[my_index] < comPairing[compare_index].value) {
				if(my_index +1>=mine) {
					break;
				}
				else {
					my_index += 1;
				}
			}
		}
		
		for(int i=0;i<compare;i++) {
			System.out.print(answer[i]+" ");
		}
		

	}
}

class Pair implements Comparable<Pair> {
	int index;
	int value;

	public Pair(int index, int value) {

		this.index = index;
		this.value = value;
	}

	@Override
	public int compareTo(Pair o) {
		return this.value - o.value;
	}

}
