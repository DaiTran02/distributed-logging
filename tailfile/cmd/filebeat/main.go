package main

import (
	configs "tailfile/configs"
	"tailfile/internal/services"
	"time"
)

func main() {

	cfg, err := configs.LoadConfig("config.yaml")

	if err != nil {
		panic(err)
	}
	client := services.NewGrpcClient(cfg)

	t := services.NewTailer("D:/NGNs/hanoi/tdnv-hanoi/site/logs/spring-boot-logger.log", "state.json")
	t.Start()

	go services.BatchAndSend(t.Out, 30, 2*time.Second, client, cfg)
	select {}
}
