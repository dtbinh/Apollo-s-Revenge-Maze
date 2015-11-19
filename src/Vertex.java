
public class Vertex  {
	
	//name of vertex and a pointer to the first node in its adj linked list
	
	public int row;
	public int col;
	public char color;
	public String direction;
	public String isCircle;
	
	
	public Vertex(int row, int col, char color, String isCircle, String direction) {
		super();
		this.row = row;
		this.col = col;
		this.color = color;
		this.isCircle = isCircle;
		this.direction = direction;
	}


	@Override
	public String toString() {
		return "[row=" + row + ", col=" + col + ", color=" + color
				+ ", direction=" + direction + ", isCircle=" + isCircle + "]";
	}
}
