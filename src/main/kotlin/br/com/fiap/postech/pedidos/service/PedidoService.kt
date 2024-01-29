package br.com.fiap.postech.pedidos.service

import br.com.fiap.postech.pedidos.dto.PedidoDto
import br.com.fiap.postech.pedidos.dto.StatusDto
import br.com.fiap.postech.pedidos.module.Pedido
import br.com.fiap.postech.pedidos.module.Status
import br.com.fiap.postech.pedidos.repository.PedidoRepository
import jakarta.persistence.EntityNotFoundException
import lombok.RequiredArgsConstructor
import org.modelmapper.ModelMapper
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import java.util.stream.Collectors

@Service
@RequiredArgsConstructor
class PedidoService {
    @Autowired
    private val repository: PedidoRepository? = null

    @Autowired
    private val modelMapper: ModelMapper? = null


    fun obterTodos(): MutableList<PedidoDto>? {
        return repository?.findAll()?.stream()
            ?.map { p -> modelMapper!!.map(p, PedidoDto::class.java) }
            ?.collect(Collectors.toList())
    }

    fun obterPorId(id: Long?): PedidoDto {
        val pedido: Pedido? = id?.let {
            repository?.findById(it)
                ?.orElseThrow { EntityNotFoundException() }
        }

        return modelMapper!!.map(pedido, PedidoDto::class.java)
    }

    fun criarPedido(dto: PedidoDto?): PedidoDto {
        val pedido: Pedido = modelMapper!!.map(dto, Pedido::class.java)
        repository?.save(pedido)

        return modelMapper.map(pedido, PedidoDto::class.java)
    }

    fun atualizaStatus(id: Long?, dto: StatusDto): PedidoDto {
        val pedido: Pedido = repository?.porIdComItens(id) ?: throw EntityNotFoundException()

        pedido.status = dto.status
        repository.atualizaStatus(dto.status, pedido)
        return modelMapper!!.map(pedido, PedidoDto::class.java)
    }

    fun aprovaPagamentoPedido(id: Long?) {
        val pedido: Pedido = repository?.porIdComItens(id) ?: throw EntityNotFoundException()

        pedido.status = Status.PAGO
        repository.atualizaStatus(Status.PAGO, pedido)
    }
}