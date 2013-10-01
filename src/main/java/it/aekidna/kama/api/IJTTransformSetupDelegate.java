package it.aekidna.kama.api;

import org.codehaus.jackson.node.ObjectNode;

public interface IJTTransformSetupDelegate {
	public IJTTransformationSetup setupTransform( IJTTransformFactory inFactory, ObjectNode inConfigNode );
}
