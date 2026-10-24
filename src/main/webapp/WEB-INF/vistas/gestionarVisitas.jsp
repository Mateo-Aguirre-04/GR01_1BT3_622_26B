<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title>Poliperros | Gestionar visitas</title>
    <style>
        * { box-sizing: border-box; }
        body { min-height: 100vh; margin: 0; padding: 32px 20px; background: #f3f4f6; color: #1a1a1a; font-family: Arial, Helvetica, sans-serif; }
        .app-shell { width: 100%; max-width: 1040px; margin: 0 auto; overflow: hidden; border: 1px solid #e5e7eb; border-radius: 18px; background: #fff; box-shadow: 0 12px 35px rgba(26,26,26,.09); }
        .brand-header { padding: 28px 24px; background: linear-gradient(120deg,#41649a,#7979cc); color: #fff; text-align: center; }
        .brand { margin: 0; letter-spacing: .08em; }
        .brand-subtitle { margin: 9px 0 0; color: rgba(255,255,255,.92); }
        .content { padding: 36px; }
        h2 { margin: 0 0 20px; color: #263c63; text-align: center; }
        .message { padding: 12px 14px; border-radius: 9px; }
        .error { border: 1px solid #fecaca; background: #fef2f2; color: #991b1b; }
        .result { border: 1px solid #bbf7d0; background: #f0fdf4; color: #166534; }
        .empty { padding: 28px; border: 1px solid #e3e6ef; border-radius: 14px; color: #596579; text-align: center; }
        .request-list { display: grid; gap: 16px; }
        .request-card { display: flex; align-items: center; justify-content: space-between; gap: 18px; padding: 20px; border: 1px solid #e3e6ef; border-radius: 14px; }
        .request-card h3 { margin: 0 0 8px; color: #263c63; }
        .request-card p { margin: 4px 0; color: #596579; }
        .actions { display: flex; flex-wrap: wrap; gap: 10px; }
        button, .link { display: inline-flex; min-height: 44px; align-items: center; justify-content: center; padding: 10px 16px; border: 2px solid #41649a; border-radius: 9px; background: #41649a; color: #fff; font: inherit; font-weight: 700; text-decoration: none; cursor: pointer; }
        .reject, .link { background: #fff; color: #41649a; }
        .navigation { display: flex; justify-content: center; margin-top: 24px; }
        @media (max-width: 650px) { body { padding: 14px; } .content { padding: 26px 18px; } .request-card { align-items: stretch; flex-direction: column; } .actions, button, .link { width: 100%; } }
    </style>
</head>
<body>
<jsp:include page="/WEB-INF/vistas/selectorVista.jsp"/>
<main class="app-shell">
    <header class="brand-header">
        <h1 class="brand">🐶 POLIPERROS</h1>
        <p class="brand-subtitle">Gestión de solicitudes de visita</p>
    </header>
    <section class="content">
        <h2>Visitas pendientes</h2>
        <c:if test="${not empty errorGestionVisita}">
            <p class="message error" role="alert"><c:out value="${errorGestionVisita}"/></p>
        </c:if>
        <c:if test="${not empty resultadoVisita}">
            <p class="message result" role="status"><c:out value="${resultadoVisita}"/></p>
        </c:if>
        <c:choose>
            <c:when test="${empty visitasPendientes}">
                <section class="empty">
                    <h3>No hay visitas pendientes</h3>
                    <p>Las nuevas solicitudes de visita aparecerán aquí.</p>
                </section>
            </c:when>
            <c:otherwise>
                <section class="request-list" aria-label="Visitas pendientes">
                    <c:forEach var="visita" items="${visitasPendientes}">
                        <article class="request-card">
                            <div>
                                <h3>Visita #<c:out value="${visita.idVisita}"/></h3>
                                <p>Fecha: <c:out value="${visita.fechaVisita}"/> · Estado: Pendiente</p>
                                <p>Visitante: <c:out value="${visita.visitante.nombres}"/> <c:out value="${visita.visitante.apellidos}"/></p>
                                <p>Teléfono: <c:out value="${visita.visitante.telefono}"/> · Correo: <c:out value="${visita.visitante.correo}"/></p>
                                <p>Perro: <c:out value="${visita.perro.nombre}"/> (ID <c:out value="${visita.perro.idPerro}"/>)</p>
                            </div>
                            <div class="actions">
                                <form action="${pageContext.request.contextPath}/visitas/gestionar" method="post">
                                    <input type="hidden" name="idVisita" value="<c:out value='${visita.idVisita}'/>">
                                    <button name="accion" value="aprobar" type="submit">Aprobar</button>
                                </form>
                                <form action="${pageContext.request.contextPath}/visitas/gestionar" method="post">
                                    <input type="hidden" name="idVisita" value="<c:out value='${visita.idVisita}'/>">
                                    <button class="reject" name="accion" value="rechazar" type="submit">Rechazar</button>
                                </form>
                            </div>
                        </article>
                    </c:forEach>
                </section>
            </c:otherwise>
        </c:choose>
        <nav class="navigation" aria-label="Navegación">
            <a class="link" href="${pageContext.request.contextPath}/inicio.jsp">Volver al menú principal</a>
        </nav>
    </section>
</main>
</body>
</html>
