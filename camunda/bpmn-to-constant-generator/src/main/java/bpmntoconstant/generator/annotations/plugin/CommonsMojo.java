package bpmntoconstant.generator.annotations.plugin;

import bpmntoconstant.generator.annotations.util.Constants;
import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugins.annotations.Component;
import org.apache.maven.plugins.annotations.Parameter;
import org.apache.maven.project.MavenProject;

import java.io.File;

public abstract class CommonsMojo extends AbstractMojo {

    @Parameter(name = Constants.CONSTANT_CLASS_POSTFIX, defaultValue = "Constants")
    protected String constantClassPostfix;
    @Parameter(name = Constants.DEFAULT_ROOT_PACKAGE, defaultValue = "bpmn.metadata")
    protected String defaultRootPackage;
    @Parameter(name = Constants.OVERWRITE, defaultValue = "false")
    protected Boolean overwrite;
    @Parameter(name = Constants.SOURCE_BPMN_FILES_CONSTANT_GENERATED_DIRECTOR, defaultValue = "target/generated-sources/annotations")
    protected File sourceBpmnFilesConstantGeneratedDirector;
    @Parameter(name = Constants.TEST_BPMN_FILES_CONSTANT_GENERATED_DIRECTOR, defaultValue = "target/generated-test-sources/test-annotations")
    protected File testBpmnFilesConstantGeneratedDirector;
    @Parameter(name = Constants.AUTO_COMPILE_AND_COPY_INTO_BUILD_DIRECTORY, defaultValue = "true")
    protected Boolean autoCompileAndCopyIntoBuildDirectory;

    @Component
    protected MavenProject project;

    public void validateField(String parameter) throws BpmMetadataMojoException {
        boolean errorFound = Boolean.FALSE;
        switch (parameter) {
            case Constants.CONSTANT_CLASS_POSTFIX:
                if (constantClassPostfix == null) {
                    errorFound = Boolean.TRUE;
                }
                break;
            case Constants.DEFAULT_ROOT_PACKAGE:
                if (constantClassPostfix == null) {
                    errorFound = Boolean.TRUE;
                }
                break;
            case Constants.OVERWRITE:
                if (overwrite == null) {
                    errorFound = Boolean.TRUE;
                }
                break;
            case Constants.SOURCE_BPMN_FILES_CONSTANT_GENERATED_DIRECTOR:
                if (sourceBpmnFilesConstantGeneratedDirector == null) {
                    errorFound = Boolean.TRUE;
                }
                break;
            case Constants.TEST_BPMN_FILES_CONSTANT_GENERATED_DIRECTOR:
                if (testBpmnFilesConstantGeneratedDirector == null) {
                    errorFound = Boolean.TRUE;
                }
                break;
            case Constants.AUTO_COMPILE_AND_COPY_INTO_BUILD_DIRECTORY:
                if (autoCompileAndCopyIntoBuildDirectory == null) {
                    errorFound = Boolean.TRUE;
                }
                break;
            default:
                BpmMetadataLogger.addError(String.format("%s configuration parameter not found!", parameter));
                throw new BpmMetadataMojoException();
        }

        if (errorFound) {
            BpmMetadataLogger.addError(String.format("%s configuration not found!", parameter));
            throw new BpmMetadataMojoException();
        }
    }

}
