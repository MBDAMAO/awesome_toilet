FROM golang:1.20-alpine

WORKDIR /app
COPY ./ ./
RUN make

EXPOSE 8082 8083
CMD ["make run"]
