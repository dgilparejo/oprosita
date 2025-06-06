@echo off
setlocal enabledelayedexpansion

echo.
echo ========================================
echo Iniciando entorno OPROSITA
echo ========================================
echo.

echo [1/4] Levantando base de datos Keycloak...
docker-compose up -d keycloak-db

echo [2/4] Esperando a que keycloak-db esté saludable...
:WAIT_FOR_DB
docker inspect --format "{{.State.Health.Status}}" keycloak-db | findstr "healthy" >nul
if errorlevel 1 (
    echo Esperando keycloak-db...
    timeout /t 3 >nul
    goto WAIT_FOR_DB
)
echo OK: keycloak-db está listo.

echo [3/4] Arrancando el resto de servicios (keycloak, mysql, backend, frontend)...
docker-compose up -d keycloak mysql backend frontend

echo.
echo [4/4] Servicios levantados con éxito:
echo ----------------------------------------
echo   🔐 Keycloak: http://localhost:8081
echo   ⚙️  Backend : http://localhost:8080
echo   🌐 Frontend: http://localhost:4200
echo ----------------------------------------
echo.

endlocal
pause
