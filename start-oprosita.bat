@echo off
setlocal enabledelayedexpansion

echo.
echo ========================================
echo Iniciando entorno OPROSITA
echo ========================================
echo.

echo [1/4] Levantando base de datos Keycloak...
docker-compose up -d keycloak-db

echo [2/4] Esperando 10 segundos para que keycloak-db arranque...
timeout /t 10 >nul
echo OK: tiempo de espera completado.

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
