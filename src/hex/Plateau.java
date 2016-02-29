package hex;

import java.io.*;

public class Plateau {
	
	public int size;
	public int[][] array;
	public int a, b, win, next_player;
	
	public Plateau(int size){
		this.size = size;
		array = new int[size][size];
		a = 3;
		b = -3;
		win = 0;
		next_player = 1;
	}
	
	/* Constructor to clone a Plateau */
	public Plateau(Plateau p){
		this.size = p.size;
		this.a = p.a;
		this.b = p.b;
		this.next_player = p.next_player;
		this.array = new int[size][size];
		
		for(int i = 0; i<size; ++i){
			for(int j = 0; j<size; ++j){
				this.array[i][j] = p.array[i][j];
			}
		}
	}
	
	
	public boolean readState(String file){
		try {
			DataInputStream f = new DataInputStream(new FileInputStream(file));
			a = f.readInt();
			b = f.readInt();
			next_player = f.readInt();
			win = f.readInt();
			
			for(int i = 0; i<size; ++i){
				for(int j = 0; j<size; ++j){
					array[i][j] = f.readInt();
				}
			}
			
			f.close();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}
		return true;
	}
	
	
	public boolean writeState(String file){
		try {
			DataOutputStream f = new DataOutputStream(new FileOutputStream(file));
			f.writeInt(a);
			f.writeInt(b);
			f.writeInt(next_player);
			f.writeInt(win);
			
			for(int i = 0; i<size; ++i){
				for(int j = 0; j<size; ++j){
					f.writeInt(array[i][j]);
				}
			}
			
			f.close();
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}
	
	
	public boolean addMove(int line, int column, int player){
		//System.out.println("Move on (l,c): " + line + " " + column);
		
		if(array[line][column] != 0){
			return false;
		}
		
		if(player == 1){// blanc, donc de haut en bas
			if(line == 0){//bord haut
				array[line][column] = 1;
			}
			else if(line == (size-1)){//bord bas
				array[line][column] = 2;
			}
			else{
				array[line][column] = a;
				a++; //index of next connected component
			}
			
			if(checkWinBlanc(line, column)){
				//System.out.println("C'est gagné !!!!!!!!!");
				win = 1;
				return true;
				//throw new ExceptionWinBlanc();
			}
			
			//now propagation of the values
			int min = getMin(line, column);
			//System.out.println("Propagation with min: " + min);
			propagateBlanc(line, column, min);
			//System.out.println("After propagation:\n" + this);
		}
		else {// noir, donc de gauche a droite
			if(column == 0){//bord gauche
				array[line][column] = -1;
			}
			else if(column == (size-1)){//bord droit
				array[line][column] = -2;
			}
			else{
				array[line][column] = b;
				b--; //index of next connected component
			}
			
			if(checkWinNoir(line, column)){
				//System.out.println("C'est gagné !!!!!!!!!");
				win = -1;
				return true;
				//throw new ExceptionWinNoir();
			}
			
			//now propagation of the values
			int max = getMax(line, column);
			//System.out.println("Propagation with max: " + max);
			propagateNoir(line, column, max);
			//System.out.println("After propagation:\n" + this);
		}
		
		this.next_player = -player;
		return true;
	}
	
	private boolean checkWinBlanc(int line, int column){
		boolean flag1 = false, flag2 = false;
		
		if(line == 0){
			flag1 = true;
		}
		if(line == (size-1)){
			flag2 = true;
		}
		
		if(line > 0){
			if(array[line-1][column] == 1){
				flag1 = true;
			}
			else if(array[line-1][column] == 2){
				flag2 = true;
			}
			if(column < (size-1)){
				if(array[line-1][column+1] == 1){
					flag1 = true;
				}
				else if(array[line-1][column+1] == 2){
					flag2 = true;
				}
			}
		}
		
		if(line < (size-1)){
			if(array[line+1][column] == 1){
				flag1 = true;
			}
			else if(array[line+1][column] == 2){
				flag2 = true;
			}
			if(column > 0){
				if(array[line+1][column-1] == 1){
					flag1 = true;
				}
				else if(array[line+1][column-1] == 2){
					flag2 = true;
				}
			}
		}
		
		if(column > 0){
			if(array[line][column-1] == 1){
				flag1 = true;
			}
			else if(array[line][column-1] == 2){
				flag2 = true;
			}
		}
		
		if(column < (size-1)){
			if(array[line][column+1] == 1){
				flag1 = true;
			}
			else if(array[line][column+1] == 2){
				flag2 = true;
			}
		}
		
		
		return flag1 && flag2;
	}
	
	private int getMin(int line, int column){
		if(line == 0){
			return 1;
		}
		if(line == (size-1)){
			return 2;
		}
		
		int min = array[line][column];

		if(array[line-1][column] > 0 && array[line-1][column] < min)
			min = array[line-1][column];
		if(array[line+1][column] > 0 && array[line+1][column] < min)
			min = array[line+1][column];
		
		if(column > 0){
			if(array[line][column-1] > 0 && array[line][column-1] < min)
				min = array[line][column-1];
			if(array[line+1][column-1] > 0 && array[line+1][column-1] < min)
				min = array[line+1][column-1];
		}
		if(column < (size-1)){
			if(array[line][column+1] > 0 && array[line][column+1] < min)
				min = array[line][column+1];
			if(array[line-1][column+1] > 0 && array[line-1][column+1] < min)
				min = array[line-1][column+1];
		}
		
		return min;
	}
	
	private void propagateBlanc(int line, int column, int min){
		//TODO vérifier si on peut donner le min en argument
		array[line][column] = min;
		
		if(line > 0){
			if(array[line-1][column] > min){
				array[line-1][column] = min;
				propagateBlanc(line-1, column, min);
			}
			if(column < (size-1)){
				if(array[line-1][column+1] > min){
					array[line-1][column+1] = min;
					propagateBlanc(line-1, column+1, min);
				}
			}
		}
		
		if(line < (size-1)){
			if(array[line+1][column] > min){
				array[line+1][column] = min;
				propagateBlanc(line+1, column, min);
			}
			if(column > 0){
				if(array[line+1][column-1] > min){
					array[line+1][column-1] = min;
					propagateBlanc(line+1, column-1, min);
				}
			}
		}
		
		if(column > 0){
			if(array[line][column-1] > min){
				array[line][column-1] = min;
				propagateBlanc(line, column-1, min);
			}
		}
		
		if(column < (size-1)){
			if(array[line][column+1] > min){
				array[line][column+1] = min;
				propagateBlanc(line, column+1, min);
			}
		}
	}
	
	
	private boolean checkWinNoir(int line, int column){
		boolean flag1 = false, flag2 = false;
		
		if(column == 0){
			flag1 = true;
		}
		if(column == (size-1)){
			flag2 = true;
		}
		
		if(line > 0){
			if(array[line-1][column] == -1){
				flag1 = true;
			}
			else if(array[line-1][column] == -2){
				flag2 = true;
			}
			if(column < (size-1)){
				if(array[line-1][column+1] == -1){
					flag1 = true;
				}
				else if(array[line-1][column+1] == -2){
					flag2 = true;
				}
			}
		}
		
		if(line < (size-1)){
			if(array[line+1][column] == -1){
				flag1 = true;
			}
			else if(array[line+1][column] == -2){
				flag2 = true;
			}
			if(column > 0){
				if(array[line+1][column-1] == -1){
					flag1 = true;
				}
				else if(array[line+1][column-1] == -2){
					flag2 = true;
				}
			}
		}
		
		if(column > 0){
			if(array[line][column-1] == -1){
				flag1 = true;
			}
			else if(array[line][column-1] == -2){
				flag2 = true;
			}
		}
		
		if(column < (size-1)){
			if(array[line][column+1] == -1){
				flag1 = true;
			}
			else if(array[line][column+1] == -2){
				flag2 = true;
			}
		}
		
		
		return flag1 && flag2;
	}
	
	private int getMax(int line, int column){
		if(column == 0){
			return -1;
		}
		if(column == (size-1)){
			return -2;
		}
		
		int max = array[line][column];

		if(array[line][column-1] < 0 && array[line][column-1] > max)
			max = array[line][column-1];
		if(array[line][column+1] < 0 && array[line][column+1] > max)
			max = array[line][column+1];
		
		if(line > 0){
			if(array[line-1][column] < 0 && array[line-1][column] > max)
				max = array[line-1][column];
			if(array[line-1][column+1] < 0 && array[line-1][column+1] > max)
				max = array[line-1][column+1];
		}
		if(line < (size-1)){
			if(array[line+1][column] < 0 && array[line+1][column] > max)
				max = array[line+1][column];
			if(array[line+1][column-1] < 0 && array[line+1][column-1] > max)
				max = array[line+1][column-1];
		}
		
		return max;
	}
	
	private void propagateNoir(int line, int column, int max){
		//TODO vérifier si on peut donner le min en argument
		array[line][column] = max;
		
		if(line > 0){
			if(array[line-1][column] < max){
				array[line-1][column] = max;
				propagateNoir(line-1, column, max);
			}
			if(column < (size-1)){
				if(array[line-1][column+1] < max){
					array[line-1][column+1] = max;
					propagateNoir(line-1, column+1, max);
				}
			}
		}
		
		if(line < (size-1)){
			if(array[line+1][column] < max){
				array[line+1][column] = max;
				propagateNoir(line+1, column, max);
			}
			if(column > 0){
				if(array[line+1][column-1] < max){
					array[line+1][column-1] = max;
					propagateNoir(line+1, column-1, max);
				}
			}
		}
		
		if(column > 0){
			if(array[line][column-1] < max){
				array[line][column-1] = max;
				propagateNoir(line, column-1, max);
			}
		}
		
		if(column < (size-1)){
			if(array[line][column+1] < max){
				array[line][column+1] = max;
				propagateNoir(line, column+1, max);
			}
		}
	}
	
	
	public String toString(){
		String s = "";
		int i,j;
		for(i = 0; i<size; i++){
			for(j=0; j<i; j++){
				s += " ";
			}
			for(j = 0; j<size; j++){
				if(array[i][j] < 0){
					s += "-" + array[i][j] + " ";
				}
				else {
					s += " " + array[i][j] + " ";
				}
			}
			s += '\n';
		}
		return s;
	}

	public void reset(){
		array = new int[size][size];
		a = 3;
		b = -3;
		win = 0;
	}

	public int[][] getArray() {
		return array;
	}

}



