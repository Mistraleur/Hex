package hex;

import java.awt.Font;
import java.awt.event.*;
import javax.swing.*;


public class MaFenetre extends JFrame {
	
	private JPanel panneau;
	private JLabel board, t1;
	private JButton isWinBlanc, isWinNoir, again;
	private int column, line;
	private int player = 1;
	private Plateau p;
	
	public MaFenetre(Plateau p){
		super();
		setLocation(0, 0);
		setSize(1017,693);
		setResizable(true);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		this.p = p;
		if(p.getSize() != 9){
			System.out.println("/////!!!///// problem of size plateau : " + p.getSize() + "/////!!!///// ");
		}
		
		board = new JLabel();
		board.setIcon(new ImageIcon("files/Plateau9.png"));
		board.setBounds(0, 0, 997, 673);
		board.addMouseListener(new damier_listener());
		
		
		t1 = new JLabel("C'est à Blanc de jouer");
		t1.setFont(new Font("Serif", Font.PLAIN, 25));
		t1.setBounds(710, 10, 400, 90);
		
		again = new JButton("Play again?");
		again.setFont(new Font("Serif", Font.PLAIN, 25));
		again.setBounds(750, 110, 190, 40);
		again.addActionListener(new again_listener());
		
		
		panneau = new JPanel();
		panneau.setLayout(null);
		panneau.add(t1);
		panneau.add(again);
		panneau.add(board);
		
		
		this.add(panneau);
		setVisible(true);
	}
	
	private void printWin(){
		t1.setText((p.getWin() < 0) ? "  Noir a gagné !" : "  Blanc a gagné !");
	}
	
	
	private class again_listener implements ActionListener {

		public void actionPerformed(ActionEvent arg0) {
			board.removeAll();
			t1.setText("C'est à Blanc de jouer");
			repaint();
			p.reset();
		}
		
	}
	
	private class damier_listener extends MouseAdapter {
		
		// methode invoquee si la souris a ete cliquee sur le damier
		public void mouseClicked(MouseEvent ev) {
			//System.out.println(ev.getX() + "  " + ev.getY());
				
			if(p.getWin() == 0){ // meaning no winner yet
				line = (ev.getY() - 92)/58;
				column = (ev.getX() - 100 - line*33)/66;
				
				if(line < 0 || line > 8 || column < 0 || column > 8){
					return;
				}
				
				if(!p.addMove(line, column, player)){ //if move non valid do nothing
					return;
				}
				
				board.setVisible(false);
				board.add(new JJeton(column, line, player));
				panneau.add(board);
				repaint();
				board.setVisible(true);
				
				if(p.getWin() != 0){
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
