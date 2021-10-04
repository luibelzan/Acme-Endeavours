package acme.features.anonymous.duty;

import java.util.Collection;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import acme.entities.duties.Duty;
import acme.framework.repositories.AbstractRepository;

@Repository
public interface AnonymousDutyRepository extends AbstractRepository{
	
	@Query("select d from Duty d")
	Collection<Duty> findMany();
	
	@Query("select d from Duty d where d.id = ?1")
	Duty findOneDutyById(int id);
	
	@Query("select d from Duty d where d.isPublic = 1")
	Collection<Duty> findPublicDuties();

}
