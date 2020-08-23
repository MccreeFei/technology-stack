package cn.mccreefei.technologystack.leetcode;

/**
 * @author MccreeFei
 * @create 2020-08-23 下午4:43
 * @refer <href>https://leetcode.com/problems/stream-of-characters/</href>
 * @idea 单词逆序构造字典树
 */
public class StreamOfCharacters {
    public class TrieNode {
        TrieNode[] next = new TrieNode[26];
        boolean isWord;
    }
    private TrieNode root = new TrieNode();
    private StringBuilder sb = new StringBuilder();

    public StreamOfCharacters(String[] words) {
        for (String word : words) {
            create(word);
        }
    }

    public boolean query(char letter) {
        sb.append(letter);
        TrieNode node = root;
        for (int i = sb.length() - 1; i >= 0; i--) {
            node = node.next[sb.charAt(i) - 'a'];
            if (node == null) {
                return false;
            }
            if (node.isWord) {
                return true;
            }
        }
        return false;
    }

    private void create(String word) {
        TrieNode node = root;
        for (int i = word.length() - 1; i >= 0; i--) {
            if (node.next[word.charAt(i) - 'a'] == null) {
                node.next[word.charAt(i) - 'a'] = new TrieNode();
            }
            node = node.next[word.charAt(i) - 'a'];
        }
        node.isWord = true;
    }
}
