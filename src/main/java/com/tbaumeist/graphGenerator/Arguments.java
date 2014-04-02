package com.tbaumeist.graphGenerator;

import java.util.logging.Level;

import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.GnuParser;
import org.apache.commons.cli.HelpFormatter;
import org.apache.commons.cli.Option;
import org.apache.commons.cli.Options;

import com.tbaumeist.graphGenerator.Enums.DEGREE_TYPE;
import com.tbaumeist.graphGenerator.Enums.LINK_TYPE;

public class Arguments {

    public String logFileLocation = "";
    public String outputFile = "";
    
    public Level logLevel = Level.INFO;
    
    public int seed = 0;
    public int nodeCount = 0;
    public int degreeCount = 0;
    
    public DEGREE_TYPE degreeType = DEGREE_TYPE.FIXED;
    public LINK_TYPE linkType = LINK_TYPE.SMALL_WORLD;

    private static Option OPT_RANDOM_SEED = new Option("s", "seed", true,
            "The seed for the random number generator (integer). [default] random seed value.");
    private static Option OPT_HELP = new Option("h", "help", false,
            "Print program help manual.");
    private static Option OPT_LOG_FILE = new Option("f", "log-file", true,
            "File name to save log file to. [default] no log file is generated.");
    private static Option OPT_NODE_COUNT = new Option("n", "node-count", true,
            "Total number of nodes to put in the graph.");
    private static Option OPT_DEGREE_COUNT = new Option("d", "degree-count",
            true, "The degree the random graph should have.");
    private static Option OPT_DEGREE_TYPE = new Option("dt", "degree-type",
            true, "Overwrote in generateOptions method");
    private static Option OPT_LINK_TYPE = new Option("lt", "link-type", true,
            "Overwrote in generateOptions method");
    private static Option OPT_GRAPH_OUTPUT = new Option("o", "output", true,
            "File to save output graph to. [default] graph written to stdout.");

    protected Arguments() {
    }

    public static Arguments Parse(String[] args) throws Exception {
        Arguments arguments = new Arguments();
        
        final Options options = generateOptions();
        final CommandLineParser parser = new GnuParser();
        final CommandLine cmd = parser.parse(options, args);

        if (cmd.hasOption(OPT_HELP.getLongOpt())) {
            HelpFormatter formatter = new HelpFormatter();
            formatter.printHelp(
                    "java -jar GraphGenerator-0.0.1-jar-with-dependencies.jar",
                    options);
            return null;
        }
        
        if(!cmd.hasOption(OPT_NODE_COUNT.getLongOpt()))
            throw new Exception("-"+ OPT_NODE_COUNT.getOpt() + " option is required!");
        if(!cmd.hasOption(OPT_DEGREE_COUNT.getLongOpt()))
            throw new Exception("-"+ OPT_DEGREE_COUNT.getOpt() + " option is required!");
        
        if(cmd.hasOption(OPT_LOG_FILE.getLongOpt()))
            arguments.logFileLocation = cmd.getOptionValue(OPT_LOG_FILE.getLongOpt());

        arguments.seed = Integer.parseInt(cmd.getOptionValue(OPT_RANDOM_SEED.getLongOpt(), Integer.toString((int) System.currentTimeMillis())));
        
        arguments.nodeCount = Integer.parseInt(cmd.getOptionValue(OPT_NODE_COUNT.getLongOpt()));
        
        arguments.degreeCount = Integer.parseInt(cmd.getOptionValue(OPT_DEGREE_COUNT.getLongOpt()));
        
        if(cmd.hasOption(OPT_DEGREE_TYPE.getLongOpt()))
            arguments.degreeType = DEGREE_TYPE.valueOf(cmd.getOptionValue(OPT_DEGREE_TYPE.getLongOpt()).toUpperCase());

        if(cmd.hasOption(OPT_LINK_TYPE.getLongOpt()))
            arguments.linkType = LINK_TYPE.valueOf(cmd.getOptionValue(OPT_LINK_TYPE.getLongOpt()).toUpperCase());
        
        if(cmd.hasOption(OPT_GRAPH_OUTPUT.getLongOpt()))
            arguments.outputFile = cmd.getOptionValue(OPT_GRAPH_OUTPUT.getLongOpt());
        
        return arguments;
    }

    private static Options generateOptions() {
        Options options = new Options();
        StringBuilder descBuilder = null;

        descBuilder = new StringBuilder(
                "The type of degree distribution to use. [default] "
                        + Enums.DEGREE_TYPE.FIXED.name() + " [options]");
        for (DEGREE_TYPE type : DEGREE_TYPE.values()) {
            descBuilder.append(" ").append(type);
        }
        OPT_DEGREE_TYPE.setDescription(descBuilder.toString());

        descBuilder = new StringBuilder(
                "The type of node links to use. [default] "
                        + Enums.LINK_TYPE.SMALL_WORLD.name() + " [options]");
        for (LINK_TYPE type : LINK_TYPE.values()) {
            descBuilder.append(" ").append(type);
        }
        OPT_LINK_TYPE.setDescription(descBuilder.toString());
        
        //OPT_NODE_COUNT.setRequired(true);
        //OPT_DEGREE_COUNT.setRequired(true);

        options.addOption(OPT_HELP);
        options.addOption(OPT_LOG_FILE);
        options.addOption(OPT_RANDOM_SEED);
        options.addOption(OPT_NODE_COUNT);
        options.addOption(OPT_DEGREE_COUNT);
        options.addOption(OPT_DEGREE_TYPE);
        options.addOption(OPT_LINK_TYPE);
        options.addOption(OPT_GRAPH_OUTPUT);
        
        return options;
    }

}
