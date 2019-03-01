import java.util.*; 
import java.lang.*; 
import java.io.*; 
import java.util.LinkedList; 
import java.util.ArrayList;

class MaxFlow{
	//define Vertex class to simplify the work 
	class Vertex{
		int index;
		int label;
		int access;
		public Vertex(){
			
		}
		public Vertex(int i){
			index = i;
			label = 0;
			access = 0;
		}
	}
	
	int pushRelabel(int[][] rGraph, int s, int t){
		int maxFlow = 0;
		//initialize the graph with the default value
		int n = rGraph.length;
		int[][] flow = new int[n][n];
		
		Vertex[] vertices= new Vertex[n];
		
		for(int i = 1; i < n; i++) {
			vertices[i]= new Vertex(i);
			if(rGraph[s][i] > 0) {
				flow[s][i] = rGraph[s][i];
				flow[i][s] = -rGraph[s][i];
				rGraph[s][i] = 0;
				rGraph[i][s] = flow[s][i];
			}
		}
		vertices[s].label = n-1; //number of vertice 
		
		//The time complexity is O(n^2*m), and we can optimize the running time to O(n^2* 1/2(m)) by picking the largest value **l** each time.
		//To calculate a, a(u) = all flow from v into u
		
		PriorityQueue<Vertex> heap = new PriorityQueue<>(new Comparator<Vertex>() {
			@Override
			public int compare(Vertex a, Vertex b) {
				return b.label - a.label;
			}
		}); //Try priority queue to find a 
			
		for(int u = 1; u < n; u++) {
			for(int v = 1; v < n; v++) {
				vertices[u].access += flow[v][u];			
			}
			if(vertices[u].access > 0 && u != t) heap.add(vertices[u]);
		}
		
		//while a(u) > 0, while exists active vertex
		
		while(!heap.isEmpty()) {
			int u = heap.poll().index; 
			boolean pushed = false;
			for(int v = 0; v < n; v++) {
				if(rGraph[u][v] > 0) {
					if(vertices[u].label == vertices[v].label + 1) {
						//PUSH(u,v)
						int delta = Math.min(vertices[u].access, rGraph[u][v]);
						flow[u][v] += delta;
						flow[v][u] -= delta;
						
						rGraph[u][v] -= delta;
						rGraph[v][u] += delta;
						
						vertices[u].access -= delta;
						vertices[v].access += delta;

						if(vertices[u].access > 0) heap.add(vertices[u]);
						if(vertices[v].access > 0 && v != t) heap.add(vertices[v]);
						
						pushed = true;
						break;
					}
				}
			}
			if(!pushed) {
				//relabel(u)
				int minLabel = Integer.MAX_VALUE;
				for(int v = 1; v < n; v++) {
					if(rGraph[u][v] > 0) minLabel = Math.min(minLabel, vertices[v].label);
				}
				vertices[u].label = 1 + minLabel;
				heap.add(vertices[u]);
			}
		}
		
		//When algorithm terminates, flow is maxflow
		for(int v = 1; v < n; v++) {
			maxFlow += flow[v][t];
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
			int n = Integer.parseInt(nm[0])+1; //Because vertex in the input starts from 1 instead of 0!
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
			output += maxFlow.pushRelabel(graph, s, t) + "\n";
			long end = System.currentTimeMillis();
			time += end - start;
		}		
		
		System.out.print("The solution for " + fileName + " is ");
		System.out.print(output);
		System.out.print("Time: "+ time + "ms");
	}
}