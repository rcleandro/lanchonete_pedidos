package br.com.fiap.postech.pedidos.dto

import br.com.fiap.postech.pedidos.module.Status
import lombok.AllArgsConstructor
import lombok.Getter
import lombok.NoArgsConstructor
import lombok.Setter

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
class StatusDto {
    var status: Status = Status.REALIZADO
}
