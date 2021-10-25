<%@page language="java"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="acme" tagdir="/WEB-INF/tags"%>

<acme:list>
	<acme:list-column code="officer.duty.list.label.initial" path="periodInitial" width="33%"/>
	<acme:list-column code="officer.duty.list.label.end" path="periodFinal" width="33%"/>
	<acme:list-column code="officer.duty.list.label.title" path="title" width="33%"/>
</acme:list>