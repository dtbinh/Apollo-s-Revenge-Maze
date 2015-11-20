import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Deque;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.Queue;
import java.util.Scanner;

/*Author: Griffin Ciluffo
  Date: November 18th 2015
  Description: Direction graph implementation */

public class Digraph {


	private final static LinkedHashMap<Vertex, ArrayList<Vertex>> mGraph = new LinkedHashMap<Vertex, ArrayList<Vertex>>();
	private static ArrayList<Vertex> storage;
	public static boolean reverseMode = false;
	public static boolean reset = false;
	private static Vertex temp;
	private static Vertex nextCircledNode;
	private static Queue<Vertex> myQ = new LinkedList<Vertex>();
	private static boolean containsCircleNode = false;
	


	public Digraph(){

		storage = new ArrayList<Vertex>();
		FileReader reader = null;
		Scanner scan = null;


		try {
			reader = new FileReader("input.txt");
			scan = new Scanner(reader);
			scan.nextLine();
			boolean visited = false;
			while(scan.hasNextLine()){

				//read in unflipped nodes
				int row = scan.nextInt();
				int col = scan.nextInt();
				char color = scan.next().charAt(0);
				String circle = scan.next();
				String direction = scan.nextLine().trim();


				//add a unflipped node to the graph
				Vertex v1 = new Vertex(row, col, color, circle, direction, visited);
				addNode(v1);
				storage.add(v1);
				//keep track of circled nodes


				//add a flipped node to the graph
				direction = flipDirection(direction);
				Vertex v2 = new Vertex(row, col, color, circle, direction, visited);
				addNode(v2);

			}


		} catch (FileNotFoundException e) {
			System.out.println(e.getLocalizedMessage());
		}


		//add the edges for each vertex
		for(Map.Entry<Vertex,ArrayList<Vertex>> entry : mGraph.entrySet()) {
			Vertex key = entry.getKey();
			//System.out.println(key);
			ArrayList<Vertex> temp2  = createAdj(key);

			//System.out.println(temp2);
			for(Vertex i: temp2){

				addEdge(key, i);
			}
		}

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
		if(v.direction != null && v.direction.equals("S")){
			if(v.row != 7){

				for(Vertex i: storage){

					if(i.col == v.col && i.row > v.row && i.color == oppositeColor){

						temp.add(i);

					}

				}
			}
		}


		//node that point North
		if(v.direction != null && v.direction.equals("N")){
			if(v.row != 1){

				for(Vertex i: storage){

					if(i.col == v.col && i.row < v.row && i.color == oppositeColor){

						temp.add(i);

					}

				}
			}
		}


		//node that point West
		if(v.direction != null && v.direction.equals("W")){
			if(v.col != 1){

				for(Vertex i: storage){

					if(i.row == v.row && i.col < v.col && i.color == oppositeColor){

						temp.add(i);

					}

				}
			}
		}


		//node that point East
		if(v.direction != null && v.direction.equals("E")){
			if(v.col != 7){

				for(Vertex i: storage){

					if(i.row == v.row && i.col > v.col && i.color == oppositeColor){

						temp.add(i);

					}

				}
			}
		}

		//node that points southeast
		if(v.direction != null && v.direction.equals("SE")){

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
		if(v.direction != null && v.direction.equals("NE")){

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
		if(v.direction != null && v.direction.equals("SW")){

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
		if(v.direction != null && v.direction.equals("NW")){

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


	public static void printMap() {
		for(Map.Entry<Vertex,ArrayList<Vertex>> entry : mGraph.entrySet()) {
			Vertex key = entry.getKey();
			ArrayList<Vertex> value = entry.getValue();

			System.out.println(key + " => " + value);
		}
	}


	public static Vertex getFlippedNode(Vertex v){

		//record position of unflipped node
		int pos = new ArrayList<Vertex>(mGraph.keySet()).indexOf(v);
		//get list of all key nodes
		ArrayList<Vertex> keys = new ArrayList<Vertex>(mGraph.keySet());
		Vertex flipped = keys.get(pos + 1);



		return flipped;
	}

	public static void engageReverseMode(){

		if(reverseMode == false){
			reverseMode = true;
			reset = true;
		}
		else 
			reverseMode = false;

	}

	
	public static void BFS(Vertex root) {

		myQ.add(root);
		//traditional bfs goes until queue is empty, but search will end until the target it reached (7,7)
		while(true) {
			Vertex destination = storage.get(storage.size()-1);
			Vertex current = myQ.remove();
			current.visited = true;
			//System.out.println("(" + current.row + ", " + current.col + ")" + " " + current.direction);
			//System.out.println(myQ);
			//if current node is a circle
			if(current.isCircle != null && current.isCircle.equals("C"))
				engageReverseMode();
			if((current.row == 7 && current.col == 7) || myQ.contains(destination)) return;

			//going in opposite direction 
			if(reverseMode){

				//get flipped vertex
				current = getFlippedNode(current);
				System.out.println("(" + current.row + ", " + current.col + ")" + " " + current.direction);
				ArrayList<Vertex> neighbors = mGraph.get(current);
				//reset the queue so that only flipped arrows are contained in it

				//check if there is a circle arrow in the adj list, since we are clearing queues when is there a switch
				for(Vertex i: neighbors) if(i.isCircle.equals("C")) containsCircleNode = true;

				//if the current node is circle and there are no circles in the queue reset the queue
				if(current.isCircle.equals("C") && !containsCircleNode){

					//System.out.println(myQ);
					temp = myQ.remove();
					myQ.clear();

				}

				//else if the current node is a circle and the queue contains another circle, then wipe the queue and re add the circle
				if(current.isCircle.equals("C") && containsCircleNode){
					myQ.clear();
					myQ.add(nextCircledNode);
					containsCircleNode = false;
				}

				//in some cases the adj list will return an empty list, so revert back to the last point that was visited
				if(neighbors.isEmpty())
					myQ.add(temp);

			

				//add adj's to queue
				for (Vertex neighbor : neighbors) {
					if (!neighbor.visited) 
						myQ.add(neighbor);	
					if(neighbor.isCircle.equals("C"))
						nextCircledNode = neighbor;
				}
			}
			//going in regular direction
			else{
				//print the current node and its direction
				System.out.println("(" + current.row + ", " + current.col + ")" + " " + current.direction);
				ArrayList<Vertex> neighbors = mGraph.get(current);
				if(current.isCircle.equals("C")){

					//temp = myQ.remove();
					myQ.clear();
					reset = false;
				}
				//add the last visited point if there are no points contained in 
				else if(neighbors.isEmpty()){

					myQ.add(temp);

				}

				//add adj's tp queue
				for (Vertex neighbor : neighbors) {
					if (!neighbor.visited) 
						myQ.add(neighbor);	 
					if(neighbor.isCircle.equals("C"))
						nextCircledNode = neighbor;
				}

				System.out.println(myQ);
			}

		}
	}

	public static void main(String[] args) {

		Digraph theG = new Digraph();
		Vertex root = theG.storage.get(0);
		BFS(root);
	}

}



