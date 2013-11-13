package it.aekidna.kama;

import it.aekidna.kama.api.IJTLocation;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang.StringUtils;
import org.codehaus.jackson.JsonNode;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.node.ContainerNode;
import org.codehaus.jackson.node.ObjectNode;

public class JTLocation implements IJTLocation
{

	private final JsonNode baseNode;
	private final String basePath;
	private final List<ContainerNode> ancestry;

	static private ObjectMapper mapper;

	static
	{
		mapper = new ObjectMapper();
	}

	public JTLocation( JsonNode inBaseNode )
	{
		this( inBaseNode, null );
	}

	public JTLocation( JsonNode inBaseNode, String inBasePath )
	{
		this( inBaseNode, inBasePath, null );
	}

	public JTLocation( JsonNode inBaseNode, String inBasePath,
			List<ContainerNode> inAncestry )
	{
		if ( !( inBaseNode instanceof ContainerNode ) && inBasePath != null )
			throw new RuntimeException( "Cannot create a JTLocation with a "
					+ "non container base node and a path" );
		baseNode = inBaseNode;
		basePath = inBasePath;
		ancestry = inAncestry != null ? inAncestry
				: new ArrayList<ContainerNode>();
	}

	@Override
	public IJTLocation getSubLocation( String inPath, boolean inForceCreate )
	{
		if ( inPath == null || inPath.equals( "" ) )
			return new JTLocation( baseNode, basePath, ancestry );
		else
			return new JTLocation( baseNode, inPath, ancestry );
	}

	@Override
	public IJTLocation getSubLocation( List<ContainerNode> inDescendentNodes,
			JsonNode inTip )
	{
		if ( inDescendentNodes == null )
			inDescendentNodes = new ArrayList<ContainerNode>();
		List<ContainerNode> desc = inDescendentNodes.subList( 0,
				inDescendentNodes.size() );
		if ( ancestry != null && ancestry.size() > 0 )
			desc.addAll( 0, ancestry );
		else if ( baseNode instanceof ContainerNode )
			desc.add( 0, (ContainerNode) baseNode );
		return new JTLocation( inTip, null, desc );

	}

	@Override
	public JsonNode getCurrentValue()
	{
		LocationIdentifier tuple = resolveNode( baseNode, basePath, ancestry,
				false );
		return tuple.getValue();
	}

	@Override
	public JsonNode getCurrentValue( String inPath )
	{
		JsonNode base = getCurrentValue();
		if ( !base.isContainerNode() )
			throw new RuntimeException( "\"" + inPath
					+ "\" path applied to a non-container node " );
		LocationIdentifier tuple = resolveNode( base, inPath, ancestry, false );
		return tuple.getValue();
	}

	@Override
	public void setValue( JsonNode inValue )
	{
		LocationIdentifier tuple = resolveNode( baseNode, basePath, ancestry,
				true );
		( (ObjectNode) tuple.getBaseNode() ).put( tuple.getDestination()
				.toString(), inValue );
	}

	@Override
	public void setValue( String inPath, JsonNode inValue )
	{
		JsonNode base = getCurrentValue();
		if ( !base.isContainerNode() )
			throw new RuntimeException( "\"" + inPath
					+ "\" path applied to a non-container node " );
		LocationIdentifier tuple = resolveNode( base, inPath, ancestry, true );
		( (ObjectNode) tuple.getBaseNode() ).put( tuple.getDestination()
				.toString(), inValue );
	}

	public static LocationIdentifier resolveNode( JsonNode inBaseNode,
			String inPath, List<ContainerNode> inAncestry, boolean inCreate )
	{
		String[] segments = inPath != null && !inPath.equals( "" )
				&& !inPath.equals( "." ) ? StringUtils.split( inPath, "/" )
				: null;
		if ( segments == null )
			return new LocationIdentifier( inBaseNode, null, inAncestry );
		else
		{
			return descend( (ContainerNode) inBaseNode, segments, inAncestry,
					inCreate );
		}
	}

	private static LocationIdentifier descend( ContainerNode inContainer,
			String[] segments, List<ContainerNode> inAncestry, boolean inCreate )
	{
		if ( segments.length == 1 )
		{
			return new LocationIdentifier( inContainer, segments[ 0 ],
					inAncestry );
		}
		else
		{
			if ( segments[ 0 ].equals( ".." ) )
			{
				if ( inAncestry == null || inAncestry.size() == 0 )
					throw new RuntimeException( "invalid parent path" );
				return descend( inAncestry.get( 0 ),
						(String[]) ArrayUtils.subarray( segments, 1,
								segments.length ), inAncestry.subList( 0,
								inAncestry.size() - 1 ), inCreate );
			}
			if ( !inContainer.has( segments[ 0 ] ) )
			{
				if ( inCreate )
					( (ObjectNode) inContainer ).put( segments[ 0 ],
							mapper.createObjectNode() );
				else
					throw new RuntimeException( "invalid path \""
							+ segments[ 0 ] + "\" in " + inContainer.toString() );
			}
			List<ContainerNode> subAncestry = inAncestry.subList( 0,
					inAncestry.size() ); // duplicating
			subAncestry.add( inContainer );
			inContainer = (ObjectNode) inContainer.get( segments[ 0 ] );
			return descend( inContainer, (String[]) ArrayUtils.subarray(
					segments, 1, segments.length ), subAncestry, inCreate );
		}
	}

	@Override
	public IJTLocation swapTip( JsonNode inNewTip )
	{
		return new JTLocation( inNewTip, null, ancestry );
	}

	@Override
	public boolean pathExists( String inPath )
	{
		try
		{
			return resolveNode( baseNode, inPath, ancestry, false ) != null;
		}
		catch ( Exception exc )
		{
			return false;
		}
	}

}

class LocationIdentifier
{
	private final JsonNode baseNode;
	private final Object destination;
	private final List<ContainerNode> ancestry;

	public LocationIdentifier( JsonNode inBaseNode, Object inDestination,
			List<ContainerNode> inAncestry )
	{
		destination = inDestination;
		baseNode = inBaseNode;
		ancestry = inAncestry;
	}

	public List<ContainerNode> getAncestry()
	{
		return ancestry;
	}

	public JsonNode getBaseNode()
	{
		return baseNode;
	}

	public Object getDestination()
	{
		return destination;
	}

	public JsonNode getValue()
	{
		return destination != null ? baseNode.get( (String) destination )
				: baseNode;
	}

}
