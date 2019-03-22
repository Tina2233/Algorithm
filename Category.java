import java.util.*;
import java.io.*;

public class Solution{
    class CategoryNode{
        int value;
        List<CategoryNode> children;
        public CategoryNode(int value){
            this.value = value;
            children = new ArrayList<>();
        }
    }
    CategoryNode res;
    int maxAvg;
    public CategoryNode getMostPopularNode(CategoryNode rootCategory){
        res = new CategoryNode(0);
        maxAvg = 0;
        dfs(rootCategory);
        return res;
    }
    public int[] dfs(CategoryNode root){
        int sum = root.value;
        int count = 1;
        for(int i = 0; i < root.children.size(); i++){
            int[] temp = dfs(root.children.get(i));
            sum += temp[0];
            count += temp[1];
        }
        int avg = sum/count;
        if(avg > maxAvg){
            maxAvg = avg;
            res = root;
        }
        return new int[]{sum, count};
    }

    public static void main(String[] args) {
        Scanner in = new Scanner(new BufferedReader(new InputStreamReader(System.in)));
        // int t = in.nextInt(); // Scanner has functions to read ints, longs, strings, chars, etc.
        // for (int i = 1; i <= t; ++i) {
        //   int n = in.nextInt();
        //   int temp = even(n);
        //   System.out.println("Case #" + i + ": " + (temp));
        int n = in.nextInt();
        int[] arr = new int[n];
        for(int i = 0; i < n; ++i){
            arr[i] = in.nextInt();
        }
        arr = reverseArray(arr);
        for(int i = 0; i < n; ++i){
            System.out.println(arr[i]);
        }
        in.close();
    }
}
