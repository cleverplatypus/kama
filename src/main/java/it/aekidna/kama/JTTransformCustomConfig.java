package it.aekidna.kama;

import it.aekidna.kama.api.IJTTransform;
import it.aekidna.kama.api.IJTTransformCustomConfig;

import org.codehaus.jackson.node.ObjectNode;

public class JTTransformCustomConfig extends BaseTransformConfig implements IJTTransformCustomConfig {

	@Override
	public Class<IJTTransform> getTransformClass() {
		return transformClass;
	}

	private Class<IJTTransform> transformClass;
	
	@SuppressWarnings("unchecked")
	public JTTransformCustomConfig( ObjectNode inSourceNode )
	{
		super( inSourceNode );
		String className = inSourceNode.has("class") ? inSourceNode.get("class").asText() : null;
		if( className == null )
			throw new RuntimeException("you must specify a 'class' property with the fully qualified IJTTransform class name");
		try {
			transformClass = (Class<IJTTransform>) Class.forName(className);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
			throw new RuntimeException("specified 'class' property cannot be resolved to a IJTTransform implementation");
		}
	}
	
	

}
