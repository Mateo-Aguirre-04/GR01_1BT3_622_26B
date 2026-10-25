<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title>Poliperros | Gestionar donaciones</title>
    <style>
        * { box-sizing: border-box; }
        body { min-height: 100vh; margin: 0; padding: 32px 20px; background: #f3f4f6; color: #1a1a1a; font-family: Arial, Helvetica, sans-serif; }
        .app-shell { width: 100%; max-width: 900px; margin: 0 auto; overflow: hidden; border: 1px solid #e5e7eb; border-radius: 18px; background: #fff; box-shadow: 0 12px 35px rgba(26,26,26,.09); }
        .brand-header { padding: 28px 24px; background: linear-gradient(120deg,#41649a,#7979cc); color: #fff; text-align: center; }
        .brand { margin: 0; letter-spacing: .08em; }
        .brand-subtitle { margin: 9px 0 0; color: rgba(255,255,255,.92); }
        .content { padding: 34px 36px 40px; }
        h2 { margin: 0 0 22px; color: #263c63; text-align: center; }
        h3 { margin: 0 0 8px; color: #263c63; }
        form.card, .card { max-width: 720px; margin: 0 auto 18px; padding: 22px; border: 1px solid #e3e6ef; border-radius: 14px; }
        .fields { display: grid; grid-template-columns: 1fr 1fr; gap: 16px; }
        .form-group { display: flex; flex-direction: column; gap: 7px; }
        .wide { grid-column: 1 / -1; }
        label { color: #263c63; font-weight: 600; }
        input, select, textarea { width: 100%; min-height: 44px; padding: 10px 12px; border: 1px solid #cbd5e1; border-radius: 8px; color: #1a1a1a; font: inherit; }
        table { width: 100%; border-collapse: collapse; }
        th, td { padding: 10px; border-bottom: 1px solid #e3e6ef; text-align: left; }
        th { color: #263c63; }
        p { color: #596579; line-height: 1.5; }
        button, .link { display: inline-flex; min-height: 46px; align-items: center; justify-content: center; padding: 11px 18px; border: 2px solid #41649a; border-radius: 9px; background: #41649a; color: #fff; font: inherit; font-weight: 700; text-align: center; text-decoration: none; cursor: pointer; }
        .link { background: #fff; color: #41649a; }
        .actions, .navigation { display: flex; flex-wrap: wrap; justify-content: center; gap: 12px; margin-top: 20px; }
        .error, .success { max-width: 720px; margin: 0 auto 18px; padding: 13px 15px; border-radius: 9px; }
        .error { border: 1px solid #fecaca; background: #fef2f2; color: #991b1b; }
        .success { border: 1px solid #bbf7d0; background: #f0fdf4; color: #166534; }
        .empty { color: #596579; text-align: center; }
        @media (max-width: 600px) { body { padding: 14px; } .content { padding: 28px 16px; } .fields { grid-template-columns: 1fr; } .wide { grid-column: auto; } .actions, .navigation, .link, button { width: 100%; } }
    </style>
</head>
<body>
<jsp:include page="/WEB-INF/vistas/selectorVista.jsp"/>
<main class="app-shell">
    <header class="brand-header">
        <h1 class="brand">🐶 POLIPERROS</h1>
        <p class="brand-subtitle">Gestión de donaciones</p>
    </header>
    <section class="content">
        <h2>Donaciones pendientes de recepción</h2>
        <c:if test="${not empty errorDonacion}">
            <p class="error" role="alert"><c:out value="${errorDonacion}"/></p>
        </c:if>
        <c:if test="${not empty resultadoDonacion}">
            <p class="success" role="status"><c:out value="${resultadoDonacion}"/></p>
        </c:if>
        <c:choose>
            <c:when test="${empty donacionesPendientes}">
                <section class="card empty">
                    <h3>No hay donaciones pendientes</h3>
                    <p>Las nuevas donaciones aparecerán aquí.</p>
                </section>
            </c:when>
            <c:otherwise>
                <c:forEach var="donacion" items="${donacionesPendientes}">
                    <article class="card">
                        <h3>Donación #<c:out value="${donacion.idDonacion}"/></h3>
                        <p>Donante: <c:out value="${donacion.donante.nombres}"/> <c:out value="${donacion.donante.apellidos}"/>
                            · Registrada el <c:out value="${donacion.fechaRegistro}"/></p>
                        <p>Materiales:
                            <c:forEach var="item" items="${donacion.items}" varStatus="estado">
                                <c:out value="${item.material}"/> (<c:out value="${item.cantidad}"/>)<c:if test="${not estado.last}">, </c:if>
                            </c:forEach>
                        </p>
                        <a class="link" href="${pageContext.request.contextPath}/donaciones/gestionar?idDonacion=${donacion.idDonacion}">Ver detalle</a>
                    </article>
                </c:forEach>
            </c:otherwise>
        </c:choose>
        <nav class="navigation" aria-label="Navegación">
            <a class="link" href="${pageContext.request.contextPath}/inicio.jsp">Volver al menú principal</a>
        </nav>
    </section>
</main>
</body>
</html>
