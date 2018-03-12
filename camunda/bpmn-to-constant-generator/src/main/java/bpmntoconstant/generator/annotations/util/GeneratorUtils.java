package bpmntoconstant.generator.annotations.util;

import bpmntoconstant.generator.annotations.plugin.BpmMetadataLogger;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class GeneratorUtils {

    public static boolean verifyPackage(String stringPath) {
        Path path = Paths.get(stringPath);
        if (!path.toFile().exists()) {
            try {
                Files.createDirectories(path);
                return true;
            } catch (IOException e) {
                BpmMetadataLogger.addError(String.format("Could not create directory: %s ", stringPath) + e.getMessage());
                return false;
            }
        }
        return true;
    }

}
