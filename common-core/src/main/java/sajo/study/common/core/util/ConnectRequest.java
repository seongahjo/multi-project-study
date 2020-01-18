package sajo.study.common.core.util;

import java.util.function.UnaryOperator;

public interface ConnectRequest {
	<T, R> R request(String endPoint, T t, Class<R> clazz, UnaryOperator<R> callback);

	<T> T fetch(String endPoint, Class<T> clazz, UnaryOperator<T> callback) throws InterruptedException;
}
