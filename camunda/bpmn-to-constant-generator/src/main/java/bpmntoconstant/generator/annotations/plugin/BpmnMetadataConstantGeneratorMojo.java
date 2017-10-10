package bpmntoconstant.generator.annotations.plugin;

import bpmntoconstant.generator.annotations.provider.BpmnResourceScanningProvider;
import bpmntoconstant.generator.annotations.support.BpmnConstantTemplateSupport;
import bpmntoconstant.generator.annotations.util.Constants;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugin.MojoFailureException;
import org.apache.maven.plugins.annotations.Execute;
import org.apache.maven.plugins.annotations.LifecyclePhase;
import org.apache.maven.plugins.annotations.Mojo;


@Mojo(name = BpmnMetadataConstantGeneratorMojo.MOJO_GOAL)
@Execute(phase = LifecyclePhase.PROCESS_SOURCES)
public class BpmnMetadataConstantGeneratorMojo extends CommonsMojo {

    public static final String MOJO_GOAL = "generate-bpmn-constants";

    @Override
    public void execute() throws MojoExecutionException, MojoFailureException {
        BpmMetadataLogger.configure(getLog());
        this.validateField(Constants.CONSTANT_CLASS_POSTFIX);
        this.validateField(Constants.DEFAULT_ROOT_PACKAGE);
        this.validateField(Constants.OVERWRITE);
        this.validateField(Constants.AUTO_COMPILE_AND_COPY_INTO_BUILD_DIRECTORY);
        this.validateField(Constants.SOURCE_BPMN_FILES_CONSTANT_GENERATED_DIRECTOR);
        this.validateField(Constants.TEST_BPMN_FILES_CONSTANT_GENERATED_DIRECTOR);
        try {

            BpmnResourceScanningProvider bpmnResourceScanningProvider = new BpmnResourceScanningProvider(project.getResources());
            BpmnResourceScanningProvider testBpmnResourceScanningProvider = new BpmnResourceScanningProvider(project.getTestResources());

            BpmnConstantTemplateSupport bpmnConstantTemplateSupport = new BpmnConstantTemplateSupport(constantClassPostfix);

            bpmnConstantTemplateSupport.initializeCreation(sourceBpmnFilesConstantGeneratedDirector.getAbsolutePath(), defaultRootPackage, bpmnResourceScanningProvider.getCandidates());
            bpmnConstantTemplateSupport.initializeCreation(testBpmnFilesConstantGeneratedDirector.getAbsolutePath(), defaultRootPackage, testBpmnResourceScanningProvider.getCandidates());

            BpmMetadataLogger.printGeneratedTables(true);

        } catch (Exception e) {
            BpmMetadataLogger.addError(e.getMessage());
            throw new BpmMetadataMojoException(e.getMessage(), e);
        }
    }

}
