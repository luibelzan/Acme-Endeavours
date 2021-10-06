package acme.features.officer.duty;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.entities.configuration.Configuration;
import acme.entities.duties.Duty;
import acme.features.anonymous.shout.AnonymousShoutRepository;
import acme.framework.components.Errors;
import acme.framework.components.Model;
import acme.framework.components.Request;
import acme.framework.entities.Officer;
import acme.framework.services.AbstractCreateService;

@Service
public class OfficerDutyCreateService implements AbstractCreateService<Officer, Duty>{
	
	@Autowired
	protected OfficerDutyRepository repository;
	
	@Autowired
	protected AnonymousShoutRepository ar;
	
	@Override
	public boolean authorise(final Request<Duty> request) {
		assert request != null;

		return true;
	}

	@Override
	public void bind(final Request<Duty> request, final Duty entity, final Errors errors) {
		assert request != null;
		assert entity != null;
		assert errors != null;

		request.bind(entity, errors);
	}
	
	@Override
	public void unbind(final Request<Duty> request, final Duty entity, final Model model) {
		assert request != null;
		assert entity != null;
		assert model != null;

		request.unbind(entity, model, "description", "isPublic", "link", "periodFinal", "periodInitial", "title", "workloadInHours");
	}
	
	@Override
	public Duty instantiate(final Request<Duty> request) {
		assert request != null;

		Duty result;

		result = new Duty();
		result.setDescription("");
		result.setLink("");
		result.setPeriodFinal(null);
		result.setPeriodInitial(null);
		result.setTitle(null);
		result.setWorkloadInHours(null);
		result.setIsPublic(false);
		final Integer id = request.getPrincipal().getActiveRoleId();
		final Officer o = this.repository.officerById(id);
		result.setOfficer(o);

		return result;
	}
	
	public Boolean validarFecha(final Date date) {
        if(date==null) {
        	return false;
        }
        Boolean res= true;
        final Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        final int[] diasMes= {31, 28, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31};
        if(Calendar.YEAR <0 || Calendar.MONTH<1 || Calendar.MONTH>12 || Calendar.DAY_OF_MONTH<1 || Calendar.DAY_OF_MONTH>31) {
            res=false;
            return res;
        }else if(Calendar.MONTH ==2 || Calendar.YEAR%4 ==0) {
            res = Calendar.DAY_OF_MONTH>=1 && Calendar.DAY_OF_MONTH<=29;
            return res;
        }else if(!(Calendar.DAY_OF_MONTH<= diasMes[Calendar.MONTH-1])) {
            res = false;
            return res;
        }
        return res;

    }
	
	@Override
	public void validate(final Request<Duty> request, final Duty entity, final Errors errors) {
		assert request != null;
		assert entity != null;
		assert errors != null;

		final Collection<Configuration> sp = this.ar.findManySpamWord();
		final List<Configuration> lsp = new ArrayList<>();
		lsp.addAll(sp);


		for (int i = 0; i < lsp.size(); i++) {
			errors.state(request, !lsp.get(i).isSpam(entity.getTitle()), "title", "officer.message.form.error.spam");
			errors.state(request, !lsp.get(i).isSpam(entity.getDescription()), "description", "officer.message.form.error.spam");
		}
		

		if (entity.getPeriodFinal() != null && entity.getPeriodInitial() != null && entity.getPeriodInitial().after(entity.getPeriodFinal())) {
			errors.state(request, false, "periodInitial", "officer.message.form.error.date");
		}

		final Date date = new Date();

		if (entity.getPeriodInitial() != null && entity.getPeriodInitial().before(date)) {
			errors.state(request, false, "periodInitial", "officer.message.form.error.date3");
		}

		if (entity.getPeriodFinal() != null && entity.getPeriodFinal().before(date)) {
			errors.state(request, false, "periodFinal", "officer.message.form.error.date3");
		}

		if (entity.getPeriodFinal() != null && entity.getPeriodInitial() != null && entity.getPeriodFinal().before(entity.getPeriodInitial())) {
			errors.state(request, false, "periodFinal", "officer.message.form.error.date");
		}
		
		if (entity.getWorkloadInHours() != null) {
			final double number = entity.getWorkloadInHours();
			final String str = String.format("%.2f", number);
			final String fullNumber = String.valueOf(number);
			final int parteEntera = Integer.parseInt(str.substring(0, str.indexOf(".")));
			final int parteDecimal = Integer.parseInt(str.substring(str.indexOf('.') + 1));
			final int workloadInMinutes = (parteEntera*60) + parteDecimal;
			final String parteDecimalCompleta = fullNumber.substring(fullNumber.indexOf('.') + 1);
			final int tamaño = parteDecimalCompleta.length();
			
			if(tamaño>2) {
				errors.state(request, false, "workloadInHours", "officer.message.form.error.workload4");
			} else if(parteDecimal<0 || parteDecimal>=60) {
				errors.state(request, false, "workloadInHours", "officer.message.form.error.workload2");
			} else if (entity.getPeriodInitial()!=null && entity.getPeriodFinal()!=null && workloadInMinutes > (entity.durationPeriodInMinutes())) {
				errors.state(request, false, "workloadInHours", "officer.message.form.error.workload");
			} else if(entity.getPeriodInitial()==null || entity.getPeriodFinal() == null) {
				errors.state(request,  false, "workloadInHours", "officer.message.form.error.workload3");

			}
		}

	}
	
	@Override
	public void create(final Request<Duty> request, final Duty entity) {
		assert request != null;
		assert entity != null;

		boolean confirmation;
		confirmation = request.getModel().getBoolean("isPublic");
		entity.setIsPublic(confirmation);

		this.repository.save(entity);
	}

	
	
}
