package config;

import lombok.Getter;

@Getter
public class TestConfig {

    String URL = "https://evo-ma-evolution.k8s.userstory.ru/api/v1";

    String URL1 = "https://www.healthcare.gov/api/";

    public static final String PATH = "src/test/java/service";

    public static final String AUTH_TOKEN = PATH + "/authToken.txt";
}

