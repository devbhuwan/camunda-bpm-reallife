package bpmntoconstant.generator.annotations.plugin;

import bpmntoconstant.generator.annotations.util.Constants;
import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugins.annotations.Component;
import org.apache.maven.plugins.annotations.Parameter;
import org.apache.maven.project.MavenProject;

public abstract class CommonsMojo extends AbstractMojo {

    @Parameter(name = Constants.CONSTANT_CLASS_POSTFIX, defaultValue = "Constants")
    protected String constantClassPostfix;

    @Parameter(name = Constants.PACKAGE_NAME, defaultValue = "bpmn.metadata")
    protected String packageName;

    @Parameter(name = Constants.OVERWRITE, defaultValue = "false")
    protected Boolean overwrite;

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
            case Constants.PACKAGE_NAME:
                if (constantClassPostfix == null) {
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
