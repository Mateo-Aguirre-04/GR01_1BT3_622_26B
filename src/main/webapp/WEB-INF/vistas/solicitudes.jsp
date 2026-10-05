<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title>Poliperros | Gestionar solicitudes</title>
    <style>
        * { box-sizing: border-box; }
        body {
            min-height: 100vh;
            margin: 0;
            padding: 32px 20px;
            background: #f3f4f6;
            color: #1a1a1a;
            font-family: Arial, Helvetica, sans-serif;
        }
        .app-shell {
            width: 100%;
            max-width: 1040px;
            margin: 0 auto;
            overflow: hidden;
            border: 1px solid #e5e7eb;
            border-radius: 18px;
            background: #fff;
            box-shadow: 0 12px 35px rgba(26, 26, 26, .09);
        }
        .brand-header {
            padding: 28px 24px;
            background: linear-gradient(120deg, #41649a, #7979cc);
            color: #fff;
            text-align: center;
        }
        .brand { margin: 0; letter-spacing: .08em; }
        .brand-subtitle { margin: 9px 0 0; color: rgba(255,255,255,.92); }
        .content { padding: 36px; }
        h2 { margin: 0 0 20px; color: #263c63; text-align: center; }
        .error {
            padding: 12px 14px;
            border: 1px solid #fecaca;
            border-radius: 9px;
            background: #fef2f2;
            color: #991b1b;
        }
        .empty {
            padding: 28px;
            border: 1px solid #e3e6ef;
            border-radius: 14px;
            color: #596579;
            text-align: center;
        }
        .request-list { display: grid; gap: 16px; }
        .request-card {
            display: flex;
            align-items: center;
            justify-content: space-between;
            gap: 18px;
            padding: 20px;
            border: 1px solid #e3e6ef;
            border-radius: 14px;
        }
        .request-card h3 { margin: 0 0 8px; color: #263c63; }
        .request-card p { margin: 4px 0; color: #596579; }
        .button {
            display: inline-flex;
            min-height: 44px;
            align-items: center;
            justify-content: center;
            padding: 10px 16px;
            border: 2px solid #41649a;
            border-radius: 9px;
            background: #41649a;
            color: #fff;
            font-weight: 700;
            text-decoration: none;
        }
        .button-secondary { background: #fff; color: #41649a; }
        .navigation { display: flex; justify-content: center; margin-top: 24px; }
        @media (max-width: 600px) {
            body { padding: 14px; }
            .content { padding: 26px 18px; }
            .request-card { align-items: stretch; flex-direction: column; }
            .button { width: 100%; }
        }
    </style>
</head>
<body>
<main class="app-shell">
    <header class="brand-header">
        <h1 class="brand">🐶 POLIPERROS</h1>
        <p class="brand-subtitle">Sistema de gestión de adopciones</p>
    </header>
    <section class="content" aria-labelledby="heading">
        <h2 id="heading">Solicitudes de adopción</h2>
        <c:if test="${not empty errorGestion}">
            <p class="error" role="alert"><c:out value="${errorGestion}"/></p>
        </c:if>
        <c:choose>
            <c:when test="${empty solicitudes}">
                <section class="empty">
                    <h3>No hay solicitudes registradas</h3>
                    <p>Las solicitudes enviadas por los adoptantes aparecerán aquí.</p>
                </section>
            </c:when>
            <c:otherwise>
                <section class="request-list" aria-label="Solicitudes registradas">
                    <c:forEach var="solicitud" items="${solicitudes}">
                        <article class="request-card">
                            <div>
                                <h3>Solicitud #<c:out value="${solicitud.idSolicitud}"/></h3>
                                <p>Perro ID: <c:out value="${solicitud.idPerro}"/></p>
                                <p>Estado: <c:out value="${solicitud.estado}"/></p>
                                <p>Fecha: <c:out value="${solicitud.fechaSolicitud}"/></p>
                            </div>
                            <a class="button" href="${pageContext.request.contextPath}/solicitudes?idSolicitud=${solicitud.idSolicitud}">Revisar solicitud</a>
                        </article>
                    </c:forEach>
                </section>
            </c:otherwise>
        </c:choose>
        <nav class="navigation" aria-label="Navegación">
            <a class="button button-secondary" href="${pageContext.request.contextPath}/inicio.jsp">Volver al menú principal</a>
        </nav>
    </section>
</main>
</body>
</html>
