package bessa.morangon.rafael.api.controller;

import bessa.morangon.rafael.api.domain.dto.ResumoDTO;
import bessa.morangon.rafael.api.domain.model.Categoria;
import bessa.morangon.rafael.api.domain.model.Despesa;
import bessa.morangon.rafael.api.domain.model.Receita;
import bessa.morangon.rafael.api.services.ResumoService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@AllArgsConstructor
@RequestMapping("/resumo")
public class ResumoController {
    private ResumoService resumoService;

    @GetMapping("/{ano}/{mes}")
    public ResponseEntity<?> resumoMensal(@PathVariable int ano, @PathVariable int mes){

        List<Despesa> despesas = resumoService.listaDespesasMes(ano, mes);

        List<Receita> receitas = resumoService.listaReceitasMes(ano, mes);

        if (receitas != null && despesas != null) {

            BigDecimal totalReceita = receitas.stream().map(Receita::getValor).reduce(BigDecimal.ZERO,
                    BigDecimal::add);
            BigDecimal totalDespesa = despesas.stream().map(Despesa::getValor).reduce(BigDecimal.ZERO,
                    BigDecimal::add);

            Map<Categoria, BigDecimal> mapa = new HashMap<>();

            for (Despesa d : despesas) {

                if (mapa.containsKey(d.getCategoria())) {
                    mapa.put(d.getCategoria(), mapa.get(d.getCategoria()).add(d.getValor()));
                }
                else {
                    mapa.put(d.getCategoria(), d.getValor());
                }
            }
            return ResponseEntity
                    .ok(new ResumoDTO(totalReceita, totalDespesa, totalReceita.subtract(totalDespesa), mapa));
        }
        return ResponseEntity.notFound().build();
    }
}

