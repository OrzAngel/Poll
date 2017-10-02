<style type="text/css">
html, body {
	height: 100%;
}

body {
	background-image:
		url(http://pic1.win4000.com/wallpaper/f/586358d1b5e53.jpg);
	background-repeat: no-repeat;
	background-size: 100% 100%;
	font-family: Monospace
}
</style>

<head>
<title>Sign up</title>
</head>

<body>

	<br>
	<br>
	<br>
	<center>
		<p>
			<font size="6" style="text-align: center" face="Impact" color="white">
				POLL PLATFORM</font>
		</p>
	</center>

	<div class="container" align="left"
		style="margin: 20 auto; width: 400px;">
		<p>Welcome! Please sign up here:</p>
		<p>
			<font color="red">${message}</font>
		</p>
		<form onsubmit="return checkSubmit()" action="signup" method="post">
			<table>
				<tr>
					<td align="left">User Name:</td>
					<td align="left"><input type="text" name="name" /></td>
				</tr>
				<tr>
					<td align="left">Password:</td>
					<td align="left"><input type="password" name="password"
						id="password" onkeyup='check();' /></td>
				</tr>
				<tr>
					<td align="left">Confirm password:</td>
					<td align="left"><input type="password" id="confirmed"
						onkeyup='check();' /></td>
					<td align="left"><span id='message'></span></td>
				</tr>
				<tr>
					<td></td>
					<td align="left"><input type="submit" value="Sign up" /></td>
				</tr>
				<tr>
					<td></td>
					<td align="left"><span id='failure'></span></td>
				</tr>
			</table>
		</form>
	</div>
</body>

<script>
	function check() {
		if (document.getElementById('confirmed').value == "") {
			return false;
		}
		if (document.getElementById('password').value == document
				.getElementById('confirmed').value) {
			document.getElementById('message').style.color = 'green';
			document.getElementById('message').innerHTML = 'matching';
			document.getElementById('failure').innerHTML = '';
			return true;
		} else {
			document.getElementById('message').style.color = 'red';
			document.getElementById('message').innerHTML = 'not matching';
			return false;
		}
	}

	function checkSubmit(evt) {
		if (check()) {
			return true;
		} else {
			document.getElementById('failure').style.color = 'red';
			document.getElementById('failure').innerHTML = 'Password not match';
			return false;
		}
	}
</script>