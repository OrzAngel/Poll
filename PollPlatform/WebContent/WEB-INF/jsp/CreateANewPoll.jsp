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
	font-family:Monospace;
}
div {
    margin-right: 10px;
    margin-left: 30px;
}
</style>


<div>
<p style = "text-align: right">${name}|<a href="UserHomePage?name=${name}" target="_self">Homepage</a>|<a href="SignInForm.html" target="_self">Signout</a></p>
<p><font size = "5">Hi ${name}!</font></p>
<p>Create a poll here:</p>

<form action="createpoll?name=${name}" method="post">
	<p>Please enter your poll name: </p>
	<input type="text" name="pollTitle" placeholder="Poll Title"/>
	<p><font size = 2 color="red">${message1}</font></p>
	<p>Please enter your question: </p>
	<input type="text" name="content"  placeholder="Poll Content"/>
	<p><font size = 2 color="red">${message2}</font></p>
	<p>Please choose the tag: </p>
	<input type="radio" name ="tag" value="business">business	
	<input type="radio" name ="tag" value="science">science
	<input type="radio" name ="tag" value="health">health
	<input type="radio" name ="tag" value="sports">sports
	<input type="radio" name ="tag" value="arts">arts
	<input type="radio" name ="tag" value="entertainment">entertainment
	<input type="radio" name ="tag" value="life">life
	<input type="radio" name ="tag" value="others">others
	<%-- <p><font size = 2 color="red">${message3}</font></p> --%>
	<br>
	<br>	
	<input type="submit" value ="create"/>
</form>
</div>
