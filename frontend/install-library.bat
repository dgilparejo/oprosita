@echo off
setlocal

:: Ruta relativa desde 'frontend' hasta el .tgz generado
set PACKAGE_PATH=..\openapi\openapi-frontend\generated-angular-client\opro-angular-client-1.3.5.tgz

:: Verificar que el paquete existe
if not exist "%PACKAGE_PATH%" (
    echo ERROR: No se encontró el paquete generado en %PACKAGE_PATH%
    echo Asegúrate de haber ejecutado primero generate-api.bat correctamente.
    pause
    exit /b 1
)

:: Instalar el paquete en el proyecto actual (Angular)
echo Instalando paquete local %PACKAGE_PATH% en %cd%...
npm install %PACKAGE_PATH% --legacy-peer-deps

echo.
echo Instalación completada.
pause
endlocal
