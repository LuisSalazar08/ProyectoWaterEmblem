package Main;

import javax.swing.JFrame;

public class Proyecto {
	public static void main(String[] args) {
		JFrame ventana = new JFrame();
		ventana.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		ventana.setResizable(false);
		ventana.setTitle("TOO - Proyecto");
		
		GamePanel panelJuego = new GamePanel();
		ventana.add(panelJuego);
		ventana.pack();
		
		ventana.setLocationRelativeTo(null);
		ventana.setVisible(true);
		
		panelJuego.iniciaHebraJuego();
		
	}
}
