package org.lgangloff.starter.repository;

import java.util.Optional;

import org.lgangloff.starter.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

/**
 * Spring Data JPA repository for the User entity.
 */
public interface UserRepository extends JpaRepository<User,Long> {

    @Query("select m from User m "
    		+ "left join fetch m.authorities "
    		+ "where m.login = :login")
    Optional<User> findOneByLogin(@Param("login") String login);
    
    
    @Query("select m from User m "
    		+ "left join fetch m.authorities "
    		+ "where m.id = :id")
    User findOne(@Param("id") Long id);
    

}
