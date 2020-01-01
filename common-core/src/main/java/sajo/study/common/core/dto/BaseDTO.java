package sajo.study.common.core.dto;

import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

@Data
public abstract class BaseDTO implements Serializable {
    protected LocalDateTime createdAt;
    protected LocalDateTime updatedAt;
}
