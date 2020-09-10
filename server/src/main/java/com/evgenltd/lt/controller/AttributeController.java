package com.evgenltd.lt.controller;

import com.evgenltd.lt.entity.Attribute;
import com.evgenltd.lt.record.AttributeRecord;
import com.evgenltd.lt.repository.AttributeRepository;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/attribute")
public class AttributeController {

    private final AttributeRepository attributeRepository;

    public AttributeController(final AttributeRepository attributeRepository) {
        this.attributeRepository = attributeRepository;
    }

    @GetMapping
    public Response<List<AttributeRecord>> list() {
        final List<AttributeRecord> result = attributeRepository.findAll(Sort.by("name"))
                .stream()
                .map(AttributeRecord::fromSimple)
                .collect(Collectors.toList());
        return new Response<>(result);
    }

    @GetMapping("{id}")
    public Response<AttributeRecord> loadById(@PathVariable("id") final Long id) {
        return new Response<>(AttributeRecord.from(attributeRepository.getOne(id)));
    }

    @PostMapping
    public Response<AttributeRecord> update(@RequestBody final AttributeRecord attributeRecord) {
        final Attribute attribute = attributeRecord.toEntity();
        final Attribute result = attributeRepository.save(attribute);
        return new Response<>(AttributeRecord.from(result));
    }

    @DeleteMapping("/{id}")
    public Response<Void> delete(@PathVariable("id") final Long id) {
        attributeRepository.deleteById(id);
        return new Response<>();
    }

}
