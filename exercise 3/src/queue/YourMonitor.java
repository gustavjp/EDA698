package queue;

class YourMonitor {
	private int nCounters;
	private int freeClerks;
	private int lastCustomer;
	private int servedCustomer;
	private int currentID;
	private boolean freeCounters[];

	// Put your attributes here...

	YourMonitor(int n) {
		nCounters = n;
		freeCounters = new boolean[n]; // n st lediga counters
	//	nextClerk = 0; // Börjar på 0 eller 99 efter klock

		// Initialize your attributes here...
	}

	/**
	 * Return the next queue number in the intervall 0...99. There is never more
	 * than 100 nextCustomer waiting.
	 */
	synchronized int customerArrived() {
		// Implement this method...
		lastCustomer++;
		lastCustomer = lastCustomer % 100;	//Max 100
		notifyAll(); // Här??
		return lastCustomer;
	}

	/**
	 * Register the clerk at counter id as free. Send a customer if any.
	 */
	synchronized void clerkFree(int id) {
		// Implement this method...
		if (!freeCounters[id]) { // Ifall det sker många knapptryckningar
			freeCounters[id] = true;
			freeClerks++;
			notifyAll();
		}
	}

	/**
	 * Wait for there to be a free clerk and a waiting customer, then return the
	 * cueue number of next customer to serve and the counter number of the
	 * engaged clerk.
	 */
	synchronized DispData getDisplayData() throws InterruptedException {
		// Implement this method...	
		while(lastCustomer == servedCustomer || freeClerks == 0) wait();
		while(!freeCounters[currentID]) currentID = (currentID+1)%nCounters;
		DispData thisCounter  = new DispData();
		thisCounter.counter = currentID;
		freeCounters[currentID] = false;
		freeClerks--;
		thisCounter.ticket = lastCustomer = (lastCustomer+1)%100;
		return thisCounter;
	}
}
