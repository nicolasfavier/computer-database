<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>

<jsp:include page="templates/headers.jsp" />
<body>
	<jsp:include page="templates/navbar.jsp" />

	<section id="main">
		<div class="container">
			<div class="alert alert-danger">
				<spring:message code="error.403.message" />
<br />
			</div>
		</div>
	</section>
	
	<jsp:include page="templates/footer.jsp" />

</body>
</html>