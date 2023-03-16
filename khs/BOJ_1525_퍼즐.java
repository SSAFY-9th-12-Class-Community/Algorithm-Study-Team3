import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.*;

public class BOJ_1525_퍼즐 {

    private static Map<Integer, Integer> hmap = new HashMap<>();
    private static int[] dirs = {1, -1, 3, -3};  // 좌측, 우측, 아래, 위 

    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringBuilder sb = new StringBuilder();
        for(int i=0; i<3; ++i) {
            sb.append(br.readLine().replace(" ", "")); 
        }
      
        // 총 9자리칸이므로 나올 수 있는 최대 경우의 수는 9!개이며, 각 숫자를 1차원 int범위 내에 존재하는 수이므로 변환
        int target = Integer.parseInt(sb.toString());
        System.out.println(getTime(target));
    }

    private static boolean isValid(int position) {
        return (position >= 0 && position<9);
    }

    private static int getTime(int target) {
        Queue<int[]> q = new LinkedList<>();
        q.add(new int[] {123456780, 0, 0}); // {현재 퍼즐의 상태, 퍼즐에서 0의 위치가 오른쪽에서 부터 몇번째에 위치하는 지를 나타내는 정보, 해당 상태를 만들기 위해 걸리는 최소 시간}
        hmap.put(123456780, 0); // 나올 수 있는 퍼즐의 상태, 해당 퍼즐을 만들기 위해 걸리는 최소 시간 

        while(!q.isEmpty()) {
            int[] info = q.poll();
            if(info[0] == target) return info[2];
            
            for(int dir: dirs) {
                int nextPosition = info[1]+dir;
                if(!isValid(nextPosition)) continue;
                if(Math.abs(dir) == 1 && (nextPosition/3) != (info[1]/3)) continue; // 본래 2차원 배열을 1차원 배열로 변환하였으므로, 좌우로 이동할 떄는 같은 열인지 확인해야한다.

                int swapValue = getSwapNumber(info[0], info[1], nextPosition);
                if(hmap.getOrDefault(swapValue, -1) == -1) {  // 이전에 나왔던 숫자 패턴에 대해서는 더 이상 bfs를 돌 필요가 없다. 
                    hmap.put(swapValue, info[2]+1); 
                    q.add(new int[] {swapValue, nextPosition, info[2]+1});
                }
            }
        }
        return -1; 
    }
    
    // origin -> 퍼즐의 상태,   zeroPosition-> 퍼즐에서 0의 위치가 어디있는지 나타내는 정보, target-> 현재 0과 위치가 바뀔 위치 
    private static int getSwapNumber(int origin, int zeroPosition, int target) {
        int ret = origin;
        int zeroPow = (int) Math.pow(10, zeroPosition);
        int pow = (int) Math.pow(10, target);
        int quo = (origin % (pow*10)) / pow; 
        ret = ret+(zeroPow-pow)*quo;
        return ret;
    }
}
