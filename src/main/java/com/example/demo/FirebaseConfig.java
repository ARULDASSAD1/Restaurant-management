package com.example.demo;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.cloud.firestore.Firestore;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.cloud.FirestoreClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.io.IOException;

import org.springframework.stereotype.Service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.core.io.Resource;

@Service
@Configuration
@Profile("!test")
public class FirebaseConfig {

    public FirebaseConfig() {
        // Private constructor to hide the implicit public one
    }


      @Value("${firebase.service-account-file}")
  private Resource serviceAccount;

  @Bean
  public Firestore firestore() throws IOException {
    try {
      FirebaseOptions options =
          FirebaseOptions.builder()
              .setCredentials(GoogleCredentials.fromStream(serviceAccount.getInputStream()))
              .build();

      if (FirebaseApp.getApps().isEmpty()) {
        FirebaseApp.initializeApp(options);
      }

      return FirestoreClient.getFirestore();
    } catch (IOException e) {
      e.printStackTrace();
      throw e;
    }
  }
}