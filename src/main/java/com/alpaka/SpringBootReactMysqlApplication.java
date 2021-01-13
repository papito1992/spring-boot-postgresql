package com.alpaka;

import com.alpaka.model.representative.Representative;
import com.alpaka.model.user.User;
import com.alpaka.repository.RepresentativeRepository;
import com.alpaka.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.servlet.function.RouterFunction;
import org.springframework.web.servlet.function.ServerResponse;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;

import static org.springframework.web.servlet.function.RequestPredicates.GET;
import static org.springframework.web.servlet.function.RouterFunctions.route;

@Slf4j
@SpringBootApplication
public class SpringBootReactMysqlApplication {


    public static void main(String[] args) {
        SpringApplication.run(SpringBootReactMysqlApplication.class, args);

    }


    @Bean
    RouterFunction<ServerResponse> routerFunction() {
        return route(GET("/swagger"), req ->
                ServerResponse.temporaryRedirect(URI.create("swagger-ui.html")).build());
    }

    @Bean
    public CommandLineRunner demoData(RepresentativeRepository representativeRepository, UserRepository userRepository) {
        //Default rep is used as a placeholder when creating new customers via front end.
        //Isbn is a unique name for each representative, later could be used for rights restriction.
        return args -> {
            Representative defaultRep = new Representative("Default", "default@default.com", "defaultRep");
            Representative john = new Representative("JOHNSNOW", "john@firmname.com", "RIUUL_ASASMMDS");
            Representative aria = new Representative("ARIALAND", "aria@firmname.com", "RIUUL_4165JGHJ");
            Representative liara = new Representative("LIARAOL", "liara@firmname.com", "RIUUL_JT1YJ561");
            Representative tom = new Representative("TOMJONES", "tom@firmname.com", "RIUUL_ALK5UIL1S");
            Representative steve = new Representative("STEVEBUCHEMI", "steve@firmname.com", "RIUUL_65489AWDS");
            Representative albert = new Representative("EINSTEIN", "einstein@firmname.com", "RIUUL_ASHJ4T984");

            List<Representative> representativeList = new ArrayList<>();
            representativeList.add(defaultRep);
            representativeList.add(john);
            representativeList.add(aria);
            representativeList.add(liara);
            representativeList.add(tom);
            representativeList.add(steve);
            representativeList.add(albert);
            representativeRepository.saveAll(representativeList);

            User user = new User("megaUser", "$2a$10$PiA5wGrvWtKQLPgAaI0eCurk5HZQI4xSEaH7CogHqTl2Ak0PRYz12");
            userRepository.save(user);
        };
    }
}
