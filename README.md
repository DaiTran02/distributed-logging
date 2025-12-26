# Distributed Logging System

## Overview

This project implements a distributed logging system designed to collect, process, store, and visualize logs from multiple services at scale. The architecture is inspired by ELK, but customized to provide better control over ingestion, processing, and back-pressure handling.

High-level flow:

* Each application service runs a lightweight log agent called **tailFile** (written in **Go**) to read log files.
* Logs are sent via **gRPC** to a centralized backend service called **processor**.
* The **processor** service (written in **Java Spring Boot**) pushes logs to **Kafka**, consumes them back for processing, filters/enriches the data, and stores the results in **Elasticsearch**.
* The processor also exposes **REST APIs** for a **Next.js** frontend to display dashboards and allow log queries.

---
## Architecture Diagram
graph LR
    subgraph Application_Services
        S1[Service A]
        S2[Service B]
        S3[Service C]
    end

    subgraph Log_Agents
        T1[tailFile (Go)]
        T2[tailFile (Go)]
        T3[tailFile (Go)]
    end

    subgraph Backend
        P[Processor\nSpring Boot]
        K[(Kafka)]
        E[(Elasticsearch)]
    end

    subgraph Frontend
        FE[Next.js Dashboard]
    end

    S1 --> T1
    S2 --> T2
    S3 --> T3

    T1 -->|gRPC| P
    T2 -->|gRPC| P
    T3 -->|gRPC| P

    P -->|Produce| K
    K -->|Consume| P

    P --> E
    FE -->|REST API| P
---

## Components

### 1. tailFile (Go)

`tailFile` is a lightweight log agent deployed alongside each application service.

**Responsibilities:**

* Continuously read log files (similar to `tail -f`).
* Batch log entries for efficiency.
* Send logs to the processor service via gRPC.

**Design principles:**

* Minimal processing logic.
* No local persistence.
* Focused on reliability and low resource usage.

Each service runs its own instance of `tailFile`.

---

### 2. Processor (Java Spring Boot)

The processor is the central log ingestion and processing service.

**Processing pipeline:**

1. Receive raw logs from multiple `tailFile` agents via gRPC.
2. Publish logs to Kafka to decouple ingestion from processing and handle traffic spikes.
3. Consume logs from Kafka.
4. Filter, parse, and enrich log data (e.g. service name, log level, timestamp, trace ID).
5. Store processed logs in Elasticsearch.
6. Expose REST APIs for querying and visualization.

**Why Kafka is used:**

* High throughput and durability.
* Back-pressure handling during peak load.
* Independent scaling of producers and consumers.

---

### 3. Kafka

Kafka acts as an intermediate message broker for log data.

**Benefits:**

* Buffering and retry capability.
* Horizontal scalability via partitions.
* Clear separation between log ingestion and processing.

---

### 4. Elasticsearch

Elasticsearch is used as the primary log storage and search engine.

**Capabilities:**

* Full-text search.
* Time-based queries.
* Filtering by service, log level, and custom fields.
* Backend for dashboards and analytics.

---

### 5. Frontend (Next.js)

The frontend application provides log visualization and querying capabilities.

**Features:**

* System overview dashboards.
* Real-time and historical log views.
* Advanced filtering by service, level, and keywords.

The frontend communicates exclusively with the processor via REST APIs.

---

## Data Flow Summary

1. Application services write logs to local files.
2. `tailFile` agents read log files in real time.
3. Logs are sent to the processor using gRPC.
4. The processor publishes logs to Kafka.
5. Logs are consumed back from Kafka for processing.
6. Processed logs are stored in Elasticsearch.
7. The frontend queries logs via REST APIs and displays them to users.

---

## Technology Stack

* **Go**: Log agent (`tailFile`)
* **gRPC**: Log transmission protocol
* **Java Spring Boot**: Processor service
* **Kafka**: Message broker
* **Elasticsearch**: Log storage and search
* **Next.js**: Frontend dashboard

---

## Notes

* This architecture is suitable for systems with a large number of services and high log volume.
* The system can be extended with:

  * Multiple Kafka partitions and consumer groups
  * Horizontal scaling of the processor service
  * Elasticsearch Index Lifecycle Management (ILM)
