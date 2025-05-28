package Main;

import java.awt.Graphics2D;
import java.util.ArrayList;
import java.util.List;

import armas.Armas;
import unidades.Clases;
import unidades.NombresUnidad;
import unidades.Stats;
import unidades.creadorUnidades;
import entidad.Enemigo;
import entidad.Entidad;
import entidad.Unidad;

public class ManejadorEntidades 
{
    private List<Entidad> entidades = new ArrayList<>();
    private List<Entidad> enemigos = new ArrayList<>();
    private final GamePanel gp;

    public ManejadorEntidades(GamePanel gp) 
    {
        this.gp = gp;
        generarEntidades();
        generarEnemigos();
    }
    
    private boolean entidadesGeneradas = false;


    public void generarEntidades() 
    {
        int tileSize = gp.getTamanioTile();
        int maxCols = gp.getMaxColMundo();
        int maxRens = gp.getMaxRenMundo();
        int level = gp.getCurrentLevel(); 

        entidades.clear();

        switch (level) {
            case 0:
            case 1:
                
                int col1X = 4 * tileSize;
                int col2X = 5 * tileSize;
                int centerRow = maxRens / 2;
                
                int[] rowOffsets = {-3, -1, 1, 3};
                Clases[] tipos = Clases.values();
                for (int i = 0; i < 4; i++) {
                    int y = (centerRow + rowOffsets[i]) * tileSize;
                    String nombre1 = NombresUnidad.aleatorio();
                    Stats stats1 = creadorUnidades.getStatsBase(tipos[i % tipos.length]);
                    Unidad u1 = new Unidad(nombre1, gp, gp.getMT(), col1X, y, stats1, tipos[i % tipos.length]);
                    Unidad u2 = new Unidad(nombre1, gp, gp.getMT(), col2X, y, stats1, tipos[i % tipos.length]);
                    add(u1);
                    add(u2);
                }
                break;

            case 2:
                
                int centerX = (maxCols / 2) * tileSize;
                int centerY = (maxRens / 2) * tileSize;
                Clases[] tipos3 = {Clases.Jinete, Clases.Infanteria, Clases.Volador, Clases.Acorazado};
                for (int i = 0; i < tipos3.length; i++) {
                    String nombre = NombresUnidad.aleatorio();
                    Stats stats = creadorUnidades.getStatsBase(tipos3[i]);
                    Unidad u = new Unidad(nombre, gp, gp.getMT(), centerX, centerY, stats, tipos3[i]);
                    add(u);
                }
                break;


        }
    }
    public void generarEnemigos()
    {
        int tileSize = gp.getTamanioTile();
        int maxCols = gp.getMaxColMundo();
        int maxRens = gp.getMaxRenMundo();
        int level = gp.getCurrentLevel(); 

        enemigos.clear();

        switch (level) {
            case 0:
            case 1:
                
                int col1X = (this.gp.getMaxColMundo()-7) * tileSize;
                int col2X = (this.gp.getMaxColMundo()-8)* tileSize;
                int centerRow = maxRens / 2;
                
                int[] rowOffsets = {-3, -1, 1, 3};
                Clases[] tipos = Clases.values();
                for (int i = 0; i < 4; i++) {
                    int y = (centerRow + rowOffsets[i]) * tileSize;
                    Stats stats1 = creadorUnidades.getStatsBase(tipos[i % tipos.length]);
                    Enemigo u1 = new Enemigo("Enemigo", gp, col1X, y, stats1, tipos[i % tipos.length]);
                    Enemigo u2 = new Enemigo("Enemigo", gp, col2X, y, stats1, tipos[i % tipos.length]);
                    addEnemigo(u1);
                    addEnemigo(u2);
                }
                break;

            case 2:
                
                int centerX = (maxCols / 2) * tileSize;
                int centerY = (maxRens / 2) * tileSize;
                Clases[] tipos3 = {Clases.Jinete, Clases.Infanteria, Clases.Volador, Clases.Acorazado};
                for (int i = 0; i < tipos3.length; i++) {
                    Stats stats = creadorUnidades.getStatsBase(tipos3[i]);
                    Enemigo u = new Enemigo("Enemigo", gp, centerX, centerY, stats, tipos3[i]);
                    addEnemigo(u);
                }
                break;


        }
    }
//    public void generarEnemigos() 
//    {
//        int tileSize = gp.getTamanioTile();
//        int centerX = (gp.getMaxColMundo() / 2) * tileSize;
//        int centerY = (gp.getMaxRenMundo() / 2) * tileSize + (0 * tileSize);
//        
//        Clases[] tipos = {Clases.Jinete, Clases.Infanteria, Clases.Acorazado, Clases.Volador};
//        for(int i = 0; i < tipos.length; i++) 
//        {
//            Stats stats = creadorUnidades.getStatsBase(tipos[i]);
//            Enemigo enemigo = new Enemigo("Enemigo " + (i+1), gp, 
//                centerX + (i * 2 * tileSize), centerY, stats, tipos[i]);
//            addEnemigo(enemigo);
//        }
//    }
    
    public void addEnemigo(Entidad e) { enemigos.add(e); }
    public void add(Entidad e) {
        entidades.add(e);
    }

    public void remove(Entidad e) 
    {
        entidades.remove(e);
        enemigos.remove(e);
    }

    public void updateAll() 
    {
    	enemigos.forEach(e -> e.update());
    	entidades.forEach(e -> e.update());
    }

    public void drawALL(Graphics2D g2) 
    {
    	 enemigos.forEach(e -> e.draw(g2));
    	 entidades.forEach(e -> e.draw(g2));
    }
    
    public List<Entidad> getEnemigos() { return enemigos; }
    public List<Entidad> getEntidades() {
        return this.entidades;
    }
}
