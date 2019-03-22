import java.util.*;
import java.io.*;

public class Solution {
    public static int test(int[][] graph, Map<Character, Character> map, String word, char[] x){
        int count = 0;
        boolean[] visited = new boolean[26];
        Set<Character> set = new HashSet<>();
        for(char c : x){
            if(!map.containsValue(c)) return -1;
            set.add(c);
        }
        for(char i : word.toCharArray()){
            visited = new boolean[26];
            if(set.contains(i)) count += bfs(graph, i, set, 2, visited);
            else count++;
        }
        return count;
    }
    public static int bfs(int[][] graph, char c, Set<Character> set, int count, boolean[] visited){
        char[] characters = {'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l','m', 'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z'};
        int res = Integer.MAX_VALUE;
        int p = c -'a';
        visited[p] = true;
        for(int i = 0; i < 26; ++i){
            char temp = characters[i];
            if(graph[i][p] == 0) continue;
            if(!set.contains(temp)) return count;
            else if(!visited[i]) res = Math.min(res, bfs(graph, temp, set, count+1, visited));
        }
        return res;
    }
    public static void main(String[] args) {
    Scanner in = new Scanner(new BufferedReader(new InputStreamReader(System.in)));
    int t = in.nextInt(); // Scanner has functions to read ints, longs, strings, chars, etc.
    String output = "";
    for (int i = 1; i <= t; ++i) {
        int n = in.nextInt();
        in.nextLine();
        int[][] inc = new int[26][26];
        Map<Character, Character> map = new HashMap<>();
        for(int j = 1; j <= n; ++j){
            String temp = in.nextLine();
            char a = temp.charAt(0);
            char b = temp.charAt(2);
            inc[a-'a'][b-'a'] = 1;
            map.put(a, b);
        }
        int k = in.nextInt();
        in.nextLine();
        char[] x = new char[k];
        String xc = in.nextLine();
        for(int l = 0; l < k; ++l){
            x[l] = xc.charAt(2*l);

        }
        String word = in.nextLine();

        int res = test(inc, map, word, x);
        output += res + "\n";
    }
    System.out.println(output.substring(0,output.length()-1));
    }
}