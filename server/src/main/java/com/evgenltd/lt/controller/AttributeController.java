package com.evgenltd.lt.controller;

import com.evgenltd.lt.entity.Attribute;
import com.evgenltd.lt.repository.AttributeRepository;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/attribute")
public class AttributeController {

    private final AttributeRepository attributeRepository;

    public AttributeController(final AttributeRepository attributeRepository) {
        this.attributeRepository = attributeRepository;
    }

    @GetMapping
    public Response<List<Attribute>> list() {
        return new Response<>(attributeRepository.findAll(Sort.by("name")));
    }

    @PostMapping
    public Response<Attribute> update(@RequestBody final Attribute attribute) {
        return new Response<>(attributeRepository.save(attribute));
    }

    @DeleteMapping("/{id}")
    public Response<Void> delete(@PathVariable("id") final Long id) {
        attributeRepository.deleteById(id);
        return new Response<>();
    }

}
