package app

import com.github.britooo.looca.api.core.Looca
import configuracao.Conexao
import configuracao.Conexao1
import java.math.BigDecimal
import java.math.RoundingMode
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.*



open class Main {
    companion object {
        @JvmStatic fun main(args: Array<String>) {

            TimerCount()

        }
    }
}


//conexões
val jdbcTemplate = Conexao().getJdbcTemplate()
val jdbcTemplate1 = Conexao1().getJdbcTemplate()
// biblioteca
val looca = Looca()


    // dados Processador
    val Processador = looca.processador.uso
    val bd1 = BigDecimal(Processador)
    val ProcessFormated = bd1.setScale(2, RoundingMode.FLOOR)


    //dados Memria ram
    val memoria = looca.memoria.emUso/1024/1024/1024
    val bd = BigDecimal(memoria)
    val MemoryFormated = bd.setScale(2, RoundingMode.FLOOR)

    //dados 1° partição
    val Particao = looca.grupoDeDiscos.volumes[0].pontoDeMontagem
    val totalDisco0 = looca.grupoDeDiscos.volumes[0].total
    val disponivelDisco0 = looca.grupoDeDiscos.volumes[0].disponivel
    val EmUsoDisco0 = (totalDisco0 - disponivelDisco0)
    val PercentDisk0 = (EmUsoDisco0*100)/totalDisco0

//    // dados 2° partição
    val Particao1 = looca.grupoDeDiscos.volumes[1].pontoDeMontagem
    val totalDisco1 = looca.grupoDeDiscos.volumes[1].total
    val disponivelDisco1 = looca.grupoDeDiscos.volumes[1].disponivel
    val EmUsoDisco1 = (totalDisco1 - disponivelDisco1)
    val PercentDisk1 = (EmUsoDisco1*100)/totalDisco1

    // dados disco completo
    val tamanhoTotal = looca.grupoDeDiscos.discos[0].tamanho
    val EmUsoTotal = EmUsoDisco0  + EmUsoDisco1
    val porcentagemTotal = ((EmUsoTotal * 100) /tamanhoTotal)

    fun inserirProcess(formatado:String) {
        val novafkEquipamento = 1
        val novafkComponente = 1
        jdbcTemplate.update("""
            insert into dbo.Leitura (fkEquipamento, fkComponente, valor, momento) values
            (?, ?, ?, ?)
        """, novafkEquipamento, novafkComponente, ProcessFormated, formatado)
        jdbcTemplate1.update("""
            insert into Leitura (fkEquipamento, fkComponente, valor, momento) values
            (?, ?, ?, ?)
        """, novafkEquipamento, novafkComponente, ProcessFormated, formatado)

    }
fun inserirMemory(formatado:String) {
    val novafkEquipamento = 1
    val novafkComponente = 2
    jdbcTemplate.update("""
            insert into dbo.Leitura (fkEquipamento, fkComponente, valor, momento) values
            (?, ?, ?, ?)
        """, novafkEquipamento,novafkComponente, MemoryFormated, formatado)
    jdbcTemplate1.update("""
            insert into Leitura (fkEquipamento, fkComponente, valor, momento) values
            (?, ?, ?, ?)
        """, novafkEquipamento,novafkComponente, MemoryFormated, formatado)

}

fun inserirDisk(formatado:String){
    val novafkEquipamento = 1
    val novafkComponente = 3
    jdbcTemplate.update("""
            insert into dbo.Leitura (fkEquipamento, fkComponente, valor, momento) values
            (?, ?, ?, ?)
        """, novafkEquipamento,novafkComponente, porcentagemTotal, formatado)
    jdbcTemplate1.update("""
            insert into Leitura (fkEquipamento, fkComponente, valor, momento) values
            (?, ?, ?, ?)
        """, novafkEquipamento,novafkComponente, porcentagemTotal, formatado)

}

    fun insertPartition1(formatado:String){
        val novafkEquipamento = 2
        val novafkComponente = 3
        jdbcTemplate.update("""
            insert into dbo.ReadDisk (fkEquipamento, fkComponente, Particao, valor, momento) values
            (?, ?, ?, ?, ?)
        """, novafkEquipamento, novafkComponente, Particao, PercentDisk0, formatado)
        jdbcTemplate1.update("""
            insert into ReadDisk (fkEquipamento, fkComponente, Particao, valor, momento) values
            (?, ?, ?, ?, ?)
        """, novafkEquipamento,novafkComponente, Particao, PercentDisk0, formatado)
    }
fun insertPartition2(formatado:String) {
    val novafkEquipamento = 2
    val novafkComponente = 3

    jdbcTemplate.update(
        """
        insert into dbo.ReadDisk (fkEquipamento, fkComponente, Particao, valor, momento) values
        (?, ?, ?, ?, ?)
    """, novafkEquipamento, novafkComponente, Particao1, PercentDisk1, formatado
    )
    jdbcTemplate1.update(
        """
        insert into ReadDisk (fkEquipamento, fkComponente, Particao, valor, momento) values
        (?, ?, ?, ?, ?)
    """, novafkEquipamento, novafkComponente, Particao1, PercentDisk1, formatado
    )

}


var contador = 0
fun TimerCount() {
    val momento = LocalDateTime.now()
    val formatacao = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss")
    val formatado = momento.format(formatacao)


    println(
        """
        frequência do processador: $ProcessFormated %
        memoria utilizada: $MemoryFormated Gb
        Disco Total Utilizado: $porcentagemTotal % 
        partições: 
        $Particao $PercentDisk0 %
        $Particao1 $PercentDisk1 %    
        data Atual: $formatado
        
    """.trimIndent()
    )
    println()
    inserirProcess(formatado)
    inserirMemory(formatado)
    inserirDisk(formatado)
    insertPartition1(formatado)
    insertPartition2(formatado)
    Timer().schedule(object : TimerTask() {
        override fun run() {
            contador++
            if (contador > -1) {
                TimerCount()

            }
        }
    }, 2000)
}


