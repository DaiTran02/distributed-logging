package services

import (
	"bufio"
	"errors"
	"fmt"
	"io"
	"os"
	"time"
)

type Tailer struct {
	Path      string
	Offset    int64
	Out       chan string
	Stop      chan struct{}
	StateFile string
}

func NewTailer(path, stateFile string) *Tailer {
	return &Tailer{
		Path:      path,
		StateFile: stateFile,
		Out:       make(chan string, 100),
		Stop:      make(chan struct{}),
	}
}

func (t *Tailer) Start() error {
	fmt.Println("Read file")
	if err := t.loadState(); err != nil {
		return err
	}

	go t.loop()
	return nil
}

func (t *Tailer) loop() {
	var file *os.File
	var err error

	for {
		file, err = os.Open(t.Path)
		if err != nil {
			time.Sleep(1 * time.Second)
			continue
		}

		fmt.Println("Reading file")

		file.Seek(t.Offset, io.SeekStart)
		reader := bufio.NewReader(file)

		for {
			select {
			case <-t.Stop:
				file.Close()
				return
			default:
			}

			line, err := reader.ReadString('\n')
			if errors.Is(err, io.EOF) {
				if t.isRotated(file) {
					fmt.Println("File rotated")
					file.Close()
					break
				}

				time.Sleep(200 * time.Millisecond)
				continue
			}

			if err != nil {
				file.Close()
				break
			}

			t.Offset += int64(len(line))
			t.saveState()
			t.Out <- line
		}

	}

}

func (t *Tailer) isRotated(f *os.File) bool {
	stat1, _ := f.Stat()
	stat2, err := os.Stat(t.Path)

	if err != nil {
		return false
	}

	return !os.SameFile(stat1, stat2) || stat1.Size() < stat2.Size()
}
