/*
 * AdministratorDashboardShowService.java
 *
 * Copyright (c) 2012-2021 Rafael Corchuelo.
 *
 * In keeping with the traditional purpose of furthering education and research, it is
 * the policy of the copyright owner to permit non-commercial use and redistribution of
 * this software. It has been tested carefully, but it is not guaranteed for any particular
 * purposes. The copyright owner does not offer any warranties or representations, nor do
 * they accept any liabilities with respect to them.
 */

package acme.features.administrator.dashboard;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.entities.duties.Duty;
import acme.forms.Dashboard;
import acme.framework.components.Model;
import acme.framework.components.Request;
import acme.framework.entities.Administrator;
import acme.framework.services.AbstractShowService;

@Service
public class AdministratorDashboardShowService implements AbstractShowService<Administrator, Dashboard> {

	// Internal state ---------------------------------------------------------

	@Autowired
	protected AdministratorDashboardRepository repository;

	// AbstractShowService<Administrator, Dashboard> interface ----------------


	@Override
	public boolean authorise(final Request<Dashboard> request) {
		assert request != null;

		return true;
	}

	@Override
	public void unbind(final Request<Dashboard> request, final Dashboard entity, final Model model) {
		assert request != null;
		assert entity != null;
		assert model != null;

		request.unbind(entity, model, //
			"averageNumberOfJobsPerEmployer", "averageNumberOfApplicationsPerWorker",  
			"avegageNumberOfApplicationsPerEmployer", "ratioOfPendingApplications", 
			"ratioOfRejectedApplications", "ratioOfAcceptedApplications", "numberPublicDuty", "numberPrivateDuty", "numberFinalDuty", 
			"numberNoFinalDuty","averageDurationPeriodDuties","deviationDurationPeriodDuties","minimumDurationPeriodDuties","maximumDurationPeriodDuties",
			"averageWorkloadDuties","deviationWorkloadDuties","minimumWorkloadDuties","maximumWorkloadDuties");
	}

	@Override
	public Dashboard findOne(final Request<Dashboard> request) {
		assert request != null;

		Dashboard result;
		Double averageNumberOfApplicationsPerEmployer;
		Double averageNumberOfApplicationsPerWorker;
		Double averageNumberOfJobsPerEmployer;
		Double ratioOfPendingApplications;
		Double ratioOfAcceptedApplications;
		Double ratioOfRejectedApplications;
		
		final Double numberPublicDuty;
		final Double numberPrivateDuty;
		final Date now = new Date();
		final List<Duty> terminadas = new ArrayList<>();
		final Collection<Duty> duties = this.repository.findDuties();
		Double averageDurationPeriodDuties;
		Double deviationDurationPeriodDuties;
		Double minimumDurationPeriodDuties;
		Double maximumDurationPeriodDuties;
		
		
		final Double averageWorkloadDuties;
		final Double deviationWorkloadDuties;
		final Double minimumWorkloadDuties;
		final Double maximumWorkloadDuties;

		averageNumberOfApplicationsPerEmployer = this.repository.averageNumberOfApplicationsPerEmployer();
		averageNumberOfApplicationsPerWorker = this.repository.averageNumberOfApplicationsPerWorker();
		averageNumberOfJobsPerEmployer = this.repository.averageNumberOfJobsPerEmployer();
		ratioOfPendingApplications = this.repository.ratioOfPendingApplications();
		ratioOfAcceptedApplications = this.repository.ratioOfAcceptedApplications();
		ratioOfRejectedApplications = this.repository.ratioOfRejectedApplications();
		
		numberPublicDuty = this.repository.numberPublicDuty();
		numberPrivateDuty = this.repository.numberPrivateDuty();
		averageDurationPeriodDuties=0.0;
		deviationDurationPeriodDuties= 0.0;
		maximumDurationPeriodDuties=0.0;
		averageWorkloadDuties=this.repository.averageWorkloadDuties();
		deviationWorkloadDuties=this.repository.deviationWorkloadDuties();
		minimumWorkloadDuties=this.repository.minimumWorkloadDuties();
		maximumWorkloadDuties=this.repository.maximumWorkloadDuties();
		
		for (final Duty d: duties) {
			averageDurationPeriodDuties= averageDurationPeriodDuties+ d.durationPeriodInHours();
		}
		
		
		for (final Duty d: duties) {
			//Comprueba si la tarea esta terminada
			if (now.getTime()>=d.getPeriodFinal().getTime()) {
				terminadas.add(d);
			}
		}
		for (final Duty d: duties) {
			//Esto es para calcular el maximo en los Workloads tasks
			if (d.durationPeriodInHours()>maximumDurationPeriodDuties) {
				maximumDurationPeriodDuties=1.0*d.durationPeriodInHours();
			}
		}
		//Ponemos que el minimo en el inicio sea el maximo posible, y de ahi vamos decreciendo
		minimumDurationPeriodDuties=maximumDurationPeriodDuties;
		for (final Duty d: duties) {
			//Para calcular el minimo de las workloads tasks
			if (d.durationPeriodInHours()<minimumDurationPeriodDuties) {
				minimumDurationPeriodDuties=1.0*d.durationPeriodInHours();
			}
		}
		final List<Double> workloadsLists= new ArrayList<>(); //Creamos una lista de workloads
		for (final Duty d: duties) {
			final Double workload= 1.0*d.durationPeriodInHours();
			workloadsLists.add(workload);  //Metemos los workloads en la lista
		}
		deviationDurationPeriodDuties= AdministratorDashboardShowService.calculateStandardDeviation(workloadsLists); //Usamos una funcion creada para calcular la desviacion tipica de  los workloads
		
		
		final Double noTerminadas = (double) (duties.size() - terminadas.size());
		final Double term = (double) terminadas.size();

		result = new Dashboard();
		result.setAvegageNumberOfApplicationsPerEmployer(averageNumberOfApplicationsPerEmployer);
		result.setAverageNumberOfApplicationsPerWorker(averageNumberOfApplicationsPerWorker);
		result.setAverageNumberOfJobsPerEmployer(averageNumberOfJobsPerEmployer);
		result.setRatioOfPendingApplications(ratioOfPendingApplications);
		result.setRatioOfAcceptedApplications(ratioOfAcceptedApplications);
		result.setRatioOfRejectedApplications(ratioOfRejectedApplications);
		
		result.setNumberPublicDuty(numberPublicDuty);
		result.setNumberPrivateDuty(numberPrivateDuty);
		result.setNumberFinalDuty(term);
		result.setNumberNoFinalDuty(noTerminadas);
		result.setAverageDurationPeriodDuties(averageDurationPeriodDuties);
		result.setDeviationDurationPeriodDuties(deviationDurationPeriodDuties);
		result.setMinimumDurationPeriodDuties(minimumDurationPeriodDuties);
		result.setMaximumDurationPeriodDuties(maximumDurationPeriodDuties);
		result.setAverageWorkloadDuties(averageWorkloadDuties);
		result.setDeviationWorkloadDuties(deviationWorkloadDuties);
		result.setMaximumWorkloadDuties(maximumWorkloadDuties);
		result.setMinimumWorkloadDuties(minimumWorkloadDuties);

		return result;
	}
	
	private static double calculateStandardDeviation(final List<Double> lista) {
		//Sumatorio de los valores de la lista
		double sum=0.0;
		for (int i =0; i< lista.size(); i++) {
			sum=sum+lista.get(i);
		}
		//Obtiene la media
		final double media = sum / lista.size();
		
		//Calcula la desviacion estandar
		double standardDeviation = 0.0;
		for (int i = 0; i < lista.size(); i++) {
			standardDeviation += Math.pow(lista.get(i) - media, 2);

		}
		return Math.sqrt(standardDeviation/lista.size());
	}


}
