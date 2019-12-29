package sajo.study.common.api;

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
import sajo.study.common.api.configuration.WebSocketConfig;
import sajo.study.common.core.dto.ChatRoom;

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
    private WebSocketStompClient stompClient;
    private StompSession stompSession;

    @Before
    public void 연결() throws Exception {
        stompClient = new WebSocketStompClient(new SockJsClient(createTransportClient()));
        stompClient.setMessageConverter(new MappingJackson2MessageConverter());
        stompSession = stompClient.connect("ws://localhost:"+port+"/stomp-chat", new StompSessionHandlerAdapter() {
            @Override
            public void afterConnected(StompSession session, StompHeaders connectedHeaders) {
                log.info(session.getSessionId());
                log.info("Connected");
            }
        }).get(1, SECONDS);
    }

    @Test
    public void 방에_접속() throws InterruptedException, ExecutionException, TimeoutException {
        ChatRoom room = new ChatRoom("NAME");
        CountDownLatch latch = new CountDownLatch(1);
        stompSession.subscribe("/topic/room/"+room.getName(),new StompFrameHandler(){
            @Override
            public Type getPayloadType(StompHeaders headers) {
                return ChatRoom.class;
            }

            @Override
            public void handleFrame(StompHeaders headers, Object payload) {
                log.info(payload.toString());
                assertEquals(room, payload);
                latch.countDown();
            }
        });
        stompSession.send("/app/chat/join", room);
        latch.await();
    }

    private List<Transport> createTransportClient() {
        List<Transport> transports = new ArrayList<>(1);
        transports.add(new WebSocketTransport(new StandardWebSocketClient()));
        return transports;
    }
}
