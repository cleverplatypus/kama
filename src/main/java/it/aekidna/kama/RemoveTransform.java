package it.aekidna.kama;

import it.aekidna.kama.api.IJTLocation;
import it.aekidna.kama.api.IJTTransformConfig;

import java.util.ArrayList;
import java.util.List;

import org.codehaus.jackson.JsonNode;
import org.codehaus.jackson.node.ArrayNode;
import org.codehaus.jackson.node.ContainerNode;
import org.codehaus.jackson.node.ObjectNode;

public class RemoveTransform extends AbstractBaseTransform {

	@Override
	public JsonNode transform(IJTTransformConfig inConfig,
			IJTLocation inSourceLocation, IJTLocation inTargetLocation) 
	{
		RemoveConfig config = (RemoveConfig) inConfig;
		System.out.println("RemoveTransform");
		ContainerNode parent = (ContainerNode) inSourceLocation.getCurrentValue( config.getSource());
		for( Object key : config.getKeys())
		{
			if( parent.isObject() )
				((ObjectNode) parent).remove(key.toString());
		}
		return inTargetLocation.getCurrentValue();
	}

	@Override
	public IJTTransformConfig parseConfig(ObjectNode inNode) {
		return new RemoveConfig(inNode);
	}
	
}


class RemoveConfig extends BaseTransformConfig
{
	private List<Object> keys;
	
	public RemoveConfig( ObjectNode inConfigNode )
	{
		super( inConfigNode );
		if(!inConfigNode.has("keys") || !inConfigNode.get("keys").isArray() )
			throw new RuntimeException("the 'keys' property must be an array");
		keys = new ArrayList<Object>();
		for( JsonNode key : ((ArrayNode) inConfigNode.get("keys")) )
		{
			if( key.isNumber() )
				keys.add( Integer.valueOf( key.asInt()));
			else
				keys.add( key.asText() );
		}
	}
	

	public List<Object> getKeys()
	{
		return keys;
	}
	
}

