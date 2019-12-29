package sajo.study.common.core.util;

import org.modelmapper.ModelMapper;

public class ModelMapperUtils {
    private static final ModelMapper modelMapper = new ModelMapper();

    private ModelMapperUtils() {
    }

    public static <T, U> U map(T entity, Class<U> clazz) {
        return modelMapper.map(entity, clazz);
    }
}
