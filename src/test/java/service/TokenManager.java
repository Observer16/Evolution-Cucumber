package service;

import config.TestConfig;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class TokenManager {
    public static String readTokenFromFile() {
        String authToken = null;
        try {
            FileReader fileReader = new FileReader(TestConfig.AUTH_TOKEN);
            BufferedReader bufferedReader = new BufferedReader(fileReader);
            authToken = bufferedReader.readLine();
            bufferedReader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return authToken;
    }
}
