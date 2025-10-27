package org.github.swszz.order.config

import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.boot.jdbc.autoconfigure.DataSourceProperties
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import javax.sql.DataSource

@Configuration
class OrderDataSourceConfig {
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
}