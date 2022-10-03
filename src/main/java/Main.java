public class Main {
    public static void main(String[] args) {

        Solution sol = new Solution();
        int[] num = new int[]{6, 9, 7, 5};
        int[][] links = new int[][]{{-1, -1}, {-1, -1}, {-1, 0}, {2, 1}};

        int answer = sol.solution(1, num, links);
        System.out.println(answer);
    }
}
