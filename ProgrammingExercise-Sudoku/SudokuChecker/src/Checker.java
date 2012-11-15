import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.TreeSet;


public class Checker {
	/**
	 * @param args
	 * @throws Exception 
	 */
	public static void main(String[] args) throws Exception {
		Exception NoInputException = new Exception("Please Input a Single File to Read");
		if (args.length != 1){
			throw NoInputException;
		}
		Checker c = new Checker();
		ArrayList<ArrayList<Integer>> solution = c.parseSolutionFile(args[0]);
		int n = c.getN(solution);
		
		boolean valid = c.checkSets(solution, n);
		if (valid){
			System.out.println("Valid Sudoku Plus Solution");
		}
		else {
			System.out.println("Invalid Sudoku Plus Solution");
		}
		
	}
	// build solution array and check to make sure square is valid
	// alert user if there is an extra or missing number
	// alert user if their board is not the size of a perfect square
	public ArrayList<ArrayList<Integer>> parseSolutionFile(String f) throws Exception{
		Exception MissingInputException = new Exception("Inputted File is missing or contains an extra entry.");
		Exception ArrayIsNotAPerfectSquareException = new Exception("Inputted Array is Not the Dimension of a Perfect Square.");
		String file = f;
		BufferedReader in = new BufferedReader(new FileReader(file));
		String line;
		int index = 0;
		int n = 0;
		int nprev;
		ArrayList<ArrayList<Integer>> solution = new ArrayList<ArrayList<Integer>>();
		ArrayList<Integer> temp = new ArrayList<Integer>();
		
		while ((line = in.readLine()) != null){
			String[] row = line.split(",");
			if (index != 0){
				nprev = n;
				n = row.length;
				//make sure rows are the same length
				if (n != nprev){
					in.close();
					throw MissingInputException;
				}
			}
			else{
				n = row.length;
				// check if n is a perfect square
				double s = Math.sqrt(n);
				if (Math.floor(s) != s){
					in.close();
					throw ArrayIsNotAPerfectSquareException;
				}
			}
			temp = new ArrayList<Integer>();
			for (int i = 0; i < n; i++){
				temp.add(Integer.parseInt(row[i]));
			}
			solution.add(temp);
			++index;
		}
		//make sure there are n rows
		if (index != n){
			in.close();
			throw MissingInputException;
		}
		in.close();
		return solution;
	}
	
	// get size of valid sudoku board
	public int getN(ArrayList<ArrayList<Integer>> sol){
		return sol.size();
	}
	
	// build row, column, and square units from solution and check for validity
	public boolean checkSets(ArrayList<ArrayList<Integer>>sol, int n){
				TreeSet<Integer> ts = new TreeSet<Integer>();
				TreeSet<Integer> perfectSet = new TreeSet<Integer>();
				for (int i = 1; i<n+1; i++){
					perfectSet.add(i);
				}
				// check all rows
				for (int row = 0; row < n; row++){
					ts = new TreeSet<Integer>();
					for (int col = 0; col < n; col++){
						ts.add(sol.get(row).get(col));
					}
					if (!ts.equals(perfectSet)){
						return false;
					}
				}
				// check all columns
				for (int col = 0; col < n; col++){
					ts = new TreeSet<Integer>();
					for (int row = 0; row < n; row++){
						ts.add(sol.get(row).get(col));
					}
					if (!ts.equals(perfectSet)){
						return false;
					}
				}
				// check all squares
				int s = (int) Math.sqrt(n);
				for (int bigx = 0; bigx < n; bigx += s){
					for (int bigy = 0; bigy < n; bigy += s){
						ts = new TreeSet<Integer>();
						for (int x = 0; x < s; x++){
							for (int y = 0; y < s; y++){
								ts.add(sol.get(bigx+x).get(bigy+y));
							}
						}
						if (!ts.equals(perfectSet)){
							return false;
						}
					}
				}
				return true;
	}

}
