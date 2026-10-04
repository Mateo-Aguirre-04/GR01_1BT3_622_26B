<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title>Poliperros | Solicitar adopción</title>
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
            max-width: 820px;
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
        .brand-subtitle { margin: 9px 0 0; color: rgba(255, 255, 255, .92); }
        .content { padding: 34px 36px 40px; }
        h2 { margin: 0 0 22px; color: #263c63; text-align: center; }
        .dog-summary, form {
            max-width: 680px;
            margin: 0 auto 20px;
            padding: 22px;
            border: 1px solid #e3e6ef;
            border-radius: 14px;
            background: #fff;
        }
        .dog-summary { color: #596579; }
        form { display: flex; flex-direction: column; gap: 16px; }
        .form-group { display: flex; flex-direction: column; gap: 7px; }
        label { color: #263c63; font-weight: 600; }
        input, textarea {
            width: 100%;
            min-height: 44px;
            padding: 10px 12px;
            border: 1px solid #cbd5e1;
            border-radius: 8px;
            color: #1a1a1a;
            font: inherit;
        }
        textarea { min-height: 88px; resize: vertical; }
        .terms { display: flex; align-items: flex-start; gap: 10px; color: #263c63; line-height: 1.5; }
        .terms input { width: 18px; min-height: 18px; margin-top: 2px; }
        button, .link {
            display: inline-flex;
            min-height: 46px;
            align-items: center;
            justify-content: center;
            padding: 11px 18px;
            border: 2px solid #41649a;
            border-radius: 9px;
            background: #41649a;
            color: #fff;
            font: inherit;
            font-weight: 700;
            text-align: center;
            text-decoration: none;
        }
        button { cursor: pointer; }
        .link { background: #fff; color: #41649a; }
        .error {
            max-width: 680px;
            margin: 0 auto 18px;
            padding: 12px 14px;
            border: 1px solid #fecaca;
            border-radius: 9px;
            background: #fef2f2;
            color: #991b1b;
        }
        .navigation { display: flex; justify-content: center; margin-top: 20px; }
        @media (max-width: 600px) {
            body { padding: 14px; }
            .content { padding: 28px 16px; }
            .navigation, .link { width: 100%; }
        }
    </style>
</head>
<body>
<main class="app-shell">
    <header class="brand-header">
        <h1 class="brand">🐶 POLIPERROS</h1>
        <p class="brand-subtitle">Sistema de adopción</p>
    </header>
    <section class="content" aria-labelledby="form-heading">
        <h2 id="form-heading">Formulario de adopción</h2>
        <c:if test="${not empty errorAdopcion}">
            <p class="error" role="alert"><c:out value="${errorAdopcion}"/></p>
        </c:if>
        <c:if test="${not empty perro}">
            <section class="dog-summary" aria-label="Perro de interés">
                Solicitud para <strong><c:out value="${perro.nombre}"/></strong>
                (ID <c:out value="${perro.idPerro}"/>).
            </section>
        </c:if>
        <form action="${pageContext.request.contextPath}/adopcion" method="post">
            <input type="hidden" name="idPerro" value="<c:out value='${not empty perro ? perro.idPerro : param.idPerro}'/>">
            <div class="form-group">
                <label for="nombres">Nombres</label>
                <input id="nombres" name="nombres" required value="<c:out value='${param.nombres}'/>">
            </div>
            <div class="form-group">
                <label for="apellidos">Apellidos</label>
                <input id="apellidos" name="apellidos" required value="<c:out value='${param.apellidos}'/>">
            </div>
            <div class="form-group">
                <label for="telefono">Teléfono</label>
                <input id="telefono" name="telefono" type="tel" required value="<c:out value='${param.telefono}'/>">
            </div>
            <div class="form-group">
                <label for="correo">Correo electrónico</label>
                <input id="correo" name="correo" type="email" required value="<c:out value='${param.correo}'/>">
            </div>
            <div class="form-group">
                <label for="infoDomicilio">Información del domicilio</label>
                <textarea id="infoDomicilio" name="infoDomicilio" required><c:out value="${param.infoDomicilio}"/></textarea>
            </div>
            <div class="form-group">
                <label for="experienciaMascotas">Experiencia con mascotas</label>
                <textarea id="experienciaMascotas" name="experienciaMascotas" required><c:out value="${param.experienciaMascotas}"/></textarea>
            </div>
            <div class="form-group">
                <label for="condicionesHogar">Condiciones del hogar</label>
                <textarea id="condicionesHogar" name="condicionesHogar" required><c:out value="${param.condicionesHogar}"/></textarea>
            </div>
            <div class="form-group">
                <label for="disponibilidadTiempo">Disponibilidad de tiempo</label>
                <textarea id="disponibilidadTiempo" name="disponibilidadTiempo" required><c:out value="${param.disponibilidadTiempo}"/></textarea>
            </div>
            <label class="terms" for="aceptacionTerminos">
                <input id="aceptacionTerminos" name="aceptacionTerminos" type="checkbox" value="true"
                       ${param.aceptacionTerminos == 'true' ? 'checked' : ''} required>
                Acepto los términos de adopción.
            </label>
            <button type="submit">Enviar solicitud</button>
        </form>
        <nav class="navigation" aria-label="Navegación">
            <a class="link" href="${pageContext.request.contextPath}/consulta">Volver al catálogo</a>
        </nav>
    </section>
</main>
</body>
</html>
