package bessa.morangon.rafael.api.domain.repository;

import bessa.morangon.rafael.api.domain.model.Despesa;
import bessa.morangon.rafael.api.domain.model.Receita;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface DespesaRepository extends MongoRepository<Despesa, String> {

    public List<Despesa> findAllByDescricao(String descricao);
    public Page<Despesa> findAllByDescricao (String descricao, Pageable pageable);
    public List<Despesa> findAllByDataDespesaBetween(LocalDate inicio, LocalDate fim);
}
