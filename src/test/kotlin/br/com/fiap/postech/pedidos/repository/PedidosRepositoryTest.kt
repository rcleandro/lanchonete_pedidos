package br.com.fiap.postech.pedidos.repository

import br.com.fiap.postech.pedidos.module.Pedido
import br.com.fiap.postech.pedidos.module.Status
import org.assertj.core.api.Assertions
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.MockitoAnnotations
import java.util.*
import kotlin.random.Random

class PedidosRepositoryTest {

    @Mock
    private lateinit var pedidoRepository: PedidoRepository
    private lateinit var openMock: AutoCloseable

    @BeforeEach
    fun setup() {
        openMock = MockitoAnnotations.openMocks(this)
    }

    @AfterEach
    fun tearDown() = openMock.close()

    @Test
    fun devePermitirCadastrarPedido() {
        // Arrange
        val pedido = gerarPedido()

        Mockito.`when`(pedidoRepository.save(Mockito.any(Pedido::class.java))).thenReturn(pedido)

        // Act
        val pedidoRegistrado = pedidoRepository.save(pedido)

        // Assert
        Assertions.assertThat(pedidoRegistrado)
            .isInstanceOf(Pedido::class.java)
            .isNotNull
            .isEqualTo(pedido)

        Mockito.verify(pedidoRepository, Mockito.times(1))
            .save(Mockito.any(Pedido::class.java))
    }

    @Test
    fun devePermitirBuscarPedidosPorId() {
        // Arrange
        val pedido = gerarPedido()

        Mockito.`when`(pedidoRepository.findById(pedido.id)).thenReturn(Optional.of(pedido))

        // Act
        val pedidoRecebidoOpcional = pedido.id.let { pedidoRepository.findById(it) }

        // Assert
        Assertions.assertThat(pedidoRecebidoOpcional).isPresent

        pedidoRecebidoOpcional.ifPresent { pedidoRecebido ->
            Assertions.assertThat(pedidoRecebido)
                .isInstanceOf(Pedido::class.java)
                .isEqualTo(pedido)
        }

        pedido.id.let {
            Mockito.verify(pedidoRepository, Mockito.times(1))
                .findById(it)
        }
    }

    @Test
    fun devePermitirListarPedidos() {
        // Arrange
        val pedido1 = gerarPedido()
        val pedido2 = gerarPedido()
        val listaPedidos = listOf(pedido1, pedido2)

        Mockito.`when`(pedidoRepository.findAll()).thenReturn(listaPedidos)

        // Act
        val pedidosRecebidoOpcional = pedidoRepository.findAll()

        // Assert
        Assertions.assertThat(pedidosRecebidoOpcional)
            .hasSize(2)
            .containsExactlyInAnyOrder(pedido1, pedido2)

        Mockito.verify(pedidoRepository, Mockito.times(1))
            .findAll()
    }

    @Test
    fun devePermitirAtualizarPedido() {
        // Arrange
        val pedido = gerarPedido()

        val novoStatus = Status.CONFIRMADO
        pedido.status = novoStatus

        Mockito.`when`(pedidoRepository.findById(pedido.id)).thenReturn(Optional.of(pedido))

        // Act
        val pedidoRecebidoOpcional = pedido.id.let { pedidoRepository.findById(it) }

        // Assert
        Assertions.assertThat(pedidoRecebidoOpcional).isPresent

        pedidoRecebidoOpcional.ifPresent { pedidoRecebido ->
            Assertions.assertThat(pedidoRecebido)
                .isInstanceOf(Pedido::class.java)
                .isEqualTo(pedido)

            Assertions.assertThat(pedidoRecebido.status)
                .isEqualTo(novoStatus)
        }

        pedido.id.let {
            Mockito.verify(pedidoRepository, Mockito.times(1))
                .findById(it)
        }
    }

    @Test
    fun devePermitirDeletarPedido() {
        // Arrange
        val pedido = gerarPedido()

        // Act
        pedidoRepository.delete(pedido)
        val pedidoRecebidoOpcional = pedido.id.let { pedidoRepository.findById(it) }

        // Assert
        Assertions.assertThat(pedidoRecebidoOpcional).isEmpty

        Mockito.verify(pedidoRepository, Mockito.times(1))
            .delete(pedido)
    }

    private fun gerarPedido(): Pedido {
        return Pedido(
            id = Random.nextLong(1000000),
        )
    }
}