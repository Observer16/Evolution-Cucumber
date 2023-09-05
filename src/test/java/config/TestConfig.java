package config;

import lombok.Getter;

@Getter
public class TestConfig {

    String URL = "https://evo-ma-evolution.k8s.userstory.ru/api/v1";

    public static final String PATH = "src/test/java/service";

    public static final String AUTH_TOKEN = PATH + "/authToken.txt";

    public static final String SCHEMA_PATH = "src/test/resources/schemas";
}

