# CLAUDE.md

This file provides guidance to Claude Code (claude.ai/code) when working with code in this repository.

## Project Overview

This is a Kotlin/Spring Boot multi-module video management system with three modules:
- **api** - REST API for HTTP clients
- **console** - CLI application for command-line operations
- **infrastructure** - Shared domain logic, database access, and external integrations (Samba, Redis, shell execution)

Both api and console depend on infrastructure. The project uses MyBatis for database access and manages TV program recordings with their associated video files.

## Build Commands

```bash
# Build entire project
./gradlew build

# Build without tests
./gradlew build -x test

# Build specific module
./gradlew api:build
./gradlew console:build
./gradlew infrastructure:build

# Create executable JARs
./gradlew api:bootJar        # Creates api/build/libs/api-0.0.1-SNAPSHOT.jar
./gradlew console:bootJar    # Creates console/build/libs/console-0.0.1-SNAPSHOT.jar

# Clean build artifacts
./gradlew clean
```

## Running Applications

```bash
# Run API server (default port 8080)
./gradlew api:bootRun

# Run console application
./gradlew console:bootRun

# Run console with arguments
./gradlew console:bootRun --args='search --name "program name"'

# Run from built JAR
java -jar api/build/libs/api-0.0.1-SNAPSHOT.jar
java -jar console/build/libs/console-0.0.1-SNAPSHOT.jar search --name "program name"
```

## Testing

```bash
# Run all tests
./gradlew test

# Run tests for specific module
./gradlew api:test
./gradlew infrastructure:test

# Run single test class
./gradlew infrastructure:test --tests "ProgramMapperTest"

# Run single test method
./gradlew infrastructure:test --tests "ProgramMapperTest.selectById"

# Run with verbose output
./gradlew test --info
```

**Note:** Infrastructure tests use TestContainers with MariaDB, so Docker must be running.

## Code Quality

```bash
# Check code style
./gradlew ktlintCheck

# Auto-format code
./gradlew ktlintFormat

# Run ktlint on specific module
./gradlew api:ktlintCheck
./gradlew api:ktlintFormat
```

## Architecture

### Module Dependencies
```
api ──────┐
          ├──> infrastructure ──> MyBatis, Samba, Redis
console ──┘
```

### Layer Architecture
The infrastructure module follows a layered pattern:

**Command Layer** (`infrastructure/command/`)
- Entry points for business operations
- `ProgramCommand` - Program CRUD and file operations
- `ExecutedFileCommand`, `CreatedFileCommand`, `SplittedFileCommand` - File management
- Commands orchestrate multiple database/component calls

**Database Layer** (`infrastructure/database/`)
- **Mappers** - MyBatis interfaces with inline SQL annotations
- **DTOs** - Database row representations
- **Converters** - Transform DTOs to domain structs

**Component Layer** (`infrastructure/component/`)
- Utility and integration services
- `SambaClient` - Network file share access (CIFS/SMB)
- `ShellComponent` - Windows command execution
- `NasComponent` - NAS mount operations
- Environment detection, file naming, etc.

### Domain Model

Video files flow through a processing pipeline tracked by status enums:

```
program (TV show metadata)
  └─> executed_file (original recording)
       └─> splitted_file (video segments)
            └─> created_file (output files)
```

Each entity has a status: REGISTERED → COMPLETED (or ERROR)

### External Integrations

**Samba/SMB File Shares**
- Two NAS configurations: video-store-nas and original-store-nas
- Configured via environment variables (VIDEO_STORE_NAS_URL, etc.)
- Used by `SambaClient` for network file operations

**Database**
- MariaDB with HikariCP connection pool (5-10 connections)
- Configured via DATABASE_CONNECTION, DATABASE_USER_NAME, DATABASE_PASSWORD
- Auto-commit disabled for transaction control

**Shell Execution**
- Windows-specific command execution via `ShellComponent`
- Detected by `EnvironmentComponent.isWindows()`

## REST API Endpoints

**Programs**
- `GET /api/v1/programs?name={query}&limit={limit}&offset={offset}` - Search programs
- `GET /api/v1/programs/{id}` - Get program details
- `DELETE /api/v1/programs/{id}` - Delete program

**Video Streaming**
- `GET /api/v1/video/{id}/stream` - Stream MP4 file

## Console CLI Commands

```bash
# Search programs
console search --name "program name"

# Get program details
console get --id 123

# Delete program
console delete --id 123

# Modify program
console modify --id 123 --status COMPLETED
```

## Configuration

Spring profiles are layered via `spring.profiles.include`.

**Environment Variables:**
- `DATABASE_CONNECTION` - JDBC URL (default: jdbc:mariadb://localhost:3306/test)
- `DATABASE_USER_NAME` / `DATABASE_PASSWORD`
- `VIDEO_STORE_NAS_URL` - Samba URL for video storage
- `VIDEO_STORE_NAS_USERNAME` / `VIDEO_STORE_NAS_PASSWORD`
- `ORIGINAL_STORE_NAS_URL` - Samba URL for originals
- `ORIGINAL_STORE_NAS_USERNAME` / `ORIGINAL_STORE_NAS_PASSWORD`

Configuration files are located in each module's `src/main/resources/`:
- `application.yml` - Module-specific base config
- `application-api.yml` - API server settings
- `application-console.yml` - Console app settings
- `application-infrastructure.yml` - Database and integration config

## Testing Patterns

**Infrastructure Tests** - Use `@MybatisTest` with `@Testcontainers`:
```kotlin
@MybatisTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Testcontainers
class ProgramMapperTest {
    @Container
    val mariadb = MariaDBContainer<Nothing>("mariadb:11.8")

    @DynamicPropertySource
    fun properties(registry: DynamicPropertyRegistry) {
        registry.add("spring.datasource.url", mariadb::getJdbcUrl)
    }

    // Tests use direct SQL setup via DataSource
}
```

**API Tests** - Use `@SpringBootTest` with `@AutoConfigureMockMvc`:
```kotlin
@SpringBootTest
@AutoConfigureMockMvc
class ProgramControllerTest {
    @MockkBean
    lateinit var programCommand: ProgramCommand

    @Autowired
    lateinit var mockMvc: MockMvc

    // Tests use MockMvc for HTTP testing
}
```

## Docker Deployment

The Dockerfile builds and runs the API module:
```bash
docker build -t ts-videos-manager .
docker run -p 8080:8080 \
  -e DATABASE_CONNECTION=jdbc:mariadb://host:3306/db \
  -e DATABASE_USER_NAME=user \
  -e DATABASE_PASSWORD=pass \
  ts-videos-manager
```

## Key Technologies

- **Kotlin 1.8.20** with Spring compiler plugin
- **Spring Boot 2.6.6** (javax, not jakarta)
- **Java 17** target
- **MyBatis 2.2.2** with inline SQL annotations
- **Gradle 8.12** (compatible version for Spring Boot 2.6.6)
- **jcifs-ng 2.1.8** for Samba/SMB
- **kotlinx-cli 0.3.5** for CLI parsing
- **TestContainers** for integration testing
- **SpringMockk** for Kotlin-friendly mocking

## Important Notes

- Gradle 9.x is not compatible with this project's Spring Boot 2.6.6 version. Stay on Gradle 8.x.
- The project uses `javax` packages (not `jakarta`), so it's not compatible with Spring Boot 3.x yet.
- Auto-commit is disabled in the datasource configuration - ensure proper transaction management.
- Shell execution features are Windows-specific and will be skipped on other platforms.
- TestContainers requires Docker to be running for infrastructure tests.
