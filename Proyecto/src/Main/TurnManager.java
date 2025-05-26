package Main;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import entidad.Cursor;
import entidad.Unidad;
public class TurnManager 
{
    private final GamePanel gamePanel;
    private final Cursor jugador;
    private List<Unidad> allyUnits;
    private List<Unidad> unitsToReset;
    private int turnCounter = 1;
    private boolean isPlayerTurn = true;
    
    public TurnManager(GamePanel gp, Cursor jugador) 
    {
        this.gamePanel = gp;
        this.jugador = jugador;
        this.allyUnits = new ArrayList<>();
        this.unitsToReset = new ArrayList<>();
        refreshUnits();
    }
    
    public void refreshUnits() 
    {
        allyUnits.clear();
        allyUnits.addAll(this.gamePanel.getME().getEntidades().stream()
        							.filter(Unidad.class::isInstance)
        							.map(Unidad.class::cast)
        							.collect(Collectors.toList()));
    }
    
    public void startPlayerTurn() 
    {
        isPlayerTurn = true;
        unitsToReset.addAll(allyUnits);
        allyUnits.forEach(u -> u.setHasActed(false));
        allyUnits.forEach(u -> u.setHasMoved(false));
        jugador.resetUnidadSeleccionada();
    }
    
    public void endPlayerTurn()
    {
        isPlayerTurn = false;
        turnCounter++;
        processEnemyPhase(); // Lógica para turno enemigo (si aplica)
    }
    
    private void processEnemyPhase() {
        // Implementar lógica para enemigos aquí
        // ...
        
        // Cuando termina el turno enemigo
        startPlayerTurn();
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
    public boolean isPlayerTurn() { return isPlayerTurn; }
}