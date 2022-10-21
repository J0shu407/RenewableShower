package de.joshi.renewableshower.config

import com.google.auth.oauth2.GoogleCredentials
import com.google.firebase.FirebaseApp
import com.google.firebase.FirebaseOptions
import com.google.firebase.database.FirebaseDatabase
import de.joshi.renewableshower.persistence.DatabaseClient
import de.joshi.renewableshower.persistence.FirebaseClient
import org.slf4j.LoggerFactory
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import java.io.FileInputStream
import java.util.logging.Logger

@Configuration
open class RenewableShowerConfiguration(val properties: RenewableShowerProperties) {

    @Bean
    open fun firebaseApp(): FirebaseApp {
        val serviceAccount = FileInputStream(properties.persistence.databaseSecrets)

        val options = FirebaseOptions.builder()
            .setCredentials(GoogleCredentials.fromStream(serviceAccount))
            .setDatabaseUrl(properties.persistence.databaseUrl)
            .build()

        return FirebaseApp.initializeApp(options)
    }

    @Bean
    open fun fireDatabase(firebaseApp: FirebaseApp): FirebaseDatabase {
        return FirebaseDatabase.getInstance(firebaseApp)
    }

}