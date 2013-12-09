package caramel.macc.andysync.core;

import caramel.macc.andysync.FileScanner;

public class FileScannerFactory{
	private static FileScannerFactory fsm = new FileScannerFactory();
	
	private FileScannerFactory(){
		
	}
	
	public static FileScannerFactory instance(){
		return fsm;
	}

	public FileScanner create(int type, String baseDir, int threadCount){
		FileScanner fscanner = null;
		
		switch(type){
//		case FileScanner.TYPE_CONCURRENT:
//			fscanner = this.createCuncurrentFileScanner(baseDir, threadCount);
//			break;
		case FileScanner.TYPE_OPTIMIZED:
			fscanner = this.createOptimizedFileScanner(baseDir, threadCount);
			break;
		}
		
		return fscanner;
	}
	
//	public FileScanner createCuncurrentFileScanner(String baseDir, int threadCount){
//		return new ConcurrentFileScanner(baseDir, threadCount);
//	}
	
	public FileScanner createOptimizedFileScanner(String baseDir, int threadCount){
		return new OptimizedFileScanner(baseDir, threadCount);
	}
	
}
