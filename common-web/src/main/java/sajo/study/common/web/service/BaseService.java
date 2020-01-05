package sajo.study.common.web.service;

import org.springframework.data.jpa.repository.JpaRepository;
import sajo.study.common.core.dto.BaseDTO;
import sajo.study.common.core.dto.MessageDTO;
import sajo.study.common.core.model.BaseEntity;

import javax.persistence.EntityNotFoundException;

import static sajo.study.common.core.util.ModelMapperUtils.map;

public abstract class BaseService<T extends BaseEntity, U extends BaseDTO> {
    protected JpaRepository<T, Long> repository;
    protected Class<T> clazz;
    protected Class<U> dto;

    public BaseService(JpaRepository<T, Long> repo, Class<T> clazz, Class<U> dto) {
        this.repository = repo;
        this.clazz = clazz;
        this.dto = dto;
    }

    public U findOne(Long idx) {
        return map(this.repository.findById(idx).orElseThrow(EntityNotFoundException::new), dto);
    }

    public String save(U dto) {
        return map(this.repository.save(map(dto, clazz)),MessageDTO.class);
    }
}
