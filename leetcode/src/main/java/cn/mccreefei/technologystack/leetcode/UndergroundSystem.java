package cn.mccreefei.technologystack.leetcode;

import javafx.util.Pair;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.function.ToDoubleBiFunction;
import java.util.stream.Collectors;

/**
 * @author MccreeFei
 * @create 2020-03-31 下午2:05
 * @refer <href>https://leetcode.com/problems/design-underground-system/</href>
 * @idea two map
 */
public class UndergroundSystem {
    /**
     * id -> Pair<fromStation, time>
     */
    private Map<Integer, Pair<String, Integer>> inMap;
    /**
     * Pair<fromStation, toStation> -> times
     */
    private Map<Pair<String, String>, List<Integer>> routeTimeMap;
    public UndergroundSystem() {
        this.inMap = new HashMap<>();
        this.routeTimeMap = new HashMap<>();
    }

    public void checkIn(int id, String stationName, int t) {
        inMap.put(id, new Pair<>(stationName, t));
    }

    public void checkOut(int id, String stationName, int t) {
        Pair<String, Integer> pair = inMap.get(id);
        List<Integer> times = routeTimeMap.computeIfAbsent(new Pair<>(pair.getKey(), stationName),
                a -> new ArrayList<>());
        times.add(t - pair.getValue());
    }

    public double getAverageTime(String startStation, String endStation) {
        List<Integer> times = routeTimeMap.getOrDefault(new Pair<>(startStation, endStation), new ArrayList<>());
        if (times.size() == 0) {
            return 0;
        }
        return times.stream().collect(Collectors.averagingDouble(a -> a));
    }
}
