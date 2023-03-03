import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Stack;
import java.util.StringTokenizer;
 
class Node {
    int num;
    int[][] map;
 
    public Node(int num, int[][] map) {
        this.num = num;
        this.map = map;
    }
}
 
class Index {
    int x;
    int y;
 
    public Index(int x, int y) {
        this.x = x;
        this.y = y;
    }
}
 
public class SW_5656 {
    static int n, w, h, temp, max;
    static int[] dx = {0, 0, -1, 1};
    static int[] dy = {-1, 1, 0, 0};
 
    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st;
 
        int T = Integer.parseInt(br.readLine());
 
        for (int t = 1; t <= T; t++) {
            max = 0;
            temp = 0;
            st = new StringTokenizer(br.readLine());
            n = Integer.parseInt(st.nextToken());
            w = Integer.parseInt(st.nextToken());
            h = Integer.parseInt(st.nextToken());
 
            int[][] graph = new int[h][w];
 
            for (int i = 0; i < h; i++) {
                st = new StringTokenizer(br.readLine());
                for (int j = 0; j < w; j++) {
                    graph[i][j] = Integer.parseInt(st.nextToken());
                    if (graph[i][j] == 0) max++; // 0인 부분 개수 세기
                }
            }
 
            solution(graph);
            System.out.println("#" + t + " " + ((w * h) - max));
        }
    }
 
    private static void solution(int[][] graph) {
        Queue<Node> queue = new LinkedList<>();
        queue.add(new Node(max, graph));
 
        while (n-- > 0) {
            int len = queue.size();
            for (int t = 0; t < len; t++) {
                Node node = queue.poll();
 
                for (int j = 0; j < w; j++) {
                    Index index = find(node.map, j); // 벽돌이 닿는 위치
                    if (index != null) queue.add(remove(node, index));
                }
            }
        }
    }
 
    private static Node remove(Node node, Index index) {
        int[][] map = copyOf(node.map);
        temp = node.num;
 
        dfs(map, index.x, index.y);
        moveToDown(map);
 
        if (max < temp) max = temp;
        return new Node(temp, map);
    }
 
    private static void dfs(int[][] map, int x, int y) { // 벽돌 깨트리기
        int target = map[x][y];
 
        if (target == 1) {
            map[x][y] = 0;
            temp++;
        } else if (target != 0) {
            map[x][y] = 0;
            temp++;
            for (int i = 0; i < 4; i++) {
                int nx = x, ny = y;
                for (int c = 0; c < target - 1; c++) {
                    nx = nx + dx[i];
                    ny = ny + dy[i];
                    if (isPossible(nx, ny))
                        if (map[nx][ny] != 0) dfs(map, nx, ny);
                }
            }
        }
    }
 
    private static void moveToDown(int[][] map) {
        Stack<Integer> stack = new Stack<>();
 
        for (int j = 0; j < w; j++) {
            for (int i = 0; i < h; i++) {
                if (map[i][j] != 0) {
                    stack.push(map[i][j]);
                    map[i][j] = 0;
                }
            }
            int index = h - 1;
            while (!stack.isEmpty()) {
                map[index--][j] = stack.pop();
            }
 
        }
    }
 
    private static int[][] copyOf(int[][] map) {
        int[][] result = new int[h][w];
        for (int i = 0; i < h; i++) {
            System.arraycopy(map[i], 0, result[i], 0, w);
        }
        return result;
    }
 
    private static Index find(int[][] map, int j) {
        for (int i = 0; i < h; i++) {
            if (map[i][j] != 0) return new Index(i, j);
        }
        return null;
    }
 
    private static boolean isPossible(int x, int y) {
        if (x < 0 || y < 0 || x >= h || y >= w) return false;
        return true;
    }
     
}