package LoopTesting;

public class ForLoop {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		
		/*
		for (int i = 1 + 1; i <= 7; i++) {
			
			System.out.println("i = " + " " + i);
			
		}
		*/
		
		int row = 1;
		int col = 1;
		
		while(col < 7 && row < 7){
			
			row++;
			col++;
			
			
			System.out.println( " row = " + " " + row + " col = " + " " + col);
			
		}
		
		
		

	}

}
