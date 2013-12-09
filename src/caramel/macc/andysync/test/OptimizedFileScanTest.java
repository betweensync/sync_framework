package caramel.macc.andysync.test;

import caramel.macc.andysync.FileScanner;
import caramel.macc.andysync.ScanEvent;
import caramel.macc.andysync.ScanEventListener;
import caramel.macc.andysync.core.FileScannerFactory;

public class OptimizedFileScanTest implements ScanEventListener{

	private FileScanner fileScanner;
	
	// for test..
	private long started = 0;
	@Override
	public void onScanEvent(ScanEvent event) {
		switch (event.getType()) {
		case STARTED:
			System.out.println("* FileScanner started..");
			started = System.currentTimeMillis();
			break;
		case SCANNED:
			System.out.println("* FileScanner scanned.. [" + event.getScanned().size() + "]");
			break;
		case ENDED:			
			this.fileScanner.stop();
			System.out.println("* Elapsed Time: " + (System.currentTimeMillis()-started));
			break;
		}
	}
	
	public void startScan(String baseDir, int threadCount){
		this.fileScanner = FileScannerFactory.instance().create(FileScanner.TYPE_OPTIMIZED, baseDir, threadCount);
		this.fileScanner.addScanEventListener(this);
		this.fileScanner.start();
	}
	
	public static void main(String[] args) {
		new OptimizedFileScanTest().startScan("D:/90. Personal", 6);
	}
}
