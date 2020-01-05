package sajo.study.common.core.util;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.web.client.RestTemplate;

public class RestTemplateUtils {
	private static final RestTemplate restTemplate = new RestTemplate();

	private RestTemplateUtils() {
	}

	public static <T, R> R post(String url, T request, Class<R> clazz) {
		HttpHeaders headers = new HttpHeaders();
		HttpEntity<T> req = new HttpEntity<>(request, headers);
		return restTemplate.postForObject(url, req, clazz);
	}

	public static <R> R get(String url, Class<R> clazz) {
		return restTemplate.getForObject(url, clazz);
	}
}
