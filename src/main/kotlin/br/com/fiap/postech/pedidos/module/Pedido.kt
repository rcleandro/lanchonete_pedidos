package br.com.fiap.postech.pedidos.module

import jakarta.persistence.*
import jakarta.validation.constraints.NotNull
import lombok.AllArgsConstructor
import lombok.Getter
import lombok.NoArgsConstructor
import lombok.Setter
import java.time.LocalDateTime

@Entity
@Table(name = "pedidos")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
data class Pedido(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long,

    @NotNull
    var dataHora: LocalDateTime = LocalDateTime.now(),

    @NotNull
    @Enumerated(EnumType.STRING)
    var status: Status = Status.REALIZADO,

    @OneToMany(cascade = [CascadeType.PERSIST], mappedBy = "pedido")
    var itens: List<ItemDoPedido> = ArrayList()
)
