package it.aekidna.kama.api;

import org.codehaus.jackson.node.ObjectNode;

public interface IJTTransformFactory {
	public IJTTransformationSetup getTransformByPath( String inPath );
	public IJTTransformationSetup resolveTransform( ObjectNode inNode );
	public void addTransformAlias( String inName, Class<? extends IJTTransform> inClass );
	public void addTransformDelegateAlias( String inName, Class<? extends IJTTransformSetupDelegate> inClass );

}
