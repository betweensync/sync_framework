package caramel.macc.andysync.core;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.LinkedBlockingQueue;

import caramel.macc.andysync.FileScanner;
import caramel.macc.andysync.SFile;
import caramel.macc.andysync.ScanEvent;
import caramel.macc.andysync.ScanEventListener;
import caramel.macc.andysync.ScanEventType;

public abstract class AbstractFileScanner implements FileScanner {
	protected Map<String, SFile> sFileMap = Collections.synchronizedMap(new HashMap<String, SFile>());
	protected List<ScanEventListener> listeners = new ArrayList<ScanEventListener>();
	protected LinkedBlockingQueue<String> scanNeededQueue = new LinkedBlockingQueue<String>();
	
	protected String baseDir = "/";
	protected int threadCount = 1;
	
	protected boolean stopped = false;
	protected boolean suspended = false;
	
	protected long scanNeededCount = 0;
	protected long scanEndedCount  = 0;
	
	public AbstractFileScanner(String baseDir, int threadCount){
		this.baseDir = baseDir;
		this.threadCount = threadCount;
	}

	@Override
	public Map<String, SFile> getSFileMap() {
		return this.sFileMap;
	}

	@Override
	public void addScanEventListener(ScanEventListener listener) {
		this.listeners.add(listener);
	}
	
	protected void fireScanEvent(ScanEvent scanEvent){
		for(ScanEventListener listener : this.listeners){
			listener.onScanEvent(scanEvent);
		}
	}

	@Override
	public String getBaseDir() {
		return this.baseDir;
	}

	@Override
	public void start() {
		synchronized(this){
			this.scanNeededCount = 0;
			this.scanEndedCount = 0;
		}
		
		// fire started event..
		ScanEvent sevent = new ScanEvent();
		sevent.setType(ScanEventType.STARTED);
		this.fireScanEvent(sevent);
		
		// 00. before start..
		this.preScan();
		
		// 01. start scan..
		this.scan();
		
		// 02. start feed from baseDir..
		this.enqueueScanNeeded(this.baseDir);
	}
	
	abstract protected void preScan();
	abstract protected void scan();
	
	protected void enqueueScanNeeded(String path){
		synchronized(this){
			this.scanNeededCount++;
		}
		
		try {
			this.scanNeededQueue.put(path);
		} catch (InterruptedException e) {
		}
	}
	
	protected void sendScannedEvent(Map<String, SFile> scanned){
		synchronized(this){
			this.scanEndedCount++;
		}
		
		ScanEvent sevent = new ScanEvent();
		sevent.setType(ScanEventType.SCANNED);
		sevent.setScanned(scanned);
		
		this.fireScanEvent(sevent);
		
		System.out.println(this.scanNeededCount + " : " + this.scanEndedCount);
		
		if (this.scanNeededCount == this.scanEndedCount){
			System.out.println("**** scan ended..");
			this.end();
		}
	}
	
	protected void end(){
		// send ended event..
		ScanEvent sevent = new ScanEvent();
		sevent.setType(ScanEventType.ENDED);
		this.fireScanEvent(sevent);
		
		this.preEnd();
	}
	
	// clean up..
	abstract protected void preEnd();	

	@Override
	public void suspend() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void resume() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void stop() {
		this.stopped = true;
		
		this.preStop();
		
		// fire stopped event.
		ScanEvent sevent = new ScanEvent();
		sevent.setType(ScanEventType.STOPPED);
		this.fireScanEvent(sevent);		
	}	
	
	abstract protected void preStop();
}
