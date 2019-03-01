import java.util.*; 
import java.lang.*; 
import java.io.*; 
import java.util.LinkedList; 

class H{
    public static void main (String[] args) throws java.lang.Exception 
    {
        //Task: read input files
        FileReader fr = new FileReader("b_lovely_landscapes.txt");

        String input = "";
        int i;
        while(((i=fr.read()) != -1)){ //read text each one as type: INT which can be transformed into Char, including "enter"; so we can split the file by "enter"
            input += (char)i;
        }
        fr.close();
        String[] lines = input.split("\n"); // "\n" means ending a line.
        //Q: Check first char in the file, then read the file once completely, or separatly?
        //How to split into several problems
         
        //first line: k problems
        int k = Integer.parseInt(lines[0]);
        int l = 1;
        String output = k + "\n";
        PriorityQueue<int[]> heap = new PriorityQueue<>(new Comparator<int[]>() {
            @Override
            public int compare(int[] a, int[] b){
                return a[1] - b[1];
            }
        });

        for(int j = 0; j < k; j++){
            String[] temp = lines[l].split(" ");
            int tags = Integer.parseInt(temp[1]);
            heap.add(new int[]{j,tags});
        }
        while(!heap.isEmpty()){
            output += heap.poll()[0] + "\n";
        }
        //Write maxflow into output files
        BufferedWriter writer = new BufferedWriter(new FileWriter("b.txt", true));
        writer.append(output);             
        writer.close();        
    }
}