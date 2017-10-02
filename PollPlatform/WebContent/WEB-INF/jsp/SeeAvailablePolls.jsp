<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<style type="text/css">
html, 
body {
height: 100%;
}
body {
background-image: url(http://pic1.win4000.com/wallpaper/f/586358d1b5e53.jpg);
background-repeat: no-repeat;
background-size: 100% 100%;
	font-family:Monospace
}
div {
    margin-right: 10px;
    margin-left: 30px;
}
</style>

<div>
<p style = "text-align: right">${name}|<a href="UserHomePage?name=${name}" target="_self">Homepage</a>|<a href="SignInForm.html" target="_self">Signout</a></p>

<p><font size = "5">Hi ${name}!</font> </p>
<p>Here are the polls:</p>

<form action="category" method="post">
tags | <input type="submit" name ="tag" value="business">	
<input type="submit" name ="tag" value="science">
<input type="submit" name ="tag" value="health">
<input type="submit" name ="tag" value="sports">
<input type="submit" name ="tag" value="arts">
<input type="submit" name ="tag" value="entertainment">
<input type="submit" name ="tag" value="life">
<input type="submit" name ="tag" value="others">
<input type="submit" name ="tag" value="most recent">		
<input type="submit" name ="tag" value="all">	
<input type="hidden" name="name" value="${name}" />						
</form>

<c:forEach var="poll" items="${pollList}">
	<a href="poll?pollId=${poll.pollId}&name=${name}">${poll.pollTitle}</a>
	<br>
</c:forEach>
</div>