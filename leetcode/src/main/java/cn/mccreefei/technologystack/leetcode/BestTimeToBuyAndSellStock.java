package cn.mccreefei.technologystack.leetcode;

/**
 * @author MccreeFei
 * @create 2019-06-13 上午11:29
 * @refer <href>https://leetcode.com/problems/best-time-to-buy-and-sell-stock/submissions/</href>
 * @idea 记录最大利润与今天前的最小售价
 */
public class BestTimeToBuyAndSellStock {
    public int maxProfit(int[] prices) {
        if (prices == null || prices.length < 2) {
            return 0;
        }
        int lowest = prices[0];
        int profit = 0;

        for (int i = 1; i < prices.length; i++) {
            int between = prices[i] - lowest;
            profit = between > profit ? between : profit;
            lowest = prices[i] < lowest ? prices[i] : lowest;
        }
        return profit;
    }

}
