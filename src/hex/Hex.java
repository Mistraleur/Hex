package hex;

public class Hex {
	
	static void print_cell(int column, int line){
		char c = (char)(65+column);
		System.out.println(c + "" + (line+1) + " " + column + "" + line);
	}

}
