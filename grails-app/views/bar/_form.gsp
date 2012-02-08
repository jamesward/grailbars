<%@ page import="com.jamesward.grailbars.Bar" %>



<div class="fieldcontain ${hasErrors(bean: barInstance, field: 'name', 'error')} ">
	<label for="name">
		<g:message code="bar.name.label" default="Name" />
		
	</label>
	<g:textField name="name" value="${barInstance?.name}"/>
</div>

