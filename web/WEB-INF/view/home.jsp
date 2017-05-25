<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>主页</title>
</head>
<body>
您好，现在是${date}。

<hr size="1"/>
<form action="species_add.do" method="post">
类别<input name="name" type="text"/> 
<input type="submit"/>
</form>
<hr size="1"/>
<c:forEach var="spc" items="${spcs }">
${spc.name }, ${spc.id },
	[<c:forEach var="p" items="${spc.pets }">
	${p.name },
	</c:forEach>]
<br/>
</c:forEach>
<hr size="1"/>
<form action="pet_add.do" method="post">
名字<input name="name" type="text"/>, 
性别<input name="sex" type="text"/>, 
等级<input name="level" type="text"/>
类别<input name="species" type="text"/>
<input type="submit"/>
</form>
<hr size="1"/>
<c:forEach var="pet" items="${pets }">
${pet.name }, ${pet.sex }, ${pet.leve }, ${pet.id }, ${pet.species.name }<br/>
</c:forEach>
</body>
</html>