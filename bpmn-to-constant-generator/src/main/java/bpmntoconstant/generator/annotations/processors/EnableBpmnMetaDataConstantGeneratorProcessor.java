package bpmntoconstant.generator.annotations.processors;

import com.google.auto.service.AutoService;
import lombok.extern.slf4j.Slf4j;

import javax.annotation.processing.*;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.TypeElement;
import java.util.Set;

import static bpmntoconstant.generator.annotations.processors.EnableBpmnMetaDataConstantGeneratorProcessor.ANNOTATIONS_ENABLE_BPMN_META_DATA_CONSTANT_GENERATOR;

/**
 * <p> </p>
 *
 * @author Bhuwan Prasad Upadhyay
 */
@SupportedAnnotationTypes({ANNOTATIONS_ENABLE_BPMN_META_DATA_CONSTANT_GENERATOR})
@SupportedSourceVersion(SourceVersion.RELEASE_8)
@AutoService(Processor.class)
@Slf4j
public class EnableBpmnMetaDataConstantGeneratorProcessor extends AbstractProcessor {

    public static final String ANNOTATIONS_ENABLE_BPMN_META_DATA_CONSTANT_GENERATOR = "bpmntoconstant.generator.annotations.EnableBpmnMetaDataConstantGenerator";

    @Override
    public boolean process(Set<? extends TypeElement> annotations, RoundEnvironment roundEnv) {
        log.info("I am don!");
        return true;
    }
}
