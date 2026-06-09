# Enterprise AI Assistant: Spring Boot, LangChain4j, & PgVector

This project is an intelligent enterprise backend assistant built with **Spring Boot**, **LangChain4j**, and **PostgreSQL (PgVector)**. It combines **Retrieval-Augmented Generation (RAG)** for searching internal HR policies with **Agentic AI Tools** capable of performing live relational database lookups (via Spring Data JPA) and mathematical computations.

---

## Key Features

* **Semantic Search Engine (RAG):** Automatically chunks, vectorizes, and stores document data using Google's `gemini-embedding-001` model into a PostgreSQL `pgvector` store.
* **Agentic Tool Execution:** Dynamically switches between automated semantic context injection and explicit on-demand function execution (e.g., database lookup, mathematical utility).
* **Unified Data Layer:** Shares a single high-performance connection pool (HikariCP) between Spring Data JPA relational queries and LangChain4j's vector operations.
* **Conversational Memory:** Preserves multi-turn conversation state using an automated message window provider.

---

## Tech Stack

* **Framework:** Spring Boot 3.x
* **AI Framework:** LangChain4j (Google AI Gemini Integration)
* **LLM & Embeddings:** Gemini 1.5 Flash & Gemini Embedding 001
* **Database:** PostgreSQL 16+ with `pgvector` extension
* **ORM:** Spring Data JPA (Hibernate)

---

## Project Structure

Ensure your directory matches this structure so Spring's component scanner resolves all beans correctly:

```text
src/main/java/com/agent/
│
├── AgentApplication.java         # Main Spring Boot Entrypoint
│
├── config/
│   ├── AgentConfig.java          # Core LangChain4j Beans (LLM, Memory, Retriever)
│   └── VectorStoreConfig.java    # Shared DataSource Vector Store Config
│
├── model/
│   └── Employee.java             # JPA Relational Entity
│
├── repository/
│   └── EmployeeRepository.java   # JPA Data Access Repository
│
└── tools/
    ├── CalculatorTool.java       # LLM Tool for Math Operations
    └── EmployeeDatabaseTool.java # LLM Tool for Live DB Lookups
```

# ==========================================
# 1. APPLICATION & AI CREDS
# ==========================================
spring.application.name=agent-assistant

langchain4j.google-ai.gemini.chat-model.api-key=YOUR_GEMINI_API_KEY
langchain4j.google-ai.gemini.chat-model.model-name=gemini-1.5-flash

# ==========================================
# 2. CENTRALIZED DATABASE CONNECTION (JPA + VECTOR)
# ==========================================
spring.datasource.url=jdbc:postgresql://localhost:5432/hr_database
spring.datasource.username=hr_admin
spring.datasource.password=hr_password
spring.datasource.driver-class-name=org.postgresql.Driver

# Connection Pool Settings
spring.datasource.hikari.maximum-pool-size=10
spring.datasource.hikari.minimum-idle=2
spring.datasource.hikari.idle-timeout=30000

# ==========================================
# 3. JPA / HIBERNATE ORM SETTINGS
# ==========================================
spring.jpa.database-platform=org.hibernate.dialect.PostgreSQLDialect
# 'update' automatically builds/modifies tables based on @Entity classes
spring.jpa.hibernate.ddl-auto=update
# Console SQL Logging (Excellent for debugging AI actions)
spring.jpa.show-sql=true
spring.jpa.properties.hibernate.format_sql=true


docker run -d \
  --name pgvector-db \
  -e POSTGRES_USER=hr_admin \
  -e POSTGRES_PASSWORD=hr_password \
  -e POSTGRES_DB=hr_database \
  -p 5433:5432 \
  pgvector/pgvector:pg16
