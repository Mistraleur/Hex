package hex;

import java.util.ArrayList;

public class Algo {
	
	public int lmin,cmin,lmax,cmax;
	
	public Algo(){	
		lmin = -1;
	}
	
	public void findWin(Plateau p, int player){
		ArrayList<Cell> list = new ArrayList<Cell>();
		
		System.out.println("Winning moves for player " + (player>0 ? "Blanc" : "Noir") + "between " + (char)(cmin+65) + "" + (lmin+1) + " and " + (char)(cmax+65) + "" + (lmax+1) );
		
		for(int i = lmin; i<=lmax;++i){
			System.out.println("trying line " + (i+1));
			for(int j = cmin; j<=cmax;++j){
				if(isWinning(new Plateau(p), player, i, j, lmin, cmin, lmax, cmax)){
					list.add(new Cell(i,j));
					System.out.println((char)(j+65) + "" + (i+1));
				}
			}
		}
		System.out.println("There are " + list.size() + " winning moves");
	}
	
	
	public static ArrayList<Cell> findWin(Plateau p, int player, int lmin, int cmin, int lmax, int cmax){
		ArrayList<Cell> list = new ArrayList<Cell>();
		
		System.out.println("Winning moves for player " + (player>0 ? "Blanc" : "Noir") );
		
		for(int i = lmin; i<=lmax;++i){
			System.out.println("trying line " + i);
			for(int j = cmin; j<=cmax;++j){
				if(isWinning(new Plateau(p), player, i, j, lmin, cmin, lmax, cmax)){
					list.add(new Cell(i,j));
					System.out.println((char)(j+65) + "" + (i+1));
				}
			}
		}
		
		return list;
	}
	
	// Explores all possible moves in the rectangle between (lmin,cmin) and (lmax,cmax)
	// to detect if the move (line,column) is winning for given player
	
	// ATTENTION The object Plateau p has to be a clone before entering this function
	
	public static boolean isWinning(Plateau p, int player, int line, int column, int lmin, int cmin, int lmax, int cmax){
		p.addMove(line, column, player);
			
		if(p.win == player){
			return true;
		}
		//if(player==1){
		// Pour tous les coups de l'opposant dans le rectangle, teste si la position reste gagnante pour le joueur
			for(int i = lmin; i<=lmax; ++i){
				for (int j = cmin; j<=cmax; ++j){
					if(p.array[i][j] == 0){
						if(isWinning(new Plateau(p), -player, i, j, lmin, cmin, lmax, cmax)){
							return false;
						}
					}
				}
			}
		/*}
		else{
			for (int j = cmin; j<=cmax; ++j){
				for(int i = lmin; i<=lmax; ++i){
					if(p.array[i][j] == 0){
						if(isWinning(new Plateau(p), -player, i, j, lmin, cmin, lmax, cmax)){
							return false;
						}
					}
				}
			}
		}
		*/
		return true;
	}
	
}
