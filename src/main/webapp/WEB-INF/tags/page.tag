<%@ tag language="java" pageEncoding="UTF-8" description="Page template"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<%@ attribute name="page" required="true" type="com.nicolas.models.Page"%>
<%@ taglib tagdir="/WEB-INF/tags" prefix="myTags"%>

<ul class="pagination">
	<li <c:if test="${page.index == 0}">style="display:none;"</c:if>><a
		href="dashboard?page=${page.index -1}&nbComputerPerPage=${page.nbComputerPerPage}"
		aria-label="Previous"> <span aria-hidden="true">&laquo;</span>
	</a></li>
	<c:forEach begin="${page.range[0]}" end="${page.range[1]}" var="index">
		<li <c:if test="${page.index == index}">class="active"</c:if>><a
			href="<myTags:link target="dashboard" page="${page}" index="${index}"/>">${index+1}</a></li>
	</c:forEach>
	<li
		<c:if test="${page.index == page.totalPages}">style="display:none;"</c:if>>
		<a
		href="dashboard?page=${page.index +1}&nbComputerPerPage=${page.nbComputerPerPage}"
		aria-label="Next"> <span aria-hidden="true">&raquo;</span></a>
	</li>
</ul>
<div class="btn-group btn-group-sm pull-right" role="group">
	<a type="button"
		href="<myTags:link  target="dashboard" index="0" page="${page}"
				nbComputerPerPage="10"/>"
		${page.nbComputerPerPage == 10 ? 'class="btn btn-primary"' : 'class="btn btn-default"'}>10</a>
	<a type="button"
		href="<myTags:link target="dashboard" index="0" page="${page}"
				nbComputerPerPage="50"/>"
		${page.nbComputerPerPage == 50 ? 'class="btn btn-primary"' : 'class="btn btn-default"'}>50</a>
	<a type="button"
		href="<myTags:link target="dashboard" index="0" page="${page}"
				nbComputerPerPage="100"/>"
		${page.nbComputerPerPage == 100 ? 'class="btn btn-primary"' : 'class="btn btn-default"'}>100</a>
</div>



