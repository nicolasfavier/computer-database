<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>

<jsp:include page="templates/headers.jsp" />
<body>
	<jsp:include page="templates/navbar.jsp" />
	<div id="messages" ><c:out value="${message}"/></div>
	<section id="main">
		<div class="container">
			<div class="row">
				<div class="col-xs-8 col-xs-offset-2 box">
					<h1>Add Computer</h1>
					<form action="addComputer" method="POST">
						<fieldset>
							<div class="form-group">
								<label for="computerName">Computer name</label> <input required
									type="text" class="form-control" id="computerName"
									name="computerName" placeholder="Computer name"
									value="${fn:escapeXml(computer.name)}">
							</div>
							<input type="hidden" id="computerId" name="id"
								value="${computer.id}">
							<div class="form-group">
								<label for="introduced">Introduced date <span
									class="errorMessage"></span></label> <input type="datetime-local"
									class="form-control date" id="introduced" name="introduced"
									placeholder="Introduced date"
									value="${fn:escapeXml(computer.introduced)}">
							</div>
							<div class="form-group">
								<label for="discontinued">Discontinued date <span
									class="errorMessage"></span></label> <input type="datetime-local"
									class="form-control date" id="discontinued" name="discontinued"
									placeholder="Discontinued date"
									value="${fn:escapeXml(computer.discontinued)}">
							</div>
							<div class="form-group">
								<label for="companyId">Company</label> <select
									class="form-control" id="companyId" name="companyId">
									<c:forEach items="${companies}" var="company">
										<option value="${company.id}"><c:out
												value="${company.name}" /></option>
									</c:forEach>
								</select>
							</div>
						</fieldset>
						<div class="actions pull-right">
							<input type="submit" value="Add"
								class="btn btn-primary validation"> or <a
								href="dashboard" class="btn btn-default">Cancel</a>
						</div>
					</form>
				</div>
			</div>
		</div>
	</section>
	<jsp:include page="/views/templates/footer.jsp" />
</body>
</html>