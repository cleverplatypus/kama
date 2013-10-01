package it.aekidna.kama.api;

import org.codehaus.jackson.JsonNode;
import org.codehaus.jackson.node.ObjectNode;

public interface IJTTransform {
	
	public void setup ( IJTTransformFactory inFactory );
	
	public JsonNode transform( 
			IJTTransformConfig inConfig, 
			IJTLocation inSourceLocation, 
			IJTLocation inTargetLocation );
	
	public IJTTransformConfig parseConfig( ObjectNode inNode );
}
