package configs

import (
	"os"

	"gopkg.in/yaml.v3"
)

type Config struct {
	Service struct {
		Name string `yaml:"name"`
	} `yaml:"service"`
	Grpc struct {
		Host string `yaml:"host"`
		Port int    `yaml:"port"`
	} `yaml:"grpc"`

	Input struct {
		Path  string `yaml:"path"`
		State string `yaml:"state"`
	} `yaml:"input"`
}

func LoadConfig(path string) (*Config, error) {
	data, err := os.ReadFile(path)
	if err != nil {
		return nil, err
	}

	var cfg Config

	if err := yaml.Unmarshal(data, &cfg); err != nil {
		return nil, err
	}

	return &cfg, nil

}
