import java.util.*;
import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;

class Suffix{
    public static String construct(String s){
        s += "$";
        int length = s.length();
        char[] c = s.toCharArray();
        int[] N = new int[length];

        //sort the T(i,1) using LSD radix sort, and get N(i, 1)

        List<Queue<Integer>> q = new LinkedList<>();
        for(int i = 0; i < 27; ++i){
            q.add(new LinkedList<>());
        }
        for(int i = length - 1; i >= 0; --i){
            if(i < length - 1){
                int temp = c[i] - 'a' + 1;
                q.get(temp).add(i);
            }
            else q.get(0).add(i);
        }
        int rank = 0;
        for(int i = 0; i < 27; ++i){
            int count = 0;
            while(!q.get(i).isEmpty()){
                int index = q.get(i).poll();
                N[index] = rank;
                count++;
            }
            rank += count;
        }

        //sort the T(i, 2l) by using sorted T(i, l), until l > length
        int l = 1;
        while(l <= length){
            N = sort(N, l, s);
            l = 2*l;
        }
            

        //Transform N(i,length) into result, and then output result
        int[] result = new int[length];
        for(int i = 0; i < length; ++i){
            result[N[i]] = i;
        }
        String res = "";
        for(int i : result){
            res += " " + i;
        }
        
        return res.substring(1);
    }

    public static int[] sort(int[] N, int l, String s){
        int[] res = new int[N.length];
        int[] T = new int[N.length];
        List<Queue<Integer>> q = new LinkedList<>();
        for(int i = 0; i < N.length; ++i){
            q.add(new LinkedList<>());
        }

        //First, Sort by N(i+l, l)
        for(int i = N.length - 1; i >= 0; --i){
            if(i+l<N.length) q.get(N[i+l]).add(i);
            else q.get(0).add(i);
        }
        int j = 0;
        for(int i = 0; i < N.length; ++i){
            while(!q.get(i).isEmpty()){
                T[j]= q.get(i).poll();
                j++;
            }
        }

        //Second, Sort by N(i, l)
        for(int i : T){
            q.get(N[i]).add(i);
        }

        //Get the N(i, 2l)
        int rank = 0;
        for(int i = 0; i < N.length; ++i){
            if(q.get(i).isEmpty()) continue;
            String sub = s.substring(q.get(i).peek(), Math.min(q.get(i).peek()+2*l, s.length()));
            int count = 0;
            while(!q.get(i).isEmpty()){
                int temp =  q.get(i).poll();
                String cur = s.substring(temp, Math.min(temp+2*l, s.length()));
                if(sub.equals(cur)){
                    res[temp] = rank;
                    count++;
                }
                else{
                    rank += count;
                    res[temp] = rank;
                    count = 1;
                    sub = cur;
                } 

            }
            rank += count; 
        }
        return res;
    }
    public static void main (String[] args) throws java.lang.Exception{
        Scanner in = new Scanner(new BufferedReader(new InputStreamReader(System.in)));
        String fileName = in.next();
        in.close();
        long time = 0;
        long start = System.currentTimeMillis();
        String input = new String(Files.readAllBytes(Paths.get(fileName)), StandardCharsets.UTF_8);
        String[] lines = input.split("\r\n");
        int k = Integer.parseInt(lines[0]);
        int index = 1;
        for(int i = 0; i < k ; ++i){
            System.out.println(construct(lines[index++]));
        }
        long end = System.currentTimeMillis();
        time = end - start;
        System.out.println("Time: "+ time);
    }
}