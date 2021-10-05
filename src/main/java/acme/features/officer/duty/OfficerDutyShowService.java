package acme.features.officer.duty;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.entities.duties.Duty;
import acme.framework.components.Model;
import acme.framework.components.Request;
import acme.framework.entities.Officer;
import acme.framework.services.AbstractShowService;

@Service
public class OfficerDutyShowService implements AbstractShowService<Officer, Duty>{
	
	@Autowired
	protected OfficerDutyRepository repository;
	
	@Override
	public boolean authorise(final Request<Duty> request) {
		assert request != null;
		final Duty deber = this.findOne(request);
		final Integer id = deber.getOfficer().getId();
		if(request.getPrincipal().getActiveRoleId() != id) {
			return false;
		}else {
			return true;
		}
	}
	
	@Override
	public void unbind(final Request<Duty> request, final Duty entity, final Model model) {
		assert request != null;
		assert entity != null;
		assert model != null;

		request.unbind(entity, model, "description", "isPublic", "link", "periodFinal", "periodInitial", "title", "workloadInHours");		
	}
	
	@Override
	public Duty findOne(final Request<Duty> request) {
		assert request != null;

		Duty result;
		int id;

		id = request.getModel().getInteger("id");
		result = this.repository.findOneDutyById(id);

		return result;
	}
	

}
