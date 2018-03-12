package bpmntoconstant.generator.annotations.provider;


import org.apache.maven.model.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;

import java.util.*;

public class BpmnResourceScanningProvider {

    private final PathMatchingResourcePatternResolver resourcePatternResolver = new PathMatchingResourcePatternResolver();
    private List<org.springframework.core.io.Resource> bpmnFiles = new ArrayList<>();

    public BpmnResourceScanningProvider(List<Resource> resources) {
        Optional.of(resources).ifPresent(resourceList -> {
            resourceList.forEach(resource -> {
                try {
                    org.springframework.core.io.Resource[] matchingResources = resourcePatternResolver.getResources(buildLocationPattern(resource));
                    bpmnFiles.addAll(Arrays.asList(matchingResources));
                } catch (Exception e) {
                    throw new IllegalArgumentException(e);
                }
            });
        });
    }

    private String buildLocationPattern(Resource resource) {
        return "file:///" + resource.getDirectory() + "/**/*.bpmn";
    }


    public Collection<org.springframework.core.io.Resource> getCandidates() {
        return bpmnFiles;
    }
}
