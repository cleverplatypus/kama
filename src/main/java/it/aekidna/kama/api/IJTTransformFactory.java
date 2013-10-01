package it.aekidna.kama.api;

import org.codehaus.jackson.node.ObjectNode;

public interface IJTTransformFactory {
	public IJTTransformationSetup getTransformByPath( String inPath );
	public IJTTransformationSetup resolveTransform( ObjectNode inNode );

}
