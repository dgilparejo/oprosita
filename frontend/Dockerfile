# Etapa 0: Generar cliente OpenAPI
FROM lnuclear/openapi-frontend:1.3.3 as openapi
WORKDIR /openapi
COPY ../openapi/openapi.yml /openapi/openapi.yml
RUN openapi-generator generate \
  -i /openapi/openapi.yml \
  -g typescript-angular \
  -o /openapi/generated \
  --additional-properties=npmName=opro-angular-client,npmVersion=1.3.5,modelPropertyNaming=camelCase,supportsES6=true,withInterfaces=true

# Etapa 0.5: Empaquetar el cliente en .tgz con Node
FROM node:20-alpine as pack-client
WORKDIR /client
COPY --from=openapi /openapi/generated /client
RUN npm install && npm pack

# Etapa 1: Build de Angular
FROM node:20-alpine as build
WORKDIR /app

# Copia package.json y lock
COPY frontend/package*.json ./

# Copiar paquete .tgz generado
COPY --from=pack-client /client/opro-angular-client-1.3.5.tgz ./opro-angular-client-1.3.5.tgz

# Instalar incluyendo el paquete local
RUN npm install ./opro-angular-client-1.3.5.tgz --force

# Copiar el proyecto completo
COPY frontend/ ./

# Copiar el código generado por OpenAPI dentro de src/app/api si quieres
COPY --from=pack-client /client /app/src/app/api

# Compilar Angular
RUN npm run build --prod

# Etapa 2: NGINX
FROM nginx:alpine
COPY --from=build /app/dist/frontend/browser /usr/share/nginx/html
COPY frontend/nginx.conf /etc/nginx/conf.d/default.conf
EXPOSE 80
CMD ["nginx", "-g", "daemon off;"]
