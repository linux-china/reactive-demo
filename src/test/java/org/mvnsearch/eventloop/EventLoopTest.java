package org.mvnsearch.eventloop;

import org.junit.Test;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

/**
 * event loop test
 *
 * @author linux_china
 */
public class EventLoopTest {

    @Test
    public void testEventLoop()  throws Exception{
        BlockingQueue<String> q = new LinkedBlockingQueue<>();
        new Thread(() -> {
            while (true) {
                try {
                    String t = q.take();
                    System.out.println(t);
                    //t will never be null, do something with it here.
                } catch (Exception ignore) {

                }
            }
        },"EventLoop").start();
        q.put("first");
        q.put("second");
        Thread.sleep(1000);
        q.put("three");
        Thread.sleep(1000);
    }
}
