package it.aekidna.kama;

import it.aekidna.kama.api.IJTLocation;
import it.aekidna.kama.api.IJTTransformConfig;

import org.codehaus.jackson.JsonNode;
import org.codehaus.jackson.node.ObjectNode;

public class DummyTransform extends AbstractBaseTransform
{

	@Override
	public JsonNode transform( IJTTransformConfig inConfig,
			IJTLocation inSourceLocation, IJTLocation inTargetLocation )
	{
		return inSourceLocation.getCurrentValue();
	}

	@Override
	public IJTTransformConfig parseConfig( ObjectNode inNode )
	{

		return new BaseTransformConfig( inNode );
	}

}
