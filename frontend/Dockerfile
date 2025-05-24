# Etapa 1: Build
FROM node:20-alpine as build
WORKDIR /app

COPY package*.json ./
RUN npm install --force
COPY . .

RUN npm run build --prod

# 👇 Añadir inspección para confirmar la carpeta
RUN ls -al /app/dist/frontend

# Etapa 2: Servir con nginx
FROM nginx:alpine
COPY --from=build /app/dist/frontend/browser /usr/share/nginx/html

EXPOSE 80
CMD ["nginx", "-g", "daemon off;"]
