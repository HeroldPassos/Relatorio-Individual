package repositorio

import app.*
import com.github.britooo.looca.api.group.processador.Processador
import dominio.CapturaKT
import org.springframework.jdbc.core.BeanPropertyRowMapper
import org.springframework.jdbc.core.JdbcTemplate
import java.time.LocalDateTime
import java.util.*

class CapturaRepositorio(val jdbcTemplate: JdbcTemplate)  {

    fun inserir(leitura: CapturaKT) {
        jdbcTemplate.update("""
            insert into healthsystem.leitura (fkEquipamento, fkComponente, idLeitura, valor, momento) values
            (?, ?, ?, ?, ?)
        """, leitura.fkEquipamento, leitura.fkcomponente, leitura.valor, leitura.momento)
    }

    fun listar():List<CapturaKT> {
        return jdbcTemplate.query("select * from healthsystem.leitura",
                BeanPropertyRowMapper(CapturaKT::class.java))
    }

    fun inserirProcess(leitura: CapturaKT) {
        leitura.fkEquipamento = 1
        leitura.fkcomponente = 1

        jdbcTemplate.update("""
            insert into healthsystem.leitura (fkEquipamento, fkComponente, valor, momento) values
            (?, ?, ?, ?, ?)
        """, leitura.fkEquipamento, leitura.fkcomponente, leitura.valor, leitura.momento)
    }

    }




