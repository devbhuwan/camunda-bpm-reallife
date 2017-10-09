package bpmntoconstant.generator.annotations.plugin;

import bpmntoconstant.generator.annotations.provider.BpmnResourceScanningProvider;
import bpmntoconstant.generator.annotations.support.BpmnConstantTemplateSupport;
import bpmntoconstant.generator.annotations.util.Constants;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugin.MojoFailureException;
import org.apache.maven.plugins.annotations.Execute;
import org.apache.maven.plugins.annotations.LifecyclePhase;
import org.apache.maven.plugins.annotations.Mojo;


@Mojo(name = "bpmnConstants")
@Execute(phase = LifecyclePhase.PROCESS_SOURCES)
public class BpmnMetadataConstantGeneratorMojo extends CommonsMojo {

    @Override
    public void execute() throws MojoExecutionException, MojoFailureException {
        BpmMetadataLogger.configure(getLog());
        this.validateField(Constants.CONSTANT_CLASS_POSTFIX);
        this.validateField(Constants.PACKAGE_NAME);
        try {

            BpmnResourceScanningProvider bpmnResourceScanningProvider = new BpmnResourceScanningProvider(project.getResources());
            BpmnResourceScanningProvider testBpmnResourceScanningProvider = new BpmnResourceScanningProvider(project.getTestResources());

            BpmnConstantTemplateSupport bpmnConstantTemplateSupport = new BpmnConstantTemplateSupport(constantClassPostfix);

            bpmnConstantTemplateSupport.initializeCreation("target/n", packageName, bpmnResourceScanningProvider.getCandidates());
            bpmnConstantTemplateSupport.initializeCreation("target/test", packageName, testBpmnResourceScanningProvider.getCandidates());

            BpmMetadataLogger.printGeneratedTables(true);

        } catch (Exception e) {
            BpmMetadataLogger.addError(e.getMessage());
            throw new BpmMetadataMojoException(e.getMessage(), e);
        }
    }

}
