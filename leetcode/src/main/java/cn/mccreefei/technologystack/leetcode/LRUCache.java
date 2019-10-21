package cn.mccreefei.technologystack.leetcode;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * @author MccreeFei
 * @create 2019-10-21 下午2:28
 * @refer <href>https://leetcode.com/problems/lru-cache/</href>
 * @idea 利用LinkedHashMap accessOrder实现
 */
public class LRUCache {
    public class LRUMap extends LinkedHashMap<Integer, Integer> {
        private static final long serialVersionUID = -3537707458821439832L;
        private int capacity;
        LRUMap(int capacity) {
            super(capacity, 0.75f, true);
            this.capacity = capacity;
        }
        @Override
        protected boolean removeEldestEntry(Map.Entry eldest) {
            return size() > capacity;
        }
    }

    private LRUMap lruMap;
    public LRUCache(int capacity) {
        lruMap = new LRUMap(capacity);
    }

    public int get(int key) {
        return lruMap.getOrDefault(key, -1);
    }

    public void put(int key, int value) {
        lruMap.put(key, value);
    }
}
