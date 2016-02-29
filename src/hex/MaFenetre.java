package hex;

import java.awt.Font;
import java.awt.event.*;
import java.util.Scanner;
import java.util.ArrayList;

import javax.swing.*;


public class MaFenetre extends JFrame {
	
	private JPanel panneau;
	private JLabel board, t1;
	private JButton findWin, isWinBlanc, isWinNoir, again, save, read;
	private JJeton last;
	private int column, line, size;
	private int player = 1;
	private boolean locked, algo;
	private Plateau p;
	private Algo a;
	
	private ButtonListener bt;
	
	public MaFenetre(int size){
		super();
		this.setLocation(0, 0);
		this.setResizable(false);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		this.size = size;
		this.p = new Plateau(size);
		this.locked = false;
		this.algo = false;
		
		this.bt = new ButtonListener();
		
		panneau = new JPanel();
		panneau.setLayout(null);
		
		board = new JLabel();
		board.addMouseListener(new damier_listener());
		
		t1 = new JLabel("C'est à Blanc de jouer");
		
		addButtons();
		setBounds();
		
		save.setEnabled(false);
		
		panneau.add(t1);
		panneau.add(board);
		
		this.add(panneau);
		this.setVisible(true);
	}
	
	private void printWin(){
		t1.setText((p.win < 0) ? "  Noir a gagné !" : "  Blanc a gagné !");
	}
	
	private void addButtons(){
		again = new JButton("Play again?");
		again.addActionListener(bt);
		
		save = new JButton("Save state !");
		save.addActionListener(bt);
		
		read = new JButton("Load board !");
		read.addActionListener(bt);
		
		/*undo = new JButton("Undo move ?");
		undo.setBounds(20, 450, 200, 40);
		undo.addActionListener(bt);
		*/
		findWin = new JButton("FindWin ?");
		findWin.setBounds(20, 500, 200, 40);
		findWin.addActionListener(bt);
		
		isWinBlanc = new JButton("IsWinBlanc ?");
		isWinBlanc.setBounds(20, 550, 200, 40);
		isWinBlanc.addActionListener(bt);
		
		isWinNoir = new JButton("IsWinNoir ?");
		isWinNoir.setBounds(20, 600, 200, 40);
		isWinNoir.addActionListener(bt);
		
		panneau.add(again);
		panneau.add(read);
		panneau.add(save);
		//panneau.add(undo);
		panneau.add(findWin);
		panneau.add(isWinBlanc);
		panneau.add(isWinNoir);
	}
	
	private void setBounds(){
		if(size == 9){
			this.setSize(1017,693);
			board.setIcon(new ImageIcon("files/Plateau9.png"));
			board.setBounds(0, 0, 997, 673);
			t1.setBounds(710, 10, 400, 90);
			again.setBounds(790, 80, 200, 40);
			read.setBounds(790, 120, 200, 40);
			save.setBounds(790, 160, 200, 40);
		}
		else if(size == 11){
			this.setSize(1212,802);
			board.setIcon(new ImageIcon("files/Plateau11.png"));
			board.setBounds(0, 0, 1192, 782);
			t1.setBounds(845, 10, 400, 90);
			again.setBounds(940, 80, 200, 40);
			read.setBounds(940, 120, 200, 40);
			save.setBounds(940, 160, 200, 40);
		}
	}
	
	
	private class ButtonListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent ev) {
			if (ev.getSource() == again) {
				board.removeAll();
				t1.setText("C'est à Blanc de jouer");
				save.setEnabled(false);
				repaint();
				p.reset();
			} else if (ev.getSource() == save) {
				p.writeState("state_board_" + size + ".txt");
				save.setEnabled(false);
			} else if (ev.getSource() == read) {
				p.readState("state_board_" + size + ".txt");
				player = p.next_player;
				
				board.setVisible(false);
				board.removeAll();
				
				int[][] a = p.getArray();
				for(int i=0;i<size;++i){
					for(int j=0;j<size;++j){
						if(a[i][j] < 0){
							board.add(new JJeton(j, i, -1));
						}
						if(a[i][j] > 0){
							board.add(new JJeton(j, i, 1));
						}
					}
				}
				
				
				if(p.next_player < 0){
					t1.setText("C'est à Noir de jouer");
				}
				else{
					t1.setText("C'est à Blanc de jouer");
				}
				
				panneau.add(board);
				repaint();
				board.setVisible(true);
				save.setEnabled(false);
			}
			/*else if (ev.getSource() == undo){
				player = -player;
				t1.setText((player < 0) ? "C'est à Noir de jouer" : "C'est à Blanc de jouer");
				board.remove(last);
				repaint();
				//board.remove((board.getSize().width);
			}*/
			else if (ev.getSource() == findWin){
				algo = true;
				System.out.println("Waiting for click [lmin,cmin] and [lmax,cmax]");
				a = new Algo();
			}
			else if (ev.getSource() == isWinBlanc){
				locked = true;
				
				int line, column, lmin, cmin, lmax, cmax;
				boolean b;
				Scanner in = new Scanner(System.in);
				
				System.out.println("give line, column, lmin, cmin, lmax, cmax:");
				line = in.nextInt();
				column = in.nextInt();
				lmin = in.nextInt();
				cmin = in.nextInt();
				lmax = in.nextInt();
				cmax = in.nextInt();
				
				if(lmax > (size-1)){
					lmax = size-1;
				}
				if(cmax > (size-1)){
					cmax = size-1;
				}
				
				b = Algo.isWinning(new Plateau(p), 1, line, column, lmin, cmin, lmax, cmax);
				System.out.println("Is " + line + ", " + column + " winning for Blanc? " + b);
				
				locked = false;
			}
			else if (ev.getSource() == isWinNoir){
				locked = true;
				
				int line, column, lmin, cmin, lmax, cmax;
				boolean b;
				Scanner in = new Scanner(System.in);
				
				System.out.println("give line, column, lmin, cmin, lmax, cmax:");
				line = in.nextInt();
				column = in.nextInt();
				lmin = in.nextInt();
				cmin = in.nextInt();
				lmax = in.nextInt();
				cmax = in.nextInt();
				
				if(lmax > (size-1)){
					lmax = size-1;
				}
				if(cmax > (size-1)){
					cmax = size-1;
				}
				
				b = Algo.isWinning(new Plateau(p), -1, line, column, lmin, cmin, lmax, cmax);
				System.out.println("Is " + line + ", " + column + " winning for Noir? " + b);
				
				locked = false;
			}
		}
	}
	
	
	private class damier_listener extends MouseAdapter {
		public void mouseClicked(MouseEvent ev) {
			//System.out.println(ev.getX() + "  " + ev.getY());
			if(locked){ // a program is running on the board, no new move possible
				return;
			}
			
			line = (ev.getY() - 92)/58;
			column = (ev.getX() - 100 - line*33)/66;
			
			if(line < 0 || line > (size-1) || column < 0 || column > (size-1) ){
				return;
			}
			
			if(algo){
				System.out.println((char)(column+65) + "" + (line+1));
				if(a.lmin < 0){
					a.lmin = line;
					a.cmin = column;
				}
				else{
					a.lmax = line;
					a.cmax = column;
					locked = true;
					a.findWin(p, player);
					locked = false;
					algo = false;
				}
				return;
			}
				
			if(p.win == 0){ // meaning no winner yet
				
				if(!p.addMove(line, column, player)){ //if move non valid do nothing
					return;
				}
				
				last = new JJeton(column, line, player);
				
				//board.setVisible(false);
				board.add(last);
				//panneau.add(board);
				repaint();
				//board.setVisible(true);
				save.setEnabled(true);
				
				if(p.win != 0){
					printWin();
				}
				else {
					player = -player;
					t1.setText((player < 0) ? "C'est à Noir de jouer" : "C'est à Blanc de jouer");
				}
				
			}
			else{ //there is a winner, the board is blocked
				
			}
		}//end mouseclicked
		
	}//end damierlistened
	

}
