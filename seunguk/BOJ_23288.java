package study;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.LinkedList;
import java.util.Queue;
import java.util.StringTokenizer;

public class BOJ_23288 {
    static int sero;
    static int garo;
    static int movement;
    static int[][] maze;
    static boolean[][] visited;
    static int dir[] = { 0, 1, 2, 3 }; // 동,남,서,북
    static int nowD = 0;
    static int direction = dir[nowD];
    static int dice[] = { 6, 1, 2, 3, 4, 5, 6 };
    static int dy[] = { 1, 0, -1, 0 };
    static int dx[] = { 0, 1, 0, -1 };
    static int nowy = 1;
    static int nowx = 1;
    static int answer = 0;
    static Queue<Node> que = new LinkedList<>();
public static void main(String[] args) throws IOException {
    BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
    StringTokenizer st = new StringTokenizer(in.readLine());

    sero = Integer.parseInt(st.nextToken()); // 세로
    garo = Integer.parseInt(st.nextToken()); // 가로
    movement = Integer.parseInt(st.nextToken()); // 움직이는 횟수

    maze = new int[sero + 1][garo + 1];
    
    for (int i = 1; i <= sero; i++) {
        st = new StringTokenizer(in.readLine());
        for (int j = 1; j <= garo; j++) {
            maze[i][j] = Integer.parseInt(st.nextToken());
        }
    }
    // System.out.println("현재 방향: "+direction);
    for (int i = 0; i < movement; i++) {

        // movement 횟수만큼 반복된다.

        move();
        // System.out.println(nowy+" "+nowx);
        // System.out.print("현재 밑의 값, 현재 maze상의 값 ");
        // System.out.print(dice[0]+" ");
        // System.out.println(maze[nowy][nowx]);
        // 밑에 있는 값은? = dice[0]; 이제 방향을 어디로 전환할 것인지를 알아보는 단계
        if (dice[0] > maze[nowy][nowx]) {
            // System.out.println("90도 회전");
            // 이동방향을 90도 회전
            nowD = (nowD + 1) % 4;
            direction = dir[nowD];
        } else if (dice[0] < maze[nowy][nowx]) {
            // 이동 방향을 90도 반시계 회전
            // System.out.println("반대로 회전");
            nowD = (nowD + 3) % 4;
            direction = dir[nowD];
        } 
            // System.out.println("변동 없음");
        
        // System.out.println("현재 방향" + direction);

        //System.out.println(bfs(nowy,nowx));
        answer += (bfs(nowy, nowx) * maze[nowy][nowx]);

    }
    // System.out.println("정답을 출력합니다:" +answer);
    System.out.println(answer);

}

static void move() {
    // 주사위를 굴리는 경우 각 자리가 어떻게 되는지
    int d0 = dice[0];// 6
    int d1 = dice[1];// 1
    int d2 = dice[2];// 2
    int d3 = dice[3];// 3
    int d4 = dice[4];// 4
    int d5 = dice[5];// 5
    int d6 = dice[6];// 6
    if (direction == dir[0] && nowx == garo) {
        nowD = 2;
        direction = dir[2];
    } else if (direction == dir[2] && nowx == 1) {
        // System.out.println("진행방향이 바뀝니다");
        nowD = 0;
        direction = dir[0];
    } else if (direction == dir[1] && nowy == sero) {
        nowD = 3;
        direction = dir[3];
    } else if (direction == dir[3] && nowy == 1) {
        nowD = 1;
        direction = dir[1];
    }

    if (direction == dir[0]) {
        // 동쪽으로 이동한다면?
        nowx += 1;
        dice[0] = d3;
        dice[1] = d4;
        dice[3] = d1;
        dice[4] = d6;
        dice[6] = d3;

    } else if (direction == dir[1]) {
        // 남쪽으로 이동한다면
        nowy += 1;
        dice[0] = d5;
        dice[1] = d2;
        dice[2] = d6;
        dice[5] = d1;
        dice[6] = d5;

    } else if (direction == dir[2]) {
        // 서쪽으로 이동한다면
        nowx -= 1;
        dice[0] = d4;
        dice[1] = d3;
        dice[3] = d6;
        dice[4] = d1;
        dice[6] = d4;

    } else if (direction == dir[3]) {
        // 북쪽으로 이동한다면
        nowy -= 1;
        dice[0] = d2;
        dice[1] = d5;
        dice[2] = d1;
        dice[5] = d6;
        dice[6] = d2;

    }

}

static int bfs(int nowy, int nowx) {
	visited = new boolean[sero + 1][garo + 1]; // 전부 false이다.

    int count = 1;
    que.add(new Node(nowy, nowx));
    visited[nowy][nowx]=true;
    while (!que.isEmpty()) {
        Node temp = que.poll();
        
        for (int i = 0; i < 4; i++) {
            int nextY = temp.y + dy[i];
            int nextX = temp.x + dx[i];
            if (nextY <1 || nextX < 1 || nextY > sero || nextX > garo) {
            	continue;
            }
            if(maze[nextY][nextX] == maze[temp.y][temp.x] && visited[nextY][nextX] == false) {
                que.add(new Node(nextY, nextX));
                visited[nextY][nextX] = true;
                count+=1;
            }
        }

    }

    return count;

}
}

class Node {
    int y;
    int x;

public Node(int y, int x) {
    this.y = y;
    this.x = x;
}
}
