package bpmntoconstant.generator.annotations.processors;

import bpmntoconstant.generator.annotations.processors.util.EnableBpmnMetadataConstantGeneratorProcessor;
import com.google.common.truth.Truth;
import com.google.testing.compile.JavaFileObjects;
import org.junit.BeforeClass;
import org.junit.Test;

import static bpmntoconstant.generator.annotations.processors.util.JavaSourceFileHelper.BPMN_METADATA_ANNOTATION_MODE;
import static bpmntoconstant.generator.annotations.processors.util.JavaSourceFileHelper.TEST_MODE;
import static com.google.testing.compile.JavaSourceSubjectFactory.javaSource;

/**
 * <p> </p>
 *
 * @author Bhuwan Prasad Upadhyay
 */
public class EnableBpmnMetadataConstantGeneratorProcessorTest {

    @BeforeClass
    public static void beforeClass() {
        System.getProperties().put(BPMN_METADATA_ANNOTATION_MODE, TEST_MODE);
    }

    @Test
    public void givenSourceFile_EnableBpmnMetadataConstantGenerator_thenCompileAndGenerateBpmnMetadataConstants() {
        Truth.assert_().about(javaSource())
                .that(JavaFileObjects.forResource("sources/GeneratorExample.java"))
                .processedWith(new EnableBpmnMetadataConstantGeneratorProcessor())
                .compilesWithoutError();
    }

}