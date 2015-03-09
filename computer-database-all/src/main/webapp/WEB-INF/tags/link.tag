<%@ tag language="java" pageEncoding="UTF-8" description="Page template"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<%@ attribute name="target" required="true"%>
<%@ attribute name="page" required="true" type="com.nicolas.dto.PageDto"%>
<%@ attribute name="index"  %>
<%@ attribute name="nbComputerPerPage"%>
<%@ attribute name="search" %>
<%@ attribute name="sortCriterion"%>

<c:if test="${empty search}" >
 <c:set var="search" value="${page.search}" />
</c:if>

<c:if test="${empty index}" >
 <c:set var="index" value="${page.index}" />
</c:if>

<c:if test="${empty nbComputerPerPage}" >
 <c:set var="nbComputerPerPage" value="${page.nbComputerPerPage}" />
</c:if>

<c:if test="${empty sortCriterion}" >
 <c:set var="sortCriterion" value="${page.sortCriterion}" />
</c:if>

<c:url value="${target}?index=${index}&nbComputerPerPage=${nbComputerPerPage}&search=${search}
&sortCriterion=${sortCriterion}"/>



