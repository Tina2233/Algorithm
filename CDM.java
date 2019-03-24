import java.util.*;
import java.io.*;

class Solution {
    public static int CDM(int[][] graph, Queue<int[]> q){
        int result = 0;
        List<int[]> position = new ArrayList<>();
        //------------------------
        //You can use BFS here
        //------------------------
        while(!q.isEmpty()){
            int[] cur = q.poll();
            int x = cur[0];
            int y = cur[1];
            int distance = 0;
            if(graph[x][y] != 1){
                distance = graph[x][y]; 
                position.add(new int[]{x,y});
            }      
            if(x+1<graph.length && graph[x+1][y] == 0){
                q.add(new int[]{x+1,y});
                graph[x+1][y] = distance - 1;
            } 
            if(x-1>=0 && graph[x-1][y] == 0){
                q.add(new int[]{x-1,y});
                graph[x-1][y] = distance - 1;
            } 
            if(y+1<graph[0].length && graph[x][y+1] == 0){
                q.add(new int[]{x,y+1});
                graph[x][y+1] = distance - 1;
            } 
            if(y-1>=0 && graph[x][y-1] == 0){
                q.add(new int[]{x,y-1});
                graph[x][y-1] = distance - 1;
            } 

        }
        if(position.size() == 0) return 0;
        result = Integer.MAX_VALUE;
        for(int[] pos : position){
            int worst = 0;
            for(int i = 0; i < graph.length; ++i){
                for(int j = 0; j < graph[0].length; ++j){
                    if(graph[i][j] == 1) continue;
                    else{
                        int temp = -graph[i][j];
                        int distance = Math.abs(pos[0] - i) + Math.abs(pos[1] - j);
                        temp = Math.min(temp, distance);
                        worst = Math.min(worst, -temp);
                    }
                }
            }
            result = Math.min(result, -worst);
        }
        
        return result;
    }
  public static void main(String[] args) {
    Scanner in = new Scanner(new BufferedReader(new InputStreamReader(System.in)));
    int t = in.nextInt(); // Scanner has functions to read ints, longs, strings, chars, etc.
    for (int i = 1; i <= t; ++i) {
      int R = in.nextInt();
      int C = in.nextInt();
      int[][] graph = new int[R][C];
      Queue<int[]> q = new LinkedList<>();
      in.nextLine();
      for(int r = 0; r < R; ++r){    
        String line = in.nextLine();
        for(int c = 0; c < C; ++c){
            graph[r][c] = line.charAt(c) - '0';
            if(graph[r][c] == 1) q.add(new int[]{r,c});
        }
      }
      int temp = CDM(graph, q);
      System.out.println("Case #" + i + ": " + (temp));
    }
  }
}