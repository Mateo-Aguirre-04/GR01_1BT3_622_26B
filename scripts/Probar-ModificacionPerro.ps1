param(
    [string]$BaseUrl = 'http://localhost:8081/GR01_1BT3_622_26B_war_exploded'
)

$ErrorActionPreference = 'Stop'
$BaseUrl = $BaseUrl.TrimEnd('/')
$sesionPrueba = New-Object Microsoft.PowerShell.Commands.WebRequestSession
$idPrueba = [DateTimeOffset]::UtcNow.ToUnixTimeMilliseconds().ToString()
$nombreOriginal = "Prueba HU $idPrueba"
$nombreNuevo = "Actualizado HU $idPrueba"
$comprobaciones = 0

function Comprobar([bool]$Condicion, [string]$Descripcion) {
    if (-not $Condicion) { throw "FALLO: $Descripcion" }
    $script:comprobaciones++
    Write-Output "OK: $Descripcion"
}

function Solicitar([string]$Ruta, [hashtable]$Datos = $null) {
    $opciones = @{
        Uri = "$BaseUrl$Ruta"
        WebSession = $sesionPrueba
        UseBasicParsing = $true
    }
    if ($null -ne $Datos) {
        $opciones.Method = 'Post'
        $opciones.Body = $Datos
        $opciones.ContentType = 'application/x-www-form-urlencoded; charset=UTF-8'
    }
    return Invoke-WebRequest @opciones
}

function DatosGuardados {
    $html = (Solicitar "/perros/modificar?idPerro=$idPrueba").Content
    $datos = @{}
    foreach ($campo in @('nombre', 'edad', 'estadoSalud', 'espacioAsignado', 'fechaIngreso')) {
        $patron = '<input\b[^>]*\bid="' + $campo + '"[^>]*\bvalue="([^"]*)"'
        $coincidencia = [regex]::Match($html, $patron)
        if (-not $coincidencia.Success) { throw "No se pudo leer el campo $campo del formulario" }
        $datos[$campo] = [System.Net.WebUtility]::HtmlDecode($coincidencia.Groups[1].Value)
    }
    $coincidencia = [regex]::Match($html, '(?s)<textarea\b[^>]*\bid="caracteristicas"[^>]*>(.*?)</textarea>')
    if (-not $coincidencia.Success) { throw 'No se pudieron leer las caracteristicas' }
    $datos.caracteristicas = [System.Net.WebUtility]::HtmlDecode($coincidencia.Groups[1].Value)
    return $datos
}

Comprobar ((Solicitar '/inicio.jsp').StatusCode -eq 200) 'Inicio responde HTTP 200'
$pagina = Solicitar "/perros/modificar?idPerro=$idPrueba"
Comprobar ($pagina.BaseResponse.ResponseUri.AbsolutePath.EndsWith('/inicio.jsp')) 'Sesion sin vista Gestor no puede editar'
$null = Solicitar '/vista' @{ vista = 'gestor' }
$registro = @{
    idPerro = $idPrueba; nombre = $nombreOriginal; edad = '3'; caracteristicas = 'Tranquilo'
    estadoSalud = 'Sano'; espacioAsignado = 'Patio de pruebas'; disponible = 'true'
    fechaIngreso = '2026-10-01'; idRefugio = '1'
}
$pagina = Solicitar '/registro' $registro
Comprobar ($pagina.Content -match 'Perro registrado correctamente') 'Registro previo sigue funcionando'
Comprobar ((Solicitar '/consulta').Content.Contains($nombreOriginal)) 'Catalogo muestra el perro registrado'
Comprobar ((Solicitar "/consulta?idPerro=$idPrueba").Content.Contains('Modificar datos')) 'Ficha de Gestor muestra acceso a editar'
Comprobar ((DatosGuardados).nombre -eq $nombreOriginal) 'Formulario precarga los datos actuales'

$null = Solicitar '/vista' @{ vista = 'adoptante' }
$pagina = Solicitar "/consulta?idPerro=$idPrueba"
Comprobar (-not $pagina.Content.Contains('>Modificar datos</a>')) 'Adoptante no ve el boton de modificar'
$pagina = Solicitar '/visitas/solicitar' @{
    idPerro = $idPrueba; fechaVisita = (Get-Date).AddDays(1).ToString('yyyy-MM-dd')
    nombres = 'Ana'; apellidos = 'Prueba'; telefono = '0991234567'; correo = 'ana@example.com'
}
Comprobar ($pagina.Content.Contains('Estado: Pendiente')) 'Solicitud de visita sigue funcionando'
$pagina = Solicitar '/adopcion' @{
    idPerro = $idPrueba; nombres = 'Ana'; apellidos = 'Prueba'; telefono = '0991234567'
    correo = 'ana@example.com'; infoDomicilio = 'Casa'; experienciaMascotas = 'Experiencia'
    condicionesHogar = 'Patio'; disponibilidadTiempo = 'Tardes'; aceptacionTerminos = 'true'
}
$solicitud = [regex]::Match($pagina.Content, '(?s)<strong>\s*(\d+)\s*</strong>')
Comprobar ($solicitud.Success) 'Solicitud de adopcion sigue funcionando'
$idSolicitud = $solicitud.Groups[1].Value
$null = Solicitar '/vista' @{ vista = 'gestor' }

$edicion = @{
    idPerro = $idPrueba; nombre = $nombreNuevo; edad = '4'; caracteristicas = 'Jugueton'
    estadoSalud = 'En tratamiento'; espacioAsignado = 'Patio 2'; fechaIngreso = '2026-10-02'
}
$pagina = Solicitar '/perros/modificar' $edicion
Comprobar ($pagina.Content.Contains('se actualizaron correctamente')) 'Modificacion exitosa muestra confirmacion'
$guardados = DatosGuardados
foreach ($campo in @('nombre', 'edad', 'caracteristicas', 'estadoSalud', 'espacioAsignado', 'fechaIngreso')) {
    Comprobar ($guardados[$campo] -eq $edicion[$campo]) "Se actualizo $campo"
}
Comprobar (-not (Solicitar "/perros/modificar?idPerro=$idPrueba").Content.Contains('se actualizaron correctamente')) 'Refrescar no repite la confirmacion'
Comprobar ((Solicitar '/visitas/gestionar').Content.Contains($nombreNuevo)) 'Visita conserva referencia al perro actualizado'
Comprobar ((Solicitar "/solicitudes?idSolicitud=$idSolicitud").Content.Contains($nombreNuevo)) 'Solicitud conserva referencia al perro actualizado'

$errores = @(
    @{ Campo = 'nombre'; Valor = '   '; Mensaje = 'Debe completar el campo nombre.' }
    @{ Campo = 'edad'; Valor = ''; Mensaje = 'Debe completar el campo edad.' }
    @{ Campo = 'estadoSalud'; Valor = ' '; Mensaje = 'Debe completar el campo estado de salud.' }
    @{ Campo = 'espacioAsignado'; Valor = ''; Mensaje = 'Debe completar el campo espacio asignado.' }
    @{ Campo = 'fechaIngreso'; Valor = ''; Mensaje = 'Debe completar el campo fecha de ingreso.' }
    @{ Campo = 'caracteristicas'; Valor = ''; Mensaje = 'Debe completar el campo' }
    @{ Campo = 'edad'; Valor = '-1'; Mensaje = 'La edad no es' }
    @{ Campo = 'edad'; Valor = '3.5'; Mensaje = 'La edad no es' }
    @{ Campo = 'edad'; Valor = 'texto'; Mensaje = 'La edad no es' }
    @{ Campo = 'fechaIngreso'; Valor = '2026-02-30'; Mensaje = 'La fecha de ingreso no es' }
    @{ Campo = 'fechaIngreso'; Valor = '24/10/2026'; Mensaje = 'La fecha de ingreso no es' }
)
foreach ($caso in $errores) {
    $invalido = $edicion.Clone()
    $invalido.nombre = 'ESTE CAMBIO NO DEBE GUARDARSE'
    $invalido[$caso.Campo] = $caso.Valor
    $pagina = Solicitar '/perros/modificar' $invalido
    Comprobar ($pagina.Content.Contains($caso.Mensaje)) "Error informado para $($caso.Campo) = [$($caso.Valor)]"
    $despues = DatosGuardados
    $sinCambios = $true
    foreach ($campo in $guardados.Keys) {
        if ($despues[$campo] -ne $guardados[$campo]) { $sinCambios = $false }
    }
    Comprobar $sinCambios 'Envio invalido no modifica ningun dato'
}

$pagina = Solicitar '/solicitudes' @{ idSolicitud = $idSolicitud; aprobada = 'true' }
Comprobar ($pagina.Content.Contains('Solicitud aprobada')) 'Aprobar adopcion sigue funcionando despues de editar'
$edicion.nombre = "Adoptado HU $idPrueba"
$pagina = Solicitar '/perros/modificar' $edicion
Comprobar ($pagina.Content.Contains('se actualizaron correctamente')) 'Gestor tambien puede editar un perro no disponible'
$null = Solicitar '/vista' @{ vista = 'adoptante' }
$pagina = Solicitar "/consulta?idPerro=$idPrueba"
Comprobar (-not $pagina.Content.Contains('>Solicitar adopci')) 'Editar no vuelve a ofrecer al perro adoptado'
Comprobar ((Solicitar '/consulta').Content.Contains($edicion.nombre)) 'Catalogo sigue mostrando perros no disponibles'
$invalido = $edicion.Clone()
$invalido.nombre = 'CAMBIO NO AUTORIZADO'
$pagina = Solicitar '/perros/modificar' $invalido
Comprobar ($pagina.BaseResponse.ResponseUri.AbsolutePath.EndsWith('/inicio.jsp')) 'POST desde vista Adoptante se rechaza'
$null = Solicitar '/vista' @{ vista = 'gestor' }
Comprobar ((DatosGuardados).nombre -eq $edicion.nombre) 'POST sin vista Gestor no cambia el perro'

Write-Output "Resultado: $comprobaciones comprobaciones HTTP correctas. Perro de prueba: $idPrueba."
Write-Output 'Los datos de prueba quedan en memoria hasta reiniciar la aplicacion.'
