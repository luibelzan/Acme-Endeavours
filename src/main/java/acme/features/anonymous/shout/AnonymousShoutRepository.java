package acme.features.anonymous.shout;

import java.util.Collection;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import acme.entities.configuration.Configuration;
import acme.entities.shouts.Shout;
import acme.framework.repositories.AbstractRepository;

@Repository
public interface AnonymousShoutRepository extends AbstractRepository{
	
	@Query("select s from Shout s")
	Collection<Shout> findMany();

	@Query("select s from Shout s where s.id = ?1")
	Shout findOneShoutById(int id);
	
	@Query("select c from Configuration c")
	Collection<Configuration> findManySpamWord();
	

}
