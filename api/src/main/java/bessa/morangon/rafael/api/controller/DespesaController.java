package bessa.morangon.rafael.api.controller;

import bessa.morangon.rafael.api.domain.dto.DespesaDTO;
import bessa.morangon.rafael.api.domain.dto.ReceitaDTO;
import bessa.morangon.rafael.api.domain.model.Despesa;
import bessa.morangon.rafael.api.domain.model.Receita;
import bessa.morangon.rafael.api.domain.repository.DespesaRepository;
import bessa.morangon.rafael.api.services.DespesaService;
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
@RequestMapping("/despesas")
public class DespesaController {

    @Autowired
    private DespesaService service;
    @GetMapping
    public ResponseEntity<Page<DespesaDTO>> buscaTodasDespesas(@PageableDefault(size = 5, sort = {"dataDespesa"})
                                                               Pageable pageable, String descricao){
        if (descricao == null) {
            return service.listaTodasDespesasComPaginacao(pageable);
        }
        return service.buscaTudoPelaDescricao(descricao, pageable);
    }
    @GetMapping("/{id}")
    public ResponseEntity<?> mostraDespesa(@PathVariable String id) {
        return service.buscaDespesaPorID(id);
    }
    @PostMapping
    @Transactional
    public ResponseEntity<DespesaDTO> cadastraDespesa(@RequestBody @Valid Despesa despesa,
                                                      UriComponentsBuilder buider) {

        DespesaDTO despesaDTO = service.cadastraDespesa(despesa);

        if(despesaDTO != null){
            URI uri = buider.path("/despesas/{id}").buildAndExpand(despesaDTO.getId()).toUri();
            return ResponseEntity.created(uri).body(despesaDTO);
        }
        return ResponseEntity.badRequest().build();
    }
    @PutMapping("/{id}")
    @Transactional
    public ResponseEntity<?> atualizaDespesa(@PathVariable String id, @RequestBody @Valid Despesa despesa) {
        return service.atualizaDespesa(id, despesa);
    }

    @DeleteMapping("/{id}")
    @Transactional
    public ResponseEntity<?> deletaDespesa(@PathVariable String id) {
        return service.deletaDespesa(id);
    }

    @GetMapping("/{ano}/{mes}")
    public ResponseEntity<?> despesaMes(@PathVariable int ano, @PathVariable int mes) {
        return service.despesaDoMes(ano, mes);
    }
}
