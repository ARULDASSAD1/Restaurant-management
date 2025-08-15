package com.example.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.model.Restaurant;
import com.example.demo.service.RestaurantService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class DemoApplication {

  @Value("${NAME:World}")
  String name;

  @RestController
  class HelloworldController {
    @GetMapping("/")
    String hello() {
      return "Hello " + name + "!";
    }
  }

  @Bean
  public CommandLineRunner demoData(RestaurantService restaurantService) {
      return args -> {
          // Add some dummy restaurants
          Restaurant restaurant1 = new Restaurant();
          restaurant1.setName("The Great Food Place");
          restaurant1.setAddress("123 Main St");
          restaurantService.createRestaurant(restaurant1);

          Restaurant restaurant2 = new Restaurant();
          restaurant2.setName("Burger Joint");
          restaurant2.setAddress("456 Oak Ave");
          restaurantService.createRestaurant(restaurant2);

          System.out.println("Dummy restaurants added!");
      };
  }

  public static void main(String[] args) {
    SpringApplication.run(DemoApplication.class, args);
  }

}
