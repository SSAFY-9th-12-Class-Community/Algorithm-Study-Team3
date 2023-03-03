
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.StringTokenizer;

public class Main {
	public static class Assignment implements Comparable<Assignment>{ // 과제 정보 (걸리는 시간, 마감날짜) 객체
		int day;
		int limit;
		public Assignment(int day, int limit) { 
			this.day = day;
			this.limit = limit;
		}
		@Override
		public int compareTo(Assignment o) { // 마감 날짜가 늦은 순으로 정렬 (내림차순)
			return o.limit - this.limit;
		}
	}
	public static void main(String[] args) throws NumberFormatException, IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st = null;
		int n = Integer.parseInt(br.readLine());
		ArrayList<Assignment> list = new ArrayList<>(); // 과제 정보 저장하기 위한 리스트 생성
		for(int i = 0; i < n; i++) { // 입력 -> 과제 정보들 가지고 있는 리스트
			st = new StringTokenizer(br.readLine());
			list.add(new Assignment(Integer.parseInt(st.nextToken()), Integer.parseInt(st.nextToken())));
		}
		Collections.sort(list); // 그리디 알고리즘 적용하기 위해 마감 날짜가 늦은 순으로 정렬
		int rest = list.get(0).limit - list.get(0).day; // 최종 마감날짜인 과제서부터 마감날짜에서 걸리는 시간의 차를 구해 여유시간을 구함
		for(int i = 1; i < list.size(); i++) { // 여유시간과 다음과제(이전 마감날짜과제) 마감날짜중 작은 값에서 걸리는 시간의 차를 구해 여유시간 갱신
			rest = Math.min(rest, list.get(i).limit) - list.get(i).day;
		}
		System.out.println(rest); // 출력
	}
}
