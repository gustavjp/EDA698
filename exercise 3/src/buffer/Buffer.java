package buffer;

import se.lth.cs.realtime.RTError;

class Buffer {
	int available; // Number of lines that are available.
	final int size = 8; // The max number of buffered lines.
	String[] buffData; // The actual buffer.
	int nextToPut; // Writers index.
	int nextToGet; // Readers index.

	Buffer() {
		buffData = new String[size];
	}

	synchronized void putLine(String inp) {
		try {
			while (available == size) wait();
		} catch (InterruptedException exc) {
			throw new RTError("Buffer.putLine interrupted: " + exc);
		};
		buffData[nextToPut] = new String(inp);
		if (++nextToPut >= size)
			nextToPut = 0;
		available++;
		notifyAll(); // Only notify() could wake up another producer.
	}

	synchronized String getLine() {
		// Write the code that implements this method ...
		try {
			while (available == 0) wait();		//V�ntar ifall det inte finns n�gra available
		} catch (InterruptedException exc) {
			throw new RTError("Buffer.getLine interrupted: " + exc);
		};	// Beh�vs ";"?
		String out = buffData[nextToGet];
		buffData[nextToGet] = null;
		nextToGet++;
		if(nextToGet >= size)		// B�rja om p� 0 om den �verstiger 8
			nextToGet = 0;
		available--;
		notifyAll();
		return out;
	}
}
