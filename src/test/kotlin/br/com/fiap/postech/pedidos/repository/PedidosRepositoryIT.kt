package br.com.fiap.postech.pedidos.repository

import br.com.fiap.postech.pedidos.module.Pedido
import br.com.fiap.postech.pedidos.module.Status
import br.com.fiap.postech.pedidos.repository.PedidoRepository
import jakarta.transaction.Transactional
import org.assertj.core.api.Assertions
import org.junit.jupiter.api.Test
import org.mockito.Mockito
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase
import org.springframework.boot.test.context.SpringBootTest
import kotlin.random.Random

@SpringBootTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Transactional
class PedidosRepositoryIT {

    @Autowired
    private lateinit var pedidoRepository: PedidoRepository

    @Test
    fun devePermitirCriarTabela() {
        val totalDeRegistros = pedidoRepository.count()
        Assertions.assertThat(totalDeRegistros).isNotNegative()
    }

    @Test
    fun devePermitirCadastrarPedido() {
        // Arrange
        val pedido = gerarPedido()

        // Act
        val pedidoRegistrado = pedidoRepository.save(pedido)

        // Assert
        Assertions.assertThat(pedidoRegistrado)
            .isInstanceOf(Pedido::class.java)
            .isNotNull
            .isEqualTo(pedido)
    }

    @Test
    fun devePermitirBuscarPedidosPorId() {
        // Arrange
        val pedido = gerarPedido()
        pedidoRepository.save(pedido)

        // Act
        val pedidoRecebidoOpcional = pedidoRepository.porIdComItens(pedido.id)

        // Assert
        Assertions.assertThat(pedidoRecebidoOpcional).isNotNull
    }

    @Test
    fun devePermitirListarPedidos() {
        // Arrange
        val pedido1 = gerarPedido()
        pedidoRepository.save(pedido1)

        val pedido2 = gerarPedido()
        pedidoRepository.save(pedido2)

        // Act
        val pedidosRecebidoOpcional = pedidoRepository.findAll()

        // Assert
        Assertions.assertThat(pedidosRecebidoOpcional)
            .hasSize(2)
    }

    @Test
    fun devePermitirAtualizarStatusDoPedido() {
        // Arrange
        val pedido = gerarPedido()
        pedidoRepository.save(pedido)

        // Act
        val pedidoRecebidoOpcional = pedidoRepository.atualizaStatus(Status.CONFIRMADO, pedido)

        // Assert
        Assertions.assertThat(pedidoRecebidoOpcional).isNotNull
    }

    @Test
    fun devePermitirDeletarPedido() {
        // Arrange
        val pedido = gerarPedido()
        pedidoRepository.save(pedido)

        // Act
        pedidoRepository.delete(pedido)
        val pedidoOpcional = pedidoRepository.findById(pedido.id)

        // Assert
        Assertions.assertThat(pedidoOpcional)
            .isEmpty
    }

    private fun gerarPedido(): Pedido {
        return Pedido(
            id = Random.nextLong(1000000),
        )
    }
}