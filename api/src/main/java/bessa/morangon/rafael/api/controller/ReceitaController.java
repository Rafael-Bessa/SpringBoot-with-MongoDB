package bessa.morangon.rafael.api.controller;

import bessa.morangon.rafael.api.domain.dto.ReceitaDTO;
import bessa.morangon.rafael.api.domain.model.Receita;
import bessa.morangon.rafael.api.services.ReceitaService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;
import java.net.URI;

@RestController
@RequestMapping("/receitas")
public class ReceitaController {
    @Autowired
    private ReceitaService service;

    @GetMapping
    public ResponseEntity<Page<ReceitaDTO>> buscaTodasReceitas(@PageableDefault(size = 5, sort = {"dataReceita"})
                                                                   Pageable pageable, String descricao){
        if (descricao == null) {
            return service.listaTodasReceitasComPaginacao(pageable);
        }
        return service.buscaTudoPelaDescricao(descricao, pageable);
    }
    @GetMapping("/{id}")
    public ResponseEntity<?> mostraReceita(@PathVariable String id) {
       return service.buscaReceitaPorID(id);
    }
    @PostMapping
    @Transactional
    public ResponseEntity<ReceitaDTO> cadastraReceita(@RequestBody @Valid Receita receita,
                                                      UriComponentsBuilder buider) {

        ReceitaDTO receitaDTO = service.cadastraReceita(receita);

        if(receitaDTO != null){
            URI uri = buider.path("/receitas/{id}").buildAndExpand(receitaDTO.getId()).toUri();
            return ResponseEntity.created(uri).body(receitaDTO);
        }
        return ResponseEntity.badRequest().build();
    }
    @PutMapping("/{id}")
    @Transactional
    public ResponseEntity<?> atualizaReceita(@PathVariable String id, @RequestBody @Valid Receita receita) {
      return service.atualizaReceita(receita, id);
    }

    @DeleteMapping("/{id}")
    @Transactional
    public ResponseEntity<?> deletaReceita(@PathVariable String id) {
        return service.deletaReceita(id);
    }

    @GetMapping("/{ano}/{mes}")
    public ResponseEntity<?> receitaMes(@PathVariable int ano, @PathVariable int mes) {
        return service.receitasDoMes(ano, mes);
    }
}
