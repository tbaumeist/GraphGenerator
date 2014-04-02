package com.tbaumeist.graphGenerator;

import java.util.logging.Logger;

import com.tbaumeist.common.logging.LoggingManager;

public class Main {
    
    private static final Logger LOGGER = Logger.getLogger(Main.class
            .getName());

    /**
     * @param args
     */
    public static void main(String[] args) {
        try {
            // Setup the logger
            LoggingManager.initialize();
            
            LOGGER.info("Starting random graph generator");

            // Read command line arguments
            Arguments arguments = Arguments.Parse(args);
            if(arguments == null)
                return;
            
            // Generate the graph
            new GraphGenerator(arguments).run();
            
            LOGGER.info("Exiting random graph generator");
        } catch (Exception ex) {
            LOGGER.severe(ex.getMessage());
        }

    }

}
