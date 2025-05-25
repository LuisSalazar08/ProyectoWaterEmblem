package entidad;

public class ManejadorMovimiento 
{
	private final long initialDelay;
	private final long repeatDelay;
	private final CalculaMovimiento cMV;
	
	private long lastMoveTime=0;
	private boolean keyWasPressed = false;
	
	public ManejadorMovimiento(CalculaMovimiento cmv, long initialDelay, long repeatDelay)
	{
		this.cMV=cmv;
		this.initialDelay=initialDelay;
		this.repeatDelay=repeatDelay;
	}
	
	public void updateMV(boolean keyPressed) 
	{
        long currentTime = System.currentTimeMillis();

        if (!keyPressed) {
            keyWasPressed = false;
            lastMoveTime = 0;
            return;
        }
        long timeSinceLast = currentTime - lastMoveTime;
        
        if (!keyWasPressed || timeSinceLast >= (keyWasPressed ? repeatDelay : initialDelay)) 
        {
            cMV.mover();
            lastMoveTime = currentTime;
            keyWasPressed = true;
        }
//        if (!keyWasPressed) {
//            cMV.mover();
//            lastMoveTime = currentTime;
//            keyWasPressed = true;
//        } else {
//            long diff = currentTime - lastMoveTime;
//            long delay = (diff < initialDelay) ? initialDelay : repeatDelay;
//            if (diff >= delay) {
//                cMV.mover();
//                lastMoveTime = currentTime;
//            }
//        }
    }
	
}
