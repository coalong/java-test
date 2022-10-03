import java.util.Arrays;

class Solution {
    private int root;
    private int size;
    private Node[] tree;

    static class Node {
        int left;
        int right;

        public Node(int left, int right) {
            this.left = left;
            this.right = right;
        }
    }

    // k: 나눌 그룹의 수
    // num: 시험장의 응시자 수가 담긴 배열
    // links: 시험장의 연결 상태를 나타내는 2차원 배열 (left, right)
    public int solution(int k, int[] num, int[][] links) {
        // 주어진 응시자 수와 연결 상태로 트리 구성
        setTree(num, links);

        int low = 0;
        int answer = (int) 1e9;

        for (int n : num) {
            low = Math.max(low, n); // 가장 인원이 많은 시험장의 정원
        }

        while (low <= answer) {
            int mid = (low + answer) / 2;
            if (solve(num, mid) <= k) {
                answer = mid - 1;
            } else {
                low = mid + 1;
            }
        }

        return answer + 1;
    }

    private int solve(int[] num, int max) {
        size = 0;
        dfs(num, root, max);
        return size + 1;
    }

    private int dfs(int[] num, int current, int max) {
        // 왼쪽 노드 시험장에서 넘어오는 인원
        int leftValue = 0;
        // 오른쪽 노드 시험장에서 넘어오는 인원
        int rightValue = 0;

        if (tree[current].left != -1) {
            leftValue = dfs(num, tree[current].left, max);
        }
        if (tree[current].right != -1) {
            rightValue = dfs(num, tree[current].right, max);
        }

        // 현재 시험장 인원 + 왼쪽 노드 시험장 인원 + 오른쪽 노드 시험장 인원 이 max 보다 작다. 시험장 자르지 않고 위로 보낸다.
        if (num[current] + leftValue + rightValue <= max) {
            return num[current] + leftValue + rightValue;
        }

        // 현재 시험장 인원 + 자식 노드 증 작은 값 이 max 보다 작다. 자식 노드 중 큰 인원 가진 시험장을 따로 자르고 위로 보낸다.
        if (num[current] + Math.min(leftValue, rightValue) <= max) {
            size += 1;
            return num[current] + Math.min(leftValue, rightValue);
        }

        // 현재 시험장을 위로 넘기고 왼쪽 노드, 오른쪽 노드 시험장들은 각각 자른다.
        size += 2;
        return num[current];
    }

    private void setTree(int[] num, int[][] links) {
        tree = new Node[num.length];
        int[] parent = new int[num.length];

        Arrays.fill(parent, -1);

        for (int i = 0; i < links.length; i++) {
            tree[i] = new Node(links[i][0], links[i][1]);

            if (tree[i].left != -1) {
                parent[tree[i].left] = i;
            }
            if (tree[i].right != -1) {
                parent[tree[i].right] = i;
            }
        }

        for (int i = 0; i < parent.length; i++) {
            if (parent[i] == -1) {
                root = i;
                break;
            }
        }
    }
}
