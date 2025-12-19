package services

import (
	"context"
	"fmt"
	"log"
	configs "tailfile/configs"
	pb "tailfile/tailbeatpd/tail"
	"time"

	"google.golang.org/grpc"
	"google.golang.org/grpc/credentials/insecure"
)

func NewGrpcClient(cfg *configs.Config) pb.LogCollectorClient {
	fmt.Println("Connect to server...")
	target := fmt.Sprintf("%s:%d", cfg.Grpc.Host, cfg.Grpc.Port)

	conn, err := grpc.NewClient(target, grpc.WithTransportCredentials(insecure.NewCredentials()))

	if err != nil {
		log.Fatalf("Did not connect: %v", err)
	} else {
		fmt.Println("Connect success")
	}

	return pb.NewLogCollectorClient(conn)

}

func pushLog(cfg *configs.Config, lines []string, client pb.LogCollectorClient) {
	logs := make([]*pb.LogEntry, 0, len(lines))

	fmt.Println("Prepare to push: ")

	nameService := cfg.Service.Name

	for _, l := range lines {
		logs = append(logs, &pb.LogEntry{
			ServiceName: nameService,
			Level:       "",
			Message:     l,
			Timestamp:   time.Now().UnixMilli(),
			Metadata:    map[string]string{"host": "localhost"},
		})
	}

	batch := &pb.LogBatch{Logs: logs}

	ctx, cancel := context.WithTimeout(context.Background(), 3*time.Second)
	defer cancel()

	resp, err := client.PushLogBatch(ctx, batch)
	if err != nil {
		fmt.Println("Cannot push log")
		return
	}

	fmt.Print("Push success: ", resp.Success, resp.Messsage)

}

func BatchAndSend(in <-chan string, batchSize int, flush time.Duration, client pb.LogCollectorClient, cfg *configs.Config) {
	batch := make([]string, 0, batchSize)
	timer := time.NewTimer(flush)
	for {
		select {
		case line := <-in:
			batch = append(batch, line)
			if len(batch) >= batchSize {
				pushLog(cfg, batch, client)
				batch = batch[:0]
				resetTimer(timer, flush)
			}
		case <-timer.C:
			if len(batch) > 0 {
				pushLog(cfg, batch, client)
				batch = batch[:0]

			}
			resetTimer(timer, flush)
		}
	}
}

func resetTimer(t *time.Timer, d time.Duration) {
	if t.Stop() {
		select {
		case <-t.C:
		default:
		}
	}

	t.Reset(d)
}
