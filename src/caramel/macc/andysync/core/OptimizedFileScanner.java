package caramel.macc.andysync.core;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import caramel.macc.andysync.SFile;

public class OptimizedFileScanner extends AbstractFileScanner {
	private List<ScanWorker> scanWorkers = Collections.synchronizedList(new ArrayList<ScanWorker>());
	
	public OptimizedFileScanner(String baseDir, int threadCount){
		super(baseDir, threadCount);
	}
	
	@Override
	protected void preScan(){
		// create worker threads..
		for(int inx = 0; inx < this.threadCount; inx++){
			this.scanWorkers.add(new ScanWorker());
		}
	}
	
	@Override
	protected void scan(){
		for(int inx = 0; inx < this.threadCount; inx++){
			this.scanWorkers.get(inx).start();
		}
	}
	
	@Override
	protected void preEnd(){
		ScanWorker sworker = null;
		
		for(int inx = 0; inx < this.scanWorkers.size(); inx++){
			sworker = this.scanWorkers.get(inx);
			sworker.terminate();
			
//			if (sworker.isAlive())
//				sworker.stop();
		}
		
		this.scanWorkers.clear();
	}
	

	@Override
	public void suspend() {
		// TODO Auto-generated method stub

	}

	@Override
	public void resume() {
		// TODO Auto-generated method stub

	}

	@Override
	public void preStop() {
		this.preEnd();
	}
	
	private class ScanWorker extends Thread{
		private boolean onGoing = true;
		
		public void terminate(){
			this.onGoing = false;
			
			synchronized(this){
				this.interrupt();
			}
		}
		
		public void run(){
			String path = null;
			
			while(onGoing){
				Map<String, SFile> scanned;

				try {
					// blocked before new path put to queue..
					path = scanNeededQueue.take();
					
					scanned = new HashMap<String, SFile>();
					
					File f = new File(path);					
					File[] files = f.listFiles();
					
					if (files != null){					
						for(File _f : files){
							// if dir is found, put absolute path to queue to launch new scan job..
							if(_f.isDirectory()){
								enqueueScanNeeded(_f.getAbsolutePath());
							}
							
							// add scanned files to map..
							scanned.put(_f.getAbsolutePath(), new SFile(_f));
						}
						
						// send scanned event..
						sendScannedEvent(scanned);
						
						// put all scanned to SFileMap..
						sFileMap.putAll(scanned);
					}

					
				} catch (InterruptedException e) {
//					e.printStackTrace();
				}
			}
		}
	}
}
