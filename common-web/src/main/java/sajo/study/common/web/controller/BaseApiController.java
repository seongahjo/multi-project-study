package sajo.study.common.web.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import sajo.study.common.core.dto.BaseDTO;
import sajo.study.common.core.model.BaseEntity;
import sajo.study.common.web.service.BaseService;

public abstract class BaseApiController<T extends BaseEntity, U extends BaseDTO> {
    protected BaseService<T, U> service;

    public BaseApiController(BaseService<T, U> service) {
        this.service = service;
    }

    @GetMapping("/{id}")
    public U getByIdx(@PathVariable Long id) {
        return service.findOne(id);
    }

    @PostMapping("")
    public String save(@RequestBody U dto) {
        return service.save(dto);
    }
}
