package cn.gking.hupaispring.core;


import cn.gking.gtools.GDataBase;
import cn.gking.gtools.GDataBaseIOAdapterBufferedInputStream;

import java.io.IOException;
import java.io.InputStream;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;

public class NetworkHandler {
    Socket socket;
    Map<String, OnResponseListener> actions=new HashMap<>();
    public NetworkHandler(Socket socket) {
        this.socket = socket;
        new Thread(){
            @Override
            public void run() {
                try {
                    InputStream inputStream = socket.getInputStream();
                    while (true){
                        if(inputStream.available()>0){
                            GDataBase gDataBase = new GDataBase(new GDataBaseIOAdapterBufferedInputStream(inputStream,null));
                            OnResponseListener action = actions.get(gDataBase.getString("action"));
                            System.out.println(gDataBase);
                            if(action!=null) action.onresponse(gDataBase);
                            else System.err.println("NO SUCH ACTION"+gDataBase.getString("action"));
                        }
                    }
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        }.start();
    }
    public void write(byte[] bytes) throws IOException {
        socket.getOutputStream().write(bytes);
        socket.getOutputStream().flush();
    }
    public void setListener(String name, OnResponseListener onResponseListener){
        actions.put(name,onResponseListener);
    }
    public void addHandler(String name, OnResponseListener onResponseListener){
        setListener(name,onResponseListener);
    }
    public interface OnResponseListener {
        void onresponse(GDataBase data);
    }
}
