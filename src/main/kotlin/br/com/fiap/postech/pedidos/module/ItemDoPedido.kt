package br.com.fiap.postech.pedidos.module

import jakarta.persistence.*
import jakarta.validation.constraints.NotNull
import jakarta.validation.constraints.Positive
import lombok.AllArgsConstructor
import lombok.Getter
import lombok.NoArgsConstructor
import lombok.Setter

@Entity
@Table(name = "item_do_pedido")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
class ItemDoPedido {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null

    @NotNull
    @Positive
    var quantidade: Int? = null

    var descricao: String? = null

    @ManyToOne(optional = false)
    var pedido: Pedido? = null
}
