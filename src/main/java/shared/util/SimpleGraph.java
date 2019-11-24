package shared.util;

import java.util.Set;

import shared.util.Graph.NoLabel;

/**
 * This class represents a Simple graph that contains no labels.
 * It is just a typedef of the class {@link Graph} with {@link NoLabel} labels.
 * 
 * @param <V> The type of the Vertexes.
 */
public class SimpleGraph<V> extends Graph<V, NoLabel> {


    /** Public Constructor */
    public SimpleGraph() {
        super();
    }


    @Override
    public SimpleGraph<V> getPathConnecting2Nodes(V startDataNode, V endDataNode) {
        return (SimpleGraph<V>) super.getPathConnecting2Nodes(startDataNode, endDataNode);
    }
    
    @Override
    public SimpleGraph<V> subGraph(Set<V> containedData) {
        return (SimpleGraph<V>) super.subGraph(containedData);
    }
}