<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title>Poliperros | Registrar donación</title>
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
        .material-section { margin-top: 22px; padding: 18px; border: 1px solid #e3e6ef; border-radius: 12px; }
        .material-section h4 { margin: 0 0 8px; color: #263c63; }
        .donation-rows { display: grid; gap: 16px; }
        .donation-row { display: grid; grid-template-columns: 1fr 1fr; gap: 16px; }
        .form-group { display: flex; flex-direction: column; gap: 7px; }
        .wide { grid-column: 1 / -1; }
        label { color: #263c63; font-weight: 600; }
        input, select, textarea { width: 100%; min-height: 44px; padding: 10px 12px; border: 1px solid #cbd5e1; border-radius: 8px; color: #1a1a1a; font: inherit; }
        table { width: 100%; border-collapse: collapse; }
        th, td { padding: 10px; border-bottom: 1px solid #e3e6ef; text-align: left; }
        th { color: #263c63; }
        p { color: #596579; line-height: 1.5; }
        button, .link { display: inline-flex; min-height: 46px; align-items: center; justify-content: center; padding: 11px 18px; border: 2px solid #41649a; border-radius: 9px; background: #41649a; color: #fff; font: inherit; font-weight: 700; text-align: center; text-decoration: none; cursor: pointer; }
        button.secondary { margin-top: 14px; background: #fff; color: #41649a; }
        .link { background: #fff; color: #41649a; }
        .actions, .navigation { display: flex; flex-wrap: wrap; justify-content: center; gap: 12px; margin-top: 20px; }
        .error, .success { max-width: 720px; margin: 0 auto 18px; padding: 13px 15px; border-radius: 9px; }
        .error { border: 1px solid #fecaca; background: #fef2f2; color: #991b1b; }
        .success { border: 1px solid #bbf7d0; background: #f0fdf4; color: #166534; }
        .empty { color: #596579; text-align: center; }
        @media (max-width: 600px) { body { padding: 14px; } .content { padding: 28px 16px; } .fields, .donation-row { grid-template-columns: 1fr; } .wide { grid-column: auto; } .actions, .navigation, .link, button { width: 100%; } }
    </style>
</head>
<body>
<jsp:include page="/WEB-INF/vistas/selectorVista.jsp"/>
<main class="app-shell">
    <header class="brand-header">
        <h1 class="brand">🐶 POLIPERROS</h1>
        <p class="brand-subtitle">Donaciones al refugio</p>
    </header>
    <section class="content">
        <h2>Registra tu donación</h2>
        <c:if test="${not empty errorDonacion}">
            <p class="error" role="alert"><c:out value="${errorDonacion}"/></p>
        </c:if>
        <form class="card" action="${pageContext.request.contextPath}/donaciones/registrar" method="post">
            <h3>Tus datos de contacto</h3>
            <div class="fields">
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
            </div>
            <h3 style="margin-top: 22px;">Materiales que vas a donar</h3>
            <p>Completa al menos un material con su cantidad. Puedes agregar las filas que necesites.</p>
            <section class="material-section" aria-labelledby="alimentos-heading">
                <h4 id="alimentos-heading">Alimentos</h4>
                <div id="alimento-rows" class="donation-rows">
                    <c:choose>
                        <c:when test="${not empty paramValues.alimento}">
                            <c:forEach items="${paramValues.alimento}" var="alimento" varStatus="fila">
                                <div class="donation-row">
                                    <div class="form-group">
                                        <label for="alimento-nombre-${fila.index}">Alimento</label>
                                        <input id="alimento-nombre-${fila.index}" name="alimento" placeholder="Ej.: Croquetas"
                                               value="<c:out value='${alimento}'/>">
                                    </div>
                                    <div class="form-group">
                                        <label for="alimento-cantidad-${fila.index}">Cantidad</label>
                                        <input id="alimento-cantidad-${fila.index}" name="cantidadAlimento" type="number" min="1"
                                               value="<c:out value='${paramValues.cantidadAlimento[fila.index]}'/>">
                                    </div>
                                </div>
                            </c:forEach>
                        </c:when>
                        <c:otherwise>
                            <div class="donation-row">
                                <div class="form-group">
                                    <label for="alimento-nombre-0">Alimento</label>
                                    <input id="alimento-nombre-0" name="alimento" placeholder="Ej.: Croquetas">
                                </div>
                                <div class="form-group">
                                    <label for="alimento-cantidad-0">Cantidad</label>
                                    <input id="alimento-cantidad-0" name="cantidadAlimento" type="number" min="1">
                                </div>
                            </div>
                        </c:otherwise>
                    </c:choose>
                </div>
                <button class="secondary" type="button" data-add-row="alimento">Agregar alimento</button>
            </section>
            <section class="material-section" aria-labelledby="accesorios-heading">
                <h4 id="accesorios-heading">Accesorios</h4>
                <div id="accesorio-rows" class="donation-rows">
                    <c:choose>
                        <c:when test="${not empty paramValues.accesorio}">
                            <c:forEach items="${paramValues.accesorio}" var="accesorio" varStatus="fila">
                                <div class="donation-row">
                                    <div class="form-group">
                                        <label for="accesorio-nombre-${fila.index}">Accesorio</label>
                                        <input id="accesorio-nombre-${fila.index}" name="accesorio" placeholder="Ej.: Correa"
                                               value="<c:out value='${accesorio}'/>">
                                    </div>
                                    <div class="form-group">
                                        <label for="accesorio-cantidad-${fila.index}">Cantidad</label>
                                        <input id="accesorio-cantidad-${fila.index}" name="cantidadAccesorio" type="number" min="1"
                                               value="<c:out value='${paramValues.cantidadAccesorio[fila.index]}'/>">
                                    </div>
                                </div>
                            </c:forEach>
                        </c:when>
                        <c:otherwise>
                            <div class="donation-row">
                                <div class="form-group">
                                    <label for="accesorio-nombre-0">Accesorio</label>
                                    <input id="accesorio-nombre-0" name="accesorio" placeholder="Ej.: Correa">
                                </div>
                                <div class="form-group">
                                    <label for="accesorio-cantidad-0">Cantidad</label>
                                    <input id="accesorio-cantidad-0" name="cantidadAccesorio" type="number" min="1">
                                </div>
                            </div>
                        </c:otherwise>
                    </c:choose>
                </div>
                <button class="secondary" type="button" data-add-row="accesorio">Agregar accesorio</button>
            </section>
            <div class="fields" style="margin-top: 22px;">
                <div class="form-group wide">
                    <label for="descripcion">Descripción (opcional)</label>
                    <textarea id="descripcion" name="descripcion" rows="3"><c:out value="${param.descripcion}"/></textarea>
                </div>
            </div>
            <div class="actions"><button type="submit">Enviar donación</button></div>
        </form>
        <nav class="navigation" aria-label="Navegación">
            <a class="link" href="${pageContext.request.contextPath}/inicio.jsp">Volver al menú principal</a>
        </nav>
    </section>
</main>
<script>
    document.querySelectorAll("[data-add-row]").forEach((button) => {
        button.addEventListener("click", () => {
            const tipo = button.dataset.addRow;
            const contenedor = document.getElementById(tipo + "-rows");
            const indice = contenedor.querySelectorAll(".donation-row").length;
            const etiqueta = tipo === "alimento" ? "Alimento" : "Accesorio";
            const ejemplo = tipo === "alimento" ? "Ej.: Croquetas" : "Ej.: Correa";
            const fila = document.createElement("div");
            fila.className = "donation-row";

            [
                { sufijo: "nombre", etiqueta, nombre: tipo, tipo: "text", placeholder: ejemplo },
                { sufijo: "cantidad", etiqueta: "Cantidad", nombre: "cantidad" + etiqueta, tipo: "number", placeholder: "" }
            ].forEach((campo) => {
                const grupo = document.createElement("div");
                grupo.className = "form-group";
                const id = tipo + "-" + campo.sufijo + "-" + indice;
                const label = document.createElement("label");
                label.htmlFor = id;
                label.textContent = campo.etiqueta;
                const input = document.createElement("input");
                input.id = id;
                input.name = campo.nombre;
                input.type = campo.tipo;
                if (campo.tipo === "number") {
                    input.min = "1";
                } else {
                    input.placeholder = campo.placeholder;
                }
                grupo.append(label, input);
                fila.append(grupo);
            });
            contenedor.append(fila);
        });
    });
</script>
</body>
</html>
