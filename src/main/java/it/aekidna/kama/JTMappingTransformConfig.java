package it.aekidna.kama;

import it.aekidna.kama.api.IJTTransformMappingConfig;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import org.codehaus.jackson.node.ObjectNode;

public class JTMappingTransformConfig extends BaseTransformConfig implements IJTTransformMappingConfig {

	private Map<String, String> mappings;
	
	public JTMappingTransformConfig( ObjectNode inSourceNode )
	{
		super( inSourceNode );
		mappings = new HashMap<String, String>();
		ObjectNode sourceMappings = (ObjectNode) inSourceNode.get("mappings");
		
		
		for( Iterator<String> iter = sourceMappings.getFieldNames(); iter.hasNext(); )
		{
			String key  = iter.next();
			mappings.put( key, sourceMappings.get( key ).asText());
		}

	}
	
	@Override
	public Map<String, String> getMappings() {
		return mappings;
	}

}
