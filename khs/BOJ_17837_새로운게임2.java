import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

public class BOJ_17837_새로운게임2 {

    private static int n,k;
    private static final Tile[][] board = new Tile[12][12];
    private static final Node[] nodes = new Node[10];
    private static final int[][] dirs = {{0, 1}, {0, -1}, {-1, 0}, {1, 0}};

    public static void main(String[] args) throws IOException {
        init();

        for(int turn=1; turn<1001; turn++) {
            for(int i=0; i<k; ++i) {
                int ny = nodes[i].y + dirs[nodes[i].dir][0];
                int nx = nodes[i].x + dirs[nodes[i].dir][1];

                if(!isValid(ny, nx) || board[ny][nx].type == 2) {
                    nodes[i].convertDir();
                }

                if(!canMove(nodes[i])) continue;
                // 현재 Tile에 있는 링크드리스트 정보와, 움직이고자하는 타일의 링크드리스트를 조작함
                ny = nodes[i].y + dirs[nodes[i].dir][0];
                nx = nodes[i].x + dirs[nodes[i].dir][1];

                Tile currnentTile = board[nodes[i].y][nodes[i].x];
                Tile nextTile = board[ny][nx];

                int pos = currnentTile.pieces.indexOf(nodes[i]);
                List<Node> subList = new LinkedList<>(); // 현재 노드의 위에 있는 노드들을 가져온다.
                for(int j=0; j<=pos; ++j) {
                    Node node = currnentTile.pieces.get(j);
                    node.y = ny; node.x = nx;
                    subList.add(node);
                }
                currnentTile.pieces.removeAll(subList);

                if(nextTile.type == 1) Collections.reverse(subList);
                nextTile.pieces.addAll(0, subList);

                if(nextTile.pieces.size() >= 4) {
                    System.out.println(turn);
                    return;
                }
            }
        }

        System.out.println(-1);
    }

    private static boolean isValid(int y, int x) {
        return (y>=0 && y<n) && (x>=0 && x<n);
    }

    private static boolean canMove(Node node) {
        int ny = node.y+dirs[node.dir][0];
        int nx = node.x+dirs[node.dir][1];
        return isValid(ny, nx) && (board[ny][nx].type != 2);
    }

    private static void init() throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int[] tmp = Arrays.stream(br.readLine().split(" ")).mapToInt(Integer::parseInt).toArray();
        n = tmp[0]; k = tmp[1];
        for(int i=0; i<n; ++i) {
            int[] row = Arrays.stream(br.readLine().split(" ")).mapToInt(Integer::parseInt).toArray();
            for(int j=0; j<row.length; ++j) {
                board[i][j] = new Tile(row[j]);
            }
        }

        for(int i=0; i<k; ++i) {
            int[] row = Arrays.stream(br.readLine().split(" ")).mapToInt(Integer::parseInt).toArray();
            nodes[i] = new Node(row[0], row[1], row[2]);
            board[row[0]-1][row[1]-1].pieces.add(nodes[i]);
        }
        br.close();
    }

    static class Tile {
        int type;
        List<Node> pieces;

        public Tile() {}
        public Tile(int t) {
            this.type = t;
            this.pieces = new LinkedList<>();
        }
    }

    static class Node {
        int y, x;
        int dir;

        public Node() {}
        public Node(int y, int x, int dir) {
            this.y = y-1;
            this.x = x-1;
            this.dir = dir-1;
        }

        public void convertDir() {
            int add = (this.dir % 2 == 0) ? 1 : -1;
            this.dir += add;
        }
    }
}
