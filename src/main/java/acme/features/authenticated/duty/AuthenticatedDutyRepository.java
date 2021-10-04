package acme.features.authenticated.duty;

import java.util.Collection;
import java.util.Date;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import acme.entities.duties.Duty;
import acme.framework.repositories.AbstractRepository;

@Repository
public interface AuthenticatedDutyRepository extends AbstractRepository{
	
	@Query("select d from Duty d where d.id = ?1")
	Duty findOneDutyById(int id);

	@Query("select d from Duty d where d.isPublic = 1")
	Collection<Duty> findPublicDuties();

	@Query("select d from Duty d where d.isPublic = 1 and d.periodFinal < ?1")
	Collection<Duty> findPublicFinishedDuties(Date now);

}
