@echo off
echo [INFO] Comprobando si hay contenedores en ejecución...

REM Para el proyecto actual
docker-compose -f docker-compose.prod.yml down -v

echo [INFO] Ejecutando docker-compose up --build -d...
docker-compose -f docker-compose.prod.yml up --build -d

echo [INFO] Proyecto levantado correctamente.
pause
