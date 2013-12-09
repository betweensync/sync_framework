package caramel.macc.andysync;

import java.io.File;

public class SFile {
	private String absolutePath;
	private String parent;
	private String name;
	private long lastModified;
	private boolean isDirectory;
	
	public SFile(){
		
	}
	
	public SFile(File f){
		this.setAbsolutePath(f.getAbsolutePath());
		this.setDirectory(f.isDirectory());
		this.setName(f.getName());
		this.setLastModified(f.lastModified());
		this.setParent(f.getParent());
	}
	
	public String getAbsolutePath() {
		return absolutePath;
	}
	public void setAbsolutePath(String absolutePath) {
		this.absolutePath = absolutePath;
	}
	public String getParent() {
		return parent;
	}
	public void setParent(String parent) {
		this.parent = parent;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public long getLastModified() {
		return lastModified;
	}
	public void setLastModified(long lastModified) {
		this.lastModified = lastModified;
	}
	public boolean isDirectory() {
		return isDirectory;
	}
	public void setDirectory(boolean isDirectory) {
		this.isDirectory = isDirectory;
	}
	@Override
	public String toString() {
		return "SFile [absolutePath=" + absolutePath + ", parent=" + parent
				+ ", name=" + name + ", lastModified=" + lastModified
				+ ", isDirectory=" + isDirectory + "]";
	}
}
