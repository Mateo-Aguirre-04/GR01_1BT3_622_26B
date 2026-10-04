<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title>Poliperros | Ficha del perro</title>
    <style>
        * {
            box-sizing: border-box;
        }

        body {
            display: flex;
            min-height: 100vh;
            margin: 0;
            padding: 32px 20px;
            align-items: center;
            justify-content: center;
            background: #f3f4f6;
            color: #1a1a1a;
            font-family: Arial, Helvetica, sans-serif;
        }

        .app-shell {
            width: 100%;
            max-width: 800px;
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
            padding: 38px 36px 42px;
        }

        .heading {
            margin: 0 0 24px;
            color: #263c63;
            font-size: clamp(1.55rem, 4vw, 2rem);
            text-align: center;
        }

        .dog-icon {
            display: grid;
            width: 64px;
            height: 64px;
            margin: 0 auto 18px;
            place-items: center;
            border-radius: 20px;
            background: #f0f0ff;
            font-size: 2.1rem;
        }

        .error-message {
            max-width: 600px;
            margin: 0 auto 24px;
            padding: 14px 16px;
            border: 1px solid #fecaca;
            border-radius: 9px;
            background: #fef2f2;
            color: #991b1b;
            line-height: 1.5;
            text-align: center;
        }

        .details {
            max-width: 620px;
            margin: 0 auto 26px;
            padding: 0 22px;
            border: 1px solid #e3e6ef;
            border-radius: 14px;
            background: #fff;
            box-shadow: 0 4px 14px rgba(26, 26, 26, .06);
        }

        .detail-row {
            display: flex;
            justify-content: space-between;
            gap: 20px;
            padding: 15px 2px;
            border-bottom: 1px solid #e5e7eb;
        }

        .detail-row:last-child {
            border-bottom: 0;
        }

        dt {
            color: #596579;
            font-weight: 600;
        }

        dd {
            margin: 0;
            color: #1a1a1a;
            font-weight: 600;
            text-align: right;
            overflow-wrap: anywhere;
        }

        .actions {
            display: flex;
            flex-wrap: wrap;
            justify-content: center;
            gap: 12px;
        }

        .button,
        .button:visited {
            display: inline-flex;
            min-height: 46px;
            align-items: center;
            justify-content: center;
            padding: 11px 20px;
            border: 2px solid #41649a;
            border-radius: 9px;
            background: #41649a;
            color: #fff;
            font-weight: 700;
            text-align: center;
            text-decoration: none;
            transition: background-color .18s ease, border-color .18s ease, box-shadow .18s ease;
        }

        .button:hover {
            border-color: #304d79;
            background: #304d79;
        }

        .button:focus-visible {
            outline: 3px solid rgba(121, 121, 204, .35);
            outline-offset: 3px;
        }

        .button-secondary,
        .button-secondary:visited {
            background: #fff;
            color: #41649a;
        }

        .button-secondary:hover {
            background: #f0f0ff;
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
                padding: 30px 18px;
            }

            .details {
                padding: 0 16px;
            }

            .detail-row {
                gap: 12px;
            }

            .actions {
                flex-direction: column;
            }

            .button {
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

    <section class="content" aria-labelledby="detail-heading">
        <c:choose>
            <c:when test="${not empty perro}">
                <div class="dog-icon" aria-hidden="true">🐶</div>
                <h2 class="heading" id="detail-heading">Ficha del perro</h2>

                <dl class="details">
                    <div class="detail-row">
                        <dt>ID</dt>
                        <dd><c:out value="${perro.idPerro}"/></dd>
                    </div>
                    <div class="detail-row">
                        <dt>Nombre</dt>
                        <dd><c:out value="${perro.nombre}"/></dd>
                    </div>
                    <div class="detail-row">
                        <dt>Edad</dt>
                        <dd><c:out value="${perro.edad}"/> años</dd>
                    </div>
                    <div class="detail-row">
                        <dt>Características</dt>
                        <dd><c:out value="${perro.caracteristicas}"/></dd>
                    </div>
                    <div class="detail-row">
                        <dt>Estado de salud</dt>
                        <dd><c:out value="${perro.estadoSalud}"/></dd>
                    </div>
                    <div class="detail-row">
                        <dt>Espacio asignado</dt>
                        <dd><c:out value="${perro.espacioAsignado}"/></dd>
                    </div>
                    <div class="detail-row">
                        <dt>Disponible</dt>
                        <dd><c:choose><c:when test="${perro.disponible}">Sí</c:when><c:otherwise>No</c:otherwise></c:choose></dd>
                    </div>
                    <div class="detail-row">
                        <dt>Fecha de ingreso</dt>
                        <dd><c:out value="${perro.fechaIngreso}"/></dd>
                    </div>
                    <div class="detail-row">
                        <dt>Refugio</dt>
                        <dd><c:out value="${perro.refugio.nombre}"/></dd>
                    </div>
                </dl>
            </c:when>
            <c:otherwise>
                <h2 class="heading" id="detail-heading">No se pudo mostrar la ficha</h2>
                <p class="error-message" role="alert"><c:out value="${errorConsulta}"/></p>
            </c:otherwise>
        </c:choose>

        <nav class="actions" aria-label="Navegación de la ficha">
            <a class="button" href="${pageContext.request.contextPath}/consulta">← Volver al catálogo</a>
            <a class="button button-secondary" href="${pageContext.request.contextPath}/inicio.jsp">← Volver al menú principal</a>
        </nav>
    </section>
</main>
</body>
</html>
