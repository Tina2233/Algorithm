//Longest Palindrome Substring
import java.util.*;
import java.io.*;

class Solution{
    static public String lps(String s){
        if(s.length() == 0) return s;
        int length = s.length();
        char[] c = s.toCharArray();
        String res = s.substring(0,1);
        boolean[][] dp = new boolean[length][length];

        for(int i = 0; i < length; ++i){
            for(int j = 0; i+j < length; ++j){
                if(c[j] == c[i+j] && (i<2 || dp[j+1][i+j-1])){
                    dp[j][j+i] = true;
                    if(i+1 > res.length()) res = s.substring(j, j+i+1);
                } 
            }
        }
        return res;
    }
    public static void main(String[] args) {
        Scanner in = new Scanner(new BufferedReader(new InputStreamReader(System.in)));
        String input = in.next();
        String output = lps(input);
        System.out.println(output); 
        in.close();
    }
}
