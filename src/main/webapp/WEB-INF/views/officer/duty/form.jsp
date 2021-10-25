<%@page language="java"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="acme" tagdir="/WEB-INF/tags"%>

<acme:form>
	<acme:form-textbox code="officer.duty.form.label.title" path="title"/>
	<acme:form-textarea code="officer.duty.form.label.description" path="description"/>
	<acme:form-moment code="officer.duty.form.label.initial" path="periodInitial"/>
	<acme:form-moment code="officer.duty.form.label.end" path="periodFinal"/>
	<acme:form-double code="officer.duty.form.label.workloadInHours" path="workloadInHours"/>
	<acme:form-textbox code="officer.duty.form.label.link" path="link"/>
	<acme:form-checkbox code="officer.duty.form.label.isPublic" path="isPublic"/>
	
	<acme:form-submit test="${command == 'show'}" 
        code="officer.duty.form.button.update" 
        action="/officer/duty/update"/>
        
    <acme:form-submit test="${command == 'update'}" 
        code="officer.duty.form.button.update" 
        action="/officer/duty/update"/>
	
	<acme:form-submit test="${command == 'update'}" 
        code="officer.duty.form.button.delete" 
        action="/officer/duty/delete"/>
        
    <acme:form-submit test="${command == 'show'}" 
        code="officer.duty.form.button.delete" 
        action="/officer/duty/delete"/>
        
    <acme:form-submit test="${command == 'delete'}" 
        code="officer.duty.form.button.delete" 
        action="/officer/duty/delete"/>
	
	<acme:form-submit test="${command == 'create'}" 
        code="officer.duty.form.button.create" 
        action="/officer/duty/create"/>
	
	<acme:form-return code="officer.duty.form.button.return"/>
</acme:form>