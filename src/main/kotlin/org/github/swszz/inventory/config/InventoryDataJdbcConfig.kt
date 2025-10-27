package org.github.swszz.inventory.config

import org.springframework.beans.factory.annotation.Qualifier
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
    basePackages = ["org.github.swszz.inventory"],
    jdbcOperationsRef = "inventoryNamedParameterJdbcOperations",
    transactionManagerRef = "inventoryTransactionManager",
)
class InventoryDataJdbcConfig {

    @Bean
    fun inventoryNamedParameterJdbcOperations(@Qualifier("inventoryDataSource") datasource: DataSource): NamedParameterJdbcOperations {
        return NamedParameterJdbcTemplate(datasource)
    }

    @Bean
    fun inventoryTransactionManager(@Qualifier("inventoryDataSource") datasource: DataSource): TransactionManager {
        return DataSourceTransactionManager(datasource)
    }
}