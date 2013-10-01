package it.aekidna.kama;

import it.aekidna.kama.api.IJTTransformContainerConfig;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.codehaus.jackson.JsonNode;
import org.codehaus.jackson.node.ArrayNode;
import org.codehaus.jackson.node.ObjectNode;

public class BaseContainerTransformConfig extends BaseTransformConfig implements
		IJTTransformContainerConfig {

	private List<ObjectNode> entries;
	
	public BaseContainerTransformConfig( ObjectNode inSourceNode )
	{
		super( inSourceNode );
		entries = new ArrayList<ObjectNode>();
		if( inSourceNode.has("entries"))
		{
			ArrayNode sourceEntries = (ArrayNode) inSourceNode.get("entries" );
			for( Iterator<JsonNode> iter = sourceEntries.iterator(); iter.hasNext(); )
			{
				entries.add((ObjectNode) iter.next());
			}
		}
		else if( inSourceNode.has("entry") )
			entries.add((ObjectNode) inSourceNode.get("entry"));
	}
	
	@Override
	public List<ObjectNode> getEntries() {
		return entries;
	}

}
