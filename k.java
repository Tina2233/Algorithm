//Longest Subtring with at most k distinct characters
import java.util.*;
import java.io.*;

class Solution{
    static public String kDistinct(String s, int k){
        String res = "";
        Map<Character, Integer> map = new HashMap<>();
        int max = 0;
        char[] c = s.toCharArray();
        int start = 0;
        for(int i = 0; i < c.length; ++i){
            if(!map.containsKey(c[i]) && map.size() < k){
                map.put(c[i], i);
            }
            else{
                if(i - start + 1 > max){
                    max = i - start + 1;
                    res = s.substring(start, i);
                }
                if(!map.containsKey(c[i])){
                    int minIndex = i;
                    for(int j : map.values()){
                        minIndex = Math.min(j, minIndex);
                    }
                    map.remove(c[minIndex]);
                    start = minIndex+1;
                }
                map.put(c[i], i);
            }
        } 
        return res;
    }

    public static void main(String[] args) {
        Scanner in = new Scanner(new BufferedReader(new InputStreamReader(System.in)));
        String input = in.next();
        int k = in.nextInt();
        String output = kDistinct(input, k);
        System.out.println(output); 
        in.close();
    }
}