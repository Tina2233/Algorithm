//MST Problem
import java.util.*;
import java.io.*;

class Solution{
    static public class Edge{
        String u;
        String v;
        int cost;
        public Edge(){}
        public Edge(String u, String v, int c){
            this.u = u;
            this.v = v;
            cost = c;
        }
    }
    public List<Edge> minCostEdge(List<Edge> list){
        Set<String> cities = new HashSet<>();
        List<Edge> result = new ArrayList<>();
        PriorityQueue<Edge> heap = new PriorityQueue<>(new Comparator<Edge>() {
            @Override
            public int compare(Edge a, Edge b){
                return a.cost - b.cost;
            }
        });
        for(int i = 0; i < list.size(); i++){
            cities.add(list.get(i).u);
            cities.add(list.get(i).v);
        }

        Iterator iter = cities.iterator();
        String current = (String)iter.next();
        cities.remove(current);

        while(!cities.isEmpty()){
            int i = 0;
            while(i < list.size()){
                if(current.equals(list.get(i).u) || current.equals(list.get(i).v)){
                    heap.add(list.get(i));
                    list.remove(i);
                } 
                else i++;
            }
            while(heap.isEmpty() && !cities.contains(heap.peek().u) && !cities.contains(heap.peek().v)){
                heap.poll();
            }
            if(!heap.isEmpty()){
                Edge temp = heap.poll();
                result.add(temp);
                if(cities.contains(temp.u)) current = temp.u;
                else current = temp.v;
                cities.remove(current);
            } 
        }
        return result;
    }

    public static void main(String[] args) {
        Scanner in = new Scanner(new BufferedReader(new InputStreamReader(System.in)));
        int n = in.nextInt();
        List<Edge> list = new ArrayList<>();
        for(int i = 0; i < n; ++i){
            String u = in.next();
            String v = in.next();
            int cost = in.nextInt();
            Edge temp = new Edge(u, v, cost);
            list.add(temp);
        }
        Solution s = new Solution();
        List<Edge> result = s.minCostEdge(list);
        for(int i = 0; i < result.size(); ++i){
            System.out.println(result.get(i).u + "  " + result.get(i).v + "  " + result.get(i).cost);
        }
        in.close();
    }
}