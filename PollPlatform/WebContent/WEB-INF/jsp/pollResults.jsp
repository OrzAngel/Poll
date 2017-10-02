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
<head>
<script
	src="https://cdnjs.cloudflare.com/ajax/libs/Chart.js/2.1.4/Chart.min.js"></script>
<title>Result for ${poll.getPollTitle()}</title>
</head>
<body>
<p style = "text-align: right">${name}|<a href="UserHomePage?name=${name}" target="_self">Homepage</a>|<a href="SignInForm.html" target="_self">Signout</a></p>
	<p><font size = "5">Hi ${name}!</font></p>
	<p>Here is the result for ${poll.getPollTitle()}:</p>
	<p>Number of people voted: ${numberOfPeopleVoted}</p>
	<p>Average score: ${average}</p>
	<div style="width: 400px; height: 400px">
		<canvas id="myChart"></canvas>
		<script>
			var x = [];
			x[0] = "${poll.getPollResults()[0]}";
			x[1] = "${poll.getPollResults()[1]}";
			x[2] = "${poll.getPollResults()[2]}";
			x[3] = "${poll.getPollResults()[3]}";
			x[4] = "${poll.getPollResults()[4]}";
			var ctx = document.getElementById("myChart");
			var myChart = new Chart(ctx, {
				type : 'bar',
				data : {
					labels : [ "1", "2", "3", "4", "5" ],
					datasets : [ {
						label : '# of Votes',
						data : x,
						backgroundColor : [ 'rgba(255, 99, 132, 0.2)' ],
						borderColor : [ 'rgba(255,99,132,1)' ],
						borderWidth : 1
					} ]
				},
				options : {
					scales : {
						yAxes : [ {
							ticks : {
								beginAtZero : true
							}
						} ]
					}
				}
			});
		</script>
	</div>
</body>
</div>