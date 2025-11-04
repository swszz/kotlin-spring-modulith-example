# CLAUDE.md

This file provides guidance to Claude Code (claude.ai/code) when working with code in this repository.

## Project Overview

This is a Kotlin Spring Modulith example project demonstrating modular monolith architecture using Spring Modulith 2.0.0-RC1 with Spring Boot 4.0.0-M2 and Kotlin 2.2.21.

## Build Commands

```bash
# Build the project
./gradlew build

# Run tests
./gradlew test

# Run a specific test class
./gradlew test --tests "org.github.swszz.ApplicationTests"

# Run the application
./gradlew bootRun

# Clean build
./gradlew clean build
```

## Prerequisites

The project requires:
- Java 24
- Graphviz (for Spring Modulith documentation generation): `brew install graphviz`
- libtool (macOS): `brew install libtool`

## Architecture

### Spring Modulith Structure

The application uses Spring Modulith to enforce modular boundaries. Each module is defined by:
1. A package under `org.github.swszz.<module-name>`
2. A `@ApplicationModule` annotated class that declares dependencies via `allowedDependencies`

**Module Dependency Graph:**
- `core`: Base module with no dependencies (provides shared utilities like `Logger`)
- `authentication`: Independent module with no dependencies (provides `@Authentication` annotation and AOP aspect)
- `order`: Depends only on `core`
- `inventory`: Depends on `authentication` and `core`

### Key Architectural Patterns

**Transaction Management:**
Each module uses its own transaction manager:
- Order module: `@Transactional("orderTransactionManager")`
- Inventory module: `@Transactional("inventoryTransactionManager")`

This demonstrates isolation between modules despite using a shared H2 database.

**Database Schema:**
- Single H2 in-memory database (`coredb`)
- Module-specific schema files: `schema-order.sql` and `schema-inventory.sql`
- Both schemas are initialized on startup via `spring.sql.init.schema-locations`

**Module Verification:**
The `ApplicationTests` class provides critical tests:
- `verifyModularStructure()`: Validates module boundaries and dependencies
- `writeDocumentationSnippets()`: Generates module documentation (requires Graphviz)

These tests will fail if module dependencies are violated, ensuring architectural compliance.

**AOP Cross-Cutting Concerns:**
The `authentication` module demonstrates cross-module concerns via AOP. The `@Authentication` annotation can be used by other modules (like `inventory`) to trigger authentication checks without direct coupling.

## Development Guidelines

When adding new modules:
1. Create a package under `org.github.swszz.<module-name>`
2. Add a `<ModuleName>Module.kt` class with `@ApplicationModule(allowedDependencies = [...])`
3. Add corresponding schema file if database tables are needed
4. Update `spring.sql.init.schema-locations` in `application.yaml`
5. Run `./gradlew test` to verify module structure compliance

When modifying module dependencies:
- Always update the `allowedDependencies` parameter in the module's `@ApplicationModule` annotation
- Run `verifyModularStructure()` test to ensure no circular dependencies or violations
- Avoid direct references to classes in modules not listed in `allowedDependencies`

## Database Access

The H2 console is available at `http://localhost:8080/h2-console` with:
- JDBC URL: `jdbc:h2:mem:coredb`
- Username: `sa`
- Password: (empty)

## Configuration

The project uses Gradle version catalogs (`gradle/libs.versions.toml`) for dependency management. Virtual threads are enabled via `spring.threads.virtual.enabled: true`.
