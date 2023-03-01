import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.LinkedList;
import java.io.*;
import java.util.Queue;


class Vertex {
    int x;
    int y;
    Vertex(int x, int y){
        this.x = x;
        this.y = y;
    }
}

public class BOJ_18809 {
    static int n,m,g,r;
    static int[][] map;
    static boolean[][] redVisited;
    static boolean[][] greenVisited;
    static int ans = 0;
    static boolean[] dfsVisited;
    static int[][] redTime;
    static int[][] greenTime;
    static int[] dx = {-1 , 1, 0, 0};
    static int[] dy = {0 ,0, -1, 1};

    static int[][] copyMap;
    static ArrayList<Vertex> dfsList = new ArrayList<>();
    static ArrayList<Vertex> green = new ArrayList<>();
    static ArrayList<Vertex> red = new ArrayList<>();
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        String[] t = br.readLine().split(" ");
        n = Integer.parseInt(t[0]);
        m = Integer.parseInt(t[1]);
        g = Integer.parseInt(t[2]);
        r = Integer.parseInt(t[3]);

        map = new int[n+1][m+1];

        for(int i=1; i<=n; i++) {
            String[] tt = br.readLine().split(" ");
            for(int j=1; j<=m; j++) {
                map[i][j]= Integer.parseInt(tt[j-1]);
                if(map[i][j]==2) {
                    dfsList.add(new Vertex(i,j));
                }
            }
        }
        dfsVisited = new boolean[dfsList.size()];
        greenDfs(0,0);
        System.out.println(ans);
    }
    public static void copyMap() {
        copyMap = new int[n+1][m+1];
        for(int i=1; i<=n; i++) {
            if (m >= 0) System.arraycopy(map[i], 1, copyMap[i], 1, m);
        }
    }
    public static void bfs() {
        int flower =0;
        greenVisited = new boolean[n+1][m+1];
        greenTime = new int[n+1][m+1];
        redVisited = new boolean[n+1][m+1];
        redTime = new int[n+1][m+1];

        copyMap();
        Queue<Vertex> rq = new LinkedList<>();
        Queue<Vertex> gq = new LinkedList<>();

        for (Vertex node : red) {
            rq.add(node);
            redVisited[node.x][node.y] = true;
        }
        for (Vertex node : green) {
            greenVisited[node.x][node.y] = true;
            gq.add(node);
        }


        while(!rq.isEmpty() && !gq.isEmpty()) {
            int rep = gq.size();
            while(rep-- > 0) {
                Vertex vertex = gq.poll();
                greenVisited[vertex.x][vertex.y] = true;
                if(copyMap[vertex.x][vertex.y]==3) {
                    continue;
                }

                for(int i=0; i<4; i++) {
                    int nx = vertex.x+dx[i];
                    int ny = vertex.y+dy[i];
                    if(isPossible(nx,ny) && (copyMap[nx][ny]==1 || copyMap[nx][ny]==2) && !greenVisited[nx][ny]) {
                        greenVisited[nx][ny]=true;
                        greenTime[nx][ny] = greenTime[vertex.x][vertex.y]+1;
                        gq.add(new Vertex(nx,ny));
                    }
                }
            }

            rep = rq.size();
            while(rep-- > 0) {
                Vertex vertex = rq.poll();
                redVisited[vertex.x][vertex.y] = true;

                if(copyMap[vertex.x][vertex.y]==3) {
                    continue;
                }
                for(int i=0; i<4; i++) {
                    int nx = vertex.x+dx[i];
                    int ny = vertex.y+dy[i];
                    if(isPossible(nx,ny) && (copyMap[nx][ny]==1 || copyMap[nx][ny]==2) && !redVisited[nx][ny]) {
                        redVisited[nx][ny] = true;
                        redTime[nx][ny] = redTime[vertex.x][vertex.y]+1;
                        if(redTime[nx][ny]==greenTime[nx][ny]) {
                            flower++;
                            copyMap[nx][ny]=3;
                        }
                        else {
                            rq.add(new Vertex(nx,ny));
                        }
                    }
                }
            }
        }
        ans = Math.max(ans, flower);
    }
    public static void redDfs(int start, int cnt) {
        if(cnt == r) {
            red.clear();
            for(int i=0; i<dfsList.size(); i++) {
                Vertex a = dfsList.get(i);
                if(dfsVisited[i] && !green.contains(a)) {
                    red.add(a);
                }
            }
            bfs();
            return ;
        }
        for(int i=start; i<dfsList.size(); i++) {
            if(dfsVisited[i]) continue;
            dfsVisited[i] = true;
            redDfs(i + 1, cnt + 1);
            dfsVisited[i]=false;
        }
    }
    public static void greenDfs(int start, int cnt) {
        if(cnt == g) {
            green.clear();
            for(int i=0; i<dfsList.size(); i++) {
                if(dfsVisited[i]) {
                    Vertex a= dfsList.get(i);
                    green.add(a);
                }
            }
            redDfs(0,0);
            return ;
        }
        for(int i=start; i<dfsList.size(); i++) {
            if(dfsVisited[i]) continue;
            dfsVisited[i] = true;
            greenDfs(i + 1, cnt + 1);
            dfsVisited[i] = false;
        }
    }

    public static boolean isPossible(int x, int y) {
        return x >= 1 && y >= 1 && x <= n && y <= m;
    }
}