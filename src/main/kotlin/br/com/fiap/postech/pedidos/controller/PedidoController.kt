package br.com.fiap.postech.pedidos.controller

import br.com.fiap.postech.pedidos.dto.PedidoDto
import br.com.fiap.postech.pedidos.dto.StatusDto
import br.com.fiap.postech.pedidos.service.PedidoService
import jakarta.validation.Valid
import jakarta.validation.constraints.NotNull
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Value
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import org.springframework.web.util.UriComponentsBuilder

@RestController
@RequestMapping("/pedidos")
class PedidoController {
    @Autowired
    private val service: PedidoService? = null

    @GetMapping
    fun listarTodos(): MutableList<PedidoDto>? {
        return service?.obterTodos()
    }

    @GetMapping("/{id}")
    fun listarPorId(@PathVariable @NotNull id: Long?): ResponseEntity<PedidoDto> {
        val dto: PedidoDto? = service?.obterPorId(id)

        return ResponseEntity.ok(dto)
    }

    @GetMapping("/porta")
    fun retornaPorta(@Value("\${local.server.port}") porta: String?): String {
        return String.format("Requisição respondida pela instância executando na porta %s", porta)
    }

    @PostMapping
    fun realizaPedido(
        @RequestBody @Valid dto: PedidoDto?,
        uriBuilder: UriComponentsBuilder
    ): ResponseEntity<PedidoDto> {
        val pedidoRealizado: PedidoDto? = service?.criarPedido(dto)

        val endereco = uriBuilder.path("/pedidos/{id}").buildAndExpand(pedidoRealizado?.id).toUri()

        return ResponseEntity.created(endereco).body(pedidoRealizado)
    }

    @PutMapping("/{id}/status")
    fun atualizaStatus(@PathVariable id: Long?, @RequestBody status: StatusDto?): ResponseEntity<PedidoDto> {
        val dto: PedidoDto? = status?.let { service?.atualizaStatus(id, it) }

        return ResponseEntity.ok(dto)
    }


    @PutMapping("/{id}/pago")
    fun aprovaPagamento(@PathVariable @NotNull id: Long?): ResponseEntity<Void> {
        service?.aprovaPagamentoPedido(id)

        return ResponseEntity.ok().build()
    }
}
