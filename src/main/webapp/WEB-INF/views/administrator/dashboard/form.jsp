<%--
- form.jsp
-
- Copyright (c) 2012-2021 Rafael Corchuelo.
-
- In keeping with the traditional purpose of furthering education and research, it is
- the policy of the copyright owner to permit non-commercial use and redistribution of
- this software. It has been tested carefully, but it is not guaranteed for any particular
- purposes.  The copyright owner does not offer any warranties or representations, nor do
- they accept any liabilities with respect to them.
--%>

<%@page language="java"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="acme" tagdir="/WEB-INF/tags"%>

<h2>
	<acme:message code="administrator.dashboard.form.title.general-indicators"/>
</h2>

<table class="table table-sm">
	<caption>
		<acme:message code="administrator.dashboard.form.title.general-indicators"/>
	</caption>	
	<tr>
		<th scope="row">
			<acme:message code="administrator.dashboard.form.label.average-number-jobs-employer"/>
		</th>
		<td>
			<acme:print value="${averageNumberOfJobsPerEmployer}"/>
		</td>
	</tr>
	<tr>
		<th scope="row">
			<acme:message code="administrator.dashboard.form.label.average-number-applications-worker"/>
		</th>
		<td>
			<acme:print value="${averageNumberOfApplicationsPerWorker}"/>
		</td>
	</tr>
	<tr>
		<th scope="row">
			<acme:message code="administrator.dashboard.form.label.average-number-applications-employer"/>
		</th>
		<td>
			<acme:print value="${avegageNumberOfApplicationsPerEmployer}"/>
		</td>
	</tr>	
	
	
	<tr>
		<th scope="row">
			<acme:message code="administrator.dashboard.form.label.number-public-duty"/>
		</th>
		<td>
			<acme:print value="${numberPublicDuty}"/>
		</td>
	</tr>
	<tr>
		<th scope="row">
			<acme:message code="administrator.dashboard.form.label.number-private-duty"/>
		</th>
		<td>
			<acme:print value="${numberPrivateDuty}"/>
		</td>
	</tr>
	<tr>
		<th scope="row">
			<acme:message code="administrator.dashboard.form.label.number-final-duty"/>
		</th>
		<td>
			<acme:print value="${numberFinalDuty}"/>
		</td>
	</tr>
	<tr>
		<th scope="row">
			<acme:message code="administrator.dashboard.form.label.number-no-final-duty"/>
		</th>
		<td>
			<acme:print value="${numberNoFinalDuty}"/>
		</td>
	</tr>
	<tr>
		<th scope="row">
			<acme:message code="administrator.dashboard.form.label.average-duration-duties"/>
		</th>
		<td>
			<acme:print value="${averageDurationPeriodDuties}"/>
		</td>
	</tr>
	<tr>
		<th scope="row">
			<acme:message code="administrator.dashboard.form.label.deviation-duration-duties"/>
		</th>
		<td>
			<acme:print value="${deviationDurationPeriodDuties}"/>
		</td>
	</tr>
	<tr>
		<th scope="row">
			<acme:message code="administrator.dashboard.form.label.minimum-duration-duties"/>
		</th>
		<td>
			<acme:print value="${minimumDurationPeriodDuties}"/>
		</td>
	</tr>
	<tr>
		<th scope="row">
			<acme:message code="administrator.dashboard.form.label.maximum-duration-duties"/>
		</th>
		<td>
			<acme:print value="${maximumDurationPeriodDuties}"/>
		</td>
	</tr>
	<tr>
		<th scope="row">
			<acme:message code="administrator.dashboard.form.label.average-workload-duties"/>
		</th>
		<td>
			<acme:print value="${averageWorkloadDuties}"/>
		</td>
	</tr>
	<tr>
		<th scope="row">
			<acme:message code="administrator.dashboard.form.label.deviation-workload-duties"/>
		</th>
		<td>
			<acme:print value="${deviationWorkloadDuties}"/>
		</td>
	</tr>
	<tr>
		<th scope="row">
			<acme:message code="administrator.dashboard.form.label.minimum-workload-duties"/>
		</th>
		<td>
			<acme:print value="${minimumWorkloadDuties}"/>
		</td>
	</tr>
	<tr>
		<th scope="row">
			<acme:message code="administrator.dashboard.form.label.maximum-workload-duties"/>
		</th>
		<td>
			<acme:print value="${maximumWorkloadDuties}"/>
		</td>
	</tr>
	
	
</table>

<h2>
	<acme:message code="administrator.dashboard.form.title.application-statuses"/>
</h2>

<div>
	<canvas id="canvas"></canvas>
</div>

<script type="text/javascript">
	$(document).ready(function() {
		var data = {
			labels : [
					"PENDING", "ACCEPTED", "REJECTED"
			],
			datasets : [
				{
					data : [
						<jstl:out value="${ratioOfPendingApplications}"/>, 
						<jstl:out value="${ratioOfAcceptedApplications}"/>, 
						<jstl:out value="${ratioOfRejectedApplications}"/>
					]
				}
			]
		};
		var options = {
			scales : {
				yAxes : [
					{
						ticks : {
							suggestedMin : 0.0,
							suggestedMax : 1.0
						}
					}
				]
			},
			legend : {
				display : false
			}
		};
	
		var canvas, context;
	
		canvas = document.getElementById("canvas");
		context = canvas.getContext("2d");
		new Chart(context, {
			type : "bar",
			data : data,
			options : options
		});
	});
</script>
