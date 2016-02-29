package hex;

public class Cell{
	public int l, c;
	
	public Cell(int line, int column){
		this.l = line;
		this.c = column;
	}
	
	public String toString(){
		return (char)(c+65) + "" + (l+1);
	}
	
	
}
