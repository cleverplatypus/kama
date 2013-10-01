package it.aekidna.kama;

import it.aekidna.kama.api.IJTLocation;
import it.aekidna.kama.api.IJTTransform;
import it.aekidna.kama.api.IJTTransformConfig;
import it.aekidna.kama.api.IJTTransformContainerConfig;
import it.aekidna.kama.api.IJTTransformationSetup;

import java.util.ArrayList;
import java.util.Iterator;

import org.codehaus.jackson.JsonNode;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.node.ArrayNode;
import org.codehaus.jackson.node.ContainerNode;
import org.codehaus.jackson.node.ObjectNode;

public class EachTransform extends AbstractBaseTransform {
	

	@SuppressWarnings("serial")
	@Override
	public JsonNode transform(IJTTransformConfig inConfig,
			IJTLocation inSourceLocation, IJTLocation inTargetLocation) {
		IJTTransformContainerConfig config = (IJTTransformContainerConfig) inConfig;
		ObjectMapper mapper = new ObjectMapper();
		ObjectNode entry = config.getEntries().iterator().next();
		JsonNode sourceNode = inSourceLocation.getCurrentValue( config.getSource() );
		ArrayNode targetArray = mapper.createArrayNode();
		if( sourceNode == null || !sourceNode.isArray() || ((ArrayNode) sourceNode).size() == 0 )
			return targetArray;
		final ArrayNode sourceArray = (ArrayNode) sourceNode; 
		IJTLocation sourceItemLocation;
		//int count = 0;
		for( Iterator<JsonNode> iter = sourceArray.iterator(); iter.hasNext(); )
		{
//			if( count == 20 )
//				break;
//			count++;
			JsonNode sourceItem = iter.next();
			
			
			sourceItemLocation = inSourceLocation.getSubLocation(
					new ArrayList<ContainerNode>() {{  add(sourceArray); }}, sourceItem);
			IJTTransformationSetup transformerSetup = factory.resolveTransform( entry );
			IJTTransform transformer = transformerSetup.getTransform();
			JsonNode result = transformer.transform( 
					transformerSetup.getConfig(),
					sourceItemLocation,
					null
					);
			if( transformerSetup.getConfig().shouldReplace() )
				throw new RuntimeException( "cannot use target=\".\" on each transform child" );
			else
				targetArray.add( result );//TODO: support nested object into array?
		}
		return targetArray;
	}

	@Override
	public IJTTransformConfig parseConfig(ObjectNode inNode) {
		return new BaseContainerTransformConfig( inNode );
	}


}
