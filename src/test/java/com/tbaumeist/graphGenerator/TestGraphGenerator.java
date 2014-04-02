package com.tbaumeist.graphGenerator;

import static org.junit.Assert.*;

import java.io.File;
import java.io.PrintStream;

import org.junit.Test;

import com.tbaumeist.common.Topology;
import com.tbaumeist.common.testing.TestHelper;


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
    
    @Test
    public void saveFileSW() throws Exception {
        
        File temp = File.createTempFile("random_graph", ".dot");
        String[] strArgs = new String[]{"-n", "10", "-d", "3", "-s", "1001", "-o", temp.getAbsolutePath()};
        Arguments args = Arguments.Parse(strArgs);
       
       GraphGenerator gen = new GraphGenerator(args);
       gen.run();
       
       assertTrue(TestHelper.filesAreEqual(temp.getAbsolutePath(), TestHelper.getResourcePath("10_3_S1001_smallWorld.dot")));
       temp.delete();
    }
    
    @Test
    public void saveFileRandom() throws Exception {
        
        File temp = File.createTempFile("random_graph", ".dot");
        String[] strArgs = new String[]{"-n", "10", "-d", "3", "-s", "1001", "-lt", "RANDOM", "-o", temp.getAbsolutePath()};
        Arguments args = Arguments.Parse(strArgs);
       
       GraphGenerator gen = new GraphGenerator(args);
       gen.run();
       
       assertTrue(TestHelper.filesAreEqual(temp.getAbsolutePath(), TestHelper.getResourcePath("10_3_S1001_random.dot")));
       temp.delete();
    }
    
    @Test
    public void saveSTDOUTRandom() throws Exception {
        
        PrintStream originalOut = System.out;
        
        File temp = File.createTempFile("random_graph", ".dot");
        PrintStream fileOut = new PrintStream(temp);
        
        System.setOut(fileOut);
        
        String[] strArgs = new String[]{"-n", "10", "-d", "3", "-s", "1001", "-lt", "RANDOM"};
        Arguments args = Arguments.Parse(strArgs);
       
       GraphGenerator gen = new GraphGenerator(args);
       gen.run();
       
       System.setOut(originalOut); // restore STDOUT
       
       assertTrue(TestHelper.filesAreEqual(temp.getAbsolutePath(), TestHelper.getResourcePath("10_3_S1001_random.dot")));
       temp.delete();
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
