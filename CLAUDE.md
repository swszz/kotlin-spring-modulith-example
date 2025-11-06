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
- `core`: Base module with no dependencies (provides shared utilities like `Logger` and `KafkaConfiguration`)
- `authentication`: Independent module with no dependencies (provides `@Authentication` annotation and AOP aspect)
- `event`: OPEN module with no dependencies (provides shared event definitions for inter-module communication)
- `order`: Depends only on `core`
- `inventory`: Depends on `authentication`, `core`, and `event` (publishes `InventoryAccessEvent`)
- `user`: Depends on `event` and `core` (can listen to events)
- `presentation`: Aggregation layer that depends on `inventory`, `order`, `user`, `authentication`, and `core`

### Key Architectural Patterns

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

**Event-Driven Communication:**
The `event` module is declared as OPEN (`type = ApplicationModule.Type.OPEN`), meaning all other modules can access it without declaring it in `allowedDependencies`. This pattern allows for decoupled, event-driven communication between modules. For example:
- `inventory` module publishes `InventoryAccessEvent` when inventory is accessed
- Other modules (like `user`) can listen to these events without direct dependencies on `inventory`

## Development Guidelines

When adding new modules:
1. Create a package under `org.github.swszz.<module-name>`
2. Add a `<ModuleName>Module.kt` class with `@ApplicationModule(allowedDependencies = [...])`
   - Use `type = ApplicationModule.Type.OPEN` for shared modules like `event` that should be accessible to all modules
3. Add corresponding schema file if database tables are needed
4. Update `spring.sql.init.schema-locations` in `application.yaml`
5. Run `./gradlew test` to verify module structure compliance

When modifying module dependencies:
- Always update the `allowedDependencies` parameter in the module's `@ApplicationModule` annotation
- Run `verifyModularStructure()` test to ensure no circular dependencies or violations
- Avoid direct references to classes in modules not listed in `allowedDependencies`
- OPEN modules (like `event`) are automatically accessible to all modules without explicit declaration

When implementing event-driven communication:
- Define event data classes in the `event` module (e.g., `event.inventory.InventoryAccessEvent`)
- Use Spring's `ApplicationEventPublisher` to publish events from any module
- Use `@EventListener` or `@TransactionalEventListener` to handle events in other modules
- Events enable loose coupling between modules without requiring direct dependencies

## Database Access

The H2 console is available at `http://localhost:8080/h2-console` with:
- JDBC URL: `jdbc:h2:mem:coredb`
- Username: `sa`
- Password: (empty)

## API Endpoints

### Presentation Module
- `GET /api/dashboard`: Aggregates data from inventory, order, user, and authentication modules
  - Returns total orders count, total inventory items count, order summaries, and inventory summaries
  - Protected by `@Authentication` aspect
  - Example: `curl http://localhost:8080/api/dashboard`

## Configuration

The project uses Gradle version catalogs (`gradle/libs.versions.toml`) for dependency management. Virtual threads are enabled via `spring.threads.virtual.enabled: true`.
