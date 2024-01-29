package br.com.fiap.postech.pedidos.config

import org.modelmapper.ModelMapper
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class Configuracao {
    @Bean
    fun obterModelMapper(): ModelMapper {
        return ModelMapper()
    }
}