package com.example.miniprojet.controller;

import java.util.List;
import java.util.stream.Collectors;

import com.example.miniprojet.ModelAssembler.PersonneModelAssembler;
import com.example.miniprojet.entity.Personne;
import com.example.miniprojet.exception.PersonneNotFoundException;
import com.example.miniprojet.service.PersonneService;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.IanaLinkRelations;
import org.springframework.hateoas.MediaTypes;
import org.springframework.hateoas.mediatype.problem.Problem;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
@RequestMapping("/personne")
public class PersonneController {

    private final PersonneService personneService;

    private final PersonneModelAssembler assembler;

    PersonneController(PersonneService personneService, PersonneModelAssembler assembler) {

        this.personneService = personneService;
        this.assembler = assembler;
    }


//    $ curl -v localhost:8080/personne
    @GetMapping
    public CollectionModel<EntityModel<Personne>> FindAll() {

        List<EntityModel<Personne>> personnes = personneService.findAll().stream() //
                .map(assembler::toModel) //
                .collect(Collectors.toList());

        return CollectionModel.of(personnes, linkTo(methodOn(PersonneController.class).FindAll()).withSelfRel());
    }

//   $ curl -v -X POST localhost:8080/personne -H 'Content-Type:application/json' -d '{"nom": "Samwise Gamgee", "prenom": "gardener","cin":"F120451"}'
    @PostMapping
    ResponseEntity<?> create(@RequestBody Personne newPersonne) {

        Personne personne = personneService.create(newPersonne);
        if( personne == null) {
            return ResponseEntity //
                    .status(HttpStatus.METHOD_NOT_ALLOWED) //
                    .header(HttpHeaders.CONTENT_TYPE, MediaTypes.HTTP_PROBLEM_DETAILS_JSON_VALUE) //
                    .body(Problem.create() //
                            .withTitle("Method not allowed") //
                            .withDetail("You cannot add a new person whose CIN (" + newPersonne.getCin() + ") already exist in database"));
        }
        EntityModel<Personne> entityModel = assembler.toModel(personne);
        return ResponseEntity //
                .created(entityModel.getRequiredLink(IanaLinkRelations.SELF).toUri()) //
                .body(entityModel);
    }

//    $ curl -v localhost:8080/personne/1
    @GetMapping("/{id}")
    public EntityModel<Personne> FindById(@PathVariable Long id) {

        return assembler.toModel(personneService.findById(id));
    }

//    $ curl -v -X PUT localhost:8080/personne/1 -H 'Content-Type:application/json' -d '{"nom": "Samwise", "prenom": "Gamgee","cin":"K123691"}'
    @PutMapping("/{id}")
    ResponseEntity<?> update(@RequestBody Personne newPersonne, @PathVariable Long id) {

        Personne personne = personneService.update(newPersonne, id);
        if( personne == null) {
            return ResponseEntity //
                    .status(HttpStatus.METHOD_NOT_ALLOWED) //
                    .header(HttpHeaders.CONTENT_TYPE, MediaTypes.HTTP_PROBLEM_DETAILS_JSON_VALUE) //
                    .body(Problem.create() //
                            .withTitle("Method not allowed") //
                            .withDetail("You cannot update a person with this new CIN (" + newPersonne.getCin() + ") because is already exist in database"));
        }
        EntityModel<Personne> entityModel = assembler.toModel(personne);

        return ResponseEntity //
                .created(entityModel.getRequiredLink(IanaLinkRelations.SELF).toUri()) //
                .body(entityModel);
    }

//   $ curl -X DELETE localhost:8080/personne/2
    @DeleteMapping("/{id}")
    ResponseEntity<?> delete(@PathVariable Long id) {

       personneService.delete(id);

        return ResponseEntity.noContent().build();
    }
}