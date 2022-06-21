<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
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
		<div id="header">
			<h1>${blogVo.title }</h1>
			<ul>
			<c:choose>
					<c:when test="${not empty authUser && not empty authUser.id}">
							<li><a href="${pageContext.request.contextPath}/user/logout">로그아웃</a></li>
							<li><a href="${pageContext.request.contextPath}/${blogVo.id }/admin/basic">블로그 관리</a></li>
					</c:when>
					<c:otherwise>
							<li><a href="${pageContext.request.contextPath}/user/login">로그인</a></li>
					</c:otherwise>				
			</c:choose>
			</ul>
		</div>
		<div id="wrapper">
			<div id="content">
				<div class="blog-content">
					<h4>Spring Camp 2016 참여기</h4>
					<p>
							${fn:replace(categorylist[categoryNo].description, newLine, "<br/>") }
					<p>
				</div>
				<ul class="blog-list">
					<li><a href="">Spring Camp 2016 참여기</a> <span>2015/05/02</span>	</li>
					<li><a href="">Spring Boot 사용법 정리</a> <span>2015/05/02</span>	</li>
					<li><a href="">Spring Security 설정법</a> <span>2015/05/02</span>	</li>
					<li><a href="">JPA + Hinernate</a> <span>2015/05/02</span>	</li>
					<li><a href="">AOP 활용하기 - DAO 실행시간 측정하기</a> <span>2015/05/02</span>	</li>
				</ul>
			</div>
		</div>

		<div id="extra">
			<div class="blog-logo">
				<img src="${pageContext.request.contextPath }${blogVo.logo }">
			</div>
		</div>

		<div id="navigation">
			<h2>카테고리</h2>
			<ul>
			<c:forEach items="${categorylist }" var='vo' varStatus='status'>
					<li><a href="${pageContext.request.contextPath}/${blogVo.id }/${status.index }">${vo.name }</a></li>
			</c:forEach>
			</ul>
		</div>
		
		<div id="footer">
			<p>
				<strong>${blogVo.title }</strong> is powered by JBlog (c)2016
			</p>
		</div>
	</div>
</body>
</html>