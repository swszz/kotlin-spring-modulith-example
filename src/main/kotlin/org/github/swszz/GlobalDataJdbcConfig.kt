package org.github.swszz

import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.boot.jdbc.autoconfigure.DataSourceProperties
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Primary
import org.springframework.data.jdbc.core.convert.MappingJdbcConverter
import org.springframework.data.jdbc.core.dialect.JdbcH2Dialect
import org.springframework.data.jdbc.core.mapping.JdbcMappingContext
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcOperations
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate
import org.springframework.jdbc.datasource.DataSourceTransactionManager
import org.springframework.transaction.TransactionManager
import javax.sql.DataSource

@Configuration
class GlobalDataJdbcConfig {

    @Bean
    @ConfigurationProperties("spring.datasource.core")
    fun coreDataSourceProperties(): DataSourceProperties {
        return DataSourceProperties()
    }

    @Bean
    @Primary
    fun coreDataSource(
        @Qualifier("coreDataSourceProperties") dataSourceProperties: DataSourceProperties
    ): DataSource {
        return dataSourceProperties.initializeDataSourceBuilder().build()
    }

    @Bean
    @Primary
    fun coreNamedParameterJdbcOperations(@Qualifier("coreDataSource") datasource: DataSource): NamedParameterJdbcOperations {
        return NamedParameterJdbcTemplate(datasource)
    }

    @Bean
    @Primary
    fun coreTransactionManager(@Qualifier("coreDataSource") datasource: DataSource): TransactionManager {
        return DataSourceTransactionManager(datasource)
    }

    @Bean
    fun jdbcMappingContext() = JdbcMappingContext()

    @Bean
    fun jdbcDialect() = JdbcH2Dialect.INSTANCE

    @Bean
    fun jdbcConverter(
        jdbcMappingContext: JdbcMappingContext,
    ): MappingJdbcConverter {
        return MappingJdbcConverter(jdbcMappingContext) { _, _ -> emptyList() }
    }
}