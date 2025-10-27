package org.github.swszz.inventory.config

import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.boot.jdbc.autoconfigure.DataSourceProperties
import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.core.io.ClassPathResource
import org.springframework.data.jdbc.core.convert.*
import org.springframework.data.jdbc.core.mapping.JdbcMappingContext
import org.springframework.data.jdbc.repository.config.EnableJdbcRepositories
import org.springframework.data.relational.core.dialect.Dialect
import org.springframework.data.relational.core.dialect.H2Dialect
import org.springframework.data.relational.core.mapping.NamingStrategy
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcOperations
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate
import org.springframework.jdbc.datasource.DataSourceTransactionManager
import org.springframework.jdbc.datasource.init.DataSourceInitializer
import org.springframework.jdbc.datasource.init.ResourceDatabasePopulator
import org.springframework.transaction.PlatformTransactionManager
import javax.sql.DataSource

@Configuration
@EnableJdbcRepositories(
    basePackages = ["org.github.swszz.inventory"],
    jdbcOperationsRef = "inventoryJdbcOperations",
    transactionManagerRef = "inventoryTransactionManager"
)
class InventoryDataSourceConfig {

    @Bean
    @ConfigurationProperties("spring.datasource.inventory")
    fun inventoryDataSourceProperties(): DataSourceProperties {
        return DataSourceProperties()
    }

    @Bean
    fun inventoryDataSource(): DataSource {
        return inventoryDataSourceProperties()
            .initializeDataSourceBuilder()
            .build()
    }

    @Bean
    fun inventoryDataSourceInitializer(@Qualifier("inventoryDataSource") dataSource: DataSource): DataSourceInitializer {
        val initializer = DataSourceInitializer()
        initializer.setDataSource(dataSource)
        initializer.setDatabasePopulator(
            ResourceDatabasePopulator().apply {
                addScript(ClassPathResource("schema-inventory.sql"))
            }
        )
        return initializer
    }

    @Bean
    fun inventoryJdbcOperations(@Qualifier("inventoryDataSource") dataSource: DataSource): NamedParameterJdbcOperations {
        return NamedParameterJdbcTemplate(dataSource)
    }

    @Bean
    fun inventoryTransactionManager(@Qualifier("inventoryDataSource") dataSource: DataSource): PlatformTransactionManager {
        return DataSourceTransactionManager(dataSource)
    }

    @Bean
    fun inventoryJdbcMappingContext(): JdbcMappingContext {
        return JdbcMappingContext()
    }

    @Bean
    fun inventoryJdbcConverter(
        @Qualifier("inventoryJdbcMappingContext") mappingContext: JdbcMappingContext,
        @Qualifier("inventoryJdbcOperations") operations: NamedParameterJdbcOperations
    ): JdbcConverter {
        return DefaultJdbcTypeFactory(operations.jdbcOperations)
            .let { typeFactory ->
                MappingJdbcConverter(mappingContext, RelationResolver { _, _ -> emptyList<Any>() })
            }
    }

    @Bean
    fun inventoryDialect(): Dialect {
        return H2Dialect.INSTANCE
    }
}
