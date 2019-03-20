import java.util.*;
import java.io.*;
import java.math.*;
import java.lang.*;

class Solution{
    //Definition of SegmentTree class
    static class SegmentTree{
        SegmentTreeNode root;
        //Use map to store the relationship between endpoints and their position in the array
        HashMap<Integer, Integer> map; 
        int[] endPoints;
        int n;
        SegmentTree(int[] set){
            n = set.length;
            endPoints = set;
            map = new HashMap<>();
            root = buildTree(0, n-2); //build the segment tree recursively
            map.put(endPoints[n-1], n-1);
        }
        public SegmentTreeNode buildTree(int start, int end){
            SegmentTreeNode node;
            if(start == end){ 
                //the node with interval[start, start], is equivalent to interval[set[start], set[start+1])
                node = new SegmentTreeNode(start, start);
                map.put(endPoints[start], start);
            }
            else{
                node = new SegmentTreeNode(start, end);
                int mid = start+ (end-start)/2;
                node.left = buildTree(start, mid);
                node.right = buildTree(mid+1, end);
            }
            return node;
        }
        public int insert(int start, int end){
            start = map.get(start);
            end = map.get(end)-1;
            insert(root, start, end);
            return root.measure;
        }
        public int delete(int start, int end){
            start = map.get(start);
            end = map.get(end)-1;
            delete(root, start, end);
            return root.measure;
        }
        void insert(SegmentTreeNode node, int start, int end){
            if(node == null || node.interval[1] < start || node.interval[0] > end) return;
            if(node.interval[0] >= start && node.interval[1] <= end){
               update(node, 1);
            }
            else{
                insert(node.left, start, end);
                insert(node.right, start, end);
                if(node.count > 0) node.measure = endPoints[node.interval[1]+1] - endPoints[node.interval[0]];
                else node.measure = node.left.measure + node.right.measure;
            }
        }
        void update(SegmentTreeNode node, int x){ //update count and measure of its canonical vertice
            if(node == null) return;
            if(x == 1){
                node.count++;
                update(node.left, 1);
                update(node.right, 1);
                node.measure = endPoints[node.interval[1]+1] - endPoints[node.interval[0]];
            }
            else{
                node.count--;
                update(node.left, -1);
                update(node.right, -1);
                if(node.count == 0 && (node.left == null || node.right == null)) node.measure = 0;
                else if(node.count == 0) node.measure = node.left.measure + node.right.measure;
            }
        }

        void delete(SegmentTreeNode node, int start, int end){
            if(node == null || node.interval[1] < start || node.interval[0] > end) return;
            if(node.interval[0] >= start && node.interval[1] <= end){
               update(node, -1);
            }
            else{
                delete(node.left, start, end);
                delete(node.right, start, end);
                if(node.count > 0) node.measure = endPoints[node.interval[1]+1] - endPoints[node.interval[0]];
                else node.measure = node.left.measure + node.right.measure;
            }
        }  
    }

    //Definition of SegmentTreeNode class
    static class SegmentTreeNode{
        int count;
        int measure;
        int[] interval;
        SegmentTreeNode left;
        SegmentTreeNode right;
        SegmentTreeNode(){
            count = 0;
            measure = 0; 
        }
        SegmentTreeNode(int start, int end){
            count = 0;
            measure = 0; 
            interval = new int[2];
            interval[0] = start;
            interval[1] = end;
        }

    }
    public static void main(String[] args)  throws java.lang.Exception{
        Scanner in = new Scanner(new BufferedReader(new InputStreamReader(System.in)));
        String fileName = in.next();
        FileReader fr = new FileReader(fileName);

        //add a timer here//
        long time = 0; 
        long startTime = System.currentTimeMillis(); 

        String input = "";
        int i;
        while((i=fr.read()) != -1){
            input += (char)(i);
        }
        fr.close();
        String[] lines = input.split("\r\n");  //It is not "\n"
        String[] firstLine = lines[0].split(" ");
        int m = Integer.parseInt(firstLine[0]);
        int n = Integer.parseInt(firstLine[1]);
        String[] endPoints = lines[1].substring(0,lines[1].length()-1).split("  ");
        int[] set = new int[m];
        for(int index = 0; index < m; ++index){
            set[index] = Integer.parseInt(endPoints[index]);
        }
        SegmentTree tree = new SegmentTree(set); //Construct segment tree

        String output = "";
        int l = 2;
        for(int j = 0; j < n; j++){     //For each operation, insert or delete
            String[] temp = lines[l++].split(" ");
            int start = Integer.parseInt(temp[1]); 
            int end = Integer.parseInt(temp[2]); 
            if(temp[0].equals("I")){
                output += tree.insert(start, end) + "\n";
            }
            else{
                output += tree.delete(start, end) + "\n";
            }
        }
        long endTime = System.currentTimeMillis();
        time += endTime - startTime;
        System.out.print("The solution for " + fileName + " is \n");
        System.out.print(output);
        System.out.print("Time: "+ time + "ms"+"\n");
    }
}