package com.nginxtelegrm.NginxTelegramMessage.repository;
import com.nginxtelegrm.NginxTelegramMessage.model.param.messageApplication.OutMessageAppParam;
import org.springframework.stereotype.Repository;

import java.util.LinkedList;
import java.util.Queue;
import java.util.concurrent.SynchronousQueue;

@Repository
public class MessageApplicationRepository {

    Queue<OutMessageAppParam> queue;
    Object lock;
    public MessageApplicationRepository(){
        queue = new LinkedList<>();
        lock = new Object();
    }

    public void insert(OutMessageAppParam message){
        synchronized (lock){
            queue.add(message);
        }

    }

    public OutMessageAppParam get(){
        synchronized (lock){
            if (queue.isEmpty())
                return null;
            return queue.poll();
        }
    }
}
