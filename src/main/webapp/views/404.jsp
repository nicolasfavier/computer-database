<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<!DOCTYPE html>
<html>
<head>
    <title>Computer Database</title>
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <!-- Bootstrap -->
<link href="<c:url value="css/bootstrap.min.css"/>" rel="stylesheet" media="screen">
<link href="<c:url value="css/font-awesome.css"/>" rel="stylesheet" media="screen">
<link href="<c:url value="css/main.css"/>" rel="stylesheet" media="screen">
</head>
<body>
    <header class="navbar navbar-inverse navbar-fixed-top">
        <div class="container">
            <a class="navbar-brand" href="dashboard.html"> Application - Computer Database </a>
        </div>
    </header>

    <section id="main">
        <div class="container">
            <div class="alert alert-danger">
                Error 404: Page not found. Too bad bitch!
                <br/>
                <!-- stacktrace -->
            </div>
        </div>
    </section>

<script src="<c:url value="js/jquery.min.js"/>"></script>
<script src="<c:url value="js/bootstrap.min.js"/>"></script>
<script src="<c:url value="js/dashboard.js"/>"></script>

</body>
</html>