package com.tbaumeist.graphGenerator;

import java.io.File;
import java.io.FileOutputStream;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import org.apache.commons.math3.random.MersenneTwister;
import org.apache.commons.math3.random.RandomGenerator;

import com.tbaumeist.common.Node;
import com.tbaumeist.common.Topology;
import com.tbaumeist.common.dataFileWriters.TopologyFileWriterDOT;
import com.tbaumeist.common.logging.LoggingManager;
import com.tbaumeist.graphGenerator.Enums.DEGREE_TYPE;
import com.tbaumeist.graphGenerator.Enums.LINK_TYPE;
import com.tbaumeist.graphGenerator.degree.DegreeSource;
import com.tbaumeist.graphGenerator.degree.FixedDegreeSource;
import com.tbaumeist.graphGenerator.degree.PoissonDegreeSource;
import com.tbaumeist.graphGenerator.link.KleinbergLinkSource;
import com.tbaumeist.graphGenerator.link.LinkLengthSource;
import com.tbaumeist.graphGenerator.link.UniformLinkSource;

public class GraphGenerator implements Runnable {

    private static final Logger LOGGER = Logger.getLogger(GraphGenerator.class
            .getName());

    private final Arguments args;
    private RandomGenerator rand = null;

    private final int MAXIMUM_CONNECT_TRIES = 10000;

    public GraphGenerator(Arguments args) throws Exception {
        this.args = args;
        LoggingManager.addLogFile(args.logFileLocation, args.logLevel);
        
        LOGGER.info("ARGUMENTS:\n" + args.toString());
    }

    public void run() {

        try {

            Topology top = this.generateTopology();
            
            // topology should be complete now
            // write out the topology file
            this.writeDOTFile(top, args.outputFile);

        } catch (Exception ex) {
            LOGGER.severe(ex.getMessage());
        }
    }
    
    public Topology generateTopology(){
        LOGGER.info("Random seed used " + args.seed);
        // configure random number generator
        this.rand = new MersenneTwister(args.seed);
        
        DegreeSource degreeSrc = this.getDegreeSource(args.degreeCount,
                args.degreeType);
        
        LOGGER.info("Degree type: " + degreeSrc.getClass().getSimpleName());

        // create only the nodes
        Topology top = this.generateNodes(args.nodeCount, degreeSrc);
        
        LOGGER.info("Number of nodes generated: " + top.getAllNodes().size());

        // connect the nodes together
        LinkLengthSource linkSrc = this.getLinkLengthSource(
                top.getAllNodes(), args.linkType);
        
        LOGGER.info("Link type: " + linkSrc.getClass().getSimpleName());
        
        this.generateLinks(top, linkSrc);
        return top;
    }

    private Topology generateNodes(int nNodes, DegreeSource degreeSrc) {
        Topology top = new Topology();

        List<Node> nodes = new ArrayList<Node>(nNodes);
        for (int i = 0; i < nNodes; i++){
            Node n = new Node((1.0 * i) / nNodes, "N" +i);
            n.setDegreeTarget(degreeSrc.getDegree());
            nodes.add(n);
        }

        top.addAllNodes(nodes);
        return top;
    }

    private void generateLinks(Topology top, LinkLengthSource linkSrc) {

        // First create a lattice (Ring topology)
        for(int i =0; i < top.getAllNodes().size(); i++) {
            Node n1 = top.getAllNodes().get(i);
            Node n2 = top.getAllNodes().get(i+1); // can get away with this because the list is circular :)
            n1.addNeighbor(n2);
            n2.addNeighbor(n1);
        }
        
        // TODO: Modifications to this have caused an increase in resources
        // required to run simulator
        for (Node src : top.getAllNodes()) {
            //System.out.println("Node " + count++);
            
            if (src.atDegree())
                continue;
            
            // Make connections until at desired degree or timeout
            int connectionTries = 0;
            while (!src.atDegree() && connectionTries++ < MAXIMUM_CONNECT_TRIES) {
                Node destination = linkSrc.getPeer(src);
                if (src == destination || src.hasDirectNeighbor(destination)
                        || destination.atDegree())
                    continue;
                src.addNeighbor(destination);
                destination.addNeighbor(src);
            }
            
            if(connectionTries >= MAXIMUM_CONNECT_TRIES)
                LOGGER.info("Failed to commplely connect: " + src.getID() + ", Target Degree: " + src.getDegreeTarget() + ", Actual Degree: " + src.getDirectNeighbors().size());
        }
    }

    private DegreeSource getDegreeSource(int degreeCount, DEGREE_TYPE degreeTyp) {

        switch (degreeTyp) {
        case FIXED:
            return new FixedDegreeSource(degreeCount);
        case POISSON:
            return new PoissonDegreeSource(degreeCount);
        }
        return null;
    }

    private LinkLengthSource getLinkLengthSource(List<Node> nodes,
            LINK_TYPE linkTyp) {

        switch (linkTyp) {
        case SMALL_WORLD:
            return new KleinbergLinkSource(this.rand, nodes);
        case RANDOM:
            return new UniformLinkSource(this.rand, nodes);
        }
        return null;
    }
    
    private void writeDOTFile(Topology top, String outPutFile) throws Exception{
        TopologyFileWriterDOT topWriter = new TopologyFileWriterDOT();
        
        if (outPutFile == null || outPutFile.isEmpty()){
            topWriter.writeDot(top, System.out);
            LOGGER.info("Wrote topology to STDOUT");
        } else {
            PrintStream output = new PrintStream(new FileOutputStream(new File(
                    outPutFile)));
            topWriter.writeDot(top, output);
            output.close();
            LOGGER.info("Wrote topology to " + args.outputFile);
        }
    }
}
