package main

import (
	"backend/controller"
	"fmt"
	"net/http"

	"github.com/gin-gonic/gin"
	"github.com/google/go-cmp/cmp"
)

func main() {
	diff := cmp.Diff("aa bb", "aa cc")
	controller.Hello()
	fmt.Printf("%v", diff)
	// 创建一个 Gin 路由器
	router := gin.Default()

	// 设置一个简单的路由
	router.GET("/", func(c *gin.Context) {
		c.JSON(http.StatusOK, gin.H{
			"message": "Welcome to the Gin Web Project!",
		})
	})

	// 启动服务器
	router.Run(":8083") // 默认监听在 8083 端口
}
