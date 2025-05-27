package tile;

import java.awt.Graphics2D;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import javax.imageio.ImageIO;

import Main.GamePanel;

public class ManejadorTiles {
	private GamePanel gP;
	private int maxTiles = 10;
	Tile[] arregloTiles;
	private int codigosMapaTiles[][];
	
	
	public ManejadorTiles(GamePanel gP) {
		this.gP = gP;
		this.arregloTiles = new Tile[maxTiles];
		this.codigosMapaTiles = new int [gP.getMaxRenMundo()][gP.getMaxColMundo()];
		getImagenesTile();
		cargaMapa("/mapas/mundo01.txt");
	}
	
	public void cargaMapa(String rutaMapa) {
		try {
			InputStream mapa = getClass().getResourceAsStream(rutaMapa);
			BufferedReader br = new BufferedReader(new InputStreamReader(mapa));
			int ren = 0, col = 0;
			while(ren < gP.getMaxRenMundo() && col < gP.getMaxColMundo()) {
					String renglonDatos = br.readLine();
					while(col < gP.getMaxColMundo()) {
						String codigos[] = renglonDatos.split(" ");
						int codigo = Integer.parseInt(codigos[col]);
						this.codigosMapaTiles[ren][col] = codigo;
						col++;
			}
			if(col == gP.getMaxColMundo()) {
				ren++;
				col = 0;
			}
		}
		br.close();
	    } catch (Exception e) {
	        e.printStackTrace();
	    }
	}
	public void getImagenesTile() {
		try {
			arregloTiles[0] = new Tile();
			arregloTiles[0].setImagen(ImageIO.read(getClass().getResourceAsStream("/tiles/agua.png")));
			arregloTiles[1] = new Tile();
			arregloTiles[1].setImagen(ImageIO.read(getClass().getResourceAsStream("/tiles/arbol.png")));
			arregloTiles[2] = new Tile();
			arregloTiles[2].setImagen(ImageIO.read(getClass().getResourceAsStream("/tiles/arena.png")));
			arregloTiles[3] = new Tile();
			arregloTiles[3].setImagen(ImageIO.read(getClass().getResourceAsStream("/tiles/muro.png")));
			arregloTiles[4] = new Tile();
			arregloTiles[4].setImagen(ImageIO.read(getClass().getResourceAsStream("/tiles/pasto.png")));
			arregloTiles[5] = new Tile();
			arregloTiles[5].setImagen(ImageIO.read(getClass().getResourceAsStream("/tiles/suelo.png")));
			arregloTiles[6] = new Tile();
			arregloTiles[6].setImagen(ImageIO.read(getClass().getResourceAsStream("/tiles/limit.png")));
		}catch(IOException e) {
			e.printStackTrace();
		}
	}
	
    public boolean isPassable(int fila, int col) {
        int codigo = codigosMapaTiles[fila][col];
        return !(codigo == 0 || codigo == 1 || codigo == 3 || codigo == 6);
    }
	
	public void draw(Graphics2D g2) {
	    int renMundo = 0, colMundo = 0;
	    
	    
	    int jugadorPantallaX = gP.getJugador().getPantallaX();
	    int jugadorPantallaY = gP.getJugador().getPantallaY();
	    int jugadorMundoX = gP.getJugador().getMundoX();
	    int jugadorMundoY = gP.getJugador().getMundoY();
	    
	    while (renMundo < gP.getMaxRenMundo() && colMundo < gP.getMaxColMundo()) {
	        int codigoTile = this.codigosMapaTiles[renMundo][colMundo];
	        int mundoX = colMundo * gP.getTamanioTile();
	        int mundoY = renMundo * gP.getTamanioTile();
	        
	        
	        int pantallaX = mundoX - jugadorMundoX + jugadorPantallaX;
	        int pantallaY = mundoY - jugadorMundoY + jugadorPantallaY;
	        
	       
	        if (mundoX + gP.getTamanioTile() > jugadorMundoX - jugadorPantallaX &&
	            mundoX - gP.getTamanioTile() < jugadorMundoX + jugadorPantallaX &&
	            mundoY + gP.getTamanioTile() > jugadorMundoY - jugadorPantallaY &&
	            mundoY - gP.getTamanioTile() < jugadorMundoY + jugadorPantallaY) {
	            
	            g2.drawImage(arregloTiles[codigoTile].getImagen(), pantallaX, pantallaY, 
	                         gP.getTamanioTile(), gP.getTamanioTile(), null);
	        }
	        
	        colMundo++;
	        if (colMundo == gP.getMaxColMundo()) {
	            colMundo = 0;
	            renMundo++;
	        }
	    }
	}
}
