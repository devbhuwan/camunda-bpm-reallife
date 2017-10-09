package bpmntoconstant.generator.annotations.provider;


import org.apache.maven.model.Resource;
import org.codehaus.plexus.util.DirectoryScanner;

import java.util.*;

public class BpmnResourceScanningProvider {

    private List<String> bpmnFiles = new ArrayList<>();

    public BpmnResourceScanningProvider(List<Resource> resources) {
        Optional.of(resources).ifPresent(resourceList -> {
            resourceList.forEach(resource -> {
                DirectoryScanner scanner = new DirectoryScanner();
                scanner.setBasedir(resource.getDirectory());
                scanner.setIncludes(new String[]{"**/*.bpmn"});
                scanner.setCaseSensitive(false);
                scanner.scan();
                bpmnFiles.addAll(Arrays.asList(scanner.getIncludedFiles()));
            });
        });
    }


    public Collection<String> getCandidates() {
        return bpmnFiles;
    }
}
