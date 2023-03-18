import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

/*
  https://www.acmicpc.net/problem/17398
  통신망 분할
  union-find 
  ---
  1. 노드들을 모두 연결하는 edge 정보를 저장한다.
  2. 삭제할 edge정보를 입력받는다.
  3. 1에서 저장한 edge에 속하는 노드들 끼리 union 한다. 이때, 2에서 삭제된 edge들은 제외한다.
     (3번 연산이 끝난 후 parent 노드는 삭제될 edge가 모두 삭제된 그래프의 상태와 동일한 상태를 지닌다.)
     
  4. 2에서 저장한 edge 정보를 역순으로 union 한다. 
     (실제로는 연결을 끊는 연산을 역연산하며 답을 추적한다. 역연산을 할때, 만일 두 노드의 부모가 같다면, 해당 edge는 3번에서 끊어지는 시점에, 각 union 되려는 노드 사이에 
     다른 사이클이 존재했다는 의미이다. 
*/

public class BOJ_17398_통신망분할 {
    private static int N, M, Q;
    private static int[] parents;
    private static boolean[] isDeleted;
    private static List<int[]> edges = new ArrayList<>();
    private static List<Integer> deletedList = new ArrayList<>();

    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());

        N = Integer.parseInt(st.nextToken());
        M = Integer.parseInt(st.nextToken());
        Q = Integer.parseInt(st.nextToken());

        parents = new int[N+1];
        for(int i=1; i<=N; ++i) parents[i] = -1;

        for(int i=0; i<M; ++i) {
            st = new StringTokenizer(br.readLine());
            int n1 = Integer.parseInt(st.nextToken());
            int n2 = Integer.parseInt(st.nextToken());
            edges.add(new int[] {n1, n2});
        }

        isDeleted = new boolean[M];
        for(int i=0; i<Q; ++i) {
            int idx = Integer.parseInt(br.readLine()) - 1;
            isDeleted[idx] = true;
            deletedList.add(idx);
        }

        for(int i=0; i<M; ++i) {
            if(isDeleted[i]) continue;
            union(edges.get(i)[0], edges.get(i)[1]);
        }

        long answer = 0;
        for(int i=Q-1; i>=0; --i) {
            int[] nodes = edges.get(deletedList.get(i));
            answer += union(nodes[0], nodes[1]);
        }
        System.out.println(answer);
    }

    private static int findParents(int node) {
        if(parents[node] < 0) return node;
        return parents[node] = findParents(parents[node]);
    }

    private static int union(int node1, int node2) {
        int p1 = findParents(node1);
        int p2 = findParents(node2);
        if(p1 == p2) return 0;

        int ret = parents[p1] * parents[p2];
        parents[Math.min(p1, p2)] += parents[Math.max(p1, p2)];
        parents[Math.max(p1, p2)] = Math.min(p1, p2);
        return ret;
    }
}
