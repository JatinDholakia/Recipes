package recipes.repo;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import recipes.models.User;

@Repository
public interface UserRepository extends CrudRepository<User, String> {
}
