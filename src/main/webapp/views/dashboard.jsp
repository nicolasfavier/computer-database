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
	<div id="messages" >${message}</div>
	<section id="main">
		<div class="container">
			<h1 id="homeTitle">${page.totalComputers} Computers found</h1>
			<div id="actions" class="form-horizontal">
				<div class="pull-left">
					<form id="searchForm" action="#" method="GET" class="form-inline">

						<input type="search" id="searchbox" name="search"
							class="form-control" placeholder="Search name" <c:if test="${search != null}">value="${search}"</c:if>/> <input
							type="submit" id="searchsubmit" value="Filter by name" 
							class="btn btn-primary" />
					</form>
				</div>
				<div class="pull-right">
					<a class="btn btn-success" id="addComputer" href="addComputer">Add
						Computer</a> 
					<a class="btn btn-default" id="editComputer"
						href="#" onclick="$.fn.toggleEditMode();">Edit</a>
				</div>
			</div>
		</div>

		<form id="deleteForm" action="#" method="POST">
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
						<th>Computer name</th>
						<th>Introduced date</th>
						<!-- Table header for Discontinued Date -->
						<th>Discontinued date</th>
						<!-- Table header for Company -->
						<th>Company</th>

					</tr>
				</thead>
				<!-- Browse attribute computers -->
				<tbody id="results">
					<c:forEach items="${page.computerList}" var="computer">
						<tr>
							<td class="editMode"><input type="checkbox" name="cb" class="cb" value="${computer.id}"></td>
							<td><a href='editComputer?id=${computer.id}' onclick=""><c:out value="${computer.name}"/></a>
							</td>
							<td><c:out value="${computer.introduced}"/></td>
							<td><c:out value="${computer.discontinued}"/></td>
							<td><c:out value="${computer.company.name}"/></td>

						</tr>
					</c:forEach>
				</tbody>
			</table>

		</div>
	</section>

	<footer class="navbar-fixed-bottom">
		<div class="container text-center">
			<ul class="pagination">
				<li <c:if test="${page.index == 0}">style="display:none;"</c:if>><a href="dashboard?page=${page.index -1}&nbPerPage=${page.nbComputerPerPage}&search=${search}" aria-label="Previous"> <span
						aria-hidden="true">&laquo;</span>
				</a></li>
				<c:forEach begin="${page.range[0]}" end="${page.range[1]}" var="index">
					<li <c:if test="${page.index == index}">class="active"</c:if>><a href="dashboard?page=${index}&nbPerPage=${page.nbComputerPerPage}&search=${search}">${index+1}</a></li>
				</c:forEach>
				<li <c:if test="${page.index == page.totalPages}">style="display:none;"</c:if>><a href="dashboard?page=${page.index +1}&nbPerPage=${page.nbComputerPerPage}&search=${search}" aria-label="Next"> <span aria-hidden="true">&raquo;</span>
				</a></li>
			</ul>
			<div class="btn-group btn-group-sm pull-right" role="group">
				<a type="button" href="dashboard?page=0&nbPerPage=10&search=${search}" ${page.nbComputerPerPage == 10 ? 'class="btn btn-primary"' : 'class="btn btn-default"'}>10</a>
				<a type="button" href="dashboard?page=0&nbPerPage=50&search=${search}" ${page.nbComputerPerPage == 50 ? 'class="btn btn-primary"' : 'class="btn btn-default"'}>50</a>
				<a type="button" href="dashboard?page=0&nbPerPage=100&search=${search}" ${page.nbComputerPerPage == 100 ? 'class="btn btn-primary"' : 'class="btn btn-default"'}>100</a>
			</div>

		</div>
	</footer>

<jsp:include page="/views/templates/footer.jsp"/>
</body>
</html>