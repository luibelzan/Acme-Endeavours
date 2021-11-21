<%@page language="java"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="acme" tagdir="/WEB-INF/tags"%>

<acme:form readonly="true">
	<acme:form-textbox code="anonymous.duty.form.label.title" path="title"/>
	<acme:form-textbox code="anonymous.duty.form.label.description" path="description"/>
	<acme:form-textbox code="anonymous.duty.form.label.periodInitial" path="periodInitial"/>
	<acme:form-textbox code="anonymous.duty.form.label.periodFinal" path="periodInitial"/>
	<acme:form-textbox code="anonymous.duty.form.label.workload" path="workloadInHours"/>
	<acme:form-textbox code="anonymous.duty.form.label.link" path="link"/>	
	<acme:form-checkbox code="anonymous.duty.form.label.isPublic" path="isPublic"/>	
	
	<acme:form-return code="anonymous.duty.form.button.return" />

</acme:form>