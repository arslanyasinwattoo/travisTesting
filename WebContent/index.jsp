<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Rest Demo</title>

</head>
<body>
	<h1>work flow and apis</h1>

	<h2>/api/import/{username}</h2>
	<p>imports users from github.</p>
	<p>saves or updates data in mysql from users,repositories and
		languages of repositories</p>
	<h2>/api/show/users</h2>
	<P>fetches all users from user table</P>
	<h2>/api/show/{id}/repos</h2>
	<p>fetches repositories based on user id</p>
	<h2>/api/show/{id}/lang</h2>
	<p>fetches languages of repository which belonged to a particular
		user</p>
	<p>show languages used and percentage of contribution in the
		particular repository</p>
	<h2>/api/show/{username}</h2>
	<p>shows languages used and percentage of contribution in all the
		repositories</p>
</body>
</html>