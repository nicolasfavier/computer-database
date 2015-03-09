<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<jsp:include page="templates/headers.jsp"/>
<body>
	<jsp:include page="templates/navbar.jsp" />

	<section id="main">
		<div class="container">	
			<div class="alert alert-danger">
				Error 500: An error has occured!
				<br/>
				<!-- stacktrace -->
			</div>
		</div>
	</section>

	<jsp:include page="templates/footer.jsp" />

</body>
</html>