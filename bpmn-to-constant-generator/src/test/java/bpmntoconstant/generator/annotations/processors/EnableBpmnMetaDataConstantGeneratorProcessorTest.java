package bpmntoconstant.generator.annotations.processors;

import com.google.common.io.Resources;
import com.google.testing.compile.JavaFileObjects;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import javax.tools.Diagnostic;
import javax.tools.JavaFileObject;

import static com.google.common.truth.Truth.assertAbout;
import static com.google.testing.compile.JavaSourceSubjectFactory.javaSource;

/**
 * <p> </p>
 *
 * @author Bhuwan Prasad Upadhyay
 */
@RunWith(JUnit4.class)
public class EnableBpmnMetaDataConstantGeneratorProcessorTest {

    @Test
    public void name() {
        assertAbout(javaSource())
                .that(JavaFileObjects.forResource(Resources.getResource("EnableBpmnMetaDataConstantGeneratorExample.java")))
                .processedWith(new EnableBpmnMetaDataConstantGeneratorProcessor())
                .compilesWithoutError();
    }

}