package com.example.demo;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;
import java.util.Optional;

import static java.util.Optional.ofNullable;

public class MSConfigJsonDataSource implements MSConfigDataSourceI {
    private final JSONObject jsonValues;

    public MSConfigJsonDataSource(String jsonString) {
        this.jsonValues = new JSONObject(jsonString);
    }

    @Override
    public Optional<Object> get(List<String> keyPath) {
        Object value = this.jsonValues;

        for (String key: keyPath) {
            if (value instanceof JSONObject && ((JSONObject) value) != JSONObject.NULL) {
                try {
                    value = ((JSONObject) value).get(key);
                } catch(JSONException e) {
                    return ofNullable(null);
                }
            }

            if (value == null) {
                break;
            }
        }

        return ofNullable(value);
    }
}
