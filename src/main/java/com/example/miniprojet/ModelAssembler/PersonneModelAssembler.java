package com.example.miniprojet.ModelAssembler;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

import com.example.miniprojet.controller.PersonneController;
import com.example.miniprojet.entity.Personne;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

@Component
public
class PersonneModelAssembler implements RepresentationModelAssembler<Personne, EntityModel<Personne>> {

    @Override
    public EntityModel<Personne> toModel(Personne employee) {

        return EntityModel.of(employee, //
                linkTo(methodOn(PersonneController.class).FindById(employee.getId())).withSelfRel(),
                linkTo(methodOn(PersonneController.class).FindAll()).withRel("employees"));
    }
}