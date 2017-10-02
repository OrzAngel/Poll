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
<p><font size = "5">Hi ${name}! </font></p>
<p>Here are your polls:</p>

<c:forEach var="poll" items="${pollList}">
	<a href="pollResults?pollId=${poll.pollId}&name=${name}">${poll.pollTitle}</a>
	<br>
</c:forEach>
<br>

<form action="CreateANewPoll" method="post">
	<input type="submit" value = "Create a new Poll"/> 
	<input type="hidden" name="name" value="${name}" /> 
<%-- 	<input type="hidden" name="pollId" value="${poll.pollId}" /> --%>
</form>
</div>