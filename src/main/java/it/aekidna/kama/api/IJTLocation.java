package it.aekidna.kama.api;

import java.util.List;

import org.codehaus.jackson.JsonNode;
import org.codehaus.jackson.node.ContainerNode;

public interface IJTLocation {
	public JsonNode getCurrentValue();
	public JsonNode getCurrentValue( String inPath );
	public void setValue( JsonNode inValue );
	public void setValue( String inPath, JsonNode inValue );
	
	public IJTLocation getSubLocation( String inPath, boolean inForceCreate  );
	public IJTLocation getSubLocation( List<ContainerNode> inDescendentNodes, JsonNode inTip );
}
