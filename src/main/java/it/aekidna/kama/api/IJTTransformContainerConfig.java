package it.aekidna.kama.api;

import java.util.List;

import org.codehaus.jackson.node.ObjectNode;

public interface IJTTransformContainerConfig extends IJTTransformConfig {
	public List<ObjectNode> getEntries();
}
