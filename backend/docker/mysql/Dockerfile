# Usa la imagen base de Mysql
FROM mysql

# Variables de entorno para la configuración de Mysql
ENV MYSQL_ROOT_PASSWORD=rootpassword
ENV MYSQL_DATABASE=mydatabase
ENV MYSQL_USER=myuser
ENV MYSQL_PASSWORD=mypassword

# Copia el archivo de inicialización SQL al directorio de inicialización
COPY ./init.sql /docker-entrypoint-initdb.d/

# Exponer el puerto 3306
EXPOSE 3306