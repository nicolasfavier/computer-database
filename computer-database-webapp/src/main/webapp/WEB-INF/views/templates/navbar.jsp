<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>

<header class="navbar navbar-inverse navbar-fixed-top">
	<div class="container">
		<div class="navbar-header">
			<a class="navbar-brand" href="dashboard"> Application - Computer
				Database </a>
		</div>
			<a href="<c:url value="j_spring_security_logout" />" > Logout</a>
			<div id="navbar" class="navbar-collapse collapse">
			<ul class="nav navbar-nav navbar-right">
				<li class="dropdown"><a href="#" class="dropdown-toggle"
					data-toggle="dropdown" role="button" aria-expanded="false"><spring:message
							code="header.lang_dropdown" /><span class="caret"></span></a>
					<ul class="dropdown-menu" role="menu">
						<li><a href="?lang=fr"> <spring:message
									code="header.lang_french" /></a></li>
						<li><a href="?lang=en"> <spring:message
									code="header.lang_english" /></a></li>
					</ul></li>
			</ul>
		</div>
	</div>
</header>