import java.util.*; 
import java.lang.*; 
import java.io.*; 
import java.util.LinkedList; 
import java.util.ArrayList;

class Simplex{
	public static String firstPhase(double[][] A){
		int m = A.length;
		int n = A[0].length - m - 1;
		int length = A[0].length;
		A[0][0] = 1;
		for(int i = n+1; i < length-1; i++){ //Creating problem P*
			A[0][i] = -1;
			A[i-n][i] = 1;
			for(int j = 0; j < length; j++){
				A[0][j] += A[i-n][j];
			}
		}

		String res = "";
		
		while(!isOptimal(A)){ //Optimality test
			int s = 0;
			double co = 0;
			for(int i = 1; i < length-1; i++){
				if(A[0][i] > co){
					co = A[0][i];
					s = i;
				}
			}
			if(isUnbounded(A, s)) return "INFEASIBLE"; //Unboundedness test

			//Ratio Test
			int row = 0;
			double ratio = Double.MAX_VALUE;
			for(int i = 1; i < m; i++){
				if(A[i][s] > 0 && A[i][length-1]/A[i][s] < ratio){
					row = i;
					ratio = A[i][length-1]/A[i][s];
				}
			}
			double temp = A[row][s];
			for(int i = 0; i < length; i++){
				A[row][i] /= temp;
			}
			for(int i = 0; i < m; i++){
				if(i != row){
					double ra = A[i][s];
					for(int j = 0; j < length; j++){
						A[i][j] -= ra*A[row][j];
					}
				}
			}
		}
		// Is the optimal value equal to zero? 
		if(A[0][length-1] > 0.00001) return "INFEASIBLE"; //Due to the accuracy, the number may not equal exactly to 0. I think 0.00001 is accurate enough.
		else{
			for(int j = 1; j < length -1; j++){
				if(A[0][j] == 0){
					int count = 0;
					boolean flag = true;
					for(int i = 1; i < m; i++){
						if(A[i][j] != 0 && A[i][j] != 1.0) flag = false;
						if(A[i][j] == 1) count++;
					}
					if(flag && count == 1) res += " "+ j;
				}				
			}
			return res.substring(1);
		}
	}

	public static boolean isOptimal(double[][] A){
		for(int i = 1; i < A[0].length - 1; i++){
			if(A[0][i] > 0.00001) return false; //Due to the accuracy, the number may not equal exactly to 0. I think 0.00001 is accurate enough.
		}
		return true;
	}

	public static boolean isUnbounded(double[][] A, int s){ 
		for(int i = 1; i < A.length; i++){
			if(A[i][s] > 0.00001) return false; //Due to the accuracy, the number may not equal exactly to 0. I think 0.00001 is accurate enough.
		}
		return true;
	}

	public static void main (String[] args) throws java.lang.Exception{
	Scanner in = new Scanner(new BufferedReader(new InputStreamReader(System.in)));
	String fileName = in.next();
	FileReader fr = new FileReader(fileName);
	
	String input = "";
	int i;
	while((i=fr.read()) != -1){
		input += (char)(i);
	}
	fr.close();
	String[] lines = input.split("\r\n");  //It is not "\n"
	int k = Integer.parseInt(lines[0]);
	int l = 1;
	String output = "";

	//add a timer here//
	long time = 0; 
	for(int j = 0; j < k; j++){
		String[] nm = lines[l++].split(" ");
		int n = Integer.parseInt(nm[0])+1; //The first column is for z. And the index for variables starts from 1.
		int m = Integer.parseInt(nm[1]); 
		double[][] A = new double[m+1][n+m+1]; //Add z-row and m slack variables
		for(int index = 1; index < m+1; index++) {
			String[] temp = lines[l++].split(" ");
			for(int x = 1; x < n; x++){ 
				A[index][x] = Double.parseDouble(temp[x-1]);
			}
			A[index][n+m] = Double.parseDouble(temp[n-1]); //The last column is for B
		}
		//calculate the time for each problem, and add them together
		long start = System.currentTimeMillis();
		output += firstPhase(A)+ "\n"; 
		long end = System.currentTimeMillis();
		time += end - start;
	}
	
	System.out.print("The solution for " + fileName + " is \n");
	System.out.print(output);
	System.out.print("Time: "+ time + "ms"+"\n");
	}
}