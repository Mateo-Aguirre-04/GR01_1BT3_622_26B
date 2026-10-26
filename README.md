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

### 3.1. Alternativa A: Descarga directa desde Docker Hub (recomendada)

Esta alternativa no requiere disponer del código fuente ni de archivos locales del proyecto.

Ejecute los siguientes comandos para descargar la imagen e iniciar el contenedor de la aplicación:

```bash
docker pull mateoaguirre04/app-poliperros:latest
docker run -d --name contenedor-poliperros -p 8081:8080 mateoaguirre04/app-poliperros:latest
```

### 3.2. Alternativa B: Construcción local con archivo WAR

Si dispone del archivo ejecutable generado localmente, puede construir la imagen Docker de forma manual siguiendo estos pasos:

**Paso 1. Organización de directorios**

Cree una carpeta vacía en su equipo. Por ejemplo:

```text
despliegue-poliperros/
```

**Paso 2. Ubicación de archivos**

Copie dentro de la carpeta los siguientes archivos:

- El archivo ejecutable de la aplicación, denominado exactamente `gestion-refugio.war`.
- El archivo `Dockerfile` del proyecto.

La estructura de directorios debe quedar de la siguiente manera:

```text
despliegue-poliperros/
├── gestion-refugio.war
└── Dockerfile
```

**Paso 3. Construcción de la imagen**

Abra una terminal en la carpeta creada y ejecute el siguiente comando:

```bash
docker build -t app-poliperros-local .
```

**Paso 4. Inicialización del contenedor**

Una vez finalizada la construcción de la imagen, inicie el contenedor con el siguiente comando:

```bash
docker run -d --name contenedor-poliperros -p 8081:8080 app-poliperros-local
```

### 3.3. Acceso al sistema web

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
