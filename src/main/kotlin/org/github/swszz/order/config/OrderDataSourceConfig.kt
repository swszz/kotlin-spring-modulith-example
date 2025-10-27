package org.github.swszz.order.config

import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.boot.jdbc.autoconfigure.DataSourceProperties
import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Primary
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
    basePackages = ["org.github.swszz.order"],
    jdbcOperationsRef = "orderJdbcOperations",
    transactionManagerRef = "orderTransactionManager"
)
class OrderDataSourceConfig {

    @Bean
    @ConfigurationProperties("spring.datasource.order")
    fun orderDataSourceProperties(): DataSourceProperties {
        return DataSourceProperties()
    }

    @Bean
    @Primary
    fun orderDataSource(): DataSource {
        return orderDataSourceProperties()
            .initializeDataSourceBuilder()
            .build()
    }

    @Bean
    fun orderDataSourceInitializer(@Qualifier("orderDataSource") dataSource: DataSource): DataSourceInitializer {
        val initializer = DataSourceInitializer()
        initializer.setDataSource(dataSource)
        initializer.setDatabasePopulator(
            ResourceDatabasePopulator().apply {
                addScript(ClassPathResource("schema-order.sql"))
            }
        )
        return initializer
    }

    @Bean
    fun orderJdbcOperations(@Qualifier("orderDataSource") dataSource: DataSource): NamedParameterJdbcOperations {
        return NamedParameterJdbcTemplate(dataSource)
    }

    @Bean
    fun orderTransactionManager(@Qualifier("orderDataSource") dataSource: DataSource): PlatformTransactionManager {
        return DataSourceTransactionManager(dataSource)
    }

    @Bean
    @Primary
    fun orderJdbcMappingContext(): JdbcMappingContext {
        return JdbcMappingContext()
    }

    @Bean
    @Primary
    fun orderJdbcConverter(
        @Qualifier("orderJdbcMappingContext") mappingContext: JdbcMappingContext,
        @Qualifier("orderJdbcOperations") operations: NamedParameterJdbcOperations
    ): JdbcConverter {
        return DefaultJdbcTypeFactory(operations.jdbcOperations)
            .let { typeFactory ->
                MappingJdbcConverter(mappingContext, RelationResolver { _, _ -> emptyList<Any>() })
            }
    }

    @Bean
    @Primary
    fun orderDialect(): Dialect {
        return H2Dialect.INSTANCE
    }
}
