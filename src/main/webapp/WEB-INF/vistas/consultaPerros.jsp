<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title>Poliperros | Consultar perros</title>
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
            margin: 0 0 8px;
            color: #263c63;
            font-size: clamp(1.55rem, 4vw, 2rem);
            text-align: center;
        }

        .intro {
            margin: 0 0 30px;
            color: #596579;
            line-height: 1.6;
            text-align: center;
        }

        .dog-grid {
            display: grid;
            grid-template-columns: repeat(auto-fit, minmax(min(100%, 245px), 1fr));
            gap: 22px;
        }

        .dog-card {
            display: flex;
            min-width: 0;
            flex-direction: column;
            padding: 24px;
            border: 1px solid #e3e6ef;
            border-radius: 14px;
            background: #fff;
            box-shadow: 0 4px 14px rgba(26, 26, 26, .06);
            transition: box-shadow .18s ease, transform .18s ease, border-color .18s ease;
        }

        .dog-card:hover {
            transform: translateY(-3px);
            border-color: #b8b8e8;
            box-shadow: 0 9px 22px rgba(65, 100, 154, .13);
        }

        .dog-icon {
            display: grid;
            width: 58px;
            height: 58px;
            margin-bottom: 16px;
            place-items: center;
            border-radius: 18px;
            background: #f0f0ff;
            font-size: 1.9rem;
        }

        .dog-card h3 {
            margin: 0 0 15px;
            color: #263c63;
            font-size: 1.25rem;
            overflow-wrap: anywhere;
        }

        .dog-info {
            display: grid;
            gap: 10px;
            margin: 0 0 20px;
        }

        .dog-info div {
            display: flex;
            justify-content: space-between;
            gap: 12px;
        }

        dt {
            color: #596579;
            font-weight: 600;
        }

        dd {
            margin: 0;
            color: #1a1a1a;
            text-align: right;
            overflow-wrap: anywhere;
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

        .dog-card .button {
            width: 100%;
            margin-top: auto;
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

        .empty-state {
            max-width: 620px;
            margin: 0 auto;
            padding: 36px 24px;
            border: 1px solid #e3e6ef;
            border-radius: 14px;
            background: #fff;
            box-shadow: 0 4px 14px rgba(26, 26, 26, .06);
            text-align: center;
        }

        .empty-icon {
            margin-bottom: 14px;
            font-size: 2.8rem;
        }

        .empty-state h3 {
            margin: 0 0 10px;
            color: #263c63;
        }

        .empty-state p {
            margin: 0;
            color: #596579;
            line-height: 1.6;
        }

        .navigation {
            display: flex;
            justify-content: center;
            margin-top: 28px;
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

            .dog-grid {
                grid-template-columns: minmax(0, 1fr);
            }

            .navigation,
            .navigation .button {
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

    <section class="content" aria-labelledby="catalog-heading">
        <h2 class="heading" id="catalog-heading">Consultar perros</h2>
        <p class="intro">Perros disponibles actualmente en el refugio.</p>

        <c:choose>
            <c:when test="${empty perrosDisponibles}">
                <section class="empty-state" aria-live="polite">
                    <div class="empty-icon" aria-hidden="true">🐶</div>
                    <h3>No hay perros disponibles actualmente.</h3>
                    <p>Los perros registrados aparecerán aquí cuando estén disponibles.</p>
                </section>
            </c:when>
            <c:otherwise>
                <section class="dog-grid" aria-label="Perros disponibles">
                    <c:forEach var="perro" items="${perrosDisponibles}">
                        <article class="dog-card">
                            <div class="dog-icon" aria-hidden="true">🐶</div>
                            <h3><c:out value="${perro.nombre}"/></h3>
                            <dl class="dog-info">
                                <div>
                                    <dt>Edad</dt>
                                    <dd><c:out value="${perro.edad}"/> años</dd>
                                </div>
                                <div>
                                    <dt>Estado de salud</dt>
                                    <dd><c:out value="${perro.estadoSalud}"/></dd>
                                </div>
                                <div>
                                    <dt>Disponible</dt>
                                    <dd>Sí</dd>
                                </div>
                            </dl>
                            <a class="button" href="${pageContext.request.contextPath}/consulta?idPerro=${perro.idPerro}">Ver ficha</a>
                        </article>
                    </c:forEach>
                </section>
            </c:otherwise>
        </c:choose>

        <nav class="navigation" aria-label="Navegación principal">
            <a class="button button-secondary" href="${pageContext.request.contextPath}/inicio.jsp">← Volver al menú principal</a>
        </nav>
    </section>
</main>
</body>
</html>
