package de.workshops.bookshelf.users;

import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.support.WebClientAdapter;
import org.springframework.web.service.invoker.HttpServiceProxyFactory;

import java.util.List;

@Component
public class UserService implements ApplicationRunner {

    @Override
    public void run(ApplicationArguments args) {
        String baseUrl = "https://jsonplaceholder.typicode.com/";
        UserRepository userRepository = createUserRepositoryWithBaseUrl(baseUrl);

        List<User> users = userRepository.getAllUsers();
        System.out.println("Loaded all users:");
        for (User user : users) {
            System.out.println("user = " + user);
        }
        System.out.println();

        User user = userRepository.findUserById(1);
        System.out.println("Loaded single user:");
        System.out.println("user = " + user);
        System.out.println();
    }

    private UserRepository createUserRepositoryWithBaseUrl(String baseUrl) {
        WebClient webClient = WebClient.builder().baseUrl(baseUrl).build();
        HttpServiceProxyFactory proxyFactory = HttpServiceProxyFactory.builder(WebClientAdapter.forClient(webClient)).build();
        return proxyFactory.createClient(UserRepository.class);
    }
}
