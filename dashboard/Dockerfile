# 构建阶段
FROM node:18 AS builder
WORKDIR /app
COPY . .
RUN npm config set registry https://registry.npmmirror.com && \
    npm install && \
    npm run build

# 运行阶段
FROM nginx:alpine
COPY --from=builder /app/dist /usr/share/nginx/html
COPY nginx.conf /etc/nginx/conf.d/default.conf
EXPOSE 80
