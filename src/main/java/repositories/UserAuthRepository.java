package repositories;

import org.springframework.data.repository.CrudRepository;

import entities.UserAuth;

public interface UserAuthRepository extends CrudRepository<UserAuth, Integer>{

}
