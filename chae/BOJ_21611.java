import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;

// 골드1
// 마법사 상어와 블리자드
public class BOJ_21611 {
    static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    static int[][] map;
    static boolean[][] visited;
    static Location[] indexMap;
    static int n, m;
    static int unoPerla, dosPerla, tresPerla;

    static int[] dr = {0, 1, 0, -1};    // 우 하 좌 상
    static int[] dc = {1, 0, -1, 0};
    static int dir = 0;

    public static void main(String[] args) throws IOException {
        int[] nm = Arrays.stream(br.readLine().split(" ")).mapToInt(Integer::parseInt).toArray();
        n = nm[0];
        m = nm[1];
        map = new int[n][n];
        visited = new boolean[n][n];
        indexMap = new Location[n * n];

        for (int i = 0; i < n; i++) {
            map[i] = Arrays.stream(br.readLine().split(" ")).mapToInt(Integer::parseInt).toArray();
        }

        // 반복문 돌면서 indexMap[0]부터 인덱스로 채우기
        saveIndex();

        // 입력받고 삭제
        for (int i = 0; i < m; i++) {
            int[] ds = Arrays.stream(br.readLine().split(" ")).mapToInt(Integer::parseInt).toArray();

            // 블리자드
            blizzard(ds[0], ds[1]);

            // 앞으로 당기기
            ordenar();

            // 연속되는 숫자 삭제, 앞으로 당기기
            while (removeConsecutiveNumber()) {
                ordenar();
            }

            // 구슬 변화
            cambioPerla();

        }

        System.out.println(unoPerla + dosPerla * 2 + tresPerla * 3);

    }

    // 인덱스 배열 생성
    private static void saveIndex() {
        int x = 0, y = 0;
        for (int i = indexMap.length - 2; i >= 0; i--) {
            indexMap[i] = new Location(x, y);
            visited[x][y] = true;

            int nextX = x + dr[dir];
            int nextY = y + dc[dir];
            if (!isPossibleToMove(nextX, nextY)) {  // 다음칸으로 갈 수 없을 경우
                dir = (dir + 1) % 4;  // 방향 바꾸기
                nextX = x + dr[dir];
                nextY = y + dc[dir];
            }

            x = nextX;
            y = nextY;
        }
    }

    // 경계 확인
    private static boolean isPossibleToMove(int x, int y) {
        if (x >= 0 && x < n && y >= 0 && y < n && !visited[x][y]) return true;
        return false;
    }

    // 블리자드
    private static void blizzard(int d, int s) {
        int[] ir = {0, -1, 1, 0, 0};       // 0 상 하 좌 우
        int[] ic = {0, 0, 0, -1, 1};
        int x = n / 2, y = n / 2;

        for (int i = 0; i < s; i++) {
            x += ir[d];
            y += ic[d];
            map[x][y] = 0;
        }
    }

    // 정렬
    private static void ordenar() {
        Location loc, left, right;      // left는 0이 시작하는 지점
        int leftIndex;

        // 0인 부분 찾기
        for (int i = 0; i < indexMap.length - 2; i++) {
            loc = indexMap[i];
            if (map[loc.x][loc.y] == 0) {   // 지워진 경우 right에서 left로 옮기기
                left = loc;
                leftIndex = i;

                // 숫자인 부분 찾기
                for (int j = i + 1; j < indexMap.length - 1; j++) { // right 찾으러가기
                    right = indexMap[j];
                    if (map[right.x][right.y] != 0) {   // 0이 아닌 경우
                        map[left.x][left.y] = map[right.x][right.y];   // 현재값을 left에 저장
                        map[right.x][right.y] = 0;  // 현재값 0

                        // left 오른쪽으로 한칸 옮겨 주기
                        leftIndex++;
                        left = indexMap[leftIndex];
                    }
                }

                break;
            }
        }
        return;
    }

    // 연속된 구슬 삭제
    private static boolean removeConsecutiveNumber() {
        boolean check = false;  // 삭제된 구슬이 있을 경우 true
        Location left, right;
        int leftValue, rightValue;

        // 4개이상 연속으로 체크되면 left 하나씩 옮기면서 삭제
        for (int i = 0; i < indexMap.length - 2; i++) {
            int count = 1;
            left = indexMap[i];
            leftValue = map[left.x][left.y];

            int j;
            for (j = i + 1; j < indexMap.length - 2; j++) {
                right = indexMap[j];
                rightValue = map[right.x][right.y];

                if (leftValue != 0 && leftValue == rightValue) {    // 왼쪽 값이랑 현재 값이랑 같을 경우
                    count++;
                    continue;
                }

                break;
            }

            // count가 4 이상이면 for문 돌면서 j - 1까지 삭제
            if (count >= 4) {

                if (map[indexMap[i].x][indexMap[i].y] == 1) {
                    unoPerla += count;
                } else if (map[indexMap[i].x][indexMap[i].y] == 2) {
                    dosPerla += count;
                } else if (map[indexMap[i].x][indexMap[i].y] == 3) {
                    tresPerla += count;
                }

                check = true;
                for (int k = i; k < j; k++) {
                    map[indexMap[k].x][indexMap[k].y] = 0;
                }
            }

            i = j - 1;
        }
        return check;
    }

    // 구슬 변화
    private static void cambioPerla() {
        int[][] newMap = new int[n][n];
        int newIndex = 0;
        int oldIndex = 0;

        for (oldIndex = 0; oldIndex < indexMap.length - 2; oldIndex++) {
            int a = 1;  // 연속된 숫자 개수
            int b = map[indexMap[oldIndex].x][indexMap[oldIndex].y];    // 구슬의 번호

            if (b == 0) break;

            // 다음 인덱스의 번호가 같을 경우 cuenta + 1
            int nextValue = map[indexMap[oldIndex + 1].x][indexMap[oldIndex + 1].y];
            while (nextValue == b) {    // 다음 값과 현재 값이 같을 경우
                a++;
                oldIndex++;
                b = nextValue;
                // 인덱스가 끝까지 갈 경우 정지
                if (oldIndex == n * n - 2) break;
                nextValue = map[indexMap[oldIndex + 1].x][indexMap[oldIndex + 1].y];
            }

            // 인덱스가 끝까지 갈 경우 정지
            if(newIndex >= n * n - 1) break;

            // 새로운 맵에 순서대로 a, b구슬 저장
            newMap[indexMap[newIndex].x][indexMap[newIndex].y] = a;
            newIndex++;
            newMap[indexMap[newIndex].x][indexMap[newIndex].y] = b;
            newIndex++;
        }

        // 맵은 새로운 맵
        map = newMap;

    }

}

class Location {
    int x;
    int y;

    public Location(int x, int y) {
        this.x = x;
        this.y = y;
    }
}