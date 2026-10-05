<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title>Poliperros | Registro confirmado</title>
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
            max-width: 760px;
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
            text-align: center;
        }

        .success-mark {
            display: grid;
            width: 64px;
            height: 64px;
            margin: 0 auto 18px;
            place-items: center;
            border-radius: 20px;
            background: #f0f0ff;
            color: #41649a;
            font-size: 2.1rem;
            font-weight: 700;
        }

        h2 {
            margin: 0 0 10px;
            color: #263c63;
            font-size: clamp(1.55rem, 4vw, 2rem);
        }

        .intro {
            margin: 0 0 26px;
            color: #596579;
            line-height: 1.6;
        }

        .details {
            max-width: 560px;
            margin: 0 auto 26px;
            padding: 0 22px;
            border: 1px solid #e3e6ef;
            border-radius: 14px;
            background: #fff;
            box-shadow: 0 4px 14px rgba(26, 26, 26, .06);
            text-align: left;
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

        .action-link {
            display: inline-flex;
            min-height: 46px;
            align-items: center;
            justify-content: center;
            padding: 11px 20px;
            border: 2px solid #41649a;
            border-radius: 9px;
            font-weight: 700;
            text-align: center;
            text-decoration: none;
            transition: background-color .18s ease, border-color .18s ease, box-shadow .18s ease;
        }

        .action-link:focus-visible {
            outline: 3px solid rgba(121, 121, 204, .35);
            outline-offset: 3px;
        }

        .action-primary {
            background: #41649a;
            color: #fff;
        }

        .action-primary:hover {
            border-color: #304d79;
            background: #304d79;
        }

        .action-secondary {
            background: #fff;
            color: #41649a;
        }

        .action-secondary:hover {
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

            .action-link {
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

    <section class="content" aria-labelledby="confirmation-heading">
        <div class="success-mark" aria-hidden="true">✓</div>
        <h2 id="confirmation-heading">Perro registrado correctamente</h2>
        <p class="intro">El registro de ingreso se completó exitosamente.</p>

        <dl class="details">
            <div class="detail-row">
                <dt>ID</dt>
                <dd><c:out value="${perroRegistrado.idPerro}"/></dd>
            </div>
            <div class="detail-row">
                <dt>Nombre</dt>
                <dd><c:out value="${perroRegistrado.nombre}"/></dd>
            </div>
            <div class="detail-row">
                <dt>Refugio</dt>
                <dd><c:out value="${perroRegistrado.refugio.nombre}"/></dd>
            </div>
            <div class="detail-row">
                <dt>Encargado</dt>
                <dd><c:out value="${encargadoActual.nombre}"/></dd>
            </div>
        </dl>

        <nav class="actions" aria-label="Acciones del registro">
            <a class="action-link action-primary" href="${pageContext.request.contextPath}/registro">Registrar otro perro</a>
            <a class="action-link action-secondary" href="${pageContext.request.contextPath}/inicio.jsp">← Volver al menú principal</a>
        </nav>
    </section>
</main>
</body>
</html>
