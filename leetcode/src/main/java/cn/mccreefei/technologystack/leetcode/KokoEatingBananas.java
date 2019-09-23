package cn.mccreefei.technologystack.leetcode;

/**
 * @author MccreeFei
 * @create 2019-09-23 下午2:46
 * @refer <href>https://leetcode.com/problems/koko-eating-bananas/</href>
 * @idea 二分法 从1和piles[]最大值之间进行二分查找能全部吃完和不能吃完的临界
 */
public class KokoEatingBananas {
    public int minEatingSpeed(int[] piles, int H) {
        int left = 1;
        int right = 0;
        for (int pile : piles) {
            right = Math.max(right, pile);
        }
        while (left <= right) {
            int mid = left + (right - left)/2;
            if (canEatAll(piles, mid, H)) {
                right = mid - 1;
            } else {
                left = mid + 1;
            }
        }
        return left;
    }

    private boolean canEatAll(int[] piles, int k, int H) {
        int hours = 0;
        for (int pile : piles) {
            hours += Math.ceil((double)pile / k);
        }
        return hours <= H;
    }
}
