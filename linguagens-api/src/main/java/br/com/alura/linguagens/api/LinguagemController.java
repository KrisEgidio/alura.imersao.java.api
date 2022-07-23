package br.com.alura.linguagens.api;

import org.springframework.web.bind.annotation.RestController;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

@RestController
public class LinguagemController {

    @Autowired
    private LinguagemRepository repositorio;
    

    @GetMapping("/linguagens")
    public List<Linguagem> obterLinguagens(){
        List<Linguagem> linguagens = repositorio.findAll();
        List<Linguagem> linguagensOrdenadas = linguagens.stream().sorted(Comparator.comparing(Linguagem::getRanking)).collect(Collectors.toList());
        return linguagensOrdenadas;
    }

    @PostMapping("/linguagens")
    public ResponseEntity<Linguagem>  cadastrarLinguagem(@RequestBody Linguagem linguagem){
        Linguagem linguagemSalva = repositorio.save(linguagem);
        return ResponseEntity.status(HttpStatus.CREATED).body(linguagemSalva);
    }

    @PutMapping("/linguagens/{id}")
    public ResponseEntity<Linguagem> editarLinguagem(@PathVariable String id, @RequestBody Linguagem linguagem){
        Linguagem linguagemEditada = repositorio.findById(id).orElseThrow();
        linguagemEditada.setTitle(linguagem.getTitle());
        linguagemEditada.setImage(linguagem.getImage());
        linguagemEditada.setRanking(linguagem.getRanking());
        return ResponseEntity.ok(linguagemEditada);
    }

    @DeleteMapping("/linguagens/{id}")
    public String deletarLinguagem(@PathVariable String id){
        try {
            repositorio.deleteById(id);
            return "Deletado com sucesso!";
        } catch (Exception e) {
            return e.getMessage();
        }
    }
}
