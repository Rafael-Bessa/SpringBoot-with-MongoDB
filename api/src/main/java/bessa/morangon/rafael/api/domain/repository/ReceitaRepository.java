package bessa.morangon.rafael.api.domain.repository;

import bessa.morangon.rafael.api.domain.model.Receita;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import java.time.LocalDate;
import java.util.List;

@Repository
public interface ReceitaRepository extends MongoRepository<Receita, String> {
    public List<Receita> findAllByDescricao(String descricao);
    public Page<Receita> findAllByDescricao (String descricao, Pageable pageable);
    public List<Receita> findAllByDataReceitaBetween(LocalDate inicio, LocalDate fim);
}
