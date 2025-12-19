package services

import (
	"encoding/json"
	"os"
)

type State struct {
	Offset int64 `json:"offset"`
}

func (t *Tailer) saveState() {
	s := State{Offset: t.Offset}
	data, _ := json.Marshal(s)
	os.WriteFile(t.StateFile, data, 0644)
}

func (t *Tailer) loadState() error {
	if _, err := os.Stat(t.StateFile); err != nil {
		return nil
	}

	data, err := os.ReadFile(t.StateFile)
	if err != nil {
		return err
	}

	var s State

	if err := json.Unmarshal(data, &s); err != nil {
		return err
	}

	t.Offset = s.Offset
	return nil
}
