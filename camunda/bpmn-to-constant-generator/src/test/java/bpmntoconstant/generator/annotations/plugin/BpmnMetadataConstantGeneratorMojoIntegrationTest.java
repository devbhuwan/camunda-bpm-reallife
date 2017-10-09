package bpmntoconstant.generator.annotations.plugin;

import io.takari.maven.testing.TestResources;
import io.takari.maven.testing.executor.MavenRuntime;
import io.takari.maven.testing.executor.MavenVersions;
import io.takari.maven.testing.executor.junit.MavenJUnitTestRunner;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.io.File;

@RunWith(MavenJUnitTestRunner.class)
@MavenVersions({"3.2.5"})
public class BpmnMetadataConstantGeneratorMojoIntegrationTest {

    @Rule
    public final TestResources resources = new TestResources();

    public final MavenRuntime maven;

    public BpmnMetadataConstantGeneratorMojoIntegrationTest(MavenRuntime.MavenRuntimeBuilder mavenBuilder) throws Exception {
        this.maven = mavenBuilder.withCliOptions("-B", "-U").build();
    }

    @Test
    public void test() throws Exception {
        File basedir = resources.getBasedir("basic");
        maven.forProject(basedir)
                .withCliOption("-X")
                .execute("compile")
                .assertErrorFreeLog();
    }


}