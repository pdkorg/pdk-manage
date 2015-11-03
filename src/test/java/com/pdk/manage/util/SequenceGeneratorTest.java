package com.pdk.manage.util;


import org.apache.commons.codec.digest.DigestUtils;
import org.junit.Assert;
import org.junit.Test;

import java.io.UnsupportedEncodingException;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.CyclicBarrier;


/**
 * Created by hubo on 15/8/6
 */
public class SequenceGeneratorTest {
    @Test
    public void testNextId() {

        CyclicBarrier b = new CyclicBarrier(4);
        CountDownLatch l = new CountDownLatch(4);

        Set<String> idSet = new HashSet<>();

        IdTestWorker i1 = new IdTestWorker(SequenceGenerator.getInstance(), b, l, idSet);
        IdTestWorker i2 = new IdTestWorker(SequenceGenerator.getInstance(), b, l, idSet);
        IdTestWorker i3 = new IdTestWorker(SequenceGenerator.getInstance(), b, l, idSet);
        IdTestWorker i4 = new IdTestWorker(SequenceGenerator.getInstance(), b, l, idSet);

        Thread t1 = new Thread(i1, "thread1");
        Thread t2 = new Thread(i2, "thread2");
        Thread t3 = new Thread(i3, "thread3");
        Thread t4 = new Thread(i4, "thread4");

        t1.start();
        t2.start();
        t3.start();
        t4.start();

        try {
            l.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        Assert.assertEquals(20000, idSet.size());

    }

    @Test
    public void testPwd() throws UnsupportedEncodingException {

        String pwd = DigestUtils.md5Hex("123456");

        System.out.println(pwd);

    }


    class IdTestWorker implements Runnable {

        private final SequenceGenerator s;

        private CyclicBarrier b;

        private CountDownLatch l;

        private final Set<String> idSet;

        public IdTestWorker(SequenceGenerator s, CyclicBarrier b, CountDownLatch l, Set<String> idSet) {
            this.s = s;
            this.b = b;
            this.l = l;
            this.idSet = idSet;
        }

        @Override
        public void run() {
            try {
                b.await();
                for(int i = 0; i < 5000; i++) {
                    String id = s.nextId();
                    boolean f;
                    synchronized (idSet) {
                        f = idSet.add(id);
                    }
                    if(!f) {
                        System.out.println("duplicate : " + id);
                    }else {
                        System.out.println(id);
                    }
                }
                l.countDown();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

    }

}


