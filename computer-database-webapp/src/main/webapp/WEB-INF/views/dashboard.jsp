<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib tagdir="/WEB-INF/tags" prefix="myTags"%>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>

<jsp:include page="templates/headers.jsp" />
<body>
	<jsp:include page="templates/navbar.jsp" />
	<div id="messages">
		<c:out value="${message}" />
	</div>
	<section id="main">
		<div class="container">
			<h1 id="homeTitle">
				<spring:message code="dashboard.n_computers_msg"
					arguments="${page.totalComputers}" />
			</h1>
			<div id="actions" class="form-horizontal">
				<div class="pull-left">
					<form id="searchForm" action="#" method="GET" class="form-inline">
						<spring:message code="dashboard.search_placeholder"
							var="search_placeholder" />
						<input type="search" id="searchbox" name="search"
							class="form-control" placeholder="${ search_placeholder }"
							<c:if test="${page.search != null}">value="${page.search}"</c:if> />
						<spring:message code="dashboard.search_filter_button"
							var="search_filter_button" />
						<input type="submit" id="searchsubmit"
							value="${ search_filter_button }" class="btn btn-primary" />
					</form>
				</div>
				<div class="pull-right">
					<a class="btn btn-success" id="addComputer"
						href="<c:url value="/add-computer" />"><spring:message
							code="dashboard.add_computer_button" /></a> <a
						class="btn btn-default" id="editComputer" href="#"
						onclick="$.fn.toggleEditMode();"><spring:message
							code="dashboard.edit_button" /></a>
				</div>
			</div>
		</div>

		<form id="deleteForm" action="delete" method="POST">
			<input type="hidden" name="selection" value="">
		</form>


		<div class="container" style="margin-top: 10px;">
			<table class="table table-striped table-bordered">
				<thead>
					<tr>
						<!-- Variable declarations for passing labels as parameters -->
						<!-- Table header for Computer Name -->

						<th class="editMode" style="width: 60px; height: 22px;"><input
							type="checkbox" id="selectall" /> <span
							style="vertical-align: top;"> - <a href="#"
								id="deleteSelected" onclick="$.fn.deleteSelected();"> <i
									class="fa fa-trash-o fa-lg"></i>
							</a>
						</span></th>
						<th><a
							href="<myTags:link target="dashboard" page="${page}"  sortCriterion="NAME" />"><spring:message
									code="dashboard.name_label" /></a></th>

						<!-- Table header for introduced_label -->

						<th><a
							href="<myTags:link target="dashboard"	sortCriterion="DATE_INTRODUCED" page="${page}" />"><spring:message
									code="dashboard.introduced_label" /></a></th>

						<!-- Table header for Discontinued Date -->

						<th><a
							href="<myTags:link target="dashboard" page="${page}"	sortCriterion="DATE_DISCONTINUED" />"><spring:message
									code="dashboard.discontinued_label" /></a></th>

						<!-- Table header for Company -->

						<th><a
							href="<myTags:link target="dashboard" page="${page}" sortCriterion="COMPANY_NAME"/>"><spring:message
									code="dashboard.company_label" /></a></th>

					</tr>
				</thead>
				<!-- Browse attribute computers -->
				<tbody id="results">
					<c:forEach items="${page.computerList}" var="computer">
						<tr>
							<td class="editMode"><input type="checkbox" name="cb"
								class="cb" value="${computer.id}"></td>
							<td><a href='edit-computer/${computer.id}' onclick=""><c:out
										value="${computer.name}" /></a></td>
							<td><c:out value="${computer.introduced}" /></td>
							<td><c:out value="${computer.discontinued}" /></td>
							<td><c:out value="${computer.company.name}" /></td>
						</tr>
					</c:forEach>
				</tbody>
			</table>
		</div>
		<script type="text/javascript">
			var strings = new Array();
			strings['delete_confirm_msg'] = "<spring:message code='dashboard.delete_confirm_msg' javaScriptEscape='true' />";
			strings['view_button'] = "<spring:message code='dashboard.view_button' javaScriptEscape='true' />";
			strings['edit_button'] = "<spring:message code='dashboard.edit_button' javaScriptEscape='true' />";
		</script>
	</section>

	<footer class="navbar-fixed-bottom">
		<div class="container text-center">
			<myTags:page page="${page}"></myTags:page>
		</div>
	</footer>

	<jsp:include page="templates/footer.jsp" />
</body>
</html>