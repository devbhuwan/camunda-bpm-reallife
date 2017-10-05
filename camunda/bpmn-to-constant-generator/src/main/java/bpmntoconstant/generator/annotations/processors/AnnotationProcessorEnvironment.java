package bpmntoconstant.generator.annotations.processors;

import lombok.Getter;
import org.apache.commons.lang3.StringUtils;

import static java.io.File.separator;

/**
 * @author Bhuwan Prasad Upadhyay
 */
@Getter
class AnnotationProcessorEnvironment {

    private String sourceGeneratedOutputDirectory;
    private String testSourceGeneratedOutputDirectory;
    private String sourceResourcesDirectory;
    private String testSourceResourcesDirectory;

    public AnnotationProcessorEnvironment() {
        this.testSourceResourcesDirectory = buildResourcesDirectory("test");
        this.sourceResourcesDirectory = buildResourcesDirectory("main");
        this.sourceGeneratedOutputDirectory = buildGeneratedOutputDirectory("generated-sources" + separator + "annotations");
        this.testSourceGeneratedOutputDirectory = buildGeneratedOutputDirectory("generated-test-sources" + separator + "test-annotations");
    }

    private String buildGeneratedOutputDirectory(String outputDirectory) {
        return projectRootDirectory() + separator + "target" + separator + outputDirectory;
    }

    private String projectRootDirectory() {
        String projectDirectory = System.getProperty("bpmn.metadata.project.basedir");
        return StringUtils.isNotBlank(projectDirectory) ? projectDirectory : System.getProperty("user.dir");
    }

    private String buildResourcesDirectory(String typeDir) {
        return "file:" + projectRootDirectory() + separator + "src" + separator + typeDir + separator + "resources" + separator + "**" + separator + "*.bpmn";
    }

}
