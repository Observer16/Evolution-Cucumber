package service;

import java.util.HashMap;
import java.util.Map;
import config.TestConfig;

public class SchemaMapping {
    private final Map<String, String> urlSchemaMapping = new HashMap<>();

    public SchemaMapping() {
/*        urlSchemaMapping.put("/categories", TestConfig.SCHEMA_PATH + "categoriesSchema.json");
        urlSchemaMapping.put("/product/{productId}", TestConfig.SCHEMA_PATH + "productProductIdSchema.json");
        urlSchemaMapping.put("/products", TestConfig.SCHEMA_PATH + "productsSchema.json");
        urlSchemaMapping.put("/product/{productId}/related-products", TestConfig.SCHEMA_PATH + "related-productsSchema.json");
        urlSchemaMapping.put("/product/{productId}/similar-products", TestConfig.SCHEMA_PATH + "similar-productsSchema.json");
        urlSchemaMapping.put("/viewed-products", TestConfig.SCHEMA_PATH + "viewed-productsSchema.json");
        urlSchemaMapping.put("/basket", TestConfig.SCHEMA_PATH + "basketSchema.json");
        urlSchemaMapping.put("/basket/add", TestConfig.SCHEMA_PATH + "basketPatchSchema.json");*/

    }

    public String getSchemaPath(String url) {
        return urlSchemaMapping.get(url);
    }
}
