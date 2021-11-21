<%@page language="java"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="acme" tagdir="/WEB-INF/tags"%>

<acme:form readonly="true">
	<acme:form-textbox code="authenticated.task.form.label.title" path="title"/>
	<acme:form-textbox code="authenticated.task.form.label.description" path="description"/>
	<acme:form-textbox code="authenticated.task.form.label.initial" path="periodInitial"/>
	<acme:form-textbox code="authenticated.task.form.label.end" path="periodInitial"/>
	<acme:form-textbox code="authenticated.task.form.label.workload" path="workloadInHours"/>
	<acme:form-textbox code="authenticated.task.form.label.link" path="link"/>
	<acme:form-textbox code="authenticated.task.form.label.isPublic" path="isPublic"/>

	<acme:form-return code="administrator.announcement.form.button.return"/>
</acme:form>