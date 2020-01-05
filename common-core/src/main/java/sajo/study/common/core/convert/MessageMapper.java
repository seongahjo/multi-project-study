package sajo.study.common.core.convert;

import sajo.study.common.core.model.BaseEntity;

@FunctionalInterface
public interface MessageMapper{
	String map(BaseEntity entity);
}
