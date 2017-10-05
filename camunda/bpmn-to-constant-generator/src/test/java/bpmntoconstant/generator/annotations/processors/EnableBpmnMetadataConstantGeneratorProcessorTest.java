package bpmntoconstant.generator.annotations.processors;

import com.google.common.truth.Truth;
import org.junit.Test;

import java.io.File;

import static com.google.testing.compile.JavaFileObjects.forResource;
import static com.google.testing.compile.JavaSourceSubjectFactory.javaSource;
import static org.junit.Assert.assertTrue;

/**
 * <p> </p>
 *
 * @author Bhuwan Prasad Upadhyay
 */
public class EnableBpmnMetadataConstantGeneratorProcessorTest {
    static final String GENERATED_ROOT_DIR = System.getProperty("user.dir") + "/target/generated-test-sources/test-annotations";

    @Test
    public void givenSourceFile_EnableBpmnMetadataConstantGenerator_thenCompileAndGenerateBpmnMetadataConstants() {
        Truth.assert_().about(javaSource())
                .that(forResource("sources/GeneratorExample.java"))
                .processedWith(new EnableBpmnMetadataConstantGeneratorProcessor())
                .compilesWithoutError();
        assertTrue(new File(GENERATED_ROOT_DIR + "/sources/bpmn/metadata/BpmnMetadataConstants.java").exists());
    }

}