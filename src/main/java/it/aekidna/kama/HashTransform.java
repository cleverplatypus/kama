package it.aekidna.kama;

import it.aekidna.kama.api.IJTLocation;
import it.aekidna.kama.api.IJTTransform;
import it.aekidna.kama.api.IJTTransformConfig;
import it.aekidna.kama.api.IJTTransformContainerConfig;
import it.aekidna.kama.api.IJTTransformationSetup;

import java.util.Iterator;
import java.util.List;

import org.codehaus.jackson.JsonNode;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.node.ObjectNode;

public class HashTransform extends AbstractBaseTransform {
	
	@Override
	public JsonNode transform(IJTTransformConfig inConfig,
			IJTLocation inSourceLocation, IJTLocation inTargetLocation) {
		IJTTransformContainerConfig config = (IJTTransformContainerConfig) inConfig;
		ObjectMapper mapper = new ObjectMapper();
		List<ObjectNode> entries = config.getEntries();
		ObjectNode hash = mapper.createObjectNode();
		for( Iterator<ObjectNode> iter = entries.iterator(); iter.hasNext(); )
		{
			ObjectNode entry  = iter.next();
			IJTTransformationSetup transformerSetup = factory.resolveTransform( entry );
			IJTTransform transformer = transformerSetup.getTransform();
			IJTLocation targetLocation;
			if( inTargetLocation != null )
				targetLocation = inTargetLocation.getSubLocation(null, hash);
			else
				targetLocation = new JTLocation(hash);
			JsonNode result = transformer.transform( 
					transformerSetup.getConfig(),
					inSourceLocation,
					targetLocation
					);
			if( transformerSetup.getConfig().shouldReplace() )
				throw new RuntimeException( "cannot use target=\".\" on hash transform child" );
			else if( targetLocation != null && transformerSetup.getConfig().shouldMerge() )
				targetLocation.setValue(transformerSetup.getConfig().getTarget(), result);
		}
		return hash;
	}

	@Override
	public IJTTransformConfig parseConfig(ObjectNode inNode) {
		return new BaseContainerTransformConfig( inNode );
	}

}
