package com.tbaumeist.graphGenerator.link;

import org.apache.commons.math3.random.RandomGenerator;

import com.tbaumeist.common.Node;

import java.util.List;

/**
 * Generates link lengths with uniform / flat probability. Terrible
 * distribution.
 */
public class UniformLinkSource extends LinkLengthSource {

    public UniformLinkSource(RandomGenerator random, List<Node> nodes) {
        super(random, nodes);

        assert nodes.size() > 1;
    }

    @Override
    public Node getPeer(Node from) {
        return this.nodes.get(random.nextInt(this.nodes.size()));
    }

}