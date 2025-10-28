package org.github.swszz.order.config

import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.boot.jdbc.autoconfigure.DataSourceProperties
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.data.jdbc.repository.config.EnableJdbcRepositories
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcOperations
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate
import org.springframework.jdbc.datasource.DataSourceTransactionManager
import org.springframework.transaction.TransactionManager
import javax.sql.DataSource

@Configuration
@EnableJdbcRepositories(
    basePackages = ["org.github.swszz.order"],
    jdbcOperationsRef = "orderNamedParameterJdbcOperations",
    transactionManagerRef = "orderTransactionManager",
)
class OrderJdbcConfiguration {

    @Bean
    @ConfigurationProperties("spring.datasource.order")
    fun orderDataSourceProperties(): DataSourceProperties {
        return DataSourceProperties()
    }

    @Bean
    fun orderDataSource(
        @Qualifier("orderDataSourceProperties") dataSourceProperties: DataSourceProperties
    ): DataSource {
        return dataSourceProperties.initializeDataSourceBuilder().build()
    }

    @Bean
    fun orderNamedParameterJdbcOperations(@Qualifier("orderDataSource") datasource: DataSource): NamedParameterJdbcOperations {
        return NamedParameterJdbcTemplate(datasource)
    }

    @Bean
    fun orderTransactionManager(@Qualifier("orderDataSource") datasource: DataSource): TransactionManager {
        return DataSourceTransactionManager(datasource)
    }
}