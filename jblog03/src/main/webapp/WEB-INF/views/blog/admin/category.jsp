<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<!doctype html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>JBlog</title>
<Link rel="stylesheet" href="${pageContext.request.contextPath}/assets/css/jblog.css">
</head>
<body>
	<div id="container">
		<c:import url="/WEB-INF/views/includes/header.jsp"/>
		<div id="wrapper">
			<div id="content" class="full-screen">
				<ul class="admin-menu">
					<li><a href="${pageContext.request.contextPath }/${authUser.id}/admin/basic">기본설정</a></li>
					<li class="selected">카테고리</li>
					<li><a href="${pageContext.request.contextPath }/${authUser.id}/admin/write">글작성</a></li>
				</ul>
		      	<table class="admin-cat">
			      		<tr>
			      			<th>번호</th>
			      			<th>카테고리명</th>
			      			<th>포스트 수</th>
			      			<th>설명</th>
			      			<th>삭제</th>      			
			      		</tr>
								<c:set var='count' value='${fn:length(categorylist)}'/>
								<c:forEach items="${categorylist }" var='vo' varStatus='status'>
								<tr>
										<td>${count-status.index }</td>
										<td>${vo.name }</td>
										<td>${vo.postCount }</td>
										<td>${vo.description }</td>
										<td>
										<c:if test="${vo.postCount == 0 and count != 1}">
												<a href="${pageContext.servletContext.contextPath }/${blogVo.id }/admin/category/delete/${vo.no }" class="del">
														<img src="${pageContext.request.contextPath}/assets/images/delete.jpg">
												</a>
										</c:if>
										</td>
								</tr>
								</c:forEach>    
						</table>
      			
      	<form:form 
      	modelAttribute="categoryVo"
      	action="${pageContext.request.contextPath }/${authUser.id }/admin/category"
      	method="post" >
      			<h4 class="n-c">새로운 카테고리 추가</h4>
		      	<table id="admin-cat-add">
		      		<tr>
		      			<td class="t">카테고리명</td>
		      			<td>
				      			<form:input path="name" type="text" name="name" />
				      			<p style="test-align:left; padding:0; color:red"><form:errors path='name'/></p>	
		      			</td>
		      		</tr>
		      		<tr>
		      			<td class="t">설명</td>
		      			<td>
		      					<form:input path="description" type="text" name="description" />
		      					<p style="test-align:left; padding:0; color:red"><form:errors path='description'/></p>	
		      			</td>
		      		</tr>
		      		<tr>
		      			<td class="s">&nbsp;</td>
		      			<td><input type="submit" value="카테고리 추가"></td>
		      		</tr>      		      		
		      	</table>
		     </form:form>
			</div>
		</div>
	<c:import url="/WEB-INF/views/includes/footer.jsp"/>
	</div>
</body>
</html>