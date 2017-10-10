package bpmntoconstant.generator.annotations.plugin;

import io.takari.maven.testing.TestMavenRuntime;
import io.takari.maven.testing.TestResources;
import org.junit.BeforeClass;
import org.junit.Rule;
import org.junit.Test;

import java.io.File;

import static io.takari.maven.testing.TestResources.assertFilesPresent;

public class BpmnMetadataConstantGeneratorMojoTest {

    @Rule
    public final TestMavenRuntime maven = new TestMavenRuntime();
    @Rule
    public final TestResources resources = new TestResources();

    @BeforeClass
    public static void preInit() {
        System.setProperty("M2_HOME", "/home/devbhuwan/bitdrafter/github/labcart/camunda-bpm-reallife/camunda/bpmn-to-constant-generator/target/apache-maven-3.2.5");
    }

    @Test
    public void test() throws Exception {
        File basedir = resources.getBasedir("basic");
        maven.executeMojo(basedir, "compile");
        assertFilesPresent(basedir, "target/output.txt");
    }

}