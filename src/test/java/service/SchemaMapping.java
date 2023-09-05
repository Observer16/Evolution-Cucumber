package service;

import java.util.HashMap;
import java.util.Map;
import config.TestConfig;

public class SchemaMapping {
    private final Map<String, String> urlSchemaMapping = new HashMap<>();

    public SchemaMapping() {
        urlSchemaMapping.put("/products", TestConfig.SCHEMA_PATH + "productSchema.json");
        urlSchemaMapping.put("/categories", TestConfig.SCHEMA_PATH + "categoriSchema.json");
    }

/*    public String getSchemaPath(String url) {
        return urlSchemaMapping.get(url);
    }*/
}
