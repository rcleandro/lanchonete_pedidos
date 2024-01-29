package br.com.fiap.postech.pedidos.dto

import lombok.AllArgsConstructor
import lombok.Getter
import lombok.NoArgsConstructor
import lombok.Setter

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
class ItemDoPedidoDto {
    var id: Long? = null
    var quantidade: Int? = null
    var descricao: String? = null
}
