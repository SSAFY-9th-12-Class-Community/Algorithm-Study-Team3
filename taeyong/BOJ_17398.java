import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.List;

public class BOJ_17398 {
    static int N, M, Q;
    static int[][] graph;
    static int[] removes;
    static boolean[] checked;
    static int[] root;
    static long answer = 0;
    
    private static void makeSet() {
    	Arrays.fill(root, -1);
    }
    
    private static int find(int a) {
    	if(root[a] <= 0) return a;
    	return root[a] = find(root[a]);
    }
    
    private static void union(int a, int b) {
    	int x = find(a);
    	int y = find(b);
    	if(x == y) return;
    	else {
    		root[x] += root[y];
    		root[y] = x;
    	}
    }
    
    private static void removeCenter() {
    	for(int i = Q - 1; i >= 0; i--) {
    		int idx = removes[i];
    		if(find(graph[idx][0]) == find(graph[idx][1])) {
    			union(graph[idx][0], graph[idx][1]);
    		}else {
    			answer += (long)root[find(graph[idx][0])] * (long)root[find(graph[idx][1])];
    			union(graph[idx][0], graph[idx][1]);
    		}
    	}
    }
    
    public static void main(String[] args) throws Exception {
        inputValues();
        removeCenter();
        System.out.println(answer);
    }
    
    private static void inputValues() throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        
        int[] arr = Arrays.stream(br.readLine().split(" ")).mapToInt(Integer::parseInt).toArray();
        N = arr[0];
        M = arr[1];
        Q = arr[2];
        graph = new int[M][2];
        removes = new int[Q];
        checked = new boolean[M];
        root = new int[N + 1];
        
        makeSet();
        
        for(int i = 0; i < M; i++) {
            arr = Arrays.stream(br.readLine().split(" ")).mapToInt(Integer::parseInt).toArray();
            graph[i][0] = arr[0];
            graph[i][1] = arr[1];
        }
        
        for(int i = 0; i < Q; i++) {
            removes[i] = Integer.parseInt(br.readLine()) - 1;
            checked[removes[i]] = true;
        }
        
        for(int i = 0; i < M; i++) {
        	if(checked[i]) continue;
        	union(graph[i][0], graph[i][1]);
        }
    }
}
