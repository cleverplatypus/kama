package it.aekidna.kama;

import it.aekidna.kama.api.IJTTransformConfig;

import org.codehaus.jackson.node.ObjectNode;

public class BaseTransformConfig implements IJTTransformConfig {
	private String target;
	private String source;
	
	public BaseTransformConfig( ObjectNode inSourceNode )
	{
		target = inSourceNode.has("target") ? inSourceNode.get("target").asText() : null;
		source = inSourceNode.has("source") ? inSourceNode.get("source").asText() : null;
	}
	
	@Override
	public String getTarget() {
		return target;
	}

	@Override
	public String getSource() {
		return source;
	}

	@Override
	public boolean shouldMerge() {
		return getTarget() != null && !getTarget().equals(".");
	}

	@Override
	public boolean shouldReplace() {
		return getTarget() != null && getTarget().equals(".");
	}
	
	@Override
	public boolean returnsNewValue() {
		return getTarget() == null;
	}


}
