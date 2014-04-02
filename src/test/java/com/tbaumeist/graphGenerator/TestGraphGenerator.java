package com.tbaumeist.graphGenerator;

import static org.junit.Assert.*;

import org.junit.Test;

import com.tbaumeist.common.Topology;


public class TestGraphGenerator{

    public TestGraphGenerator() {
    }

    @Test
    public void nodeCount() throws Exception {
        String[] strArgs = new String[]{"-n", "10", "-d", "3", "-s", "1001"};
        Arguments args = Arguments.Parse(strArgs);
       
       GraphGenerator gen = new GraphGenerator(args);
       Topology top = gen.generateTopology();
       
       assertTrue(top.getAllNodes().size() == args.nodeCount);
    }
    
    /*
    @Test
    public void largeGraph() throws Exception {
        String[] strArgs = new String[]{"-n", "10000", "-d", "15", "-s", "1001"};
        Arguments args = Arguments.Parse(strArgs);
       
       GraphGenerator gen = new GraphGenerator(args);
       Topology top = gen.generateTopology();
       
       assertTrue(top.getAllNodes().size() == args.nodeCount);
    }
    */
}
