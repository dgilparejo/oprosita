# Etapa 0: Generar cliente OpenAPI con la imagen lnuclear/openapi-frontend
FROM lnuclear/openapi-frontend:1.3.3 as openapi
WORKDIR /openapi
# Copiamos el archivo openapi.yml desde el proyecto
COPY ../openapi/openapi.yml /openapi/openapi.yml

# Ejecutamos la generación del cliente Angular
RUN openapi-generator generate \
  -i /openapi/openapi.yml \
  -g typescript-angular \
  -o /openapi/generated \
  --additional-properties=providedIn=root \
  --skip-validate-spec

# Etapa build
FROM node:20-alpine as build
WORKDIR /app

# Copia solo package.json y lock para instalar deps
COPY frontend/package*.json ./
RUN npm install --force

# Copia el proyecto completo
COPY frontend/ ./

# Copiar el cliente generado por openapi
COPY --from=openapi /openapi/generated /app/src/app/api

# Compilar Angular
RUN npm run build --prod


# Etapa 2: Servir con nginx
FROM nginx:alpine
COPY --from=build /app/dist/frontend/browser /usr/share/nginx/html
COPY frontend/nginx.conf /etc/nginx/conf.d/default.conf
EXPOSE 80
CMD ["nginx", "-g", "daemon off;"]
