package Main;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import entidad.Cursor;
import entidad.Enemigo;
import entidad.Unidad;
public class TurnManager 
{
    private final GamePanel gamePanel;
    private final Cursor jugador;
    private List<Unidad> allyUnits;
    private List<Enemigo> enemyUnits;
    private int turnCounter = 1;
    private boolean isPlayerTurn = true;	
    private boolean isEnemyTurn = false;
    
    public TurnManager(GamePanel gp, Cursor jugador) 
    {
        this.gamePanel = gp;
        this.jugador = jugador;
        refreshUnits();
    }
    
    public void refreshUnits() 
    {
    	allyUnits = gamePanel.getME().getEntidades().stream()
                .filter(Unidad.class::isInstance)
                .map(Unidad.class::cast)
                .collect(Collectors.toList());
            
        enemyUnits = gamePanel.getME().getEnemigos().stream()
                .filter(Enemigo.class::isInstance)
                .map(Enemigo.class::cast)
                .collect(Collectors.toList());
    }
    
    public void startPlayerTurn() 
    {
    	isPlayerTurn = true;
        isEnemyTurn = false;
        allyUnits.forEach(u -> {
            u.setHasActed(false);
            u.setHasMoved(false);
        });
        jugador.resetUnidadSeleccionada();
    }
    public void startEnemyTurn() 
    {
        isPlayerTurn = false;
        isEnemyTurn = true;
        enemyUnits.forEach(e -> {
            e.setHasActed(false);
            e.setHasMoved(false);
        });
    }
    public void endPlayerTurn()
    {
    	startEnemyTurn();
    }
    public void endEnemyTurn() 
    {
        turnCounter++;
        startPlayerTurn();
    }
    public void notificarAccionEnemigo() 
    {
        if(enemyUnits.stream().allMatch(e -> e.hasActed() || !e.isViva())) 
        {	
        	enemyUnits.forEach(e -> {
                e.setHasActed(false);
                e.setHasMoved(false);
            });
            endEnemyTurn();
        }
    }
    public void unitDidMovement(Unidad unidad)
    {
    	unidad.setHasMoved(true);
    }
    public void unitDidAction(Unidad unidad) 
    {
        unidad.setHasActed(true);
        checkTurnCompletion();
    }
    
    private void checkTurnCompletion() 
    {
        boolean allActed = allyUnits.stream().allMatch(Unidad::hasActed);
        if(allActed) {
            endPlayerTurn();
        }
    }
    
    public boolean canUnitAct(Unidad unidad) 
    {
        return isPlayerTurn && 
               allyUnits.contains(unidad) && 
               !unidad.hasActed() && 
               unidad.isViva();
    }
    
    // Getters
    public int getCurrentTurn() { return turnCounter; }
    public boolean isEnemyTurn() { return isEnemyTurn; }
    public boolean isPlayerTurn() { return isPlayerTurn; }
}