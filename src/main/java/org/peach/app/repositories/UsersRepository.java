package org.peach.app.repositories;

import org.peach.app.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.util.Optional;

@Repository
public interface UsersRepository extends JpaRepository<User,Long> {
    Optional<User> findFirstByNameIgnoreCase(@NotEmpty(message = "Name shouldn't be empty")
                                        @Size(min = 2, max = 30, message = "Invalid size of name")
                                        @Pattern(regexp = "[a-zA-Z]+", message = "name should contains only letters")
                                        String name);

}
