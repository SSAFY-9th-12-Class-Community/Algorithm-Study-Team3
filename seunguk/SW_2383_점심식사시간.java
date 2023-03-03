package day03;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.StringTokenizer;
 
public class SW_2383_점심식사시간 {
    static ArrayList<Home> home;
    static ArrayList<Exit> exit;
    static int[] array;
    static int len;
    static int homeIdx;
    static int exitIdx;
    static int answer;
 
    public static void main(String[] args) throws IOException {
        BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(in.readLine());
 
        int tc = Integer.parseInt(st.nextToken());
 
        for (int test_case = 1; test_case <= tc; test_case++) {
            home = new ArrayList<>(); // home에 대한 정보를 넣을 것이다.
            exit = new ArrayList<>(); // 탈출구에 대한 정보를 넣을 것이다.
            st = new StringTokenizer(in.readLine());
            len = Integer.parseInt(st.nextToken());
            homeIdx = 0;
            exitIdx = 0;
            answer = 999999;
 
            for (int i = 0; i < len; i++) {
                st = new StringTokenizer(in.readLine());
                for (int j = 0; j < len; j++) {
                    int a = Integer.parseInt(st.nextToken());
                    if (a == 1) {
                        // 집인 경우
                        home.add(new Home(homeIdx++, i, j));
                    } else if (a >= 2 && a <= 10) {
                        // 탈출구인 경우
                        exit.add(new Exit(a, i, j));
                    }
                }
            }
            array = new int[homeIdx];
            comb(0);
 
            System.out.println("#" + test_case + " " + answer);
 
        }
    }
 
    private static void comb(int cnt) {
        if (cnt == homeIdx) {
            // System.out.println();
            getMin(array);
            // System.out.println("현재의 정답은:" + answer);
            return;
        }
 
        for (int i = 0; i < 2; i++) {
            array[cnt] = i;
            comb(cnt + 1);
        }
    }
 
    private static void getMin(int[] arr) {
        int answer0 = 0; // 0번 출구 전부 탈출하는데 걸리는 시간
        int answer1 = 0; // 1번 출구 전부 탈출하는데 걸리는 시간
        ArrayList<Integer> idx0 = new ArrayList<>(); // 0번으로 가는 집
        ArrayList<Integer> idx1 = new ArrayList<>(); // 1번으로 가는 집
 
        for (int i = 0; i < homeIdx; i++) {
            int distance = Math.abs(home.get(i).y - exit.get(arr[i]).y) + Math.abs(home.get(i).x - exit.get(arr[i]).x);
 
            if (arr[i] == 0) {
                // 0번 출구로 나가는 경우라면?
                idx0.add(distance + 1);
            } else {
                idx1.add(distance + 1);
            }
        }
 
        // 0번 출구쪽 값 구하는 과정
        Collections.sort(idx0);
        Collections.sort(idx1);
        int temp = exit.get(0).value;
        if (idx0.size() > 3) {
            int start = 0;
            int end = 2;
            while (true) {
                if (end == idx0.size() - 1) {
                    answer0 = idx0.get(end) + temp;
                    break;
                }
                idx0.set(end + 1, Math.max(idx0.get(start) + temp, idx0.get(end + 1)));
                start += 1;
                end += 1;
            }
 
        } else {
            if (idx0.size() == 0) {
                answer0 = 0;
            } else {
                answer0 = idx0.get(idx0.size() - 1) + temp;
            }
 
        }
 
        // 1번 출구쪽 값 구하는 과정
        int temp1 = exit.get(1).value;
        if (idx1.size() > 3) {
            int start = 0;
            int end = 2;
            while (true) {
                if (end == idx1.size() - 1) {
                    answer1 = idx1.get(end) + temp1;
                    break;
                }
                idx1.set(end + 1, Math.max(idx1.get(start) + temp, idx1.get(end + 1)));
                start += 1;
                end += 1;
            }
 
        } else {
            if (idx1.size() == 0) {
                answer1 = 0;
            } else {
                answer1 = idx1.get(idx1.size() - 1) + temp1;
            }
        }
        int realtemp = 0;
        realtemp = Math.max(answer0, answer1);
        if (answer >= realtemp) {
            answer = realtemp;
        }
 
    }
 
    static public class Home {
        int idx; // 몇번째 집인가?
        int y; // y 좌표
        int x; // x 좌표
 
        public Home(int idx, int y, int x) {
            super();
            this.idx = idx;
            this.y = y;
            this.x = x;
        }
 
    }
 
    static public class Exit {
        int value; // 내려가는데 걸리는 시간
        int y;
        int x;
 
        public Exit(int idx, int y, int x) {
            super();
            this.value = idx;
            this.y = y;
            this.x = x;
        }
 
    }
 
}