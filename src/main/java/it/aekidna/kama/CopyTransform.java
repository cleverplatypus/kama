package it.aekidna.kama;

import it.aekidna.kama.api.IJTLocation;
import it.aekidna.kama.api.IJTTransformConfig;

import org.codehaus.jackson.JsonNode;
import org.codehaus.jackson.node.ObjectNode;

public class CopyTransform extends AbstractBaseTransform {

	@Override
	public JsonNode transform(IJTTransformConfig inConfig,
			IJTLocation inSourceLocation, IJTLocation inTargetLocation) {
		JsonNode out = inSourceLocation.getCurrentValue( inConfig.getSource());
		return out;
	}
	
	@Override
	public IJTTransformConfig parseConfig(ObjectNode inNode) {
		
		return new BaseTransformConfig( inNode );
	}

}
