package org.github.swszz.inventory.config

import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.boot.jdbc.autoconfigure.DataSourceProperties
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import javax.sql.DataSource

@Configuration
class InventoryDataSourceConfig {
    @Bean
    @ConfigurationProperties("spring.datasource.inventory")
    fun inventoryDataSourceProperties(): DataSourceProperties {
        return DataSourceProperties()
    }

    @Bean
    fun inventoryDataSource(
        @Qualifier("inventoryDataSourceProperties") dataSourceProperties: DataSourceProperties
    ): DataSource {
        return dataSourceProperties.initializeDataSourceBuilder().build()
    }
}