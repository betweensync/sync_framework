package caramel.macc.andysync;

public interface Executable {
	public void start();
	public void suspend();
	public void resume();
	public void stop();
}
