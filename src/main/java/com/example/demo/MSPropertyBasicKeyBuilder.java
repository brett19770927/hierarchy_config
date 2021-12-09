package com.example.demo;

import org.springframework.core.env.Environment;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static java.util.Optional.ofNullable;

public class MSPropertyBasicKeyBuilder implements MSPropertyKeyBuilderI {

    private final Environment environment;
    private final String[] hierarchyPath;

    public MSPropertyBasicKeyBuilder(Environment environment) {
        this.environment = environment;

        this.hierarchyPath = ofNullable(
                environment.getProperty("hierarchy.path")
            )
            .orElse("")
            .split(",");
    }

    @Override
    public List<List<String>> getKeyPathList(String key) {
        List<List<String>> keyPathList = new ArrayList<List<String>>();
        for (int i = this.hierarchyPath.length; i >= 0; i--) {
            Optional<List<String>> keyList = this.getKeyPath(i, key);

            if (keyList.isPresent()) {
                keyPathList.add(keyList.get());
            }
        }

        return keyPathList;
    }

    private Optional<List<String>> getKeyPath(int depth, String key) {
        List<String> keyPath = new ArrayList<String>();

        for (int i = 0; i < depth && depth > 0; i++) {
            Optional<String> envKeyValue = Optional.ofNullable(this.environment.getProperty(hierarchyPath[i]));
            if (!envKeyValue.isPresent()) {
                ofNullable(envKeyValue);
            }

            keyPath.add(this.hierarchyPath[i]);
            keyPath.add(envKeyValue.get());
        }

        keyPath.add(key);
        return ofNullable(keyPath);
    }
}
