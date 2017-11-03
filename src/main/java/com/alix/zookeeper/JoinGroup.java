package com.alix.zookeeper;

import com.alix.util.PropUtil;
import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.KeeperException;
import org.apache.zookeeper.ZooDefs;

/**
 * 接下来我们实现如何在一个组中注册成员。我们将使用ephemeral znode来创建这些成员节点。那么当客户端程序退出时，这些成员将被删除。+
 * @author shadowyy
 * @version 2017/9/7 11:35
 */
public class JoinGroup extends ConnectionWatcher {

    public void join(String groupName, String memberName) throws KeeperException, InterruptedException {
        String path = "/" + groupName + "/" + memberName;
        String createdPath = zk.create(path, null/*data*/, ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT);
        System.out.println("Created " + createdPath);
    }

    public static void main(String[] args) throws Exception {
        JoinGroup joinGroup = new JoinGroup();
        joinGroup.connect(PropUtil.load("zk.properties").getProperty("ip"));
        joinGroup.join("zoo","xxx");
        // stay alive until process is killed or thread is interrupted
        //Thread.sleep(Long.MAX_VALUE);
    }
}
