<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Send Mail Using Cloud Foundry Service Broker</title>
</head>
<body>
	<form action="sendMail" method="POST">
  		Mail To:<br><input type="text" name="to">
  		<br>
  		Mail From:<br><input type="text" name="from">
  		<br>
  		Application Id:<br><input type="text" name="appId">
  		<br>
  		Content:<br><input type="text" name="content">
  		<br>
  		<input type="submit" value="Send Mail">
	</form> 
</body>
</html>