
- Set System Properties for Multi Module Maven Project `bpmn.metadata.project.basedir` 
```xml
<plugin>
    <groupId>org.codehaus.mojo</groupId>
    <artifactId>properties-maven-plugin</artifactId>
    <version>1.0.0</version>
    <executions>
        <execution>
            <goals>
                <goal>set-system-properties</goal>
            </goals>
            <configuration>
                <properties>
                    <property>
                        <name>bpmn.metadata.project.basedir</name>
                        <value>${project.basedir}</value>
                    </property>
                </properties>
            </configuration>
        </execution>
    </executions>
</plugin>
```