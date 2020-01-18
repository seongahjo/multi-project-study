package sajo.study.common.core.util;

import org.springframework.messaging.converter.MappingJackson2MessageConverter;
import org.springframework.messaging.simp.stomp.StompFrameHandler;
import org.springframework.messaging.simp.stomp.StompHeaders;
import org.springframework.messaging.simp.stomp.StompSession;
import org.springframework.messaging.simp.stomp.StompSessionHandlerAdapter;
import org.springframework.web.socket.client.standard.StandardWebSocketClient;
import org.springframework.web.socket.messaging.WebSocketStompClient;
import org.springframework.web.socket.sockjs.client.SockJsClient;
import org.springframework.web.socket.sockjs.client.Transport;
import org.springframework.web.socket.sockjs.client.WebSocketTransport;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeoutException;
import java.util.function.UnaryOperator;

import static java.util.concurrent.TimeUnit.MINUTES;
import static java.util.concurrent.TimeUnit.SECONDS;

public class StompTemplateUtils implements ConnectRequest {
	private StompSession stompSession;


	/**
	 * websocket 의존성....
	 * => jiksaw
	 */
	public StompTemplateUtils(int port) {
		WebSocketStompClient stompClient = new WebSocketStompClient(new SockJsClient(createTransportClient()));
		stompClient.setMessageConverter(new MappingJackson2MessageConverter());
		try {
			stompSession = stompClient.connect("ws://localhost:" + port + "/stomp-chat", new StompSessionHandlerAdapter() {
				@Override
				public void afterConnected(StompSession session, StompHeaders connectedHeaders) {
				}
			}).get(1, SECONDS);
		} catch (InterruptedException | ExecutionException | TimeoutException e) {
			e.printStackTrace();
		}
	}


	// 소켓 통신시 send -> subscribe 형태...

	@Override
	public <T, R> R request(String endPoint, T t, Class<R> clazz, UnaryOperator<R> callback) {
		stompSession.send(endPoint, t);
		return null;
	}

	// GET
	@Override
	public <T> T fetch(String endPoint, Class<T> clazz, UnaryOperator<T> callback) throws InterruptedException {
		StompResult<T> latch = new StompResult<>();
		stompSession.subscribe(endPoint, new StompFrameTestHandler<T>(clazz) {
			@Override
			public void handleFrameImpl(StompHeaders headers, T payload) {
				latch.countDown(callback.apply(payload));
			}
		});
		if (!latch.await(1, MINUTES)) throw new RuntimeException();
		return latch.getResult();
	}


	private List<Transport> createTransportClient() {
		List<Transport> transports = new ArrayList<>(1);
		transports.add(new WebSocketTransport(new StandardWebSocketClient()));
		return transports;
	}

	private abstract static class StompFrameTestHandler<T> implements StompFrameHandler {
		private Class<T> clazz;

		public StompFrameTestHandler(Class<T> clazz) {
			this.clazz = clazz;
		}

		@Override
		public Type getPayloadType(StompHeaders headers) {
			return clazz;
		}

		public abstract void handleFrameImpl(StompHeaders headers, T payload);

		@Override
		@SuppressWarnings("unchecked")
		public void handleFrame(StompHeaders headers, Object payload) {
			handleFrameImpl(headers, (T) payload);
		}
	}
}
