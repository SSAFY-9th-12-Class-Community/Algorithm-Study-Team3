package BeakJoon;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;

public class Main_23288 {
    static int[][] arr;
    static int[][] points;
    static int[][] visited;

    static int n, m;

    static int[] dr = {0, 0, 1, -1};
    static int[] dc = {1, -1, 0, 0};

    static int nextR;
    static int nextC;
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        int[] nmk = Arrays.stream(br.readLine().split(" ")).mapToInt(Integer::parseInt).toArray();
        n = nmk[0];
        m = nmk[1];
        int k = nmk[2];

        arr = new int[n][m];
        points = new int[n][m];
        visited = new int[n][m];

        for (int i = 0; i < n; i++){
            arr[i] = Arrays.stream(br.readLine().split(" ")).mapToInt(Integer::parseInt).toArray();
        }

//      점수판 생성
        for(int i = 0; i < n; i++){
            for (int j = 0; j < m; j++){

                points[i][j] = arr[i][j] * searchPoint(0, i, j);    // point * count;
            }
        }

//      주사위 굴리기



    }

    public static int searchPoint(int count, int r, int c){
        count++;
        int point = arr[r][c];

        visited[r][c] = 1;

        for (int i = 0; i < 4; i++){
            nextR = r+dr[i];
            nextC = c+dc[i];
            if (nextR >= 0 && nextR < n && nextC >= 0 && nextC < m ) {
                if(visited[nextR][nextC] == 0 && point == arr[nextR][nextC]){
                    count = searchPoint(count, nextR, nextC);
                }
            }
        }

        return count;

    }
}

class dice {
    int top = 1;
    int bottom = 6;
    int front = 2;
    int back= 5;
    int left = 4;
    int right = 3;

    int direction = 0;      // 0:east, 1:south, 2:west, 3:north


    public void east(){

    }
}
