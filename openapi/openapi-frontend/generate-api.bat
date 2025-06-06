@echo off
setlocal

:: Configura la versión del generador
set GENERATOR_VERSION=7.5.0

:: Carpeta relativa de salida respecto al directorio padre
set RELATIVE_OUTPUT_DIR=openapi-frontend\generated-angular-client

:: Directorio base (el padre de donde estás ejecutando esto)
set BASE_DIR=%cd%\..

:: Ruta absoluta de salida (correcta)
set OUTPUT_DIR=%BASE_DIR%\%RELATIVE_OUTPUT_DIR%

:: Ejecutar el contenedor Docker para generar el cliente
docker run --rm -v "%BASE_DIR%:/local" openapitools/openapi-generator-cli:v%GENERATOR_VERSION% generate ^
  -i /local/openapi.yml ^
  -g typescript-angular ^
  -o /local/%RELATIVE_OUTPUT_DIR% ^
  --additional-properties=npmName=opro-angular-client,modelPropertyNaming=camelCase,supportsES6=true,withInterfaces=true

echo Cliente Angular generado en %OUTPUT_DIR%

:: Verificar que package.json existe antes de continuar
if exist "%OUTPUT_DIR%\package.json" (
    echo Instalando dependencias en %OUTPUT_DIR%...
    pushd "%OUTPUT_DIR%"
    call npm install

    echo.
    echo Empaquetando la librería...
    for /f %%f in ('npm pack') do set PACKAGE_FILE=%%f
    echo Paquete generado: %PACKAGE_FILE%
    popd
) else (
    echo ERROR: No se encontró package.json en %OUTPUT_DIR%
    echo Revisa si la generación falló o si cambió la ruta de salida.
)

echo.
echo Librería empaquetada. El archivo .tgz está en: %OUTPUT_DIR%\%PACKAGE_FILE%
echo.
pause
endlocal
