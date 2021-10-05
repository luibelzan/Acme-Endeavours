package acme.framework.entities;

import java.util.Collection;

import javax.persistence.Entity;
import javax.persistence.OneToMany;

import acme.entities.duties.Duty;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Officer extends UserRole{
	
	protected static final long	serialVersionUID	= 1L;

	// Attributes -------------------------------------------------------------

	// Derived attributes -----------------------------------------------------

	// Relationships ----------------------------------------------------------
	@OneToMany(mappedBy = "officer")
	Collection<Duty> duty;

}
