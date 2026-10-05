<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title>Poliperros | Detalle de solicitud</title>
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
            max-width: 900px;
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
        .content { padding: 34px 36px 40px; }
        h2, h3 { color: #263c63; }
        h2 { text-align: center; margin: 0 0 22px; }
        h3 { margin: 0 0 12px; }
        .section {
            margin-bottom: 20px;
            padding: 22px;
            border: 1px solid #e3e6ef;
            border-radius: 14px;
            background: #fff;
        }
        dl { display: grid; gap: 10px; margin: 0; }
        .row { display: grid; grid-template-columns: minmax(130px, 1fr) 2fr; gap: 16px; }
        dt { color: #596579; font-weight: 700; }
        dd { margin: 0; overflow-wrap: anywhere; }
        .dog-card { padding: 14px; border-radius: 8px; background: #f3f4f6; overflow-wrap: anywhere; }
        .actions { display: flex; flex-wrap: wrap; justify-content: center; gap: 12px; }
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
            font: inherit;
            font-weight: 700;
            text-decoration: none;
            cursor: pointer;
        }
        .button-secondary { background: #fff; color: #41649a; }
        @media (max-width: 600px) {
            body { padding: 14px; }
            .content { padding: 26px 18px; }
            .row { grid-template-columns: 1fr; gap: 4px; }
            .actions, .button { width: 100%; }
        }
    </style>
</head>
<body>
<main class="app-shell">
    <header class="brand-header">
        <h1 class="brand">🐶 POLIPERROS</h1>
        <p class="brand-subtitle">Revisión de solicitud</p>
    </header>
    <section class="content" aria-labelledby="heading">
        <h2 id="heading">Solicitud #<c:out value="${solicitud.idSolicitud}"/></h2>
        <section class="section" aria-labelledby="request-heading">
            <h3 id="request-heading">Estado de la solicitud</h3>
            <dl>
                <div class="row"><dt>Estado</dt><dd><c:out value="${solicitud.estado}"/></dd></div>
                <div class="row"><dt>Fecha de solicitud</dt><dd><c:out value="${solicitud.fechaSolicitud}"/></dd></div>
            </dl>
        </section>
        <section class="section" aria-labelledby="adopter-heading">
            <h3 id="adopter-heading">Datos del adoptante</h3>
            <dl>
                <div class="row"><dt>Nombres</dt><dd><c:out value="${datosAdoptante.nombres}"/> <c:out value="${datosAdoptante.apellidos}"/></dd></div>
                <div class="row"><dt>Teléfono</dt><dd><c:out value="${datosAdoptante.telefono}"/></dd></div>
                <div class="row"><dt>Correo</dt><dd><c:out value="${datosAdoptante.correo}"/></dd></div>
                <div class="row"><dt>Domicilio</dt><dd><c:out value="${datosAdoptante.infoDomicilio}"/></dd></div>
                <div class="row"><dt>Experiencia</dt><dd><c:out value="${datosAdoptante.experienciaMascotas}"/></dd></div>
                <div class="row"><dt>Condiciones del hogar</dt><dd><c:out value="${datosAdoptante.condicionesHogar}"/></dd></div>
                <div class="row"><dt>Disponibilidad</dt><dd><c:out value="${datosAdoptante.disponibilidadTiempo}"/></dd></div>
            </dl>
        </section>
        <section class="section" aria-labelledby="dog-heading">
            <h3 id="dog-heading">Ficha del perro</h3>
            <p class="dog-card"><c:out value="${fichaPerro}"/></p>
        </section>
        <div class="actions">
            <c:if test="${solicitud.estado == 'Pendiente'}">
                <form action="${pageContext.request.contextPath}/solicitudes" method="post">
                    <input type="hidden" name="idSolicitud" value="<c:out value='${solicitud.idSolicitud}'/>">
                    <input type="hidden" name="aprobada" value="true">
                    <button class="button" type="submit">Aprobar si el perro sigue disponible</button>
                </form>
            </c:if>
            <a class="button button-secondary" href="${pageContext.request.contextPath}/solicitudes">Volver a solicitudes</a>
        </div>
    </section>
</main>
</body>
</html>
