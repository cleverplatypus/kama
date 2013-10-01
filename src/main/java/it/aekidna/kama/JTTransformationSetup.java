package it.aekidna.kama;

import it.aekidna.kama.api.IJTTransform;
import it.aekidna.kama.api.IJTTransformConfig;
import it.aekidna.kama.api.IJTTransformationSetup;

public class JTTransformationSetup implements IJTTransformationSetup {

	private IJTTransformConfig config;
	private IJTTransform transform;

	public JTTransformationSetup( IJTTransform inTransform, IJTTransformConfig inConfig )
	{
		config = inConfig;
		transform = inTransform;
	}
	
	@Override
	public IJTTransform getTransform() {
		return transform;
	}

	@Override
	public IJTTransformConfig getConfig() {
		return config;
	}

}
