package de.workshops.bookshelf.users;

import de.workshops.bookshelf.users.User;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.service.annotation.GetExchange;
import org.springframework.web.service.annotation.HttpExchange;

import java.util.List;

@HttpExchange("/users")
public interface UserRepository {

    @GetExchange
    List<User> getAllUsers();

    @GetExchange("/{id}")
    User findUserById(@PathVariable long id);
}
