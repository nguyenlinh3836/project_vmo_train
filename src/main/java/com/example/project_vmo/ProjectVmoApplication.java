package com.example.project_vmo;

import com.cloudinary.Cloudinary;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

@SpringBootApplication
@EnableWebMvc
public class ProjectVmoApplication {

  public static void main(String[] args) {
    SpringApplication.run(ProjectVmoApplication.class, args);
  }
  @Bean
  public Cloudinary cloudinaryConfig() {
    Cloudinary cloudinary = null;
    Map config = new HashMap();
    config.put("cloud_name", "dfzuzpzez");
    config.put("api_key", "347389112387959");
    config.put("api_secret", "ySigh8OEr7MQqoYeF-0KjgU2-Vc");
    cloudinary = new Cloudinary(config);
    return cloudinary;
  }
}
