import java.util.TimerTask;

/*
 * Created on Aug 6, 2004
 * Watchdog timer

 */

/**
 * @author dick
 * USED BY AARON PHILIPS student of Dr. Scott Dick

 */
public class Watchdog extends TimerTask {

Thread watched;

	public Watchdog (Thread target){
		// Constructor sets the class variable 'watched'
		watched = target;
	}
	/* (non-Javadoc)
	 * @see java.lang.Runnable#run()
	 */
	public void run() {

		watched.stop();
		// System.out.println("You're dead!");
	}

}
