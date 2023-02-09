package day01;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.StringTokenizer;

public class 새로운게임2 {
    static int len; // maze의 각 변의 길이
    static int horseCount; // 말의 개수
    static int[][] map; // 지도
    static Horse[] info; // 말 자체에 대한 정보
    static ArrayList<Horse>[][] horseInfo; // 2차원 배열에 말의 정보를 담는 것(위에 올라가고 그런것들을 위해)

static int answer = 0;

@SuppressWarnings("unchecked")
public static void main(String[] args) throws IOException {
    BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
    StringTokenizer st = new StringTokenizer(in.readLine());

    len = Integer.parseInt(st.nextToken());

    horseCount = Integer.parseInt(st.nextToken()); // 조건의 개수

    map = new int[len + 1][len + 1];

    for (int i = 1; i <= len; i++) {
        // maze를 완성한다.
        st = new StringTokenizer(in.readLine());
        for (int j = 1; j <= len; j++) {
            map[i][j] = Integer.parseInt(st.nextToken());
        }
    }

    info = new Horse[horseCount+1];
    // people = new ArrayList[condition]; // 몇개가 들어가지?
    horseInfo = new ArrayList[len + 1][len + 1];

    for (int i = 1; i <= len; i++) {
        for (int j = 1; j <= len; j++) {
            horseInfo[i][j] = new ArrayList<>();
        }
    }
    for (int i = 1; i <= horseCount; i++) {
        st = new StringTokenizer(in.readLine());
        int ly = Integer.parseInt(st.nextToken());
        int lx = Integer.parseInt(st.nextToken());
        int ddir = Integer.parseInt(st.nextToken());

        horseInfo[ly][lx].add(new Horse(i, ly, lx, ddir));
        
        info[i] = new Horse(i,ly,lx,ddir);

    }
    // 여기는 값이 제대로 들어갔나 확인하는 부분
    // maze 부분
    for (int i = 1; i <= len; i++) {
        for (int j = 1; j <= len; j++) {
            System.out.print(map[i][j] + " ");
        }
        System.out.println();
    }
    // 여기는 people 에 제대로 들어갔는지를 확인하는 부분이다.

    for (int i = 1; i <= len; i++) {
        for (int j = 1; j <= len; j++) {
            if (horseInfo[i][j].size() != 0) {
                System.out.print(horseInfo[i][j]);
            }

        }
        System.out.println();
    }

    // 여기서부터 움직임을 구현하자

    while (true) {
        answer += 1;
        if (answer >= 1000) {
            // 1000번을 넘어가면 정답을 -1로 바꾸고 나와버린다
            answer = -1;
            break;
        }

        for (int i = 1; i <= horseCount; i++) {
            move(i); // i번째 말을 움직인다.
            // 말이 전부 같은 위치에 있다면? break;한다.

        }

    }

    System.out.println(answer);

}

private static void move(int a) {
	int temp =0;
    // 먼저 방향을 봐야한다.
    if (info[a].dir == 1) {
        // 동쪽으로 이동한다
        if ((info[a].x) + 1 > len || map[info[a].y][(info[a].x) + 1] == 3) {
            // 동쪽으로 이동할 시 파란색 칸이라면?
            // 방향을 바꾼다

        } else if (map[info[a].y][(info[a].x) + 1] == 1) {
            // 동쪽으로 이동할 시 빨간색 칸이라면?
            // 그 칸으로 가되 현재 가지고 있는 값들을 역순으로 하나씩 쑤셔 박는다

        } else if (map[info[a].y][(info[a].x) + 1] == 0) {
            // 동쪽으로 이동할 시 흰색 칸이라면?
            // 그냥 쑤셔 박는다
//        	if(horseInfo[info[a].y][(info[a].x)+1].size()==0) {
//        		//이동방향에 아무것도 들어가있지 않다면?
//        		//그냥 옮긴다
//        		horseInfo[info[a].y][(info[a].x)+1].add(new Horse(a, info[a].y, (info[a].x)+1, info[a].dir));
//        		horseInfo[info[a].y][info[a].x].clear();
//        	}
        	if(horseInfo[info[a].y][info[a].x].size()==1) {
        		//나 혼자만 들어 있는 상황이라면?
        		horseInfo[info[a].y][(info[a].x)+1].add(new Horse(a, info[a].y, (info[a].x)+1, info[a].dir));
        		info[a].x+=1;
        		horseInfo[info[a].y][info[a].x].clear();
        	}
        	else {
        		for(int i=0;i<horseInfo[info[a].y][info[a].x].size();i++) {
        			if(horseInfo[info[a].y][info[a].x].get(i).idx==a) {
        				//내가 움직이고자 하는 말을 찾는다면?
        				//그 이후의 말을 전부 옮긴다.
        				temp=i;
        				horseInfo[info[a].y][(info[a].x)+1].add(new Horse(a, info[a].y, (info[a].x)+1, info[a].dir));
                		info[a].x+=1;
                		horseInfo[info[a].y][info[a].x].remove(i);
                		break;
        				
        			}
        			
        		}
        		for(int i=temp+1;i<horseInfo[info[a].y][info[a].x].size();i++) {
        			int horsenumber = horseInfo[info[a].y][info[a].x].get(i).idx;
        			horseInfo[info[a].y][(info[a].x)+1].add(new Horse(horsenumber, info[horsenumber].y, (info[horsenumber].x)+1, info[horsenumber].dir));
            		info[horsenumber].x+=1;
            		horseInfo[info[a].y][info[a].x].remove(i);
        			
        		}
        	}
        	
        	
        	

        }

    } else if (info[a].dir == 2) {
        // 서쪽으로 이동한다
        if ((info[a].x) - 1 < 0 || map[info[a].y][(info[a].x) - 1] == 3) {
            // 서쪽으로 이동할 시 파란색 칸이라면?
        } else if (map[info[a].y][(info[a].x) - 1] == 1) {
            // 서쪽으로 이동할 시 빨간색 칸이라면?
        } else if (map[info[a].y][(info[a].x) - 1] == 0) {
            // 서쪽으로 이동할 시 흰색 칸이라면?
        }

    } else if (info[a].dir == 3) {
        // 북쪽으로 이동한다
        if ((info[a].y) - 1 < 0 || map[(info[a].y) - 1][(info[a].x)] == 3) {
            // 북쪽으로 이동할 시 파란색 칸이라면?
        } else if (map[(info[a].y) - 1][(info[a].x)] == 1) {
            // 북쪽으로 이동할 시 빨간색 칸이라면?
        } else if (map[(info[a].y) - 1][(info[a].x)] == 0) {
            // 북쪽으로 이동할 시 흰색 칸이라면?
        }

    } else {
        // 남쪽으로 이동한다.
        if ((info[a].y) + 1 > len || map[(info[a].y) + 1][(info[a].x)] == 3) {
            // 북쪽으로 이동할 시 파란색 칸이라면?
        } else if (map[(info[a].y) + 1][(info[a].x)] == 1) {
            // 북쪽으로 이동할 시 빨간색 칸이라면?
        } else if (map[(info[a].y) + 1][(info[a].x)] == 0) {
            // 북쪽으로 이동할 시 흰색 칸이라면?
        }

    }

}
}

class Horse {
    int idx; // 몇번 째 말인가?
    int y;
    int x;
    int dir;

public Horse(int idx, int y, int x, int dir) {
    this.idx = idx;
    this.y = y;
    this.x = x;
    this.dir = dir;
}
}