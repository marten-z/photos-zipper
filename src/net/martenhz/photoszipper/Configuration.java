package net.martenhz.photoszipper;

public class Configuration {

	private String directoryA, directoryB, directoryC;
	
	
	public Configuration() {}
	
	
	public String getDirectoryA() {
		return this.directoryA;
	}
	
	public String getDirectoryB() {
		return this.directoryB;
	}
	
	public String getDirectoryC() {
		return this.directoryC;
	}
	
	
	public void setDirectoryA(final String directoryPath) {
		this.directoryA = directoryPath;
	}
	
	public void setDirectoryB(final String directoryPath) {
		this.directoryB = directoryPath;
	}
	
	public void setDirectoryC(final String directoryPath) {
		this.directoryC = directoryPath;
	}
	
}
