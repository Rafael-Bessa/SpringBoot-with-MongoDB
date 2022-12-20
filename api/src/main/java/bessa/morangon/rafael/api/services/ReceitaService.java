package bessa.morangon.rafael.api.services;

import bessa.morangon.rafael.api.domain.dto.ReceitaDTO;
import bessa.morangon.rafael.api.domain.model.Receita;
import bessa.morangon.rafael.api.domain.repository.ReceitaRepository;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Calendar;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class ReceitaService {

    private final ReceitaRepository repository;
    private final ModelMapper mapper;
    public ResponseEntity<Page<ReceitaDTO>> listaTodasReceitasComPaginacao(Pageable pageable){
        Page<Receita> all = repository.findAll(pageable);
        Page<ReceitaDTO> receitas = all.map(receita -> mapper.map(receita, ReceitaDTO.class));
        return ResponseEntity.ok(receitas);
    }

    public ResponseEntity<ReceitaDTO> buscaReceitaPorID(String id){
        Optional<Receita> receita = repository.findById(id);

        if(receita.isPresent()){
            return ResponseEntity.ok(mapper.map(receita.get(), ReceitaDTO.class));
        }
        return ResponseEntity.notFound().build();
    }

    private boolean verificaSeReceitaPodeSerCadastrada(Receita receita){

        List<Receita> receitas = repository.findAllByDescricao(receita.getDescricao());
        List<ReceitaDTO> dtos = receitas.stream().map(r -> mapper.map(r,ReceitaDTO.class)).collect(Collectors.toList());

        for (ReceitaDTO dto: dtos) {
            if(dto != null && dto.getDataReceita().getMonthValue() == LocalDate.now().getMonthValue()){
                return false;
            }
        }
        return true;
    }
    public ReceitaDTO cadastraReceita(Receita receita){

        if(verificaSeReceitaPodeSerCadastrada(receita)){
            repository.save(receita);
            return mapper.map(receita, ReceitaDTO.class);
        }
        return null;
    }

    public ResponseEntity<?> atualizaReceita(Receita receita, String id){
        Optional<Receita> r = repository.findById(id);
        if(r.isPresent()){
            r.get().setDescricao(receita.getDescricao());
            r.get().setValor(receita.getValor());
            repository.save(r.get());
            return ResponseEntity.ok(r.get());
        }
        return ResponseEntity.notFound().build();
    }

    public ResponseEntity<?> deletaReceita(String id){
        Optional<Receita> byId = repository.findById(id);

        if(byId.isPresent()){
            repository.delete(byId.get());
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }

    public ResponseEntity<?> receitasDoMes(int ano, int mes){

        Calendar instance = Calendar.getInstance();
        instance.set(Calendar.MONTH, mes - 1);

        LocalDate primeiroDia = LocalDate.of(ano, mes, 1);
        LocalDate ultimoDia = LocalDate.of(ano, mes, instance.getActualMaximum(Calendar.DAY_OF_MONTH));

        List<Receita> receitas = repository.findAllByDataReceitaBetween(primeiroDia, ultimoDia);

        if(receitas.size() == 0) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(receitas.stream()
                .map(r -> mapper.map(r, ReceitaDTO.class))
                .collect(Collectors.toList()));
    }

    public ResponseEntity<Page<ReceitaDTO>> buscaTudoPelaDescricao(String descricao, Pageable pageable){
        Page<Receita> allByDescricao = repository.findAllByDescricao(descricao, pageable);

        if(allByDescricao.getSize() == 0){
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(allByDescricao.map(r -> mapper.map(r, ReceitaDTO.class)));
    }
}
