package dominio

import java.time.LocalDateTime

data class CapturaKT(
    var fkEquipamento: Int,
    var fkcomponente: Int,
    var Particao: String,
    var valor:Double,
    var momento:LocalDateTime
) {
    constructor() : this(0,0," ",0.0, LocalDateTime.parse("1111-11-11T11.11"))
}
