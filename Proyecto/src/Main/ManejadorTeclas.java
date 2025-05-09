package Main;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class ManejadorTeclas implements KeyListener {
    private boolean teclaArriba, teclaAbajo, teclaIzquierda, teclaDerecha;
    private boolean flechaArriba, flechaAbajo, flechaIzquierda, flechaDerecha, teclaEnter, teclaEsc;

    private boolean flechaArribaProc, flechaAbajoProc, flechaIzquierdaProc, flechaDerechaProc, teclaEnterProc, teclaEscProc;

    @Override
    public void keyPressed(KeyEvent e) {
        switch (e.getKeyCode()) {
            case KeyEvent.VK_W:      teclaArriba    = true; break;
            case KeyEvent.VK_S:      teclaAbajo     = true; break;
            case KeyEvent.VK_A:      teclaIzquierda = true; break;
            case KeyEvent.VK_D:      teclaDerecha   = true; break;
            case KeyEvent.VK_ENTER:  teclaEnter     = true; break;
            case KeyEvent.VK_UP:     flechaArriba   = true; break;
            case KeyEvent.VK_DOWN:   flechaAbajo    = true; break;
            case KeyEvent.VK_LEFT:   flechaIzquierda= true; break;
            case KeyEvent.VK_RIGHT:  flechaDerecha  = true; break;
            case KeyEvent.VK_ESCAPE: teclaEsc       = true; break;
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        switch (e.getKeyCode()) {
            case KeyEvent.VK_W:      teclaArriba    = false; break;
            case KeyEvent.VK_S:      teclaAbajo     = false; break;
            case KeyEvent.VK_A:      teclaIzquierda = false; break;
            case KeyEvent.VK_D:      teclaDerecha   = false; break;
            case KeyEvent.VK_ENTER:  
            	teclaEnter 		 = false; 
            	teclaEnterProc 	 = false; 
            	break;
            case KeyEvent.VK_UP:
                flechaArriba     = false;
                flechaArribaProc = false;
                break;
            case KeyEvent.VK_DOWN:
                flechaAbajo      = false;
                flechaAbajoProc  = false;
                break;
            case KeyEvent.VK_LEFT:
                flechaIzquierda     = false;
                flechaIzquierdaProc = false;
                break;
            case KeyEvent.VK_RIGHT:
                flechaDerecha      = false;
                flechaDerechaProc  = false;
                break;
            case KeyEvent.VK_ESCAPE:
                teclaEsc      = false;
                teclaEscProc  = false;
                break;
        }
    }

    @Override public void keyTyped(KeyEvent e) {}

    public boolean getTeclaArriba()    { return teclaArriba; }
    public boolean getTeclaAbajo()     { return teclaAbajo; }
    public boolean getTeclaIzquierda() { return teclaIzquierda; }
    public boolean getTeclaDerecha()   { return teclaDerecha; }
    public boolean getTeclaEnter()   { 
    	if(teclaEnter && !teclaEnterProc) {
    		teclaEnterProc = true;
    		return true;
    	}
    	return false;
    }

    public boolean getFlechaArriba() {
        if (flechaArriba && !flechaArribaProc) {
            flechaArribaProc = true;
            return true;
        }
        return false;
    }
    public boolean getFlechaAbajo() {
        if (flechaAbajo && !flechaAbajoProc) {
            flechaAbajoProc = true;
            return true;
        }
        return false;
    }
    public boolean getFlechaIzquierda() {
        if (flechaIzquierda && !flechaIzquierdaProc) {
            flechaIzquierdaProc = true;
            return true;
        }
        return false;
    }
    public boolean getFlechaDerecha() {
        if (flechaDerecha && !flechaDerechaProc) {
            flechaDerechaProc = true;
            return true;
        }
        return false;
    }
    public boolean getTeclaEsc() {
        if (teclaEsc && !teclaEscProc) {
            teclaEscProc = true;
            return true;
        }
        return false;
    }

    public boolean getMovimiento() {
        return teclaArriba || teclaAbajo || teclaIzquierda || teclaDerecha;
    }
}

