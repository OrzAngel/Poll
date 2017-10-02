<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>


<style type="text/css">
html, body {
	height: 100%;
}

body {
	background-image:
		url(http://pic1.win4000.com/wallpaper/f/586358d1b5e53.jpg);
	background-repeat: no-repeat;
	background-size: 100% 100%;
		font-family:Monospace
}


</style>


<br><br><br><br><br>
<center><p><font size="6" style="text-align: center" face="Impact" color="white"> POLL PLATFORM</font></p></center>
<div class="container" align="center" style="margin:20 auto; width: 500px;">
	<p><font color="red">${message}</font></p>
	<form action="signin" method="post">
	
		<table>
			<tr>
				<td align="left">Name:</td>
				<td align="left"><input type="text" name="name" /></td>
			</tr>
			<tr>
				<td align="left">Password:</td>
				<td align="left"><input type="password" name="password" /></td>
			</tr>
			<tr>
				<td></td>
				<td align="left"><input type="submit" value="Login" /></td>
			</tr>

		</table>
	</form>

	<a href="SignUpForm.html">Don't have an account? Sign up here! </a>
</div>

