package cn.mccreefei.technologystack.leetcode;

import java.util.*;

/**
 * @author MccreeFei
 * @create 2020-01-10 下午3:46
 * @refer <href>https://leetcode.com/problems/accounts-merge/</href>
 * @idea 基于每个email的前后邻居构造图 dfs搜索出所有同一个人的email,排序,插入所属人
 */
public class AccountsMerge {
    public List<List<String>> accountsMerge(List<List<String>> accounts) {
        List<List<String>> result = new ArrayList<>();
        if (accounts == null || accounts.size() < 1) {
            return result;
        }
        // email -> neighbors
        Map<String, Set<String>> emailNeighborMap = new HashMap<>();
        //email -> username
        Map<String, String> emailNameMap = new HashMap<>();
        for (List<String> account : accounts) {
            String username = account.get(0);
            for (int i = 1; i < account.size(); i++) {
                if (!emailNeighborMap.containsKey(account.get(i))) {
                    emailNeighborMap.put(account.get(i), new HashSet<>());
                }
                emailNameMap.put(account.get(i), username);
                if (i == 1) {
                    continue;
                }
                emailNeighborMap.get(account.get(i)).add(account.get(i - 1));
                emailNeighborMap.get(account.get(i - 1)).add(account.get(i));
            }
        }

        Set<String> visited = new HashSet<>();
        for (String email : emailNameMap.keySet()) {
            if (visited.add(email)) {
                List<String> list = new LinkedList<>();
                dfs(email, list, visited, emailNeighborMap);
                Collections.sort(list);
                list.add(0, emailNameMap.get(email));
                result.add(list);
            }
        }
        return result;
    }

    private void dfs(String email, List<String> buffer, Set<String> visited, Map<String, Set<String>> emailNeighborMap) {
        buffer.add(email);
        for (String neighbor : emailNeighborMap.get(email)) {
            if (visited.add(neighbor)) {
                dfs(neighbor, buffer, visited, emailNeighborMap);
            }
        }
    }
}
