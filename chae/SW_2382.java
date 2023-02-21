import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

// SW 역량테스트
// 미생물 격리
public class SW_2382 {
    public static void main(String[] args) throws IOException {
        int[] dr = {0, -1, 1, 0, 0};    // 0, 상, 하, 좌, 우
        int[] dc = {0, 0, 0, -1, 1};
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringBuilder sb = new StringBuilder();

        int TC = Integer.parseInt(br.readLine());

        for (int t = 1; t <= TC; t++) {
            int[] nmk = Arrays.stream(br.readLine().split(" ")).mapToInt(Integer::parseInt).toArray();

            int n = nmk[0];     // int[n][n]
            int m = nmk[1];     // m 시간
            int k = nmk[2];     // 군집 개수

            // 배열만들기
            List<Microbe> microbes = new ArrayList<>();

            // 배열에 미생물 넣기
            for (int i = 0; i < k; i++) {
                int[] detail = Arrays.stream(br.readLine().split(" ")).mapToInt(Integer::parseInt).toArray();

                microbes.add(new Microbe(detail[2], detail[0] * k + detail[1], detail[0], detail[1], detail[3]));
            }

            for (int time = 0; time < m; time++) {

                // 이동
                for (int i = 0; i < microbes.size(); i++){
                    microbes.get(i).r = microbes.get(i).r + dr[microbes.get(i).direction];
                    microbes.get(i).c = microbes.get(i).c + dc[microbes.get(i).direction];
                    microbes.get(i).index = (microbes.get(i).r * n) + microbes.get(i).c;

                    // 약품일 경우
                    if ((microbes.get(i).r == 0 || microbes.get(i).c == 0 || microbes.get(i).r == n - 1 || microbes.get(i).c == n - 1)) {
                        // amount /= 2 , direction 짝수면 -1 홀수면 +1;
                        microbes.get(i).amount /= 2;

                        if (microbes.get(i).direction % 2 == 0) microbes.get(i).direction -= 1;
                        else microbes.get(i).direction += 1;

                        if (microbes.get(i).amount == 0) {
                            microbes.remove(i);
                            i--;
                        }
                    }


                }

                // 인덱스 순으로 정렬
                microbes.sort(new Comparator<Microbe>() {
                    @Override
                    public int compare(Microbe o1, Microbe o2) {
                        if (o1.index == o2.index) {
                            return o2.amount - o1.amount;
                        }
                        return o1.index - o2.index;
                    }
                });

                // 다음 군집과 비교해 인덱스가 같을 경우 amount가 큰 곳으로 합치고 작은거 삭제
                for (int i = 0; i < microbes.size()-1 ; i++) {
                    if (microbes.get(i).index == microbes.get(i+1).index) {
                        microbes.get(i).amount += microbes.get(i+1).amount;

                        microbes.remove(i+1);
                        i--;
                    }

                }
            }
            int answer= 0;

            for (int i = 0; i < microbes.size(); i++) {
                answer += microbes.get(i).amount;
            }

            sb.append("#").append(t).append(" ").append(answer).append("\n");
        }

        System.out.println(sb);
    }

}

class Microbe {
    int index;
    int amount;
    int r;
    int c;
    int direction;

    public Microbe(int amount, int index, int r, int c, int direction) {
        this.amount = amount;
        this.index = index;
        this.r = r;
        this.c = c;
        this.direction = direction;
    }
}
