package it.aekidna.kama;

import it.aekidna.kama.api.IJTLocation;
import it.aekidna.kama.api.IJTTransformConfig;
import it.aekidna.kama.api.IJTTransformMappingConfig;

import java.util.Map;

import org.codehaus.jackson.JsonNode;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.node.ObjectNode;

public class MapTransform extends AbstractBaseTransform
{

	@Override
	public JsonNode transform( IJTTransformConfig inConfig,
			IJTLocation inSourceLocation, IJTLocation inTargetLocation )
	{
		ObjectMapper mapper = new ObjectMapper();
		IJTLocation targetLocation = inTargetLocation;
		if ( targetLocation == null )
			targetLocation = new JTLocation( mapper.createObjectNode() );
		IJTTransformMappingConfig config = (IJTTransformMappingConfig) inConfig;

		Map<String, String> mappings = config.getMappings();

		for ( String sourceKey : mappings.keySet() )
		{
			if ( inSourceLocation.pathExists( sourceKey ) )
			{

				String targetKey = mappings.get( sourceKey );
				targetLocation.setValue( targetKey,
						inSourceLocation.getCurrentValue( sourceKey ) );
			}
		}
		return targetLocation.getCurrentValue();
	}

	@Override
	public IJTTransformConfig parseConfig( ObjectNode inNode )
	{

		return new JTMappingTransformConfig( inNode );
	}

}
