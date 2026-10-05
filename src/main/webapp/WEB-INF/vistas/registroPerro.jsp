<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title>Poliperros | Registrar perro</title>
    <style>
        * {
            box-sizing: border-box;
        }

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
            max-width: 860px;
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

        .brand {
            display: flex;
            align-items: center;
            justify-content: center;
            gap: 13px;
            margin: 0;
            font-size: clamp(1.7rem, 5vw, 2.3rem);
            letter-spacing: .08em;
        }

        .brand-icon {
            font-size: .9em;
            letter-spacing: normal;
        }

        .brand-subtitle {
            margin: 9px 0 0;
            color: rgba(255, 255, 255, .92);
            font-size: 1rem;
        }

        .content {
            padding: 34px 36px 40px;
        }

        .page-heading {
            margin: 0 0 24px;
            color: #263c63;
            font-size: clamp(1.55rem, 4vw, 2rem);
            text-align: center;
        }

        .error-message {
            max-width: 700px;
            margin: 0 auto 22px;
            padding: 12px 14px;
            border: 1px solid #fecaca;
            border-radius: 9px;
            background: #fef2f2;
            color: #991b1b;
            line-height: 1.45;
        }

        form {
            display: flex;
            max-width: 700px;
            flex-direction: column;
            gap: 18px;
            margin: 0 auto;
            padding: 28px;
            border: 1px solid #e3e6ef;
            border-radius: 14px;
            background: #fff;
            box-shadow: 0 4px 14px rgba(26, 26, 26, .06);
        }

        .form-group {
            display: flex;
            flex-direction: column;
            gap: 7px;
            min-width: 0;
        }

        label {
            color: #263c63;
            font-size: .95rem;
            font-weight: 600;
        }

        input,
        textarea,
        select {
            width: 100%;
            min-height: 46px;
            padding: 10px 12px;
            border: 1px solid #cbd5e1;
            border-radius: 9px;
            background: #fff;
            color: #1a1a1a;
            font: inherit;
        }

        textarea {
            min-height: 100px;
            resize: vertical;
        }

        input:focus,
        textarea:focus,
        select:focus,
        button:focus-visible,
        .menu-link:focus-visible {
            outline: 3px solid rgba(121, 121, 204, .35);
            outline-offset: 2px;
            border-color: #7979cc;
        }

        button,
        .menu-link {
            display: inline-flex;
            min-height: 46px;
            align-items: center;
            justify-content: center;
            padding: 11px 20px;
            border: 2px solid #41649a;
            border-radius: 9px;
            font: inherit;
            font-weight: 700;
            text-align: center;
            text-decoration: none;
            transition: background-color .18s ease, border-color .18s ease, box-shadow .18s ease;
        }

        button {
            width: 100%;
            margin-top: 4px;
            background: #41649a;
            color: #fff;
            cursor: pointer;
        }

        button:hover,
        .menu-link:hover {
            border-color: #304d79;
            background: #304d79;
        }

        .menu-link {
            background: #fff;
            color: #41649a;
        }

        .menu-link:hover {
            color: #fff;
        }

        .navigation {
            display: flex;
            justify-content: center;
            margin-top: 22px;
        }

        @media (max-width: 600px) {
            body {
                padding: 14px;
            }

            .app-shell {
                border-radius: 14px;
            }

            .brand-header {
                padding: 25px 18px;
            }

            .content {
                padding: 28px 16px;
            }

            form {
                padding: 22px 18px;
            }

            .navigation,
            .menu-link {
                width: 100%;
            }
        }
    </style>
</head>
<body>
<main class="app-shell">
    <header class="brand-header">
        <h1 class="brand"><span class="brand-icon" aria-hidden="true">🐶</span> POLIPERROS</h1>
        <p class="brand-subtitle">Sistema de Gestión de Refugios</p>
    </header>

    <section class="content" aria-labelledby="page-heading">
        <h2 class="page-heading" id="page-heading">Registrar perro</h2>

        <c:if test="${not empty errorRegistro}">
            <p class="error-message" role="alert"><c:out value="${errorRegistro}"/></p>
        </c:if>

        <form action="${pageContext.request.contextPath}/registro" method="post" onsubmit="return validarRegistro(event)">
            <div class="form-group">
                <label for="idPerro">ID del perro</label>
                <input id="idPerro" name="idPerro" type="number" min="1" step="1" required value="<c:out value='${param.idPerro}'/>">
            </div>

            <div class="form-group">
                <label for="nombre">Nombre</label>
                <input id="nombre" name="nombre" type="text" required data-trim-required value="<c:out value='${param.nombre}'/>">
            </div>

            <div class="form-group">
                <label for="edad">Edad</label>
                <input id="edad" name="edad" type="number" min="0" step="1" required value="<c:out value='${param.edad}'/>">
            </div>

            <div class="form-group">
                <label for="caracteristicas">Características</label>
                <textarea id="caracteristicas" name="caracteristicas" required data-trim-required><c:out value="${param.caracteristicas}"/></textarea>
            </div>

            <div class="form-group">
                <label for="estadoSalud">Estado de salud</label>
                <input id="estadoSalud" name="estadoSalud" type="text" required data-trim-required value="<c:out value='${param.estadoSalud}'/>">
            </div>

            <div class="form-group">
                <label for="espacioAsignado">Espacio asignado</label>
                <input id="espacioAsignado" name="espacioAsignado" type="text" required data-trim-required value="<c:out value='${param.espacioAsignado}'/>">
            </div>

            <div class="form-group">
                <label for="disponible">Disponibilidad</label>
                <select id="disponible" name="disponible" required>
                    <option value="true" ${param.disponible == 'true' ? 'selected' : ''}>Disponible</option>
                    <option value="false" ${param.disponible == 'false' ? 'selected' : ''}>No disponible</option>
                </select>
            </div>

            <div class="form-group">
                <label for="fechaIngreso">Fecha de ingreso</label>
                <input id="fechaIngreso" name="fechaIngreso" type="date" required value="<c:out value='${param.fechaIngreso}'/>">
            </div>

            <div class="form-group">
                <label for="idRefugio">Refugio</label>
                <select id="idRefugio" name="idRefugio" required>
                    <option value="${refugioActual.idRefugio}"><c:out value="${refugioActual.nombre}"/> (ID <c:out value="${refugioActual.idRefugio}"/>)</option>
                </select>
            </div>

            <button type="submit">Registrar perro</button>
        </form>

        <nav class="navigation" aria-label="Navegación principal">
            <a class="menu-link" href="${pageContext.request.contextPath}/inicio.jsp">← Volver al menú principal</a>
        </nav>
    </section>
</main>
<script>
    function validarRegistro(event) {
        const formulario = event.currentTarget;
        const idPerro = formulario.elements.idPerro;
        const edad = formulario.elements.edad;
        const idRefugio = formulario.elements.idRefugio;

        if (idPerro.value.trim() === "" || !Number.isInteger(Number(idPerro.value)) || Number(idPerro.value) <= 0) {
            alert("El ID del perro debe ser un número entero mayor que cero.");
            idPerro.focus();
            return false;
        }

        if (edad.value.trim() === "" || !Number.isInteger(Number(edad.value)) || Number(edad.value) < 0) {
            alert("La edad debe ser un número entero igual o mayor que cero.");
            edad.focus();
            return false;
        }

        if (idRefugio.value.trim() === "" || !Number.isInteger(Number(idRefugio.value)) || Number(idRefugio.value) <= 0) {
            alert("Selecciona un refugio con un ID entero mayor que cero.");
            idRefugio.focus();
            return false;
        }

        const camposTexto = formulario.querySelectorAll("[data-trim-required]");
        for (const campo of camposTexto) {
            if (campo.value.trim() === "") {
                const etiqueta = formulario.querySelector('label[for="' + campo.id + '"]');
                alert("El campo " + (etiqueta ? etiqueta.textContent : "de texto") + " no puede contener únicamente espacios.");
                campo.focus();
                return false;
            }
        }

        return true;
    }
</script>
</body>
</html>
