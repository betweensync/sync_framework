package caramel.macc.andysync;

import java.util.Map;


public interface FileScanner extends Executable{
	public static final int TYPE_CONCURRENT = 0;
	public static final int TYPE_OPTIMIZED 	= 1;	
	
	public Map<String, SFile> getSFileMap();
	public void addScanEventListener(ScanEventListener listener);
	public String getBaseDir();
}
