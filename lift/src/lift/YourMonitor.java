package lift;


public class YourMonitor {
	private int here, next, load;
	private int waitEntry[];
	private int waitExit[];
	
	



	public YourMonitor() {

		
	}

	
	public synchronized void next(int next){
		this.next = next;
		notifyAll();
	}
	
	public synchronized boolean here(int here){
		boolean currentFloor = this.here == here;
		notifyAll();	
		return currentFloor;
	}
	
	public synchronized void load(int floor){
		if(load < 4 && waitEntry[floor] > 0){
			
		}
		
	}
}
