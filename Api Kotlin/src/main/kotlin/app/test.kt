
import com.github.britooo.looca.api.core.Looca
import java.math.BigDecimal
import java.text.DecimalFormat
import java.math.RoundingMode
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

fun main() {

    val looca = Looca()

    val momento = LocalDateTime.now()

    val formatando = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss")
    val formatado = momento.format(formatando)

    val Particao = looca.grupoDeDiscos.volumes[0].pontoDeMontagem
    val disponivel = looca.grupoDeDiscos.volumes[0].disponivel
    val total = looca.grupoDeDiscos.volumes[0].total
    val EmUso = (total - disponivel)
    val porcentagem = (EmUso*100) / total


    //val Particao1 = looca.grupoDeDiscos.volumes[1].pontoDeMontagem
   // val disponivel1 = looca.grupoDeDiscos.volumes[1].disponivel  /1024/1024/1024
   // val total1 = looca.grupoDeDiscos.volumes[1].total /1024/1024/1024
    //val EmUso1 = (total1 - disponivel1)
    //val porcentagem1 = ((EmUso1 * 100) /total1)

    // valor total do disco


    val NomeDisco = looca.grupoDeDiscos.discos[0].modelo

    val totalDisco0 = looca.grupoDeDiscos.volumes[0].total
    val disponivelDisco0 = looca.grupoDeDiscos.volumes[0].disponivel
    val EmUsoDisco0 = (totalDisco0 - disponivelDisco0)

    //val totalDisco1 = looca.grupoDeDiscos.volumes[1].total
    //val disponivelDisco1 = looca.grupoDeDiscos.volumes[1].disponivel
    //val EmUsoDisco1 = (totalDisco1 - disponivelDisco1)

    val tamanhoTotal = looca.grupoDeDiscos.discos[0].tamanho
    val EmUsoTotal = EmUsoDisco0 //+ EmUsoDisco1
    val porcentagemTotal = ((EmUsoTotal * 100) /tamanhoTotal)

    val processos = looca.grupoDeProcessos.processos

    val Processador = looca.processador.uso
    val random = Math.random()
    val bd = BigDecimal(Processador)
    val roundoff = bd.setScale(2, RoundingMode.FLOOR)


    val memoria = looca.memoria.emUso/1024/1024/1024


    println(formatado)
    println("disco ${Particao}: ${EmUso}")
    //println("disco ${Particao11}: ${EmUso1}")
    println("disco ${Particao} porcentagem: ${porcentagem}%")
    //println("disco ${Particao11} porcentagem: ${porcentagem1}%")
    println("disco ${NomeDisco} porcentagem: ${porcentagemTotal}%")
    println(roundoff)

}