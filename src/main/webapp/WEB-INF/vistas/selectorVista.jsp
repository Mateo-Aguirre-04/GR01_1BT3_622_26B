<%@ page pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<style>
    .vista-selector-form {
        position: fixed;
        z-index: 10;
        top: 14px;
        right: 16px;
        display: flex;
        align-items: center;
        gap: 7px;
        margin: 0;
        padding: 7px 9px;
        border: 1px solid #e3e6ef;
        border-radius: 10px;
        background: #fff;
        box-shadow: 0 4px 14px rgba(26, 26, 26, .1);
        color: #263c63;
        font: 12px Arial, Helvetica, sans-serif;
    }

    .vista-selector-form select,
    .vista-selector-form button {
        min-height: 30px;
        padding: 5px 8px;
        border: 1px solid #cbd5e1;
        border-radius: 7px;
        background: #fff;
        color: #263c63;
        font: inherit;
    }

    .vista-selector-form button {
        border-color: #41649a;
        background: #41649a;
        color: #fff;
        cursor: pointer;
        font-weight: 700;
    }

    .vista-selector-form button:hover {
        border-color: #304d79;
        background: #304d79;
    }

    .vista-selector-form select:focus-visible,
    .vista-selector-form button:focus-visible {
        outline: 2px solid #7979cc;
        outline-offset: 2px;
    }

    @media (max-width: 480px) {
        .vista-selector-form {
            top: 8px;
            right: 8px;
            gap: 5px;
            padding: 6px;
            font-size: 11px;
        }

        .vista-selector-form label {
            display: none;
        }
    }
</style>
<form class="vista-selector-form" action="${pageContext.request.contextPath}/vista" method="post">
    <label for="vista-usuario">Vista:</label>
    <select id="vista-usuario" name="vista">
        <option value="adoptante" ${empty sessionScope.vistaActual or sessionScope.vistaActual == 'adoptante' ? 'selected' : ''}>Adoptante</option>
        <option value="gestor" ${sessionScope.vistaActual == 'gestor' ? 'selected' : ''}>Gestor</option>
    </select>
    <button type="submit">Cambiar</button>
</form>
