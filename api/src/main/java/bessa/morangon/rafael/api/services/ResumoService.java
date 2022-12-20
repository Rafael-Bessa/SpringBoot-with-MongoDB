package bessa.morangon.rafael.api.services;

import bessa.morangon.rafael.api.domain.model.Despesa;
import bessa.morangon.rafael.api.domain.model.Receita;
import bessa.morangon.rafael.api.domain.repository.DespesaRepository;
import bessa.morangon.rafael.api.domain.repository.ReceitaRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Calendar;
import java.util.List;

@Service
@AllArgsConstructor
public class ResumoService {

    private ReceitaRepository receitaRepository;
    private DespesaRepository despesaRepository;

    public List<Receita> listaReceitasMes(int ano, int mes){
        Calendar instance = Calendar.getInstance();
        instance.set(Calendar.MONTH, mes - 1);

        LocalDate primeiroDia = LocalDate.of(ano, mes, 1);
        LocalDate ultimoDia = LocalDate.of(ano, mes, instance.getActualMaximum(Calendar.DAY_OF_MONTH));
        return receitaRepository.findAllByDataReceitaBetween(primeiroDia, ultimoDia);
    }

    public List<Despesa> listaDespesasMes(int ano, int mes){
        Calendar instance = Calendar.getInstance();
        instance.set(Calendar.MONTH, mes - 1);

        LocalDate primeiroDia = LocalDate.of(ano, mes, 1);
        LocalDate ultimoDia = LocalDate.of(ano, mes, instance.getActualMaximum(Calendar.DAY_OF_MONTH));
        return despesaRepository.findAllByDataDespesaBetween(primeiroDia, ultimoDia);
    }

}
