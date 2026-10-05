<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title>Poliperros | Resultado de solicitud</title>
    <style>
        * { box-sizing: border-box; }
        body {
            display: flex;
            min-height: 100vh;
            margin: 0;
            padding: 24px;
            align-items: center;
            justify-content: center;
            background: #f3f4f6;
            color: #1a1a1a;
            font-family: Arial, Helvetica, sans-serif;
        }
        main {
            width: 100%;
            max-width: 680px;
            padding: 40px 28px;
            border: 1px solid #e5e7eb;
            border-radius: 18px;
            background: #fff;
            box-shadow: 0 12px 35px rgba(26, 26, 26, .09);
            text-align: center;
        }
        h1 { color: #263c63; }
        p { color: #596579; line-height: 1.6; }
        a {
            display: inline-flex;
            min-height: 46px;
            align-items: center;
            justify-content: center;
            margin-top: 14px;
            padding: 11px 18px;
            border-radius: 9px;
            background: #41649a;
            color: #fff;
            font-weight: 700;
            text-decoration: none;
        }
    </style>
</head>
<body>
<main>
    <h1>Resultado de la decisión</h1>
    <p role="status"><c:out value="${resultadoDecision}"/></p>
    <a href="${pageContext.request.contextPath}/solicitudes">Volver a solicitudes</a>
</main>
</body>
</html>
