import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.*;


public class BOJ_1477 {
    static int N, M, L;
    static int maxDistance = Integer.MIN_VALUE;
    static int answer = 0;
    static List<Integer> restDistance;
    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        List<Integer> inputs = new ArrayList<>();
        restDistance = new ArrayList<>();
        StringTokenizer st = new StringTokenizer(br.readLine());
        N = Integer.parseInt(st.nextToken());
        M = Integer.parseInt(st.nextToken());
        L = Integer.parseInt(st.nextToken());

        st = new StringTokenizer(br.readLine());
        while (st.hasMoreTokens()) {
            inputs.add(Integer.parseInt(st.nextToken()));
        }
        inputs.add(L);
        Collections.sort(inputs); // 오름차순으로 정렬
        int prevDistance = 0;
        for (int i = 0; i < N + 1; i++) {
            if (i == N) {
                restDistance.add(L - prevDistance);
                maxDistance = Math.max(maxDistance, L - prevDistance);
                break;
            }
            maxDistance = Math.max(maxDistance, inputs.get(i) - prevDistance);
            restDistance.add(inputs.get(i) - prevDistance);
            prevDistance = inputs.get(i);
        }

        System.out.println(binarySearch(1, maxDistance));
    }

    public static int binarySearch(int left, int right) {
        int middle = (left + right) >> 1;
        if (left <= right) {
            if (isPossible(middle)) {
                answer = middle;
                return binarySearch(left, middle - 1);
            } else {
                return binarySearch(middle + 1, right);
            }
        }
        return answer;
    }

    public static boolean isPossible(int middle) {
        int cnt = 0;
        for (int rest : restDistance) {
            if(rest <= middle) continue;
            cnt += Math.ceil(rest / (double)middle) - 1;
        }
        return cnt <= M;
    }
}


/*
*
* 우선순위 큐로 생각한 코드: 실패
* */
//public class BOJ_1477 {
//    static int N, M, L;
//    static int minValue = Integer.MAX_VALUE;
//    public static void main(String[] args) throws Exception {
//        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
//        List<Integer> inputs = new ArrayList<>();
//        PriorityQueue<Location> pq = new PriorityQueue<>();
//        StringTokenizer st = new StringTokenizer(br.readLine());
//        N = Integer.parseInt(st.nextToken());
//        M = Integer.parseInt(st.nextToken());
//        L = Integer.parseInt(st.nextToken());
//
//        st = new StringTokenizer(br.readLine());
//        while (st.hasMoreTokens()) {
//            inputs.add(Integer.parseInt(st.nextToken()));
//        }
//
//        Collections.sort(inputs);
//
//        int prevLocation = 0;
//        int i = 0;
//        for (; i < N + 1; i++) {
//            if(i == N) {
//                minValue = Math.min(minValue, L - prevLocation);
//                pq.add(new Location(prevLocation, L, L - prevLocation));
//                break;
//            }
//            int restLocation = inputs.get(i);
//            pq.add(new Location(prevLocation, restLocation, restLocation - prevLocation));
//            minValue = Math.min(minValue, restLocation - prevLocation);
//            prevLocation = restLocation;
//        }
//
//        System.out.println(minValue);
//
//        for (i = 0; i < M; i++) {
//            Location location = pq.poll();
//            int middle = (location.difference) / 2;
//
//            pq.add(new Location(location.start, location.start + middle, middle));
//            pq.add(new Location(location.start + middle, location.end, location.end - location.start - middle));
//        }
//
//        System.out.println(pq.poll().difference);
//    }
//}
//
//class Location implements Comparable<Location> {
//    int start;
//    int end;
//    int difference;
//
//    public Location(int start, int end, int difference) {
//        this.start = start;
//        this.end = end;
//        this.difference = difference;
//    }
//
//    @Override
//    public int compareTo(Location o) {
//        if (this.difference < o.difference) {
//            return 1;
//        } else if (this.difference > o.difference) {
//            return -1;
//        }
//        return 0;
//    }
//}
