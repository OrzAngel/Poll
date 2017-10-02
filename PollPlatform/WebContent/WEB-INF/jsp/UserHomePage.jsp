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

<p style = "text-align: right">${user.name}|<a href="UserHomePage?name=${user.name}" target="_self">Homepage</a>|<a href="SignInForm.html" target="_self">Signout</a></p>
<p><font size = "5">Welcome ${user.name}!</font></p>
<p>${message}</p>

<p>Your Current Points is ${user.points}.</p>
<p>Here are the recommended polls for you: </p>

<c:forEach var="poll" items="${pollList}">
	<a href="poll?pollId=${poll.pollId}&name=${user.name}">${poll.pollTitle}</a>
	<br>
</c:forEach>
<br>
<form action="SeeMyPolls" >
	<input type="hidden" name = "name" value="${user.name}" />
	<input type="submit" value="See My Polls" />
</form>
<form action="SeeAvailablePolls" >
	<input type="hidden" name = "name" value="${user.name}" />
	<input type="submit" value="See Avaliable Polls" />
</form>

</div>

