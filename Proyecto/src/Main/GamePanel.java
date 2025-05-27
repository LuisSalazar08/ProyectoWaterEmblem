package Main;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;

import javax.swing.JPanel;

import entidad.Unidad;
import entidad.Cursor;
import entidad.Entidad;
import tile.ManejadorTiles;
import ui.InfoBox;
import ui.OptionMenu;
import ui.TurnBox;

public class GamePanel extends JPanel implements Runnable {
    private final int tamanioOriginalTile = 16;
    private final int escala = 3;
    private final int tamanioTile = tamanioOriginalTile * escala;
    private final int maxRenPantalla = 15;
    private final int maxColPantalla = 26;
    private final int anchoPantalla = tamanioTile * maxColPantalla;
    private final int altoPantalla = tamanioTile * maxRenPantalla;

    private Thread hebraJuego;
    private final int FPS = 60;

    private ManejadorTeclas mT = new ManejadorTeclas();
    private Cursor cursor = new Cursor(this, mT);
    private ManejadorTiles mTi = new ManejadorTiles(this);
    private ManejadorEntidades mE = new ManejadorEntidades(this);
    private InfoBox infoBox = new InfoBox(10, 10, 150, 100);
    private TurnBox turnBox = new TurnBox(anchoPantalla, 60, 40);
    private OptionMenu menu = new OptionMenu(this, 100, 0, "Mover", "Atacar", "Esperar", "Salir");
    private TurnManager turnManager = new TurnManager(this, cursor);

    private GameState state;
    private OptionMenu startMenu;
    private String[] nivelMapas = {
        "/mapas/mundo01.txt",
        "/mapas/mundo02.txt",
        "/mapas/mundo03.txt"
    };

    private final int maxRenMundo = 45;
    private final int maxColMundo = 78;
    private final int anchoMundo = tamanioTile * maxColMundo;
    private final int altoMundo = tamanioTile * maxRenMundo;

    public GamePanel() {
        setPreferredSize(new Dimension(anchoPantalla, altoPantalla));
        setBackground(Color.BLACK);
        setDoubleBuffered(true);
        addKeyListener(mT);
        setFocusable(true);

        state = GameState.MENU;
        int mx = (anchoPantalla - 200) / 2;
        int my = (altoPantalla - 100) / 2;
        startMenu = new OptionMenu(this, 200, 0,
            "Nivel 1", "Nivel 2", "Nivel 3", "Salir");
        startMenu.show(mx, my);
        startMenu.setOptions("Nivel 1", "Nivel 2", "Nivel 3", "Salir");
    }

    public void iniciaHebraJuego() {
        hebraJuego = new Thread(this);
        hebraJuego.start();
    }

    @Override
    public void run() {
        double intervaloDibujo = 1_000_000_000.0 / FPS;
        double delta = 0;
        long ultimaVez = System.nanoTime();
        long tiempoActual;

        while (hebraJuego != null) {
            tiempoActual = System.nanoTime();
            delta += (tiempoActual - ultimaVez) / intervaloDibujo;
            ultimaVez = tiempoActual;
            if (delta >= 1) {
                update();
                repaint();
                delta--;
            }
        }
    }

    public void update() {
        switch (state) {
            case MENU:
                startMenu.update(
                    mT.getFlechaArriba(),
                    mT.getFlechaAbajo(),
                    mT.getTeclaEnter(),
                    mT.getTeclaEsc()
                );
                if (startMenu.isOptionSelected()) {
                    int sel = startMenu.getSelectedIndex();
                    if (sel >= 0 && sel <= 2) {
                        mTi.cargaMapa(nivelMapas[sel]);
                        state = GameState.PLAYING;
                    } else {
                        System.exit(0);
                    }
                }
                break;
            case PLAYING:
                if (turnManager.isPlayerTurn())
                    logicaJugador();
                else
                    logicaEnemigos();
                break;
        }
    }

    public void logicaEnemigos() { }

    public void logicaJugador() {
        Unidad u = cursor.getUnidadSeleccionada();
        mE.updateAll();
        if (u != null && u.estaSeleccionada()) {
            u.update();
            return;
        }
        if (menu.isVisible()) {
            menu.update(
                mT.getFlechaArriba(),
                mT.getFlechaAbajo(),
                mT.getTeclaEnter(),
                mT.getTeclaEsc()
            );
        } else {
            if (mT.getTeclaArriba() || mT.getTeclaAbajo() ||
                mT.getTeclaIzquierda() || mT.getTeclaDerecha()) {
                cursor.update();
                cursor.actualizarSeleccion(this);
            }
            if (u != null && mT.getTeclaEnter()) {
                int cx = (anchoPantalla - menu.getWidth()) / 2;
                int cy = (altoPantalla - menu.getHeight()) / 2;
                menu.show(cx, cy);
            }
        }
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;

        if (state == GameState.MENU) {
            startMenu.draw(g2);
        } else {
            mTi.draw(g2);
            mE.drawALL(g2);
            for (Entidad e : mE.getEntidades()) {
                if (e instanceof Unidad) {
                    Unidad uu = (Unidad) e;
                    if (uu.estaSeleccionada()) {
                        uu.drawHighlight(g2);
                    }
                }
            }
            cursor.draw(g2);
            turnBox.draw(g2, turnManager);
            infoBox.draw(g2, cursor.getUnidadSeleccionada());
            menu.draw(g2);
        }

        g2.dispose();
    }

    public TurnManager getTM() { return turnManager; }
    public ManejadorTeclas getMT() { return mT; }
    public ManejadorEntidades getME() { return mE; }
    public int getTamanioOriginalTile() { return tamanioOriginalTile; }
    public int getEscala() { return escala; }
    public int getTamanioTile() { return tamanioTile; }
    public int getMaxRenPantalla() { return maxRenPantalla; }
    public int getMaxColPantalla() { return maxColPantalla; }
    public int getAnchoPantalla() { return anchoPantalla; }
    public int getAltoPantalla() { return altoPantalla; }
    public int getMaxRenMundo() { return maxRenMundo; }
    public int getMaxColMundo() { return maxColMundo; }
    public int getAnchoMundo() { return anchoMundo; }
    public int getAltoMundo() { return altoMundo; }
    public Cursor getJugador() { return cursor; }
    public ManejadorTiles getManejadorTiles() { return mTi; }
}
