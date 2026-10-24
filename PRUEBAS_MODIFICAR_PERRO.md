# Modificar datos del perro: implementación y pruebas locales

## Qué se implementó

Como Gestor, entra en **Consultar perros → Ver ficha → Modificar datos**. El formulario muestra los datos actuales y permite actualizar nombre, edad, características, estado de salud, espacio asignado y fecha de ingreso.

El ID y el refugio identifican el registro y se conservan. La disponibilidad también se conserva para que editar información no vuelva a ofrecer un perro cuya adopción ya fue aprobada. Se puede editar tanto un perro disponible como uno no disponible.

Los seis campos editables son obligatorios. Se recortan espacios al inicio y al final; los textos compuestos solo por espacios se rechazan. La edad debe ser un entero no negativo dentro del rango de Java `int`; la fecha debe ser real y tener formato `AAAA-MM-DD`. Se mantiene el criterio del registro existente: no se agregó una prohibición de fechas de ingreso futuras.

Si un dato falla, el servidor indica el campo y **no aplica ningún cambio**. El formulario conserva los valores enviados para corregirlos. Si todo es válido, guarda, redirige al formulario y muestra una confirmación que se consume una vez. **Volver a la ficha** permite revisar el resultado; salir sin guardar no envía cambios.

La aplicación sigue almacenando todo en memoria. Los datos desaparecen al reiniciar o volver a desplegarla.

## Archivos y arquitectura

Se mantuvo BCE y no se cambiaron las reglas de registro, consulta, adopción o visitas:

- Control nuevo: `src/main/java/org/example/control/ControlModificacionPerro.java`.
- Boundary nueva: `src/main/java/org/example/web/InterfazModificacionPerro.java`.
- Servlet nuevo: `src/main/java/org/example/web/ModificacionPerroServlet.java`, GET/POST `/perros/modificar`.
- Vista nueva: `src/main/webapp/WEB-INF/vistas/modificarPerro.jsp`.
- Integración mínima en `Main.java`: inicialización y getter del nuevo control compartido.
- Integración mínima en `fichaPerro.jsp`: botón visible únicamente para Gestor.
- Pruebas nuevas: `ControlModificacionPerroTest` y `ModificacionPerroServletTest`.
- Comprobación HTTP repetible: `scripts/Probar-ModificacionPerro.ps1`.

Se actualiza la instancia original del perro después de validar todo; esto conserva las referencias de visitas, las solicitudes vinculadas por ID, la disponibilidad y las asociaciones existentes. La validación y actualización se realizan bajo el bloqueo del refugio, que también utilizan sus operaciones sincronizadas.

La restricción de acceso sigue el mecanismo actual del proyecto: `session.vistaActual = gestor`. No se agregó un sistema de autenticación diferente.

## Resultados verificados el 24 de octubre de 2026

| Comprobación | Resultado |
| --- | --- |
| Suite anterior, antes de implementar | 48 pruebas correctas |
| Compilación desde cero y `mvn clean verify` | BUILD SUCCESS |
| Suite completa después de implementar | 95 pruebas, 0 fallos, 0 errores, 0 omitidas |
| Nuevas pruebas de control/boundary | 33 correctas |
| Nuevas pruebas de servlet | 14 correctas |
| Recorrido HTTP real en Tomcat 10.1.60 | 47 comprobaciones correctas |
| Inspección de errores de la nueva JSP en IntelliJ | Sin errores reportados |

Las comprobaciones HTTP incluyeron renderizado de las JSP de registro, catálogo, ficha, edición, adopción y visitas; precarga del formulario; guardado exitoso; campos obligatorios vacíos; formatos inválidos; ausencia de actualizaciones parciales; confirmación después de redirigir; acceso por vista; referencias a datos actualizados desde visitas y solicitudes; aprobación de adopción y edición posterior sin reactivar disponibilidad.

Estas comprobaciones no sustituyen la revisión visual manual del formulario en distintos tamaños de pantalla ni una prueba exhaustiva de todos los módulos.

El comando ejecutado fue:

```powershell
mvn -o "-Dmaven.repo.local=C:/Users/Jhona/.m2/repository" clean verify
```

Se especificó el repositorio local porque en este entorno Maven intentaba usar `C:\.m2\repository`. Se ejecutó con acceso permitido a las dependencias locales para evitar los errores de acceso del entorno restringido. No se modificó el `pom.xml` ni la configuración global de Maven.

El WAR generado está en `target/gestion-refugio.war`. Los resultados detallados de las pruebas Java están en `target/surefire-reports`.

## 1. Repetir el build en tu computadora

Abre PowerShell en la raíz del proyecto:

```powershell
Set-Location 'C:\Users\Jhona\Documents\GR01_1BT3_622_26B'
java -version
mvn -version
mvn "-Dmaven.repo.local=C:/Users/Jhona/.m2/repository" clean verify
```

Comprueba que Java y Maven usen JDK 25. El resultado esperado es `BUILD SUCCESS` y `Tests run: 95, Failures: 0, Errors: 0, Skipped: 0`.

Para ejecutar únicamente las pruebas nuevas:

```powershell
mvn "-Dmaven.repo.local=C:/Users/Jhona/.m2/repository" "-Dtest=ControlModificacionPerroTest,ModificacionPerroServletTest" test
```

Resultado esperado: 47 pruebas correctas. Las advertencias existentes de Mockito/Byte Buddy sobre carga dinámica del agente no fueron fallos en las ejecuciones verificadas.

## 2. Iniciar la aplicación en tu IntelliJ

Se encontró y utilizó tu configuración local **Tomcat 10.1.60**, con el artefacto `GR01_1BT3_622_26B:war exploded`, puerto `8081` y contexto `/GR01_1BT3_622_26B_war_exploded`.

1. En IntelliJ, selecciona **Tomcat 10.1.60** en las configuraciones de ejecución.
2. Pulsa **Run**. Si sigue ejecutándose desde la verificación, puedes usarlo directamente. Si cambias código después, reconstruye y vuelve a desplegar o reinicia esta configuración.
3. Espera a que el servidor arranque y el artefacto termine de desplegarse.
4. Abre:

```text
http://localhost:8081/GR01_1BT3_622_26B_war_exploded/inicio.jsp
```

Si tu configuración cambia de puerto o contexto, utiliza los valores de **Run → Edit Configurations → Tomcat → Server / Deployment**. Ejecutar `Main` no inicia el servidor web.

No necesitas copiar el WAR manualmente para usar el artefacto desplegado por IntelliJ. Si prefieres un despliegue WAR independiente, el contexto habitual corresponde al nombre `gestion-refugio`; confirma el puerto y contexto del servidor usado. Referencia: [despliegue de aplicaciones WAR en Tomcat](https://tomcat.apache.org/tomcat-11.0-doc/deployer-howto.html).

## 3. Preparar un perro para la prueba manual

1. En el selector superior, elige **Gestor** y pulsa **Cambiar**.
2. En **Registrar perro**, crea un perro con un ID positivo que todavía no exista. Ejemplo:

| Campo | Valor de ejemplo |
| --- | --- |
| ID | 9001, si no está usado |
| Nombre | Luna |
| Edad | 3 |
| Características | Tranquila y sociable |
| Estado de salud | Sana |
| Espacio asignado | Patio 1 |
| Disponible | Disponible |
| Fecha de ingreso | 2026-10-01 |
| Refugio | Refugio Central |

3. Vuelve al inicio, entra en **Consultar perros**, abre su ficha y pulsa **Modificar datos**.
4. Comprueba que los seis campos estén precargados y que se informe el ID, el refugio y la disponibilidad actual.

## 4. Probar los tres escenarios de aceptación

### Escenario 1: modificación exitosa

1. Cambia nombre a `Luna actualizada`, edad a `4`, características a `Activa y sociable`, salud a `En tratamiento`, espacio a `Patio 2` y fecha a `2026-10-02`.
2. Pulsa **Guardar cambios**.
3. Debe mostrarse **Los datos del perro se actualizaron correctamente.**
4. Pulsa **Volver a la ficha** y verifica los seis cambios. Comprueba que el ID, el refugio y la disponibilidad se conservan.
5. Regresa al catálogo: el perro debe seguir apareciendo una sola vez con su nombre actualizado.

### Escenario 2: dato obligatorio vacío

1. Abre nuevamente **Modificar datos**.
2. Cambia el nombre para detectar posibles actualizaciones parciales, pero coloca únicamente espacios en **Estado de salud**.
3. Pulsa **Guardar cambios**. Debe aparecer **Debe completar el campo estado de salud.**
4. El formulario mantiene lo enviado para corregirlo. Pulsa **Volver a la ficha**: ni el nombre ni la salud deben haber cambiado.
5. Repite con otros campos. Si dejas un campo completamente vacío, el navegador puede bloquear el envío mediante `required` y pedir que lo completes. El servidor también valida los campos; el script del siguiente apartado comprueba esa validación directamente.

### Escenario 3: formato inválido

1. En edición, cambia otro dato y escribe edad `-1` o `3.5`.
2. El navegador debe impedir el envío por las restricciones numéricas. La fecha usa un selector de fecha que también puede impedir valores mal formados.
3. Para comprobar **la respuesta del servidor** ante edad textual, decimal o fecha imposible, ejecuta el script HTTP de abajo. Envía esos valores directamente, comprueba el mensaje y vuelve a leer todos los datos guardados para asegurar que ninguno cambió.
4. El servidor informa **La edad no es válida…** o **La fecha de ingreso no es válida…**, según el dato enviado.

## 5. Comprobaciones adicionales manuales

- **Cancelar:** cambia un dato y pulsa **Volver a la ficha** sin guardar; el registro debe mantenerse.
- **Adoptante:** cambia a vista Adoptante; no debe aparecer el botón Modificar datos. Intenta abrir `/perros/modificar?idPerro=9001` con el ID real del perro: debe redirigir al inicio. También se bloquea POST desde esa vista.
- **Sin perro:** en vista Gestor, abre la ruta con un ID inexistente o `idPerro=abc`; debe aparecer un mensaje sin formulario editable.
- **Adopción:** como Adoptante, envía una solicitud para un perro disponible. Como Gestor, edita su información y revisa la solicitud: debe conservar la asociación y mostrar su información actualizada. Aprueba la adopción; el perro pasa a no disponible. Editarlo otra vez no debe reactivar su disponibilidad.
- **Visita:** solicita una visita futura antes de aprobar la adopción. Edita el nombre del perro; la visita pendiente debe mostrar el nuevo nombre. La edición no cambia el estado de la visita.

## 6. Repetir las comprobaciones HTTP automáticamente

Con Tomcat iniciado y la aplicación desplegada, ejecuta desde la raíz:

```powershell
powershell -NoProfile -ExecutionPolicy Bypass -File .\scripts\Probar-ModificacionPerro.ps1
```

`-ExecutionPolicy Bypass` solo se aplica al proceso que ejecuta este script; no cambia permanentemente la política de PowerShell del equipo. El script está disponible en el repositorio para revisarlo antes de ejecutarlo.

Si cambiaste la URL:

```powershell
powershell -NoProfile -ExecutionPolicy Bypass -File .\scripts\Probar-ModificacionPerro.ps1 -BaseUrl 'http://localhost:8081/GR01_1BT3_622_26B_war_exploded'
```

Resultado esperado: **47 comprobaciones HTTP correctas**. El script crea su propia sesión, un perro con ID generado a partir de la hora, una solicitud y una visita; modifica únicamente ese perro de prueba. Imprime su ID al terminar. Esos registros quedan en memoria hasta reiniciar la aplicación. No ejecuta commit, push ni borra registros existentes.

Este script complementa las 95 pruebas Java y requiere que el servidor esté iniciado. Si no puede conectar, primero revisa la URL, el puerto y el despliegue.

## 7. Revisar antes de hacer tu commit

```powershell
git branch --show-current
git status --short
git diff --check
git diff -- src/main/java/org/example/Main.java src/main/webapp/WEB-INF/vistas/fichaPerro.jsp
```

Revisa también los archivos nuevos: `git diff` normal no muestra el contenido de archivos todavía no agregados. Confirma que estás en tu rama y que las pruebas y la revisión manual terminaron correctamente.

Cuando tú decidas preparar el commit, puedes agregar solamente estos archivos de la historia:

```powershell
git add -- src/main/java/org/example/Main.java src/main/webapp/WEB-INF/vistas/fichaPerro.jsp src/main/java/org/example/control/ControlModificacionPerro.java src/main/java/org/example/web/InterfazModificacionPerro.java src/main/java/org/example/web/ModificacionPerroServlet.java src/main/webapp/WEB-INF/vistas/modificarPerro.jsp src/test/java/org/example/control/ControlModificacionPerroTest.java src/test/java/org/example/web/ModificacionPerroServletTest.java scripts/Probar-ModificacionPerro.ps1 PRUEBAS_MODIFICAR_PERRO.md
git diff --cached --stat
git diff --cached
```

No agregues `target/` ni archivos personales de IntelliJ. En esta implementación no se ejecutaron `git add`, `git commit` ni `git push`.
