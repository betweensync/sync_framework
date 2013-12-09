package caramel.macc.andysync;

import java.util.Map;


public class ScanEvent {
	private ScanEventType type;
	
	private Map<String, SFile> scanned;
	
	private String errorMessage;
	private Throwable throwable;
	
	public ScanEventType getType() {
		return type;
	}
	public void setType(ScanEventType type) {
		this.type = type;
	}
	public Map<String, SFile> getScanned() {
		return scanned;
	}
	public void setScanned(Map<String, SFile> scanned) {
		this.scanned = scanned;
	}
	public String getErrorMessage() {
		return errorMessage;
	}
	public void setErrorMessage(String errorMessage) {
		this.errorMessage = errorMessage;
	}
	public Throwable getThrowable() {
		return throwable;
	}
	public void setThrowable(Throwable throwable) {
		this.throwable = throwable;
	}
}
