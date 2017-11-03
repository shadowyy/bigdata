package com.alix.zookeeper;

import com.alix.util.PropUtil;
import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.KeeperException;
import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.ZooDefs;
import org.apache.zookeeper.ZooKeeper;

import java.io.IOException;
import java.util.concurrent.CountDownLatch;

/**
 * 创建一个zoo的组
 *
 * @author shadowyy
 * @version 2017/9/7 11:19
 */
public class CreateGroup implements Watcher {
    private static final int SESSION_TIMEOUT = 5000;
    private CountDownLatch connectedSignal = new CountDownLatch(1);
    private ZooKeeper zk;

    /**
     * 建立连接函数会立即返回，所以我们需要等待连接建立成功后再进行其他的操作。我们使用CountDownLatch来阻塞当前线程，直到zookeeper准备就绪
     *
     * @param hosts
     * @throws IOException
     * @throws InterruptedException
     */
    public void connect(String hosts) throws IOException, InterruptedException {
        zk = new ZooKeeper(hosts, SESSION_TIMEOUT, this);
        connectedSignal.await();
    }

    /**
     * znode的性质分为ephemeral和persistent两种。ephemeral性质的znode在创建他的客户端的会话结束，或者客户端以其他原因断开与服务器的连接时，会被自动删除
     * 而persistent性质的znode就不会被自动删除，除非客户端主动删除，而且不一定是创建它的客户端可以删除它，其他客户端也可以删除它。这里我们创建一个persistent的znod
     *
     * @param groupName
     * @throws KeeperException
     * @throws InterruptedException
     */
    public void create(String groupName) throws KeeperException, InterruptedException {
        String path = "/" + groupName;
        //参数包括：一是znode的path；二是znode的内容（一个二进制数组），三是一个access control list(ACL，访问控制列表，这里使用完全开放模式)
        String createdPath = zk.create(path, null/*data*/, ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT);
        System.out.println("Created " + createdPath);
    }

    public void close() throws InterruptedException {
        zk.close();
    }

    /**
     * 当客户端连接上了zookeeper服务器，Watcher将由process()函数接收一个连接成功的事件。我们接下来调用CountDownLatch，释放之前的阻塞
     *
     * @param event
     */
    @Override
    public void process(WatchedEvent event) {
        if (event.getState() == Event.KeeperState.SyncConnected) {
            connectedSignal.countDown();
        }
    }

    public static void main(String[] args) throws Exception {
        CreateGroup createGroup = new CreateGroup();
        createGroup.connect(PropUtil.load("zk.properties").getProperty("ip"));
        createGroup.create("zoo");
        createGroup.close();
    }
}