package com.example.demo;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static java.util.Arrays.asList;
import static java.util.Collections.emptyList;
import static java.util.Optional.ofNullable;
import static java.util.stream.Collectors.toList;
import static java.util.stream.Stream.concat;

public class MSYamlPropertyKeyBuilder implements MSPropertyKeyBuilderI {

    private final Optional<String> environment;
    private final Optional<String> region;
    private final Optional<String> slot;

    public MSYamlPropertyKeyBuilder(
        String environment,
        String region,
        String slot
    ) {
        this.environment = ofNullable(environment);
        this.region = ofNullable(region);
        this.slot = ofNullable(slot);
    }

    @Override
    public List<List<String>> getKeyPathList(String key) {
        return getKeyPathList(asList(key.split("\\.")));
    }

    private List<List<String>> getKeyPathList(List<String> keySplit) {
        return List.of(
            buildKeyList(List.of(environment, region, slot), keySplit),
            buildKeyList(List.of(environment, region), keySplit),
            buildKeyList(List.of(environment), keySplit),
            keySplit
        );
    }

    private List<String> buildKeyList(
        List<Optional<String>> envVars,
        List<String> keySplit
    ) {
        return (varsExist(envVars))? 
                concat(
                    envVars.stream().map(i -> i.get())
                    ,keySplit.stream()
                )
                .collect(toList())
            : emptyList()
        ;
    }

    private boolean varsExist(List<Optional<String>> vars) {
        for (Optional<String> var: vars) {
            if (!var.isPresent()){
                return false;
            }
        }

        return true;
    }
}
