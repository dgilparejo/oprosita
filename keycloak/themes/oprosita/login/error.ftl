<html>
<head>
    <title>Error</title>
</head>
<body>
<h1>Se ha producido un error</h1>
<p>${message?if_exists?html}</p>
<a href="${url.loginUrl}">Volver al login</a>
</body>
</html>
