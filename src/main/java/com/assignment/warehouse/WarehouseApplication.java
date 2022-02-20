package com.assignment.warehouse;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class WarehouseApplication {

    public static void main(String[] args) {

        SpringApplication.run(WarehouseApplication.class, args);
    }

/*    @Bean
    CommandLineRunner runner(ArticleService pStockService){
        return args -> {
            //Read Json and write to DB
            ObjectMapper mapper = new ObjectMapper();
            TypeReference<List<Article>> typeReference = new TypeReference<List<Article>>(){};
            InputStream inputStream = TypeReference.class.getResourceAsStream("/json/inventory.json");
            try{
            List<Article> vArticles = mapper.readValue(inputStream, typeReference);
            pStockService.save(vArticles);
            System.out.println("Stock Updated");

            } catch (IOException e){
                System.out.println("Unable to Save User " + e.getMessage());
            }
        };
    }*/

}
