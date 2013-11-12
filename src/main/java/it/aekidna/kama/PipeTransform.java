package it.aekidna.kama;

import it.aekidna.kama.api.IJTLocation;
import it.aekidna.kama.api.IJTTransform;
import it.aekidna.kama.api.IJTTransformConfig;
import it.aekidna.kama.api.IJTTransformContainerConfig;
import it.aekidna.kama.api.IJTTransformationSetup;

import java.util.Iterator;
import java.util.List;

import org.codehaus.jackson.JsonNode;
import org.codehaus.jackson.node.ObjectNode;

public class PipeTransform extends AbstractBaseTransform
{

	@Override
	public JsonNode transform( IJTTransformConfig inConfig,
			IJTLocation inSourceLocation, IJTLocation inTargetLocation )
	{
		IJTTransformContainerConfig config = (IJTTransformContainerConfig) inConfig;
		List<ObjectNode> entries = config.getEntries();
		IJTLocation sourceLocation = inSourceLocation;
		IJTLocation targetLocation = inTargetLocation;
		IJTTransformationSetup transformerSetup = null;
		JsonNode result = null;
		for ( Iterator<ObjectNode> iter = entries.iterator(); iter.hasNext(); )
		{
			ObjectNode entry = iter.next();
			transformerSetup = factory.resolveTransform( entry );
			IJTTransform transformer = transformerSetup.getTransform();

			result = transformer.transform( transformerSetup.getConfig(),
					sourceLocation, targetLocation );
			if ( targetLocation == null
					|| transformerSetup.getConfig().returnsNewValue() )
				targetLocation = new JTLocation( result );
			else if ( targetLocation != null
					&& transformerSetup.getConfig().shouldMerge() )
				targetLocation.setValue( transformerSetup.getConfig()
						.getTarget(), result );
			sourceLocation = targetLocation;
		}

		return targetLocation.getCurrentValue();
	}

	@Override
	public IJTTransformConfig parseConfig( ObjectNode inNode )
	{
		return new BaseContainerTransformConfig( inNode );
	}

}
