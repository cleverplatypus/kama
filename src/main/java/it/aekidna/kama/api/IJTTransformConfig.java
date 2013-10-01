package it.aekidna.kama.api;

public interface IJTTransformConfig {
	public String getTarget();
	public String getSource();
	
	public boolean shouldMerge();
	public boolean shouldReplace();
	public boolean returnsNewValue();
}
