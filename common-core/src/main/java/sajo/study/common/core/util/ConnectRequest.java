package sajo.study.common.core.util;

public interface ConnectRequest {
	<T, R> R request(String endPoint, T t, Class<R> clazz);
	<T> T fetch(String endPoint, Class<T> clazz);
}
