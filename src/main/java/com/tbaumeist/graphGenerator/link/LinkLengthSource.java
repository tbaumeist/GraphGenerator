package com.tbaumeist.graphGenerator.link;

import org.apache.commons.math3.random.RandomGenerator;

import com.tbaumeist.common.Node;

import java.util.List;

public abstract class LinkLengthSource {

    protected final RandomGenerator random;
    protected final List<Node> nodes;

    /**
     * @param random
     *            To make decisions.
     * @param nodes
     *            Nodes which make up the network being wired.
     */
    LinkLengthSource(RandomGenerator random, List<Node> nodes) {
        this.random = random;
        this.nodes = nodes;
        
        assert this.random != null;
        assert this.nodes != null;
        assert !this.nodes.isEmpty();
    }

    /**
     * Find a suitable peer which fits this distribution. Assumes that node
     * locations do not change during generation.
     * 
     * @param from
     *            Node to form a link from.
     * @return a node for which the link length matches the link length
     *         distribution scheme.
     */
    public abstract Node getPeer(Node from);
}
