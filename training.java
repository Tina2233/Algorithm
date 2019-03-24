import java.util.*;
import java.io.*;

class Solution{
    public static int training(int n, int p, int[] temp, Map<Integer, Integer> map){
        for(int f : map.values()){
            if(f >= p) return 0;
        }
        Arrays.sort(temp);
        int sum = 0;
        int result = Integer.MAX_VALUE;
        for(int i = 0; i < n; i++){
            sum += temp[i];
            if(i >= p-1){
                if(i>=p)sum -= temp[i-p];
                result = Math.min(temp[i]*p-sum, result);
            } 
        }
        return result;
    }
  public static void main(String[] args) {
    Scanner in = new Scanner(new BufferedReader(new InputStreamReader(System.in)));
    int t = in.nextInt(); // Scanner has functions to read ints, longs, strings, chars, etc.
    for (int i = 1; i <= t; ++i) {
      int n = in.nextInt();
      int p = in.nextInt();
      int[] temp = new int[n];
      Map<Integer, Integer> map = new HashMap<>();
      for(int j = 0; j < n; ++j){
        temp[j] = in.nextInt();
        map.put(temp[j], map.getOrDefault(temp[j], 0)+1);
      }
      int result = training(n,p,temp,map);
      System.out.println("Case #" + i + ": " + result);
    }
  }
}