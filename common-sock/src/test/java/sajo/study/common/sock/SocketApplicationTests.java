package sajo.study.common.sock;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.messaging.converter.MappingJackson2MessageConverter;
import org.springframework.messaging.simp.stomp.StompFrameHandler;
import org.springframework.messaging.simp.stomp.StompHeaders;
import org.springframework.messaging.simp.stomp.StompSession;
import org.springframework.messaging.simp.stomp.StompSessionHandlerAdapter;
import org.springframework.test.context.junit.jupiter.SpringExtension;
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

import static java.util.concurrent.TimeUnit.MINUTES;
import static java.util.concurrent.TimeUnit.SECONDS;
import static org.assertj.core.api.Assertions.fail;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Import(WebSocketConfig.class)
public class SocketApplicationTests {
    @Value("${local.server.port}")
    private int port;
    private StompSession stompSession;

    @BeforeEach
    public void 연결() throws InterruptedException, ExecutionException, TimeoutException {
        WebSocketStompClient stompClient;
        stompClient = new WebSocketStompClient(new SockJsClient(createTransportClient()));
        stompClient.setMessageConverter(new MappingJackson2MessageConverter());
        stompSession = stompClient.connect("ws://localhost:" + port + "/stomp-chat", new StompSessionHandlerAdapter() {
            @Override
            public void afterConnected(StompSession session, StompHeaders connectedHeaders) {
            }
        }).get(1, SECONDS);
    }

    @Test
    public void 방에_접속() throws InterruptedException {
        assertNotNull(stompSession);
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
                assertEquals(room, payload);
                latch.countDown();
            }
        });
        if(!latch.await(1, MINUTES)) fail("not end");
    }

    @Test
    public void 메세지_전송() throws InterruptedException {
        assertNotNull(stompSession);
        CountDownLatch latch = new CountDownLatch(1);
        ChatRoomDTO room = new ChatRoomDTO("MESSAGE_SNED");
        MessageDTO message = new MessageDTO("from", "message");
        stompSession.send("/app/chat/" + room.getName() + "/message", message);
        stompSession.subscribe("/topic/chat/" + room.getName() + "/message", new StompFrameTestHandler<MessageDTO>(MessageDTO.class) {
            @Override
            public void handleFrame(StompHeaders headers, MessageDTO payload) {
                assertEquals(message, payload);
                latch.countDown();
            }
        });
        if(!latch.await(1, MINUTES)) fail("not end");
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
