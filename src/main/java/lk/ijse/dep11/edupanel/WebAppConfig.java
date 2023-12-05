package lk.ijse.dep11.edupanel;


import com.google.auth.oauth2.GoogleCredentials;
import com.google.cloud.storage.Bucket;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.cloud.StorageClient;
import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.web.multipart.commons.CommonsMultipartFile;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;
import org.springframework.web.multipart.support.StandardServletMultipartResolver;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import java.io.IOException;
import java.io.InputStream;

@ComponentScan
@Configuration
@EnableWebMvc
@PropertySource("classpath:/application.properties")
public class WebAppConfig {
//    @Bean
//    public CommonsMultipartResolver multipartResolver(){
//        return new CommonsMultipartResolver();
//    }
@Bean
public Bucket defaultBucket() throws IOException {
    InputStream serviceAccount =
            getClass().getResourceAsStream("/edupanel-8cdfd-firebase-adminsdk-zulk4-f33a5c244f.json");

    FirebaseOptions options = new FirebaseOptions.Builder()
            .setCredentials(GoogleCredentials.fromStream(serviceAccount))
            .setStorageBucket("edupanel-8cdfd.appspot.com")
            .build();

    FirebaseApp.initializeApp(options);
    return StorageClient.getInstance().bucket();

}

    @Bean(destroyMethod = "close")
    public HikariDataSource dataSource (Environment env){
        HikariConfig config = new HikariConfig();
        config.setJdbcUrl(env.getRequiredProperty("spring.datasource.url"));
        config.setUsername(env.getRequiredProperty("spring.datasource.username"));
        config.setPassword(env.getRequiredProperty("spring.datasource.password"));
        config.setDriverClassName(env.getRequiredProperty("spring.datasource.driver-class-name"));
        config.setMaximumPoolSize(env.getRequiredProperty("spring.datasource.hikari.maximum-pool-size", Integer.class));
        return new HikariDataSource(config);
    }


    @Bean
    public StandardServletMultipartResolver multipartResolver(){
        return new StandardServletMultipartResolver();
    }
}
