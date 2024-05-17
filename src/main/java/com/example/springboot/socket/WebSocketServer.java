package com.example.springboot.socket;

import com.example.springboot.Application;
import com.example.springboot.pojo.*;
import com.example.springboot.service.UserScoreService;
import com.example.springboot.service.UserService;
import com.example.springboot.service.WordService;
import com.example.springboot.service.WrongWordService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.websocket.*;
import jakarta.websocket.server.PathParam;
import jakarta.websocket.server.ServerEndpoint;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

import static java.lang.Thread.sleep;

@Component
@ServerEndpoint(value = "/api/websocket/{username}/{book_id}")
@Slf4j
public class WebSocketServer {

    private static final Logger logger = LoggerFactory.getLogger(Application.class);

    // 存储所有连接的WebSocket客户端
    private static final ConcurrentHashMap<Integer,ConcurrentHashMap<Long,ConcurrentHashMap<String, OnlineUser>>> roomSessionMap = new ConcurrentHashMap<>();
    private static final ConcurrentHashMap<Long, OnlineGameMessage> wordsMap = new ConcurrentHashMap<>();
    private static ApplicationContext applicationContext;

    public static void setApplicationContext(ApplicationContext context) {
        applicationContext = context;
    }

    public static WordService getWordService() {
        return wordService;
    }

    public static void setWordService(WordService wordService) {
        WebSocketServer.wordService = wordService;
    }

    public static WordService wordService;
    public static UserService userService;
    public static WrongWordService wrongWordService;
    public static UserScoreService userScoreService;

    public static UserService getUserService() {
        return userService;
    }

    public static void setUserService(UserService userService) {
        WebSocketServer.userService = userService;
    }

    private Long m_roomId;
    private static Long roomId = 0L;

    public ConcurrentHashMap<Integer,ConcurrentHashMap<Long,ConcurrentHashMap<String, OnlineUser>>> getSessionMap() {
        return roomSessionMap;
    }
    public ConcurrentHashMap<Long, OnlineGameMessage> getWordsMap() {
        return wordsMap;
    }
    private ObjectMapper objectMapper = new ObjectMapper(); // Jackson的对象映射器
    @OnOpen
    public void onOpen(Session session, @PathParam("username") String username, @PathParam("book_id") Integer bookId) {
        OnlineUser onlineUser = new OnlineUser(username, -1L, 0, false, session);
        ConcurrentHashMap<String, OnlineUser> sessionMap = new ConcurrentHashMap<>();
        sessionMap.put(username, onlineUser);
        ConcurrentHashMap<Long,ConcurrentHashMap<String, OnlineUser>> room = roomSessionMap.get(bookId);
        if (room == null || room.isEmpty()) {
            room = new ConcurrentHashMap<>();
            sessionMap.get(username).setRoomId(roomId);
            room.put(roomId, sessionMap);
            m_roomId = roomId;
            roomId += 1L;
            roomSessionMap.put(bookId, room);
        } else {
            // 遍历room寻找房间人数小于2的房间，未找到则新创建一个房间
            boolean success = false;
            for (Map.Entry<Long,ConcurrentHashMap<String, OnlineUser>> entry : room.entrySet()) {
                if (entry.getValue().size() < 2) {
                    m_roomId = entry.getKey();
                    onlineUser.setRoomId(m_roomId);
                    entry.getValue().put(username, onlineUser);
                    success = true;
                    logger.info("有新的用户进入，book_id={}，userId={}，当前{}号房间在线人数：{}",bookId, username, m_roomId, roomSessionMap.get(bookId).get(m_roomId).size());
                    sendToAllUserForRoom(bookId, m_roomId, "房间信息");
                    //session.getBasicRemote().sendText("连接房间成功房间号：" + entry.getKey());
                }
            }
            if (!success) {
                m_roomId = roomId;
                onlineUser.setRoomId(m_roomId);
                sessionMap.put(username, onlineUser);
                room.put(m_roomId, sessionMap);
                roomId += 1L;
                logger.info("有新的用户进入，book_id={}，userId={}，当前{}号房间在线人数：{}",bookId, username, m_roomId, roomSessionMap.get(bookId).get(m_roomId).size());
                sendToAllUserForRoom(bookId, m_roomId, "房间信息");
            }
            if(roomSessionMap.get(bookId).get(m_roomId).size() == 2){
                ArrayList<Words> words;
                words = (ArrayList<Words>) wordService.findAllWordsByBookId(bookId);
                Collections.shuffle(words);
                // 截取12个单词
                if (words.size() > 50)
                    words = new ArrayList<>(words.subList(0, 50));
//                for (Words word : words) {
//                    logger.info("onOpen: 下标：{}, 单词：{}" , words.indexOf(word), word.getWord());
//                }
                wordsMap.put(m_roomId, new OnlineGameMessage(words, false, 0, 0));
                sendWordsToAllUser(bookId, m_roomId, getWordItem());
            }
        }
    }

    private WordItem getWordItem() {
        ArrayList<Words> words = new ArrayList<>(wordsMap.get(m_roomId).getWords()); // 直接赋值不new会导致原words被更改
        Integer index = wordsMap.get(m_roomId).getWordIndex();
        Words word = words.get(index);
        String selectWord = word.getWord();
        //logger.info("getWordItem: 当前单词下标：{}, 单词：{}", index, selectWord);
        ArrayList<String> options = new ArrayList<>();
        options.add(word.getTranslation().getMeanings().get(0).toString());
        Collections.shuffle(words);
        for (int i = 0, j = 0; i < 3; i++, j++) {
            if (words.get(j).getWord().equals(selectWord)){
                j++;
            }
            if(j < words.size()) options.add(words.get(j).getTranslation().getMeanings().get(0).toString());
        }
        // 获得0~3的随机数
        int answer = new Random().nextInt(4);
        // 将options下标为0位置的单词放到options下标为answer位置
        Collections.swap(options, 0, answer);
        // logger.info("单词：{}，答案：{}", options, answer);
        return new WordItem(word, options, answer);
    }

    @OnClose
    public void onClose(Session session, @PathParam("username") String userId, @PathParam("book_id") Integer bookId) {
        roomSessionMap.get(bookId).get(m_roomId).remove(userId);
        try {
            if (session != null && session.isOpen()) {
                session.close();
            }
        } catch (IOException e) {
            // 处理异常，可能是日志记录或其他操作
            System.err.println("关闭 Session 时出错: " + e.getMessage());
        }
        sendMessageToAllUserForRoom(bookId, m_roomId, -1, "用户" + userId + "退出");
        logger.info("用户{}退出，当前房间{}在线人数：{}", userId, m_roomId, roomSessionMap.get(bookId).get(m_roomId).size());
    }

    @OnMessage
    public void onMessage(String message, Session session, @PathParam("username") String userId, @PathParam("book_id") Integer bookId) throws JsonProcessingException, InterruptedException {
        logger.info("收到用户{}的消息：{}", userId, message);
        JsonNode jsonNode = objectMapper.readTree(message);
        if (jsonNode.has("code")) {
            Integer type = jsonNode.get("code").asInt();
            logger.info("用户{}发送了消息类型：{}", userId, type);
            switch (type) {
                case 1: // 退出房间
                    roomSessionMap.get(bookId).get(m_roomId).get(userId).closeSession();
                    break;
                case 2: // 获取单词
                    sendWordsToAllUser(bookId, m_roomId, getWordItem());
                    break;
                case 3: // 获取房间信息
                    sendToAllUserForRoom(bookId, m_roomId, "房间信息");
                    break;
                case 4:
                    break;
                case 5: // 答题完成
                    Long score = objectMapper.readValue(jsonNode.get("data").toString(), Long.class);
                    sendScoreToOtherUser(bookId, m_roomId, session, score);
                    wordsMap.get(m_roomId).setResponseNum(wordsMap.get(m_roomId).getResponseNum() + 1);
                    if (wordsMap.get(m_roomId).getResponseNum() == 2) {
                        // 延时两秒
                        //sleep(1000);
                        wordsMap.get(m_roomId).setWordIndex(wordsMap.get(m_roomId).getWordIndex() + 1);
                        if (wordsMap.get(m_roomId).getWordIndex() == 12) {
                            wordsMap.get(m_roomId).setIsEnd(true);
                            logger.info("房间{}单词已全部回答完毕", m_roomId);
                            sendMessageToAllUserForRoom(bookId, m_roomId, 8, "单词已全部回答完毕");
                        } else {
                            logger.info("房间{}单词已全部回答完毕, 下一个单词：{}", m_roomId, wordsMap.get(m_roomId).getWords().get(wordsMap.get(m_roomId).getWordIndex()));
                            sendWordsToAllUser(bookId, m_roomId, getWordItem());
                            wordsMap.get(m_roomId).setResponseNum(0);
                        }
                    }
                    break;
                case 6:
                    WrongWord wrongWord = objectMapper.readValue(jsonNode.get("data").toString(), WrongWord.class);
                    wrongWordService.save(wrongWord);
                    break;
                }
        }
    }
    @OnError
    public void onError(Session session, Throwable error, @PathParam("username") String userId, @PathParam("book_id") Integer bookId) {
        logger.error("WebSocket连接出错");
        sendMessageToAllUserForRoom(bookId, m_roomId, -1, "用户" + userId + "退出");
        roomSessionMap.get(bookId).get(m_roomId).remove(userId);
        error.printStackTrace();
    }
    private void sendToAllUserForRoom(Integer bookId, Long roomId, String type) {
        // 给房间里的所有用户发送信息
        ConcurrentHashMap<String, OnlineUser> room = roomSessionMap.get(bookId).get(roomId);
        Collection<OnlineUser> users = room.values();
        List<OnlineUser> usernames = new ArrayList<>(users);
        for (OnlineUser session : users) {
            try {
                String jsonMessage = objectMapper.writeValueAsString(Result.success(10, usernames));
                // 服务器向客户端发送消息
                session.getSession().getBasicRemote().sendText(jsonMessage);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
    private void sendMessageToAllUserForRoom(Integer bookId, Long roomId, Integer code, String message) {
        // 给房间里的所有用户发送信息
        ConcurrentHashMap<String, OnlineUser> room = roomSessionMap.get(bookId).get(roomId);
        Collection<OnlineUser> users = room.values();
        for (OnlineUser session : users) {
            try {
                String jsonMessage = objectMapper.writeValueAsString(Result.success(code, message));
                // 服务器向客户端发送消息
                session.getSession().getBasicRemote().sendText(jsonMessage);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    private void sendWordsToAllUser(Integer bookId, Long roomId, WordItem wordItem) {
        // 给房间里的所有用户发送信息
        ConcurrentHashMap<String, OnlineUser> room = roomSessionMap.get(bookId).get(roomId);
        Collection<OnlineUser> users = room.values();
        //ArrayList<Words> words = wordsMap.get(roomId).getWords();
        for (OnlineUser session : users) {
            try {
                String jsonMessage = objectMapper.writeValueAsString(Result.success(3, wordItem));
                // 服务器向客户端发送消息
                session.getSession().getBasicRemote().sendText(jsonMessage);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
    private void sendScoreToOtherUser(Integer bookId, Long roomId, Session s, Long score) {
        // 给房间里的所有用户发送信息
        ConcurrentHashMap<String, OnlineUser> room = roomSessionMap.get(bookId).get(roomId);
        Collection<OnlineUser> users = room.values();
        //ArrayList<Words> words = wordsMap.get(roomId).getWords();
        for (OnlineUser session : users) {
            if(session.getSession() != s){
                try {
                    String jsonMessage = objectMapper.writeValueAsString(Result.success(0, score));
                    // 服务器向客户端发送消息
                    session.getSession().getBasicRemote().sendText(jsonMessage);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }

    // 写一个处理客户端发送的Result类型json数据的函数
    public void handleResult(String message, Session session, @PathParam("username") String userId, @PathParam("book_id") Integer bookId) throws IOException {
        Result result = objectMapper.readValue(message, Result.class);
        //if(re)
    }
}
