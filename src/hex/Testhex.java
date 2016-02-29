package hex;

import java.io.IOException;
import java.util.Scanner;

import javax.swing.plaf.synth.SynthSpinnerUI;

public class Testhex {

	public static void main(String[] args) throws IOException {
		// TODO Auto-generated method stub
		/*int size = 0;
		Scanner in = new Scanner(System.in);
		
		while(size != 9 && size != 11){
			System.out.println("Quelle taille de plateau ? (9/11)");
			size = in.nextInt();
			
			System.out.println("voila : " + size);
		}
		
		MaFenetre j = new MaFenetre(size);
		*/
		MaFenetre j = new MaFenetre(9);
		
	}

}
