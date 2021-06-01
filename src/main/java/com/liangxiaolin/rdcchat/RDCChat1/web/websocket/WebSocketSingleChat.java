package com.liangxiaolin.rdcchat.RDCChat1.web.websocket;

import javax.websocket.*;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.HashMap;
import java.util.Set;

/**
 * @ServerEndpoint 注解标识该类是websocket类
 */
@ServerEndpoint("/webSocketSingleChat")
public class WebSocketSingleChat {
    //将用户的名字和每个websocket连接存在map集合中，static修饰，其他类可以直接调用
    private static HashMap<String,WebSocketSingleChat> webSocketMap = new HashMap<String,WebSocketSingleChat>();

    //session保存用户请求过来的信息
    private Session session;
    private String userName;

    /**
     *
     *
     * @param session
     * 当有客户端与服务器建立连接时调用
     */
    @OnOpen
    public void onOpen(Session session) {
        String values = session.getQueryString();
        System.out.println("values:"+values);
        String userName0 = values.substring(0,values.indexOf("&"));
        String userName = userName0.substring(userName0.indexOf("=")+1);
		/*String receiver0 = values.substring(values.indexOf("&")+1);
		String receiver = receiver0.substring(receiver0.indexOf("=")+1);*/
        this.session = session;
        try {
            this.userName = URLDecoder.decode(userName,"UTF-8");
        } catch (UnsupportedEncodingException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        webSocketMap.put(this.userName, this); //加入用户姓名和此连接
    }

    /**
     *与服务器连接关闭时调用
     */
    @OnClose
    public void onClose() {
        webSocketMap.remove(this.userName);
    }

    /**
     *
     *
     * @param message
     * @param session
     * 收到来自客户端的消息时调用，session保存发送消息的客户端信息
     */
    @OnMessage
    public void onMessage(String message, Session session) {
        String values = session.getQueryString();
        String userName0 = values.substring(0,values.indexOf("&"));
        String userName = userName0.substring(userName0.indexOf("=")+1);
        System.out.println("userName"+userName);
        String receiver0 = values.substring(values.indexOf("&")+1);
        String receiver = null;//receiver0.substring(receiver0.indexOf("=")+1);
        String messageUser=null;
        try {
            messageUser = URLDecoder.decode(userName, "UTF-8");
            receiver = URLDecoder.decode(receiver0.substring(receiver0.indexOf("=")+1), "UTF-8");
            System.out.println("receiver"+receiver);
        } catch (UnsupportedEncodingException e1) {
            // TODO Auto-generated catch block
            e1.printStackTrace();
        }

        //
        Set<String> keys = webSocketMap.keySet();
        for (String key : keys) {
            if (key.equals(receiver)) {//---连接
                try {
                    webSocketMap.get(key).sendMessage(messageUser+"说："+message);
                } catch (IOException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                    continue;
                }
            }
        }

    }

    /**
     *发生错误时调用
     *
     * @param session
     * @param error
     */
    @OnError
    public void onError(Session session, Throwable error) {

        error.printStackTrace();
    }


    public void sendMessage(String message) throws IOException {
        this.session.getBasicRemote().sendText(message);
        // this.session.getAsyncRemote().sendText(message);
    }


    public static synchronized Set<String> getUser() {

        return webSocketMap.keySet();
    }
}
