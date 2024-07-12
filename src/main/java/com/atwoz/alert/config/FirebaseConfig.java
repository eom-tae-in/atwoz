package com.atwoz.alert.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@Configuration
public class FirebaseConfig {

    private static final String FILE_URL = "src/main/resources/firebase/firebase_key.json";
    private static final String TYPE = "type";
    private static final String PROJECT_ID = "project_id";
    private static final String PRIVATE_KEY_ID = "private_key_id";
    private static final String PRIVATE_KEY = "private_key";
    private static final String CLIENT_EMAIL = "client_email";
    private static final String CLIENT_ID = "client_id";
    private static final String AUTH_URI = "auth_uri";
    private static final String TOKEN_URI = "token_uri";
    private static final String AUTH_CERT = "auth_provider_x509_cert_url";
    private static final String CLIENT_CERT = "client_x509_cert_url";
    private static final String UNIVERSE_DOMAIN = "universe_domain";
    private static final String REPLACE_TARGET_MARK = "\\\\n";
    private static final String REPLACE_VALUE_MARK = "\n";

    @Value("${firebase.type}")
    private String type;

    @Value("${firebase.project_id}")
    private String projectId;

    @Value("${firebase.private_key_id}")
    private String privateKeyId;

    @Value("${firebase.private_key}")
    private String privateKey;

    @Value("${firebase.client_email}")
    private String clientEmail;

    @Value("${firebase.client_id}")
    private String clientId;

    @Value("${firebase.auth_uri}")
    private String authUri;

    @Value("${firebase.token_uri}")
    private String tokenUri;

    @Value("${firebase.auth_provider_x509_cert_url}")
    private String authCert;

    @Value("${firebase.client_x509_cert_url}")
    private String clientCert;

    @Value("${firebase.universe_domain}")
    private String universeDomain;

    @Bean
    public FirebaseApp firebaseApp() throws IOException {
        if (!FirebaseApp.getApps().isEmpty()) {
            return FirebaseApp.getInstance();
        }

        FirebaseOptions options = new FirebaseOptions.Builder()
                .setCredentials(getCredentials())
                .build();

        return FirebaseApp.initializeApp(options);
    }

    private GoogleCredentials getCredentials() throws IOException {
        saveConfigToFile();

        File file = new File(FILE_URL);
        return GoogleCredentials.fromStream(new FileInputStream(file));
    }

    private void saveConfigToFile() throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        ObjectWriter writer = mapper.writerWithDefaultPrettyPrinter(); // Use pretty printer to format output

        Map<String, String> firebaseConfig = saveFirebaseConfigByValues();

        File file = new File(FILE_URL);
        if (!file.exists()) {
            writer.writeValue(file, firebaseConfig);
            file.createNewFile();
        }
        updatePrivateKeyInJson(file, mapper, privateKey);
    }

    private Map<String, String> saveFirebaseConfigByValues() {
        Map<String, String> firebaseConfig = new HashMap<>();
        firebaseConfig.put(TYPE, type);
        firebaseConfig.put(PROJECT_ID, projectId);
        firebaseConfig.put(PRIVATE_KEY_ID, privateKeyId);
        firebaseConfig.put(PRIVATE_KEY, privateKey);
        firebaseConfig.put(CLIENT_EMAIL, clientEmail);
        firebaseConfig.put(CLIENT_ID, clientId);
        firebaseConfig.put(AUTH_URI, authUri);
        firebaseConfig.put(TOKEN_URI, tokenUri);
        firebaseConfig.put(AUTH_CERT, authCert);
        firebaseConfig.put(CLIENT_CERT, clientCert);
        firebaseConfig.put(UNIVERSE_DOMAIN, universeDomain);

        return firebaseConfig;
    }

    private void updatePrivateKeyInJson(File jsonFile, ObjectMapper mapper, String newPrivateKey) throws IOException {
        Map<String, Object> jsonMap = mapper.readValue(jsonFile, Map.class);
        jsonMap.put(PRIVATE_KEY, replaceNewLines(newPrivateKey));
        mapper.writeValue(jsonFile, jsonMap);
    }

    private String replaceNewLines(String value) {
        return value.replaceAll(REPLACE_TARGET_MARK, REPLACE_VALUE_MARK);
    }
}
