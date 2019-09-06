package cn.mccreefei.technologystack.leetcode;

/**
 * @author MccreeFei
 * @create 2019-09-06 上午10:21
 * @refer <href>https://leetcode.com/problems/best-time-to-buy-and-sell-stock-with-cooldown/</href>
 * @idea 动态规划 buy[i]代表到达i天时，以buy或者colldown为操作时最大利润。sell[i]代表到达i天时，以sell或者colldown为操作时最大利润
 */
public class BestTimeToBuyAndSellStockWithCooldown {
    public int maxProfit(int[] prices) {
        if (prices == null || prices.length < 2) {
            return 0;
        }
        int n = prices.length;
        int[] buy = new int[n];
        int[] sell = new int[n];

        buy[0] = -prices[0];
        buy[1] = Math.max(-prices[0], -prices[1]);

        sell[0] = 0;
        sell[1] = Math.max(0, prices[1] - prices[0]);

        for (int i = 2; i < n; i++) {
            buy[i] = Math.max(buy[i-1], sell[i-2] - prices[i]);
            sell[i] = Math.max(sell[i-1], buy[i-1] + prices[i]);
        }
        return Math.max(Math.max(buy[n-1], sell[n-1]), 0);
    }
}
