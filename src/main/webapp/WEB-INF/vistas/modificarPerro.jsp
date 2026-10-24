<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title>Poliperros | Modificar datos del perro</title>
    <style>
        * { box-sizing: border-box; }
        body { min-height: 100vh; margin: 0; padding: 32px 20px; background: #f3f4f6; color: #1a1a1a; font-family: Arial, Helvetica, sans-serif; }
        .app-shell { width: 100%; max-width: 900px; margin: 0 auto; overflow: hidden; border: 1px solid #e5e7eb; border-radius: 18px; background: #fff; box-shadow: 0 12px 35px rgba(26,26,26,.09); }
        .brand-header { padding: 28px 24px; background: linear-gradient(120deg,#41649a,#7979cc); color: #fff; text-align: center; }
        .brand-header h1 { margin: 0; letter-spacing: .08em; }
        .content { padding: 32px; }
        h2 { margin-top: 0; color: #263c63; text-align: center; }
        .summary { color: #596579; line-height: 1.6; }
        .message { padding: 14px 16px; border-radius: 9px; line-height: 1.5; }
        .error { border: 1px solid #fecaca; background: #fef2f2; color: #991b1b; }
        .success { border: 1px solid #bbf7d0; background: #f0fdf4; color: #166534; }
        .fields { display: grid; grid-template-columns: repeat(2, minmax(0,1fr)); gap: 20px; }
        .wide { grid-column: 1 / -1; }
        label { display: block; margin-bottom: 8px; color: #263c63; font-weight: 600; }
        input, textarea { width: 100%; min-height: 44px; padding: 10px 12px; border: 1px solid #cbd5e1; border-radius: 8px; color: #1a1a1a; font: inherit; }
        textarea { min-height: 100px; resize: vertical; }
        .actions { display: flex; flex-wrap: wrap; justify-content: center; gap: 12px; margin-top: 24px; }
        button, .button { display: inline-flex; min-height: 46px; align-items: center; justify-content: center; padding: 11px 18px; border: 2px solid #41649a; border-radius: 9px; background: #41649a; color: #fff; font: inherit; font-weight: 700; text-decoration: none; cursor: pointer; }
        .secondary { background: #fff; color: #41649a; }
        button:hover, .button:hover { box-shadow: 0 3px 10px rgba(65,100,154,.2); }
        input:focus-visible, textarea:focus-visible, button:focus-visible, a:focus-visible { outline: 3px solid #7979cc; outline-offset: 2px; }
        @media (max-width: 600px) { body { padding: 14px; } .content { padding: 24px 18px; } .fields { grid-template-columns: 1fr; } .actions { flex-direction: column; } }
    </style>
</head>
<body>
<jsp:include page="/WEB-INF/vistas/selectorVista.jsp"/>
<main class="app-shell">
    <header class="brand-header">
        <h1>🐶 POLIPERROS</h1>
        <p>Sistema de Gestión de Refugios</p>
    </header>
    <section class="content" aria-labelledby="edit-heading">
        <h2 id="edit-heading">Modificar datos del perro</h2>
        <c:if test="${not empty errorModificacion}">
            <p class="message error" role="alert"><c:out value="${errorModificacion}"/></p>
        </c:if>
        <c:if test="${not empty resultadoModificacion}">
            <p class="message success" role="status"><c:out value="${resultadoModificacion}"/></p>
        </c:if>
        <c:if test="${not empty perro}">
            <p class="summary">Perro #<c:out value="${perro.idPerro}"/> · Refugio: <c:out value="${perro.refugio.nombre}"/> · Disponible:
                <c:choose><c:when test="${perro.disponible}">Sí</c:when><c:otherwise>No</c:otherwise></c:choose>.
                El ID, el refugio y la disponibilidad se conservan al editar.</p>
            <p class="summary">Todos los campos del formulario son obligatorios.</p>
            <form action="${pageContext.request.contextPath}/perros/modificar" method="post">
                <input type="hidden" name="idPerro" value="<c:out value='${perro.idPerro}'/>">
                <div class="fields">
                    <div>
                        <label for="nombre">Nombre</label>
                        <input id="nombre" name="nombre" required value="<c:out value='${datosEdicion.nombre}'/>">
                    </div>
                    <div>
                        <label for="edad">Edad (años)</label>
                        <input id="edad" name="edad" type="number" min="0" step="1" required value="<c:out value='${datosEdicion.edad}'/>">
                    </div>
                    <div class="wide">
                        <label for="caracteristicas">Características</label>
                        <textarea id="caracteristicas" name="caracteristicas" required><c:out value="${datosEdicion.caracteristicas}"/></textarea>
                    </div>
                    <div>
                        <label for="estadoSalud">Estado de salud</label>
                        <input id="estadoSalud" name="estadoSalud" required value="<c:out value='${datosEdicion.estadoSalud}'/>">
                    </div>
                    <div>
                        <label for="espacioAsignado">Espacio asignado</label>
                        <input id="espacioAsignado" name="espacioAsignado" required value="<c:out value='${datosEdicion.espacioAsignado}'/>">
                    </div>
                    <div class="wide">
                        <label for="fechaIngreso">Fecha de ingreso</label>
                        <input id="fechaIngreso" name="fechaIngreso" type="date" required value="<c:out value='${datosEdicion.fechaIngreso}'/>">
                    </div>
                </div>
                <div class="actions">
                    <button type="submit">Guardar cambios</button>
                    <a class="button secondary" href="${pageContext.request.contextPath}/consulta?idPerro=${perro.idPerro}">Volver a la ficha</a>
                </div>
            </form>
        </c:if>
        <nav class="actions" aria-label="Navegación">
            <a class="button secondary" href="${pageContext.request.contextPath}/consulta">Volver al catálogo</a>
        </nav>
    </section>
</main>
</body>
</html>
