package acme.features.anonymous.duty;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.entities.duties.Duty;
import acme.framework.components.Model;
import acme.framework.components.Request;
import acme.framework.entities.Anonymous;
import acme.framework.services.AbstractListService;

@Service
public class AnonymousDutyListService implements AbstractListService<Anonymous, Duty>{
	
	// Internal state ---------------------------------------------------------

		@Autowired
		protected AnonymousDutyRepository repository;

		// AbstractListService<Administrator, Shout> interface --------------


		@Override
		public boolean authorise(final Request<Duty> request) {
			assert request != null;

			return true;
		}

		@Override
		public void unbind(final Request<Duty> request, final Duty entity, final Model model) {
			assert request != null;
			assert entity != null;
			assert model != null;

			request.unbind(entity, model, "title", "periodInitial", "periodFinal", "description", "link", "isPublic", "workloadInHours");

		}

		@Override
		public Collection<Duty> findMany(final Request<Duty> request) {
			final List<Duty> res = new ArrayList<>();
			final Collection<Duty> dutiesPublics = this.repository.findPublicDuties();
			final Date now = new Date();
			for (final Duty d : dutiesPublics) {
				if (d.getPeriodFinal().after(now)) {
					res.add(d);
				}
			}
			Collections.sort(res, Comparator.comparing(x -> x.getWorkloadInHours()));
			return res;
		}

}
