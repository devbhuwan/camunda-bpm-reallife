package bpmntoconstant.generator.annotations.processors;

import com.squareup.javapoet.FieldSpec;
import com.squareup.javapoet.TypeName;
import com.squareup.javapoet.TypeSpec;
import org.apache.commons.lang3.StringUtils;
import org.camunda.bpm.model.bpmn.instance.ExtensionElements;
import org.camunda.bpm.model.bpmn.instance.FlowNode;
import org.camunda.bpm.model.bpmn.instance.camunda.CamundaFormData;
import org.camunda.bpm.model.bpmn.instance.camunda.CamundaInputOutput;

import javax.lang.model.element.Modifier;
import java.util.HashSet;
import java.util.Set;

/**
 * @author Bhuwan Prasad Upadhyay
 */
class MetadataSpecHelper {

    private MetadataSpecHelper() {
        //no-arg constructor
    }

    public static TypeSpec.Builder innerClassSpec(String className) {
        return TypeSpec.classBuilder(className).addModifiers(Modifier.PUBLIC, Modifier.STATIC);
    }


    public static FieldSpec fieldSpec(String name) {
        return FieldSpec
                .builder(TypeName.get(String.class), name)
                .addModifiers(Modifier.PUBLIC, Modifier.STATIC, Modifier.FINAL)
                .initializer("$S", name)
                .build();
    }

    public static Set<String> variableKeys(FlowNode element) {
        final Set<String> variableKeys = new HashSet<>();
        ExtensionElements extensionElements = element.getExtensionElements();
        if (extensionElements != null) {
            byCamundaFormData(variableKeys, extensionElements);
            byCamundaInputOutput(variableKeys, extensionElements);
        }
        return variableKeys;
    }

    private static void byCamundaFormData(Set<String> variableKeys, ExtensionElements extensionElements) {
        extensionElements.getElementsQuery().filterByType(CamundaFormData.class)
                .list().forEach(camundaFormData -> camundaFormData.getCamundaFormFields()
                .forEach(camundaFormField -> addNonEmptyVariableKey(variableKeys, camundaFormField.getCamundaId())));
    }

    private static void byCamundaInputOutput(Set<String> variableKeys, ExtensionElements extensionElements) {
        extensionElements.getElementsQuery().filterByType(CamundaInputOutput.class)
                .list().forEach(camundaInputOutput -> {
            camundaInputOutput.getCamundaInputParameters()
                    .forEach(camundaInputParameter -> addNonEmptyVariableKey(variableKeys, camundaInputParameter.getCamundaName()));
            camundaInputOutput.getCamundaOutputParameters()
                    .forEach(camundaOutputParameter -> addNonEmptyVariableKey(variableKeys, camundaOutputParameter.getCamundaName()));
        });
    }

    private static void addNonEmptyVariableKey(Set<String> variableKeys, String varKey) {
        if (StringUtils.isNotBlank(varKey))
            variableKeys.add(varKey);
    }

}
