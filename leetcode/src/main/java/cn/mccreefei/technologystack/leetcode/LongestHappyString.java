package cn.mccreefei.technologystack.leetcode;

/**
 * @author MccreeFei
 * @create 2020-04-07 上午11:29
 * @refer <href>https://leetcode.com/problems/longest-happy-string/</href>
 * @idea 贪心算法 设a>=b>=c 选取最多两个a,选取之后a的数量>=b,则选一个b，重复此过程,直至b==0,此时最后选取最多两个a
 */
public class LongestHappyString {
    public String longestDiverseString(int a, int b, int c) {
        StringBuffer sb = new StringBuffer();
        generate(a, b, c, 'a', 'b', 'c', sb);
        return sb.toString();
    }

    private void generate(int a, int b, int c, char aa, char bb, char cc, StringBuffer sb) {
        if (b > a) {
            generate(b, a, c, bb, aa, cc, sb);
            return;
        }
        if (c > b) {
            generate(a, c, b, aa, cc ,bb, sb);
            return;
        }
        if (b == 0) {
            for (int i = 0; i < Math.min(2, a); i++) {
                sb.append(aa);
            }
            return;
        }
        int useA = Math.min(a, 2);
        int useB = a - useA >= b ? 1 : 0;
        for (int i = 0; i < useA; i++) {
            sb.append(aa);
        }
        if (useB == 1) {
            sb.append(bb);
        }
        generate(a - useA, b - useB, c, aa, bb, cc, sb);
    }
}
