package bpmntoconstant.generator.annotations.provider;

import bpmntoconstant.generator.annotations.plugin.BpmMetadataLogger;
import bpmntoconstant.generator.annotations.processors.EnableBpmnMetadataConstantGeneratorProcessor;
import bpmntoconstant.generator.annotations.util.GeneratorUtils;
import org.springframework.core.io.Resource;

import java.util.Collection;

public abstract class AbstractTemplateProvider {

    private String postfix;

    public AbstractTemplateProvider(String postfix) {
        this.postfix = postfix;
    }

    public void initializeCreation(String path, String packageName, Collection<Resource> candidates) {
        int generatedCount = 0;

        if (!GeneratorUtils.verifyPackage(path)) {
            return;
        }

        for (Resource bpmnFile : candidates) {
            BpmMetadataLogger.info("Generating Constant From Bpmn File: " + bpmnFile);
            if (createHelper(path, bpmnFile, this.postfix, packageName)) {
                generatedCount++;
            }
        }

        BpmMetadataLogger.plusGenerated(generatedCount);
    }

    private boolean createHelper(String path, Resource bpmnResource, String postfix, String packageName) {
        EnableBpmnMetadataConstantGeneratorProcessor processor = new EnableBpmnMetadataConstantGeneratorProcessor();
        processor.generateMetadataConstantSourceFile(path, packageName, bpmnResource, postfix);
        return true;
    }

}
