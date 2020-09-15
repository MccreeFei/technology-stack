package cn.mccreefei.technologystack.leetcode;

import java.util.LinkedList;

/**
 * @author MccreeFei
 * @create 2020-09-15 上午11:14
 * @refer <href>https://leetcode.com/problems/check-if-string-is-transformable-with-substring-sort-operations/</href>
 * @idea 将某一元素从后j位置向前移动到i位置， 只要i-j位置中的元素都大于等于该元素，那么此元素一定可以通过冒泡的方式移动到i位置，
 * 因此只需判断i-j中有无比该元素小的元素即可。
 */
public class CheckIfStringIsTransformableWithSubstringSortOperations {
    public boolean isTransformable(String s, String t) {
        //队列存放数字出现的index
        LinkedList[]  queueArray = new LinkedList[10];
        for (int i = 0; i < 10; i++) {
            queueArray[i] = new LinkedList();
        }
        for (int i = 0; i < s.length(); i++) {
            queueArray[s.charAt(i) - '0'].offer(i);
        }
        for (int i = 0; i < t.length(); i++) {
            int idx = t.charAt(i) - '0';
            // s,t元素不相同直接false
            if (queueArray[idx].isEmpty()) {
                return false;
            }
            int pos = (int)queueArray[idx].peek();
            //如果出现比idx小的元素，出现在他所处位置pos之前，则返回false
            for (int j = 0; j < idx; j++) {
                if (!queueArray[j].isEmpty() && (int)queueArray[j].peek() < pos) {
                    return false;
                }
            }
            queueArray[idx].poll();
        }
        return true;
    }
}
