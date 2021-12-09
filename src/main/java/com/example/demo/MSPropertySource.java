package com.example.demo;

import org.springframework.core.env.Environment;
import org.springframework.core.env.PropertySource;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static java.util.Optional.ofNullable;


public class MSPropertySource extends PropertySource<String> {

    private final MSConfigDataSourceI dataSource;
    private final MSPropertyKeyBuilderI keyBuilder;

    public MSPropertySource(
        MSPropertyKeyBuilderI keyBuilder,
        MSConfigDataSourceI dataSource
    ) {
        super("MSPropertySource");
        this.keyBuilder = keyBuilder;
        this.dataSource = dataSource;
    }

    @Override
    public Object getProperty(String key) {
        for (List<String> keyList: this.keyBuilder.getKeyPathList(key)) {
            Optional<Object> value = this.dataSource.get(keyList);

            if ( value.isPresent()) {
                return value.get();
            }
        }

        return null;
    }
}
