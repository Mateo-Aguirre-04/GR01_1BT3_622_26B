<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <meta name="description" content="Poliperros, sistema de gestión de refugios de perros.">
    <title>Poliperros | Inicio</title>
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
            padding: 34px 24px;
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
            font-size: clamp(1.8rem, 5vw, 2.5rem);
            letter-spacing: .08em;
        }

        .brand-icon {
            font-size: .9em;
            letter-spacing: normal;
        }

        .brand-subtitle {
            margin: 10px 0 0;
            color: rgba(255, 255, 255, .92);
            font-size: 1rem;
        }

        .content {
            padding: 42px 36px 48px;
        }

        .welcome {
            max-width: 650px;
            margin: 0 auto 34px;
            text-align: center;
        }

        .welcome h2 {
            margin: 0 0 12px;
            color: #263c63;
            font-size: clamp(1.55rem, 4vw, 2rem);
        }

        .welcome p {
            margin: 0;
            color: #596579;
            font-size: 1.05rem;
            line-height: 1.65;
        }

        .modules {
            display: grid;
            grid-template-columns: repeat(auto-fit, minmax(min(100%, 260px), 320px));
            justify-content: center;
            gap: 22px;
        }

        .module-card {
            display: flex;
            flex-direction: column;
            align-items: center;
            padding: 28px 24px;
            border: 1px solid #e3e6ef;
            border-radius: 14px;
            background: #fff;
            box-shadow: 0 4px 14px rgba(26, 26, 26, .06);
            text-align: center;
            transition: box-shadow .18s ease, transform .18s ease, border-color .18s ease;
        }

        .module-card:hover {
            transform: translateY(-3px);
            border-color: #b8b8e8;
            box-shadow: 0 9px 22px rgba(65, 100, 154, .13);
        }

        .module-icon {
            display: grid;
            width: 68px;
            height: 68px;
            margin-bottom: 18px;
            place-items: center;
            border-radius: 20px;
            background: #f0f0ff;
            font-size: 2.2rem;
        }

        .module-card h3 {
            margin: 0 0 10px;
            color: #263c63;
            font-size: 1.25rem;
        }

        .module-card p {
            flex: 1;
            margin: 0 0 22px;
            color: #596579;
            line-height: 1.55;
        }

        .module-link {
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
            text-decoration: none;
            transition: background-color .18s ease, border-color .18s ease, box-shadow .18s ease;
        }

        .module-link:hover {
            border-color: #304d79;
            background: #304d79;
        }

        .module-link:focus-visible {
            outline: 3px solid #7979cc;
            outline-offset: 3px;
        }

        @media (max-width: 600px) {
            body {
                padding: 14px;
            }

            .app-shell {
                border-radius: 14px;
            }

            .brand-header {
                padding: 27px 18px;
            }

            .content {
                padding: 32px 18px;
            }

            .welcome {
                margin-bottom: 26px;
            }

            .modules {
                grid-template-columns: minmax(0, 1fr);
            }

            .module-card {
                width: 100%;
                padding: 25px 20px;
            }

            .module-link {
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

    <section class="content" aria-labelledby="welcome-heading">
        <div class="welcome">
            <h2 id="welcome-heading">Bienvenido a Poliperros</h2>
            <p>Gestiona de manera sencilla la información de los perros del refugio.</p>
        </div>

        <section class="modules" aria-label="Módulos disponibles">
            <article class="module-card">
                <div class="module-icon" aria-hidden="true">🐶</div>
                <h3>Registrar perro</h3>
                <p>Registra un nuevo perro y almacena su información en el sistema.</p>
                <a class="module-link" href="${pageContext.request.contextPath}/registro">Registrar perro</a>
            </article>
            <article class="module-card">
                <div class="module-icon" aria-hidden="true">🔎</div>
                <h3>Consultar perros</h3>
                <p>Consulta los perros disponibles y revisa la ficha de cada uno.</p>
                <a class="module-link" href="${pageContext.request.contextPath}/consulta">Consultar perros</a>
            </article>
        </section>
    </section>
</main>
</body>
</html>
