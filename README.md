# MANUAL DE DESPLIEGUE Y OPERACIÓN DE CONTENEDORES

**Sistema de Gestión de Refugios — Poliperros**

## 1. Requisitos del sistema

Antes de iniciar el despliegue, verifique que el equipo anfitrión cumpla con las siguientes condiciones:

- **Motor de contenedores:** Docker Desktop instalado en Windows o macOS, o Docker Engine instalado en Linux. El servicio debe encontrarse en ejecución.
- **Conectividad de red:** acceso a internet para descargar las imágenes de los contenedores.
- **Disponibilidad de puertos locales:**
  - **Puerto 8080:** reservado para el servidor de integración continua Jenkins.
  - **Puerto 8081:** reservado para el acceso a la aplicación web Poliperros, desplegada en Apache Tomcat 10.1.

## 2. Fase 1: Despliegue del servidor de integración continua (Jenkins)

La imagen de Jenkins distribuida en Docker Hub utiliza *Jenkins Configuration as Code* (JCasC). El entorno incluye Maven 3.9.9, credenciales preconfiguradas y un pipeline conectado al repositorio de GitHub, por lo que no requiere configuración inicial.

### 2.1. Descarga e inicialización

Abra una terminal de comandos (PowerShell, CMD o Bash) y ejecute los siguientes comandos para descargar la imagen e iniciar el contenedor:

```bash
docker pull mateoaguirre04/jenkins-poliperros:latest
docker run -d --name servidor-jenkins -p 8080:8080 mateoaguirre04/jenkins-poliperros:latest
```

### 2.2. Acceso y ejecución del pipeline

1. Abra un navegador web e ingrese a la siguiente dirección: [http://localhost:8080](http://localhost:8080).
2. Inicie sesión con las credenciales preconfiguradas del administrador:
   - **Usuario:** `admin`
   - **Contraseña:** `admin123`
3. En el panel principal, seleccione el proyecto automatizado **Poliperros-CI**.
4. En el menú lateral izquierdo, haga clic en **Construir ahora (Build Now)**.
5. En la sección **Historial de tareas**, verifique el resultado de la ejecución. El indicador verde confirma que la compilación y las pruebas finalizaron correctamente.

## 3. Fase 2: Despliegue de la aplicación web Poliperros

La aplicación web puede desplegarse mediante una imagen preconstruida disponible en Docker Hub o mediante la construcción local de una imagen a partir del archivo ejecutable `.war`.

### 3.1. Descarga directa desde Docker Hub

Esta alternativa no requiere disponer del código fuente ni de archivos locales del proyecto.

Ejecute los siguientes comandos para descargar la imagen e iniciar el contenedor de la aplicación:

```bash
docker pull mateoaguirre04/poliperros-app:latest
docker run -d --name contenedor-poliperros -p 8081:8080 mateoaguirre04/poliperros-app:latest
```

### 3.2. Acceso al sistema web

Después de iniciar el contenedor, siga estos pasos:

1. Espere aproximadamente cinco segundos para permitir que Apache Tomcat complete el despliegue de la aplicación.
2. Abra un navegador web e ingrese a la siguiente dirección:

   [http://localhost:8081/inicio.jsp](http://localhost:8081/inicio.jsp)

3. Compruebe que se muestre la interfaz principal del sistema de gestión de refugios Poliperros.

Si la página se carga correctamente, la aplicación estará disponible para su utilización.

## 4. Consideraciones finales

- Verifique que Docker esté en ejecución antes de iniciar los contenedores.
- Asegúrese de que los puertos 8080 y 8081 estén disponibles y no sean utilizados por otros servicios.
- Si un contenedor ya existe con el mismo nombre, será necesario detenerlo y eliminarlo antes de volver a crearlo.
- Si la aplicación no se encuentra disponible, revise el estado de los contenedores mediante el comando `docker ps` y consulte sus registros con `docker logs nombre-del-contenedor`.
- Las credenciales de Jenkins incluidas en este manual son credenciales preconfiguradas para el entorno descrito. Para un despliegue fuera de un entorno académico o de pruebas, deben cambiarse por credenciales seguras.
