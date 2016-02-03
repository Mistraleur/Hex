package hex;

import java.awt.Font;
import java.awt.event.*;

import javax.swing.*;

public class MaFenetre extends JFrame {
	
	private JPanel panneau;
	private JLabel board, t1, t2, tn, tb;
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
		
		/*t1 = new JLabel("C'est à");
		t1.setFont(new Font("Serif", Font.PLAIN, 25));
		t1.setBounds(730, 10, 100, 50);
		
		t2 = new JLabel("blanc de jouer");
		t2.setFont(new Font("Serif", Font.PLAIN, 25));
		t2.setBounds(720, 30, 1000, 50);
		
		tn = new JLabel("NOIR");
		tn.setFont(new Font("Serif", Font.PLAIN, 25));
		tn.setBounds(950, 50, 100, 50);
		*/
		
		panneau = new JPanel();
		panneau.setLayout(null);
		//panneau.add(t1);
		//panneau.add(t2);
		//panneau.add(tn);
		panneau.add(board);
		
		this.add(panneau);
		
		setVisible(true);
		
		
	}
	
	
	
	private class damier_listener extends MouseAdapter {
		
		// methode invoquee si la souris a ete cliquee sur le damier
		public void mouseClicked(MouseEvent ev) {
			//System.out.println(ev.getX() + "  " + ev.getY());
				
				line = (ev.getY() - 92)/58;
				column = (ev.getX() - 100 - line*33)/66;
				
				if(line < 0 || line > 8 || column < 0 || column > 8){
					return;
				}
				
				//Hex.print_cell(column, line);
				//System.out.println(column + " " + line);
				p.addMove(line, column, player);
				board.setVisible(false);
				
				panneau.add(new JJeton(column, line, player));
				panneau.add(board);
				repaint();
				board.setVisible(true);
				
				player = -player;
		}
		

	}
	

}
