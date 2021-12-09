package com.example.demo;

import java.util.List;
import java.util.Map;
import java.util.Optional;

public class MSConfigTestMapDataSource implements MSConfigDataSourceI {
    private final Map<String, Object> configValues = Map.of(
        "brett.company", Map.of (
            "Brett Tronics", Map.of(
                "brett.db.connection", "db.brettTronics",
                "brett.domain", "www.brett-tronics.com",
                "brett.department", Map.of(
                    "gadgets", Map.of(
                        "brett.db.connection", "db.brettTronics.gadgets",
                        "brett.location", "South Africa"
                    ))
                )
            ),
        "brett.machine.name", "SilverFlame"
    );

    @Override
    public Optional<Object> get(List<String> keyPath) {
        Object value = this.configValues;

        for (String key : keyPath) {
            value = ((Map<String, Object>) value).get(key);

            if (value == null) {
                return Optional.ofNullable(value);
            }
        }

        return Optional.of(value);
    }
}
