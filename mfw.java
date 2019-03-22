//Most frequently used word/words
import java.util.*;
import java.io.*;

class Solution{
    static public List<String> mostFrequentWord (String text, List<String> excluded){
        List<String> result = new ArrayList<>();
        Map<String, Integer> map = new HashMap<>();
        if(text.length() == 0) return result;
        Set<String> set = new HashSet<>();
        for(String s : excluded){
            set.add(s.toLowerCase());
        }
        int max = 0;
        text += ".";
        char[] c = text.toCharArray();
        StringBuffer word = new StringBuffer();
        for(char i : c){
            if(Character.isLetter(i)){
                word.append(i);
            }
            else if(word.length()>0){
                String temp = word.toString();
                if(!set.contains(temp)){
                     if(!map.containsKey(temp)) result.add(temp);
                     map.put(temp, map.getOrDefault(temp,0) + 1);
                     if(map.get(temp) > max){
                          max = map.get(temp);
                    }
                }
                word = new StringBuffer();
            }
        }
        int i = 0;
        // while( i < result.size()){
        //     if(map.get(result.get(i)) == max) i++;
        //     else result.remove(i);
        // }
        return result;
    }
    public static void main(String[] args) {
        Scanner in = new Scanner(new BufferedReader(new InputStreamReader(System.in)));
        String input = in.nextLine();
        List<String> ex = new ArrayList<>();
        int n = in.nextInt();
        for(int i = 0; i < n; i++){
            ex.add(in.next());
        }
        List<String> output = mostFrequentWord(input, ex);
        for(String s : output){
            System.out.println(s); 
        }
        in.close();
    }
}