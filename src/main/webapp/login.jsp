<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/css/bootstrap.min.css">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <meta charset="UTF-8"/>
    <title>Регистрация</title>
</head>
<body>
    <form action="login" method="POST">
        <h1>Login</h1>
        <h6>Login: <input type="text" name="login" /> <br></h6>
        <h6>Password: <input type="password" name="password" /> <br></h6>
        <input type="submit" value="Sign in">
        <h1>${error}</h1>
    </form>

    <div class="button">
        <p class="button"><a href="http://localhost:8080/registration" target="_self">Sign UP</a></p>
    </div>
</body>
</html>