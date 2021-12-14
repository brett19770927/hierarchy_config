package com.example.demo;

import org.springframework.context.ApplicationContext;
import org.yaml.snakeyaml.Yaml;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static java.util.Collections.emptyMap;

public class MSYamlFileLoader {

    private final ApplicationContext context;

    public MSYamlFileLoader(ApplicationContext context) {
        this.context = context;
    }

    public Map<String, Object> load(List<String> vars) {
        String propertiesPath = "ms-config";
        Map<String, Object> map = load(propertiesPath);
        Map<String, Object> subMap = map;

        for (String var: vars) {
            propertiesPath = propertiesPath + "/" + var;
            subMap.put(var, load(propertiesPath));
            subMap = (Map<String, Object>) subMap.get(var);
        }

        return map;
    }

    private Map<String, Object> load(String propertiesPath) {
        try {
            return (new Yaml()).load(
                context.getResource(
                    String.format(
                        "classpath:%s/properties.yml",
                        propertiesPath)
                    )
                .getInputStream()
            );
        } catch (IOException e) {
            e.printStackTrace();
        }

        return new HashMap<String, Object>();
    }
}
