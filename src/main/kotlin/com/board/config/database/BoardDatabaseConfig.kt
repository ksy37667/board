package com.board.config.database

import com.zaxxer.hikari.HikariDataSource
import org.hibernate.cfg.AvailableSettings
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties
import org.springframework.boot.autoconfigure.orm.jpa.JpaProperties
import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.boot.orm.jpa.EntityManagerFactoryBuilder
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Primary
import org.springframework.data.jpa.repository.config.EnableJpaRepositories
import org.springframework.jdbc.datasource.LazyConnectionDataSourceProxy
import org.springframework.orm.hibernate5.SpringBeanContainer
import org.springframework.orm.jpa.JpaTransactionManager
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean
import org.springframework.transaction.PlatformTransactionManager
import org.springframework.transaction.annotation.EnableTransactionManagement
import javax.persistence.EntityManagerFactory
import javax.sql.DataSource


@Configuration
@EnableTransactionManagement
@EnableJpaRepositories(
    basePackages = ["com.board.repository"],
    entityManagerFactoryRef = "boardEntityManager",
    transactionManagerRef = "boardTransactionManager"
)
class BoardDatabaseConfig {

    @Bean
    @Primary
    @ConfigurationProperties(prefix = "board.master.datasource")
    fun boardMasterDataSourceProperties(): DataSourceProperties {
        return DataSourceProperties()
    }

    @Bean
    @Primary
    @ConfigurationProperties(prefix = "board.master.datasource.hikari")
    fun boardMasterHikariDataSource(@Qualifier("boardMasterDataSourceProperties") masterProperty: DataSourceProperties): HikariDataSource {
        return masterProperty.initializeDataSourceBuilder().type(HikariDataSource::class.java).build()
    }

    @Bean
    fun boardRoutingDataSource(
        @Qualifier("boardMasterHikariDataSource") masterDataSource: DataSource
    ): DataSource {
        val dataSourceMap: Map<Any, Any> = hashMapOf(
            DBType.MASTER to masterDataSource
        )

        return LazyConnectionDataSourceProxy(MasterRoutingDataSource().apply {
            this.setDefaultTargetDataSource(masterDataSource)
            this.setTargetDataSources(dataSourceMap)
            this.afterPropertiesSet()
        })
    }

    @Bean
    @Primary
    @ConfigurationProperties(prefix = "board.jpa")
    fun boardJpaProperties(): JpaProperties {
        return JpaProperties()
    }

    @Bean
    @Primary
    fun boardEntityManager(
        builder: EntityManagerFactoryBuilder,
        beanFactory: ConfigurableListableBeanFactory,
        @Qualifier("boardRoutingDataSource") boardRoutingDataSource: DataSource
    ): LocalContainerEntityManagerFactoryBean {
        return builder
            .dataSource(boardRoutingDataSource)
            .properties(mapOf(
                AvailableSettings.BEAN_CONTAINER to SpringBeanContainer(beanFactory)
            ))
            .packages(
                "com.board.entity"
            )
            .build()
    }

    @Bean
    @Primary
    fun boardTransactionManager(@Qualifier("boardEntityManager") boardEntityManager: EntityManagerFactory): PlatformTransactionManager {
        return JpaTransactionManager(boardEntityManager)
    }
}