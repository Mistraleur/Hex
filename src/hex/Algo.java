package hex;

import java.util.ArrayList;

public class Algo {
	
	public int lmin,cmin,lmax,cmax;
	
	public Algo(){	
		lmin = -1;
	}
	
	
	public void findWinThreaded(Plateau p, int player){
		System.out.println("Threaded winning moves for player " + (player>0 ? "Blanc" : "Noir") + " between " + (char)(cmin+65) + "" + (lmin+1) + " and " + (char)(cmax+65) + "" + (lmax+1) );
		int n = 0;
		ThreadAlgo[] t = new ThreadAlgo[(lmax-lmin+1)];
		
		for(int i = lmin; i<=lmax;++i){
			t[i-lmin] = new ThreadAlgo(lmin, cmin, lmax, cmax, player, i, new Plateau(p));
			System.out.println("Starting thread for line " + (i+1));
			t[i-lmin].start();
			/*for(int j = cmin; j<=cmax;++j){
				if(isWinning(new Plateau(p), player, i, j, lmin, cmin, lmax, cmax)){
					System.out.println((char)(j+65) + "" + (i+1));
				}
			}*/
			
		}
		for(int i=lmin; i<=lmax; ++i){
			try {
				t[i-lmin].join();
				n += t[i-lmin].n;
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		System.out.println("All threads terminated and found " + n + " moves");
		//System.out.println("There are " + n + " winning moves");
	}
	
	public void findWin(Plateau p, int player){
		System.out.println("Winning moves for player " + (player>0 ? "Blanc" : "Noir") + " between " + (char)(cmin+65) + "" + (lmin+1) + " and " + (char)(cmax+65) + "" + (lmax+1) );
		int n = 0;
		for(int i = lmin; i<=lmax;++i){
			System.out.println("trying line " + (i+1));
			for(int j = cmin; j<=cmax;++j){
				if(isWinning(new Plateau(p), player, i, j, lmin, cmin, lmax, cmax)){
					System.out.println((char)(j+65) + "" + (i+1));
					n++;
				}
			}
		}
		System.out.println("There are " + n + " winning moves");
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
	
	
	private class ThreadAlgo extends Thread{
		private Plateau p;
		private int lmin, cmin, lmax, cmax;
		private int index, player, n;
		
		public ThreadAlgo(int lmin, int cmin, int lmax, int cmax, int player, int index, Plateau p){
			this.p = p;
			this.lmin = lmin;
			this.lmax = lmax;
			this.cmin = cmin;
			this.cmax = cmax;
			this.player = player;
			this.index = index;
			this.n = 0;
		}
		
		public void run() {
			for(int j = cmin; j<=cmax;++j){
				if(isWinning(new Plateau(p), player, index, j, lmin, cmin, lmax, cmax)){
					System.out.println((char)(j+65) + "" + (index+1));
					n++;
				}
			}
		}
		
	}
	
}
