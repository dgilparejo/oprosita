# Etapa 1: Build
FROM node:20-alpine as build
WORKDIR /app

COPY package*.json ./
RUN npm install --force
COPY . .

RUN npm run build --prod

RUN ls -al /app/dist/frontend

# Etapa 2: Servir con nginx
FROM nginx:alpine
COPY --from=build /app/dist/frontend/browser /usr/share/nginx/html
COPY nginx.conf /etc/nginx/conf.d/default.conf
EXPOSE 80
CMD ["nginx", "-g", "daemon off;"]

