import java.util.*; 
import java.lang.*; 
import java.io.*; 
import java.util.LinkedList;
import java.util.ArrayList;

class MaxFlow{
	boolean bfs(int[][] rGraph, int s, int t, int[] path) {
		int n = rGraph.length;
		Queue<Integer> q = new LinkedList<>();
		boolean[] visited = new boolean[n];
		q.offer(s);
		while(!q.isEmpty()) {
			int size = q.size();
			for(int i = 0; i < size; i++) {
				int cur = q.poll();
				for(int v = 0; v < n; v++) {
					if(!visited[v] && rGraph[cur][v]>0) {
						q.offer(v);
						path[v] = cur;
					}					
				}
				visited[cur] = true; 
			}
		}
		return visited[t] == true;
	}
	
	int fordFulkerson(int[][] rGraph, int s, int t) {
		//initialize the flow to 0, and rGraph equals to graph
		int maxFlow = 0;
		int n = rGraph.length;
		//find a path in rGraph using bfs
		int[] path = new int[n];
		path[s] = -1;
		int pathFlow = Integer.MAX_VALUE;
		int cur;
		//Use bfs to find a path each time
		while(bfs(rGraph, s, t, path)){
			cur = t;
			while(path[cur] != -1) {
				pathFlow = Math.min(pathFlow, rGraph[path[cur]][cur]);
				cur = path[cur];
			}
			
			//update rGraph
			cur = t;
			while(path[cur] != -1) {
				rGraph[path[cur]][cur] -= pathFlow;
				rGraph[cur][path[cur]] += pathFlow;
				cur = path[cur];
			}
			maxFlow += pathFlow;
			pathFlow = Integer.MAX_VALUE;
		}
		return maxFlow;
	}	
	

	public static void main (String[] args) throws java.lang.Exception{
		String fileName = "16.in";
		FileReader fr = new FileReader(fileName);
		MaxFlow maxFlow = new MaxFlow();
		String input = "";
		int i;
		while((i=fr.read()) != -1){
			input += (char)(i);
		}
		fr.close();
		String[] lines = input.split("\n");
		int k = Integer.parseInt(lines[0]);
		int l = 1;
		String output = "";

		//add a timer here//
		long time = 0; 
		for(int j = 0; j < k; j++){
			String[] nm = lines[l++].split(" ");
			int n = Integer.parseInt(nm[0])+1;
			int m = Integer.parseInt(nm[1]);
			int[][] graph = new int[n][n];
			String[] st = lines[l++].split(" ");
			int s = Integer.parseInt(st[0]);
			int t = Integer.parseInt(st[1]);
			for(int index =0; index < m; index++) {
				String[] temp = lines[l++].split(" ");
				graph[Integer.parseInt(temp[0])][Integer.parseInt(temp[1])] = Integer.parseInt(temp[2]);
			}
			//calculate the time for each problem, and add them together
			long start = System.currentTimeMillis();
			output += maxFlow.fordFulkerson(graph, s, t) + "\n";
			long end = System.currentTimeMillis();
			time += end - start;
		}		
		System.out.print("The solution for " + fileName + " is ");
		System.out.print(output);
		System.out.print("Time: "+ time + "ms");
	}
}