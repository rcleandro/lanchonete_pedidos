package br.com.fiap.postech.pedidos.repository

import br.com.fiap.postech.pedidos.module.Pedido
import br.com.fiap.postech.pedidos.module.Status
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Modifying
import org.springframework.data.jpa.repository.Query
import org.springframework.transaction.annotation.Transactional

interface PedidoRepository : JpaRepository<Pedido, Long> {
    @Transactional
    @Modifying(clearAutomatically = true)
    @Query("update Pedido p set p.status = :status where p = :pedido")
    fun atualizaStatus(status: Status, pedido: Pedido?)

    @Query(value = "SELECT p from Pedido p LEFT JOIN FETCH p.itens where p.id = :id")
    fun porIdComItens(id: Long?): Pedido?
}