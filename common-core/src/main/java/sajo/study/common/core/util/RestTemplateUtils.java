package sajo.study.common.core.util;

import lombok.NoArgsConstructor;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.web.client.RestTemplate;

import java.util.function.UnaryOperator;

@NoArgsConstructor
public class RestTemplateUtils implements ConnectRequest {
	private static final RestTemplate restTemplate = new RestTemplate();

	@Override
	public <T, R> R request(String url, T request, Class<R> clazz, UnaryOperator<R> callback) {
		HttpHeaders headers = new HttpHeaders();
		HttpEntity<T> req = new HttpEntity<>(request, headers);
		return callback.apply(restTemplate.postForObject(url, req, clazz));
	}

	@Override
	public <R> R fetch(String url, Class<R> clazz, UnaryOperator<R> callback) {
		return callback.apply(restTemplate.getForObject(url, clazz));
	}
}
