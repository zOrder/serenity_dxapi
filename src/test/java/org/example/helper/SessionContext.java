package org.example.helper;

public class SessionContext {
    private static String accessToken;

    public static void setAccessToken(String token) {
        accessToken = token;
    }

    public static String getAccessToken() {
        return accessToken;
    }
}
