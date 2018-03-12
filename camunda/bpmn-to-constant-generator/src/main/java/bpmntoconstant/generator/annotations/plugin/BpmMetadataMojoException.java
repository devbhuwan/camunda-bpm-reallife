package bpmntoconstant.generator.annotations.plugin;

import org.apache.maven.plugin.MojoExecutionException;

public class BpmMetadataMojoException extends MojoExecutionException {

    public BpmMetadataMojoException() {
        super("");
        BpmMetadataLogger.printGeneratedTables(true);
    }

    public BpmMetadataMojoException(String message) {
        super(message);
        BpmMetadataLogger.printGeneratedTables(true);
    }

    public BpmMetadataMojoException(String message, Throwable cause) {
        super(message, cause);
        BpmMetadataLogger.printGeneratedTables(true);
    }
}