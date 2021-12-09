package com.example.demo;

import java.util.List;
import java.util.Optional;

public interface MSConfigDataSourceI {
    Optional<Object> get(List<String> keyPath);
}
