package cn.mccreefei.technologystack.leetcode;

/**
 * @author MccreeFei
 * @create 2020-01-17 下午2:52
 * @refer <href>https://leetcode.com/problems/add-and-search-word-data-structure-design/</href>
 * @idea 字典树
 */
public class WordDictionary {
    public class TrieNode {
        TrieNode[] children = new TrieNode[26];
        boolean isWord;
    }
    TrieNode root;
    /** Initialize your data structure here. */
    public WordDictionary() {
        root = new TrieNode();
    }

    /** Adds a word into the data structure. */
    public void addWord(String word) {
        TrieNode node = root;
        for (int i = 0; i < word.length(); i++) {
            if (node.children[word.charAt(i)-'a'] == null) {
                node.children[word.charAt(i)-'a'] = new TrieNode();
            }
            node = node.children[word.charAt(i)-'a'];
        }
        node.isWord = true;
    }

    /** Returns if the word is in the data structure. A word could contain the dot character '.' to represent any one letter. */
    public boolean search(String word) {
        return match(word.toCharArray(), 0, root);
    }

    private boolean match(char[] chars, int cur, TrieNode node) {
        if (cur == chars.length) {
            return node.isWord;
        }
        if (chars[cur] == '.') {
            for (int i = 0; i < node.children.length; i++) {
                if (node.children[i] != null && match(chars, cur+1, node.children[i])) {
                    return true;
                }
            }
        } else {
            return (node.children[chars[cur]-'a'] != null && match(chars, cur+1, node.children[chars[cur]-'a']));
        }
        return false;
    }
}
