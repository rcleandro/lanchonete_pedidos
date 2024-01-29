package br.com.fiap.postech.pedidos.dto

import br.com.fiap.postech.pedidos.module.Status
import lombok.AllArgsConstructor
import lombok.Getter
import lombok.NoArgsConstructor
import lombok.Setter
import java.time.LocalDateTime

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
class PedidoDto {
    var id: Long? = null
    var dataHora: LocalDateTime = LocalDateTime.now()
    var status: Status = Status.REALIZADO
    var itens: List<ItemDoPedidoDto> = ArrayList()
}