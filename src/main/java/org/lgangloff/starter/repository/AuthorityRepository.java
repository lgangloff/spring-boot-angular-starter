package org.lgangloff.starter.repository;

import org.lgangloff.starter.domain.Authority;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Spring Data JPA repository for the Authority entity.
 */
public interface AuthorityRepository extends JpaRepository<Authority, String> {

	Authority findOneByName(String name);
	
}
