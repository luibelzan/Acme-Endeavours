package acme.features.authenticated.officer;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import acme.framework.entities.Officer;
import acme.framework.entities.UserAccount;
import acme.framework.repositories.AbstractRepository;

@Repository
public interface AuthenticatedOfficerRepository extends AbstractRepository{
	
	@Query("select ua from UserAccount ua where ua.id = ?1")
	UserAccount findOneUserAccountById(int id);

	@Query("select o from Officer o where o.userAccount.id = ?1")
	Officer findOneOfficerByUserAccountId(int id);

}
