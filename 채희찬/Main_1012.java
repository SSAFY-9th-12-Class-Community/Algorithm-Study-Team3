package BeakJoon;

import java.util.Scanner;

// 실버 2
// 유기농 배추
public class Main_1012 {

    static int Y;
    static int X;
    static int[][] datos;
    static int[][] visite;

    static int[] dx = {0, 0, 1, -1};
    static int[] dy = {1, -1, 0, 0};

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        int TC = sc.nextInt();
        for (int t = 0; t < TC; t++) {
            int cuenta = 0;
            Y = sc.nextInt();
            X = sc.nextInt();
            int cuentaDeCol = sc.nextInt();

            datos = new int[X][Y];
            visite = new int[X][Y];

            // datos로 데이터 입력
            for (int i = 0; i < cuentaDeCol; i++) {
                int y = sc.nextInt();
                int x = sc.nextInt();

                datos[x][y] = 1;
            }

            // 이동하다가 datos가 1이고 visite가 0일 경우 dfs;
            for (int i = 0; i < X; i++) {
                for (int j = 0; j < Y; j++){
                    if (datos[i][j] == 1 && visite[i][j] == 0) {
                        cuenta++;
                        dfs(i, j);
                    }
                }
            }

            System.out.println(cuenta);
        }
    }

    // 방문 찍고 4방탐색 후 조건 맞으면 dfs호출
    public static void dfs(int x, int y){
        visite[x][y] = 1;
        for(int i = 0; i < 4; i++) {
            if (x+dx[i] >= 0 && x+dx[i] < X && y+dy[i] >= 0 && y+dy[i]< Y) {
                if (datos[x + dx[i]][y + dy[i]] == 1 && visite[x+dx[i]][y+dy[i]] == 0) {
                    dfs(x+dx[i], y+dy[i]);
                }
            }
        }
    }


}
