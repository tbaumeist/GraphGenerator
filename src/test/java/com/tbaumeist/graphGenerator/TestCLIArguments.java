package com.tbaumeist.graphGenerator;

import static org.junit.Assert.*;

import java.nio.file.Files;
import java.nio.file.Paths;

import org.junit.Test;

import com.tbaumeist.graphGenerator.Enums.DEGREE_TYPE;
import com.tbaumeist.graphGenerator.Enums.LINK_TYPE;

public class TestCLIArguments{

    public TestCLIArguments() {
    }

    @Test
    public void correctOne() throws Exception {
        
       String[] strArgs = new String[]{"-n", "10", "-d", "3", "-s", "1001"};
       Arguments args = Arguments.Parse(strArgs);
       
       assertTrue(true);
       assertTrue(args.degreeCount == 3);
       assertTrue(args.nodeCount == 10);
       assertTrue(args.seed == 1001);
       assertTrue(args.logFileLocation.equals(""));
    }
    
    @Test
    public void correctTwo() throws Exception {
        
       String[] strArgs = new String[]{"-n", "10", "-d", "3", "-s", "1001", "-o", "test.dot", "-f", "test.log"};
       Arguments args = Arguments.Parse(strArgs);
       
       args.outputFile.close();
       Files.delete(Paths.get("test.dot"));
       
       assertTrue(true);
       assertTrue(args.degreeCount == 3);
       assertTrue(args.nodeCount == 10);
       assertTrue(args.seed == 1001);
       assertTrue(args.logFileLocation.equals("test.log"));
    }
    
    @Test
    public void correctThree() throws Exception {
        
       String[] strArgs = new String[]{"-n", "10", "-d", "3", "-s", "1001", "-lt", "SMALL_WORLD", "-dt", "FIXED"};
       Arguments args = Arguments.Parse(strArgs);
       
       assertTrue(true);
       assertTrue(args.degreeCount == 3);
       assertTrue(args.nodeCount == 10);
       assertTrue(args.seed == 1001);
       assertTrue(args.logFileLocation.equals(""));
       assertTrue(args.linkType == LINK_TYPE.SMALL_WORLD);
       assertTrue(args.degreeType == DEGREE_TYPE.FIXED);
    }
    
    @Test
    public void correctFour() throws Exception {
        
       String[] strArgs = new String[]{"-n", "10", "-d", "3", "-s", "1001", "-lt", "RANDOM", "-dt", "POISSON"};
       Arguments args = Arguments.Parse(strArgs);
       
       assertTrue(true);
       assertTrue(args.degreeCount == 3);
       assertTrue(args.nodeCount == 10);
       assertTrue(args.seed == 1001);
       assertTrue(args.logFileLocation.equals(""));
       assertTrue(args.linkType == LINK_TYPE.RANDOM);
       assertTrue(args.degreeType == DEGREE_TYPE.POISSON);
    }
    
    @Test
    public void correctFive() throws Exception {
        
       String[] strArgs = new String[]{"-n", "10", "-d", "3", "-s", "1001", "-lt", "rAnDoM", "-dt", "pOiSsOn"};
       Arguments args = Arguments.Parse(strArgs);
       
       assertTrue(true);
       assertTrue(args.degreeCount == 3);
       assertTrue(args.nodeCount == 10);
       assertTrue(args.seed == 1001);
       assertTrue(args.logFileLocation.equals(""));
       assertTrue(args.linkType == LINK_TYPE.RANDOM);
       assertTrue(args.degreeType == DEGREE_TYPE.POISSON);
    }
    
    @Test
    public void help() throws Exception {
        
       String[] strArgs = new String[]{"-n", "10", "-d", "3", "-s", "1001", "-lt", "rAnDoM", "-dt", "pOiSsOn", "-h"};
       Arguments args = Arguments.Parse(strArgs);
       
       assertTrue(true);
       assertTrue(args == null);
    }
    
    @Test
    public void noNodeCount() throws Exception {
        
       String[] strArgs = new String[]{"-d", "3"};
       try{
           Arguments.Parse(strArgs);
       }catch(Exception ex){
           assertTrue(true);
           return;
       }
       assertTrue(false);
    }
    
    @Test
    public void noDegreeCount() throws Exception {
        
       String[] strArgs = new String[]{"-n", "10"};
       try{
           Arguments.Parse(strArgs);
       }catch(Exception ex){
           assertTrue(true);
           return;
       }
       assertTrue(false);
    }

}
