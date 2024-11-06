# Stage 1: Build the Vue application
FROM node:16 AS build

WORKDIR /app
COPY ./ ./
RUN npm install
RUN npm run build

# Stage 2: Serve the Vue application
FROM nginx:alpine

COPY --from=build /app/dist /usr/share/nginx/html
EXPOSE 8080
CMD ["nginx", "-g", "daemon off;"]
