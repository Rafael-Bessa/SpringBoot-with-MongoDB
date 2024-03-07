2package bessa.morangon.rafael.api.services;

import bessa.morangon.rafael.api.domain.dto.DespesaDTO;
import bessa.morangon.rafael.api.domain.dto.ReceitaDTO;
import bessa.morangon.rafael.api.domain.model.Despesa;
import bessa.morangon.rafael.api.domain.model.Receita;
import bessa.morangon.rafael.api.domain.repository.DespesaRepository;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.Calendar;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class DespesaService {

    private final DespesaRepository repository;
    private final ModelMapper mapper;
    public ResponseEntity<Page<DespesaDTO>> listaTodasDespesasComPaginacao(Pageable pageable) {
        Page<Despesa> all = repository.findAll(pageable);
        Page<DespesaDTO> despesas = all.map(despesa -> mapper.map(despesa, DespesaDTO.class));
        return ResponseEntity.ok(despesas);
    }

    public ResponseEntity<Page<DespesaDTO>> buscaTudoPelaDescricao(String descricao, Pageable pageable) {
        Page<Despesa> allByDescricao = repository.findAllByDescricao(descricao, pageable);

        if(allByDescricao.getTotalElements() == 0){
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(allByDescricao.map(d -> mapper.map(d, DespesaDTO.class)));
    }

    public ResponseEntity<?> buscaDespesaPorID(String id) {
        Optional<Despesa> despesa = repository.findById(id);

        if(despesa.isPresent()){
            return ResponseEntity.ok(mapper.map(despesa.get(), DespesaDTO.class));
        }
        return ResponseEntity.notFound().build();
    }

    public DespesaDTO cadastraDespesa(Despesa despesa) {
        if(verificaSeDespesaPodeSerCadastrada(despesa)){
            repository.save(despesa);
            return mapper.map(despesa, DespesaDTO.class);
        }
        return null;
    }
    public ResponseEntity<?> atualizaDespesa(String id, Despesa despesa) {
        Optional<Despesa> r = repository.findById(id);

        if(r.isPresent()){
            r.get().setDescricao(despesa.getDescricao());
            r.get().setValor(despesa.getValor());
            r.get().setCategoria(despesa.getCategoria());
            repository.save(r.get());
            return ResponseEntity.ok(r.get());
        }
        return ResponseEntity.notFound().build();
    }

    public ResponseEntity<?> deletaDespesa(String id) {
        Optional<Despesa> byId = repository.findById(id);

        if(byId.isPresent()){
            repository.delete(byId.get());
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }

    public ResponseEntity<?> despesaDoMes(int ano, int mes) {
        Calendar instance = Calendar.getInstance();
        instance.set(Calendar.MONTH, mes - 1);

        LocalDate primeiroDia = LocalDate.of(ano, mes, 1);
        LocalDate ultimoDia = LocalDate.of(ano, mes, instance.getActualMaximum(Calendar.DAY_OF_MONTH));

        List<Despesa> despesas = repository.findAllByDataDespesaBetween(primeiroDia, ultimoDia);

        if(despesas.size() == 0) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(despesas.stream()
                .map(r -> mapper.map(r, DespesaDTO.class))
                .collect(Collectors.toList()));
    }

    private boolean verificaSeDespesaPodeSerCadastrada(Despesa despesa){

        List<Despesa> despesas = repository.findAllByDescricao(despesa.getDescricao());
        List<DespesaDTO> dtos = despesas.stream().map(d -> mapper.map(d,DespesaDTO.class)).collect(Collectors.toList());

        for (DespesaDTO dto: dtos) {
            if(dto != null && dto.getDataDespesa().getMonthValue() == LocalDate.now().getMonthValue()){
                return false;
            }
        }
        return true;
    }
}
