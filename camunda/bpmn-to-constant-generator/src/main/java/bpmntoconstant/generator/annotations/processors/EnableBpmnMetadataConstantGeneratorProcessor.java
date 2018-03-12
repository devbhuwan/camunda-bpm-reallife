package bpmntoconstant.generator.annotations.processors;

import bpmntoconstant.generator.annotations.plugin.BpmMetadataLogger;
import com.squareup.javapoet.JavaFile;
import com.squareup.javapoet.TypeSpec;
import org.camunda.bpm.model.bpmn.Bpmn;
import org.camunda.bpm.model.bpmn.BpmnModelInstance;
import org.camunda.bpm.model.bpmn.instance.*;
import org.camunda.bpm.model.bpmn.instance.Error;
import org.camunda.bpm.model.bpmn.instance.Process;
import org.springframework.core.io.Resource;

import javax.lang.model.element.Modifier;
import java.nio.file.Paths;
import java.util.*;

import static bpmntoconstant.generator.annotations.processors.JavaSourceFileHelper.buildClassName;
import static bpmntoconstant.generator.annotations.processors.MetadataSpecHelper.*;
import static com.squareup.javapoet.JavaFile.builder;

/**
 * @author Bhuwan Prasad Upadhyay
 */
public class EnableBpmnMetadataConstantGeneratorProcessor {

    private static final String IDS = "IDS";
    private static final String VARIABLE_KEYS = "VARIABLE_KEYS";

    private final Map<String, Set<String>> idVariableKeysMap = new HashMap<>();


    private void resetIdVariableKeysMap() {
        idVariableKeysMap.put(IDS, new TreeSet<>());
        idVariableKeysMap.put(VARIABLE_KEYS, new TreeSet<>());
    }


    public void generateMetadataConstantSourceFile(String generatedOutputDirectory, String packageName, Resource resource, String postfix) {
        try {
            this.resetIdVariableKeysMap();
            BpmnModelInstance bpmnModelInstance = Bpmn.readModelFromFile(resource.getFile());
            String className = buildClassName(resource, postfix);
            TypeSpec.Builder classSpec = TypeSpec.classBuilder(className)
                    .addModifiers(Modifier.PUBLIC);
            TypeSpec.Builder idsClassSpec = innerClassSpec("Ids");
            TypeSpec.Builder variableKeysClassSpec = innerClassSpec("VariableKeys");

            collects(bpmnModelInstance);

            idVariableKeysMap.get(IDS)
                    .forEach(key -> idsClassSpec.addField(fieldSpec(key)));
            idVariableKeysMap.get(VARIABLE_KEYS)
                    .forEach(key -> variableKeysClassSpec.addField(fieldSpec(key)));
            classSpec.addType(idsClassSpec.build());
            classSpec.addType(variableKeysClassSpec.build());
            JavaFile javaFile = builder(packageName, classSpec.build()).build();
            BpmMetadataLogger.addRowGeneratedTable(postfix, className + ".java", "Ok");
            javaFile.writeTo(Paths.get(generatedOutputDirectory));
        } catch (Exception ex) {
            throw new IllegalArgumentException(ex);
        }
    }

    private void collects(BpmnModelInstance bpmnModelInstance) {
        collectsByProcess(bpmnModelInstance.getModelElementsByType(Process.class));
        collectsByNode(bpmnModelInstance.getModelElementsByType(Event.class));
        collectsByNode(bpmnModelInstance.getModelElementsByType(Task.class));
        collectsByNode(bpmnModelInstance.getModelElementsByType(CallActivity.class));
        collectsByNode(bpmnModelInstance.getModelElementsByType(SubProcess.class));
        collectsByFLow(bpmnModelInstance.getModelElementsByType(SequenceFlow.class));
        collectsByError(bpmnModelInstance.getModelElementsByType(org.camunda.bpm.model.bpmn.instance.Error.class));
        collectsByMessage(bpmnModelInstance.getModelElementsByType(Message.class));
    }

    private void collectsByMessage(Collection<Message> elements) {
        elements.forEach(node -> addNonEmptyId(node.getName()));
    }

    private void collectsByError(Iterable<Error> elements) {
        elements.forEach(node -> addNonEmptyId(node.getId()));
    }


    private void collectsByProcess(Collection<Process> elements) {
        elements.forEach(node -> addNonEmptyId(node.getId()));
    }

    private void addNonEmptyId(String id) {
        if (org.apache.commons.lang3.StringUtils.isNotBlank(id))
            idVariableKeysMap.get(IDS).add(id);
    }


    private void collectsByNode(Collection<? extends FlowNode> elements) {
        elements.forEach(node -> {
            addNonEmptyId(node.getId());
            idVariableKeysMap.get(VARIABLE_KEYS).addAll(variableKeys(node));
        });
    }

    private void collectsByFLow(Collection<SequenceFlow> elements) {
        elements.forEach(node -> {
            addNonEmptyId(node.getId());
            idVariableKeysMap.get(VARIABLE_KEYS).addAll(variableKeys(node.getSource()));
            idVariableKeysMap.get(VARIABLE_KEYS).addAll(variableKeys(node.getTarget()));
        });
    }


}