<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>

<jsp:include page="templates/headers.jsp"/>
<body>
	<header class="navbar navbar-inverse navbar-fixed-top">
		<div class="container">
			<a class="navbar-brand" href="dashboard.html"> Application -
				Computer Database </a>
		</div>
	</header>
	<section id="main">
		<div class="container">
			<div class="row">
				<div class="col-xs-8 col-xs-offset-2 box">
					<div class="label label-default pull-right">${computer.id}</div>
					<h1>Edit Computer</h1>

					<form action="editComputer" method="POST">
						<input type="hidden" value="0" />
						<fieldset>
							<div class="form-group">
								<label for="computerName">Computer name</label> <input
									type="text" class="form-control" id="computerName"
									name="computerName" placeholder="Computer name"
									value="${fn:escapeXml(computer.name)}">
							</div>
							<input type="hidden" id="computerId" name="id" value="${computer.id}">
							<div class="form-group">
								<label for="introduced">Introduced date</label> <input
									type="datetime-local" class="form-control" id="introduced"
									name="introduced" placeholder="Introduced date"
									value="${fn:escapeXml(computer.introduced)}">
							</div>
							<div class="form-group">
								<label for="discontinued">Discontinued date</label> <input
									type="datetime-local" class="form-control" id="discontinued"
									name="discontinued" placeholder="Discontinued date"
									value="${fn:escapeXml(computer.discontinued)}">
							</div>
							<div class="form-group">
								<label for="companyId">Company</label> <select
									class="form-control" id="companyId" name="companyId">
									<c:forEach items="${companies}" var="company">
										<option
											<c:if test="${company.id == computer.id}">selected</c:if>
											value="${company.id}"><c:out value="${company.name}"/></option>
									</c:forEach>
								</select>
							</div>
						</fieldset>
						<div class="actions pull-right">
							<input type="submit" value="Edit" class="btn btn-primary">
							or <a href="dashboard" class="btn btn-default">Cancel</a>
						</div>
					</form>
				</div>
			</div>
		</div>
	</section>
<jsp:include page="/views/templates/footer.jsp"/>
</body>
</html>