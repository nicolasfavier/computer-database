<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>

<jsp:include page="templates/headers.jsp" />
<body>
	<jsp:include page="templates/navbar.jsp" />
	<section id="main">
		<div class="container">
			<div class="row">
				<div class="col-xs-8 col-xs-offset-2 box">
					<h1>
						<spring:message code="computer_form.add_computer_title" />
					</h1>
					
					<form:form modelAttribute="computerDto" action="add-computer"
						method="POST">
						<fieldset>
							<div class="form-group">
								<label for="name"> <spring:message
										code="computer_form.name_label" />
								</label>
								<spring:message code="computer_form.name_placeholder"
									var="computer_name_placeholder" />
								<input required type="text" required class="form-control"
									id="computerName" name="name"
									placeholder="${ computer_name_placeholder }"
									value="${fn:escapeXml(computer.name)}">
								<form:errors path="name" cssClass="error"></form:errors>
							</div>
							<div class="form-group">
								<label for="introduced"> <spring:message
										code="computer_form.introduced_label" />
								</label>
								<spring:message code="computer_form.introduced_placeholder"
									var="computer_introduced_placeholder" />
								<input type="datetime-local" class="form-control date"
									id="introduced" name="introduced"
									placeholder="${ computer_introduced_placeholder }"
									value="${fn:escapeXml(computer.introduced)}">
								<form:errors path="introduced" cssClass="error"></form:errors>
							</div>


							<div class="form-group">
								<label for="discontinued"> <spring:message
										code="computer_form.discontinued_label" />
								</label>
								<spring:message code="computer_form.discontinued_placeholder"
									var="computer_discontinued_placeholder" />
								<input type="datetime-local" class="form-control date"
									id="discontinued" name="discontinued"
									placeholder="${ computer_discontinued_placeholder }"
									value="${fn:escapeXml(computer.discontinued)}">
								<form:errors path="discontinued" cssClass="error"></form:errors>
							</div>

							<div class="form-group">
								<label for="companyId"><spring:message
										code="computer_form.company_label" /></label>
								<form:select class="form-control" id="company.id"
									name="company.id" path="company.id">
									<c:forEach items="${companies}" var="company">
										<option value="${company.id}"><c:out
												value="${company.name}" /></option>
									</c:forEach>
									<c:choose>
										<c:when test="${computer.company.id == 0}">
											<option value="0" selected>No company</option>
										</c:when>
										<c:otherwise>
											<option value="0">No company</option>
										</c:otherwise>
									</c:choose>
								</form:select>
							</div>
						</fieldset>
						<div class="actions pull-right">
							<spring:message code="computer_form.add_button" var="add_message" />

							<input type="submit" value="${ add_message }"
								class="btn btn-primary validation">
							<spring:message code="computer_form.or" />
							<a href="dashboard" class="btn btn-default"><spring:message
									code="computer_form.cancel_button" /></a>
						</div>
					</form:form>
				</div>
			</div>
		</div>
		<script type="text/javascript">
			var strings = new Array();
			strings['DateRegex'] = "<spring:message code='binding.date.regex' javaScriptEscape='true' />";
		</script>
	</section>
	<jsp:include page="templates/footer.jsp" />
</body>
</html>