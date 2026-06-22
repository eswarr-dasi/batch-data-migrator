# ⚙️ batch-data-migrator

> **Project 3 of 10** · [30-Day Dev Roadmap](https://github.com/eswarr-dasi/dev-project-roadmap) · Jun 27, 2026
>
> A **Spring Batch** data migration pipeline that reads records from legacy CSV files and a source database,
> transforms them, and loads them into a target MySQL database — with retry logic, skip policies, and
> chunk-based processing for production-scale data volumes.
>
> ---
>
> ## ✨ Features
>
> - **CSV → MySQL migration** — Read flat files, validate, transform, and persist
> - - **DB → DB migration** — Migrate from legacy relational source to new normalized schema
>   - - **Chunk-based processing** — Configurable chunk size for memory-efficient large datasets
>     - - **Skip & retry policies** — Automatically skip malformed records, retry transient failures
>       - - **Job parameters** — Pass source file path, target schema, chunk size at runtime
>         - - **Progress tracking** — Spring Batch job/step metadata persisted to DB (read/write/skip counts)
>           - - **Idempotent re-runs** — Completed jobs can be re-run with new parameters safely
>             - - **Listener hooks** — Job and step listeners for logging and alerting
>              
>               - ---
>
> ## 🛠️ Tech Stack
>
> | Layer | Technology |
> |-------|------------|
> | Language | Java 17 |
> | Framework | Spring Boot 3.x + Spring Batch 5 |
> | Source DB | H2 (dev) / PostgreSQL (prod) |
> | Target DB | MySQL 8 |
> | Build | Maven |
> | Testing | JUnit 5, Spring Batch Test |
>
> ---
>
> ## 🏗️ Pipeline Architecture
>
> ```
> [ItemReader]  →  [ItemProcessor]  →  [ItemWriter]
>     │                  │                  │
> CSV / JPA          Validate &          MySQL JPA /
> FlatFile /         Transform           JdbcBatch
> JdbcCursor         (map to DTO)        Insert
> ```
>
> ### Jobs
>
> | Job | Description |
> |-----|-------------|
> | `csvMigrationJob` | Reads CSV, validates rows, writes to MySQL |
> | `dbMigrationJob` | Reads from legacy DB via cursor, writes to new schema |
>
> ### Step flow
> ```
> csvMigrationJob
>   └── step1: readCsv → validateAndTransform → writeToMySQL
>        ├── SkipPolicy: skip on DataIntegrityViolationException (max 100)
>        └── RetryPolicy: retry on TransientDataAccessException (max 3)
> ```
>
> ---
>
> ## 🚀 Getting Started
>
> ### Prerequisites
> - Java 17+
> - - MySQL 8 running locally
>   - - Maven 3.8+
>    
>     - ### Run a CSV migration job
>     - ```bash
>       mvn spring-boot:run \
>         -Dspring-boot.run.arguments="\
>           --job.name=csvMigrationJob \
>           --input.file=src/main/resources/data/expenses.csv \
>           --chunk.size=500"
>       ```
>
> ### Check job status
> Spring Batch stores execution metadata in the configured datasource — query `BATCH_JOB_EXECUTION` for status.
>
> ---
>
> ## 🧪 Testing
>
> ```bash
> mvn test
> ```
>
> - Job integration tests using `@SpringBatchTest` + embedded H2
> - - Processor unit tests with sample input/output records
>   - - Skip/retry policy behaviour tests
>    
>     - ---
>
> ## 🎯 Career Relevance
>
> Directly reflects the **Spring Batch pipeline** I built at Valens Software to migrate critical bank-payments
> data from a legacy system to MySQL at production scale. This pattern (read → transform → write with retry)
> is foundational in any data-heavy fintech/enterprise engineering role.
>
> ---
>
> ## 📅 Part of the 30-Day Dev Challenge
>
> See the full roadmap: [dev-project-roadmap](https://github.com/eswarr-dasi/dev-project-roadmap)
>
> *Built by [Eswarr Dasi](https://github.com/eswarr-dasi) · Jun 2026*
