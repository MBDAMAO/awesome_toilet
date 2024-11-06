OUTPUT_DIR := "$(CURDIR)/bin"
BINARY_NAME := awesome_toilet_backend

# 默认目标
all: clean build run

# 构建目标：仅针对 linux/amd64 平台
build:
	@echo "Building for linux/amd64..."
	@mkdir -p $(OUTPUT_DIR)/$(BINARY_NAME)-linux-amd64
	GOOS=linux GOARCH=amd64 go build -o $(OUTPUT_DIR)/$(BINARY_NAME)-linux-amd64/$(BINARY_NAME)
	@echo "Build successful! Output at: $(OUTPUT_DIR)/$(BINARY_NAME)-linux-amd64/$(BINARY_NAME)"

# 清理构建目录
clean:
	@echo "Cleaning up build artifacts..."
	@rm -rf $(OUTPUT_DIR)
	@echo "Clean complete."

# 运行目标：仅在构建后运行二进制文件
run:
	@echo "Running $(BINARY_NAME)..."
	$(OUTPUT_DIR)/$(BINARY_NAME)-linux-amd64/$(BINARY_NAME)
