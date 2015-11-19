import java.awt.Point;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.TreeMap;

/*Author: Griffin Ciluffo
  Date: November 18th 2015
  Description: Direction graph implementation */

public class Digraph {


	private final static Map<Vertex, ArrayList<Vertex>> mGraph = new LinkedHashMap<Vertex, ArrayList<Vertex>>();
	private ArrayList<Vertex> storage;
	public final static boolean reverseMode = false;

	public Digraph(){

		storage = new ArrayList<Vertex>();
		FileReader reader = null;
		Scanner scan = null;


		try {
			reader = new FileReader("input.txt");
			scan = new Scanner(reader);
			scan.nextLine();

			while(scan.hasNextLine()){

				//read in unflipped nodes
				int row = scan.nextInt();
				int col = scan.nextInt();
				char color = scan.next().charAt(0);
				String circle = scan.next();
				String direction = scan.nextLine().trim();


				//add a unflipped node to the graph
				Vertex v1 = new Vertex(row, col, color, circle, direction);
				addNode(v1);
				storage.add(v1);

				//add a flipped node to the graph
				direction = flipDirection(direction);
				Vertex v2 = new Vertex(row, col, color, circle, direction);
				addNode(v2);

			}


		} catch (FileNotFoundException e) {
			System.out.println(e.getLocalizedMessage());
		}


		//add the edges for each vertex
		for(Map.Entry<Vertex,ArrayList<Vertex>> entry : mGraph.entrySet()) {
			Vertex key = entry.getKey();
			System.out.println(key);
			ArrayList<Vertex> temp2  = createAdj(key);
			
			//System.out.println(temp2);
			for(Vertex i: temp2){

				addEdge(key, i);
			}
		}

		
		//printMap();


	}


	public void addEdge(Vertex start, Vertex dest) {
		/* Confirm both endpoints exist. */
		if (!mGraph.containsKey(start) || !mGraph.containsKey(dest)){
			System.out.println("Both nodes must be in graph");
			System.exit(0);}

		/* Add the edge. */
		mGraph.get(start).add(dest);
	}

	public boolean addNode(Vertex v) {
		/* If the node already exists, don't do anything. */
		if (mGraph.containsKey(v))
			return false;

		/* Otherwise, add the node with an empty set of outgoing edges. */
		mGraph.put(v, new ArrayList<Vertex>());
		return true;
	}


	//method that returns list of adjcencies for nodes
	public ArrayList<Vertex> createAdj(Vertex v){

		ArrayList<Vertex> temp = new ArrayList<Vertex>();
		char oppositeColor = flipColor(v.color);

		//Nodes that point south
		if(v.direction == "S"){
			if(v.row != 7){

				for(Vertex i: storage){

					if(i.col == v.col && i.row > v.row && i.color == oppositeColor){

						temp.add(i);

					}

				}
			}
		}


		//node that point North
		if(v.direction == "N"){
			if(v.row != 1){

				for(Vertex i: storage){

					if(i.col == v.col && i.row < v.row && i.color == oppositeColor){

						temp.add(i);

					}

				}
			}
		}


		//node that point West
		if(v.direction == "W"){
			if(v.col != 1){

				for(Vertex i: storage){

					if(i.row == v.row && i.col < v.col && i.color == oppositeColor){

						temp.add(i);

					}

				}
			}
		}


		//node that point East
		if(v.direction == "E"){
			if(v.col != 7){

				for(Vertex i: storage){

					if(i.row == v.row && i.col > v.col && i.color == oppositeColor){

						temp.add(i);

					}

				}
			}
		}

		//node that points southeast
		if(v.direction == "SE"){

			if(v.row != 7 ||v.col != 7){

				int tempRow = v.row;
				int tempColumn = v.col;

				while(tempRow < 7 && tempColumn < 7){

					tempRow++;
					tempColumn++;

					for(Vertex i: storage){

						if(i.row == tempRow && i.col == tempColumn && i.color == oppositeColor)
							temp.add(i);

					}
				}

			}

		}


		//node that points northeast
		if(v.direction == "NE"){

			if(v.row != 1 ||v.col != 7){

				int tempRow = v.row;
				int tempColumn = v.col;

				while(tempRow != 1 && tempColumn != 7){

					tempRow--;
					tempColumn++;

					for(Vertex i: storage){

						if(i.row == tempRow && i.col == tempColumn && i.color == oppositeColor)
							temp.add(i);

					}
				}

			}

		}


		//node that points southwest
		if(v.direction == "SW"){

			if(v.row != 7 ||v.col != 1){

				int tempRow = v.row;
				int tempColumn = v.col;

				while(tempRow != 7 && tempColumn != 1){

					tempRow++;
					tempColumn--;

					for(Vertex i: storage){

						if(i.row == tempRow && i.col == tempColumn && i.color == oppositeColor)
							temp.add(i);

					}
				}

			}

		}


		//node that points northwest
		if(v.direction == "NW"){

			if(v.row != 1 ||v.col != 1){

				int tempRow = v.row;
				int tempColumn = v.col;

				while(tempRow > 1 && tempColumn > 1){

					tempRow--;
					tempColumn--;

					for(Vertex i: storage){

						if(i.row == tempRow && i.col == tempColumn && i.color == oppositeColor)
							temp.add(i);

					}
				}

			}

		}

		return temp;
	}


	public static String flipDirection(String d){

		String flip = null;


		if(d.equals("N"))
			flip = "S";
		if(d.equals("S"))
			flip = "N";
		if(d.equals("E"))
			flip = "W";
		if(d.equals("W"))
			flip = "E";
		if(d.equals("NW"))
			flip = "SE";
		if(d.equals("SE"))
			flip = "NW";
		if(d.equals("NE"))
			flip = "SW";
		if(d.equals("SW"))
			flip = "NE";


		return flip;
	}

	public char flipColor(char c){

		if(c == 'R')
			c = 'B';
		else
			c = 'R';


		return c;
	}


	public void printMap() {
		for(Map.Entry<Vertex,ArrayList<Vertex>> entry : mGraph.entrySet()) {
			Vertex key = entry.getKey();
			ArrayList<Vertex> value = entry.getValue();

			System.out.println(key + " => " + value);
		}
	}


	public static void main(String[] args) {

		Digraph theG = new Digraph();
		/*
		Vertex test = new Vertex(1, 1, 'R', "N", "E");
		Vertex test2 = new Vertex(1, 5, 'R', "N", "S");
		ArrayList<Vertex> output = theG.createAdj(test);
		ArrayList<Vertex> output2 = theG.createAdj(test2);
		System.out.println(output);
		System.out.println(output2);
		*/
	}
	
}
		


