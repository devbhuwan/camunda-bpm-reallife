package bpmntoconstant.generator.annotations.processors;

import com.google.auto.service.AutoService;
import com.squareup.javapoet.JavaFile;
import com.squareup.javapoet.MethodSpec;
import com.squareup.javapoet.TypeSpec;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;

import javax.annotation.processing.*;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.Modifier;
import javax.lang.model.element.TypeElement;
import java.io.IOException;
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
        PathMatchingResourcePatternResolver resourcePatternResolver = new PathMatchingResourcePatternResolver();
        try {
            Resource[] bpmnResources = resourcePatternResolver.getResources("classpath*:**/*.bpmn");
        } catch (IOException e) {
            e.printStackTrace();
        }
        MethodSpec main = MethodSpec.methodBuilder("main")
                .addModifiers(Modifier.PUBLIC, Modifier.STATIC)
                .returns(void.class)
                .addParameter(String[].class, "args")
                .addStatement("$T.out.println($S)", System.class, "Hello, JavaPoet!")
                .build();

        TypeSpec helloWorld = TypeSpec.classBuilder("HelloWorld" + System.currentTimeMillis())
                .addModifiers(Modifier.PUBLIC, Modifier.FINAL)
                .addMethod(main)
                .build();

        JavaFile javaFile = JavaFile.builder("com.example.helloworld", helloWorld)
                .build();

        try {
            javaFile.writeTo(processingEnv.getFiler());
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }
}
