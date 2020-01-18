package sajo.study.common.core.util;

public class SimpleTemplate {

	private String endpointPrefix;
	private ConnectRequest template;

	public SimpleTemplate(String endpointPrefix, ConnectRequest template) {
		this.endpointPrefix = endpointPrefix;
		this.template = template;
	}

	public <T, R> R post(String endpoint, T t, Class<R> clazz) {
		return template.request(getAbsolutePath(endpoint), t, clazz, r -> r);
	}

	public <T> T get(String endpoint, Class<T> clazz) throws InterruptedException {
		return template.fetch(getAbsolutePath(endpoint), clazz,r->r);
	}

	private String getAbsolutePath(String endpoint) {
		return endpointPrefix + endpoint;
	}
}
