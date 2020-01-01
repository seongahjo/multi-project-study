package sajo.study.common.sock;

import lombok.extern.slf4j.Slf4j;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.messaging.converter.MappingJackson2MessageConverter;
import org.springframework.messaging.simp.stomp.StompFrameHandler;
import org.springframework.messaging.simp.stomp.StompHeaders;
import org.springframework.messaging.simp.stomp.StompSession;
import org.springframework.messaging.simp.stomp.StompSessionHandlerAdapter;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.socket.client.standard.StandardWebSocketClient;
import org.springframework.web.socket.messaging.WebSocketStompClient;
import org.springframework.web.socket.sockjs.client.SockJsClient;
import org.springframework.web.socket.sockjs.client.Transport;
import org.springframework.web.socket.sockjs.client.WebSocketTransport;
import sajo.study.common.core.dto.BaseDTO;
import sajo.study.common.core.dto.ChatRoomDTO;
import sajo.study.common.core.dto.MessageDTO;
import sajo.study.common.sock.configuration.WebSocketConfig;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeoutException;

import static java.util.concurrent.TimeUnit.SECONDS;
import static org.junit.Assert.assertEquals;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Slf4j
@Import(WebSocketConfig.class)
public class SocketApplicationTests {
    @Value("${local.server.port}")
    private int port;
    private StompSession stompSession;

    @Before
    public void 연결() throws InterruptedException, ExecutionException, TimeoutException {
        WebSocketStompClient stompClient;
        stompClient = new WebSocketStompClient(new SockJsClient(createTransportClient()));
        stompClient.setMessageConverter(new MappingJackson2MessageConverter());
        stompSession = stompClient.connect("ws://localhost:" + port + "/stomp-chat", new StompSessionHandlerAdapter() {
            @Override
            public void afterConnected(StompSession session, StompHeaders connectedHeaders) {
                log.info(session.getSessionId());
                log.info("Connected");
            }
        }).get(1, SECONDS);
    }

    @Test
    public void 방에_접속() throws InterruptedException {
        ChatRoomDTO room = new ChatRoomDTO("NAME");
        stompSession.send("/app/chat/join", room);
        CountDownLatch latch = new CountDownLatch(1);
        stompSession.subscribe("/topic/chat/" + room.getName(), new StompFrameHandler() {
            @Override
            public Type getPayloadType(StompHeaders headers) {
                return ChatRoomDTO.class;
            }

            @Override
            public void handleFrame(StompHeaders headers, Object payload) {
                log.info(payload.toString());
                assertEquals(room, payload);
                latch.countDown();
            }
        });
        latch.await();
    }

    @Test
    public void 메세지_전송() throws InterruptedException {
        CountDownLatch latch = new CountDownLatch(1);
        ChatRoomDTO room = new ChatRoomDTO("MESSAGE_SNED");
        MessageDTO message = new MessageDTO("from", "message");
        stompSession.send("/app/chat/" + room.getName() + "/message", message);
        stompSession.subscribe("/topic/chat/" + room.getName() + "/message", new StompFrameTestHandler<MessageDTO>(MessageDTO.class) {
            @Override
            public void handleFrame(StompHeaders headers, MessageDTO payload) {
                log.info("RECEIVED {} ", payload.toString());
                assertEquals(message, payload);
                latch.countDown();
            }
        });
        latch.await();
    }

    private abstract static class StompFrameTestHandler<T extends BaseDTO> implements StompFrameHandler {
        private Class<T> clazz;

        public StompFrameTestHandler(Class<T> clazz) {
            this.clazz = clazz;
        }

        @Override
        public Type getPayloadType(StompHeaders headers) {
            return clazz;
        }

        public abstract void handleFrame(StompHeaders headers, T payload);

        @Override
        @SuppressWarnings("unchecked")
        public void handleFrame(StompHeaders headers, Object payload) {
            handleFrame(headers, (T) payload);
        }
    }

    private List<Transport> createTransportClient() {
        List<Transport> transports = new ArrayList<>(1);
        transports.add(new WebSocketTransport(new StandardWebSocketClient()));
        return transports;
    }
}
