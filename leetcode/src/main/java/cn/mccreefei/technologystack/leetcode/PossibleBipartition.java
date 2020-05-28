package cn.mccreefei.technologystack.leetcode;

/**
 * @author MccreeFei
 * @create 2020-05-28 下午2:17
 * @refer <href>https://leetcode.com/problems/possible-bipartition/</href>
 * @idea graph dfs
 */
public class PossibleBipartition {
    public boolean possibleBipartition(int N, int[][] dislikes) {
        //构造图 表示graph[m][n] == 1表示m-1与n-1dislike
        int[][] graph = new int[N][N];
        for (int[] dislike : dislikes) {
            graph[dislike[0] - 1][dislike[1] - 1] = 1;
            graph[dislike[1] - 1][dislike[0] - 1] = 1;
        }
        //给每个数涂色 0表示未涂色 1 和 -1代表涂色的对立面 dislike双方必须吐对立面
        int[] colored = new int[N];
        for (int i = 0; i < N; i++) {
            if (colored[i] == 0 && !dfs(graph, colored, i, 1)) {
                return false;
            }
        }
        return true;

    }

    /**
     * 给cur涂上color色，并且对cur所有dislike的对象吐-color色 成功返回true 否则false
     */
    private boolean dfs(int[][] graph, int[] colored, int cur, int color) {
        colored[cur] = color;
        for (int i = 0; i < graph.length; i++) {
            if (graph[cur][i] == 1) {
                //当前i节点已被涂上color色 冲突
                if (colored[i] == color) {
                    return false;
                }
                if (colored[i] == 0 && !dfs(graph, colored, i, -color)) {
                    return false;
                }
            }
        }
        return true;
    }
}
