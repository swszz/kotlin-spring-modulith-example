# Kotlin Spring Modulith Example

A sample project demonstrating **modular monolith architecture** using Spring Modulith with Kotlin, showcasing how to build well-structured, maintainable applications with enforced module boundaries.

## Overview

This project demonstrates Spring Modulith's ability to enforce architectural boundaries within a monolithic application. Unlike traditional monoliths where any code can call any other code, Spring Modulith allows you to define clear module boundaries and dependencies, making your codebase more maintainable and easier to understand.

### Key Features

- **Enforced Module Boundaries**: Spring Modulith verifies module dependencies at test time
- **Module-Specific Transaction Management**: Each module has its own transaction manager for isolation
- **Cross-Cutting Concerns with AOP**: Authentication aspect demonstrates module interaction patterns
- **Automatic Documentation Generation**: Generates module diagrams and documentation
- **Kotlin-First**: Built with Kotlin 2.2.21 and leverages Kotlin features
- **Modern Spring**: Uses Spring Boot 4.0.0-M2 with virtual threads enabled

## Architecture

### Module Structure

The application consists of four modules with explicit dependencies:

```
core (shared utilities)
  ↑
  ├── authentication (AOP aspects, annotations)
  ├── order (order management)
  │     ↑
  └── inventory (stock management)
        ↑ (depends on both core and authentication)
```

Each module is defined by:
1. A package under `org.github.swszz.<module-name>`
2. A `@ApplicationModule` class declaring allowed dependencies
3. Module-specific services, repositories, and controllers

### Module Descriptions

- **core**: Base module providing shared utilities (e.g., `Logger`)
- **authentication**: Provides `@Authentication` annotation and AOP aspect for cross-cutting authentication concerns
- **order**: Order management with its own transaction manager (`orderTransactionManager`)
- **inventory**: Inventory management with authentication integration (`inventoryTransactionManager`)

### Database Design

- Single H2 in-memory database (`coredb`)
- Module-specific schema files: `schema-order.sql` and `schema-inventory.sql`
- Shared database with logical separation through transaction managers

## Prerequisites

### Java

- **Java 24** (configured via Gradle toolchain)

### macOS Setup

Install required dependencies using Homebrew:

```bash
brew install libtool
brew link libtool
brew install graphviz
brew link --overwrite graphviz
```

Note: Graphviz is required for generating module documentation diagrams.

## Getting Started

### Build the Project

```bash
./gradlew build
```

### Run Tests

```bash
# Run all tests
./gradlew test

# Run a specific test
./gradlew test --tests "org.github.swszz.ApplicationTests"
```

### Run the Application

```bash
./gradlew bootRun
```

The application will start on `http://localhost:8080`

### Access H2 Console

Visit `http://localhost:8080/h2-console` with the following credentials:

- **JDBC URL**: `jdbc:h2:mem:coredb`
- **Username**: `sa`
- **Password**: (leave empty)

## Important Tests

The `ApplicationTests` class contains critical architectural tests:

### Module Structure Verification

```kotlin
@Test
fun verifyModularStructure() {
    modules.verify()
}
```

This test **fails** if:
- A module accesses another module not listed in its `allowedDependencies`
- Circular dependencies are introduced
- Module boundaries are violated

### Documentation Generation

```kotlin
@Test
fun writeDocumentationSnippets() {
    Documenter(modules).writeDocumentation()
}
```

Generates module diagrams and documentation in the build output.

## Development Guidelines

### Adding a New Module

1. Create a package: `org.github.swszz.<module-name>`
2. Add a module configuration class:

```kotlin
@ApplicationModule(allowedDependencies = ["core"])
class MyNewModule
```

3. Create schema file if needed: `src/main/resources/schema-mynew.sql`
4. Update `application.yaml` to include the schema location
5. Run tests to verify module structure: `./gradlew test`

### Modifying Module Dependencies

1. Update `allowedDependencies` in the module's `@ApplicationModule` annotation
2. Run `verifyModularStructure()` test to ensure compliance
3. Avoid direct references to classes in non-allowed modules

### Transaction Management

When creating services, use the appropriate transaction manager:

```kotlin
@Service
class MyService(private val repository: MyRepository) {
    @Transactional("myModuleTransactionManager")
    fun doSomething() { ... }
}
```

## Technology Stack

- **Kotlin**: 2.2.21
- **Spring Boot**: 4.0.0-M2
- **Spring Modulith**: 2.0.0-RC1
- **Database**: H2 (in-memory)
- **Build Tool**: Gradle with Kotlin DSL
- **Testing**: JUnit 5, AssertJ, Spring Boot Test

## Project Structure

```
src/main/kotlin/org/github/swszz/
├── SpringBootTemplateApplication.kt
├── core/
│   ├── CoreModule.kt
│   └── Logger.kt
├── authentication/
│   ├── AuthenticationModule.kt
│   ├── Authentication.kt
│   └── AuthenticationAspect.kt
├── order/
│   ├── OrderModule.kt
│   ├── Order.kt
│   ├── OrderRepository.kt
│   ├── OrderService.kt
│   └── OrderController.kt
└── inventory/
    ├── InventoryModule.kt
    ├── Inventory.kt
    ├── InventoryRepository.kt
    ├── InventoryService.kt
    └── InventoryController.kt
```

## Configuration

Dependencies are managed using Gradle version catalogs (`gradle/libs.versions.toml`).

Key configuration in `application.yaml`:
- Virtual threads enabled: `spring.threads.virtual.enabled: true`
- H2 console enabled at `/h2-console`
- Spring Modulith debug logging enabled

## Resources

- [Spring Modulith Documentation](https://docs.spring.io/spring-modulith/reference/)
- [Spring Modulith GitHub](https://github.com/spring-projects/spring-modulith)
- [Kotlin Spring Plugin](https://kotlinlang.org/docs/all-open-plugin.html#spring-support)
