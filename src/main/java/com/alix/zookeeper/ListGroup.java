package com.alix.zookeeper;

import com.alix.util.PropUtil;
import org.apache.zookeeper.KeeperException;

import java.util.List;

/**
 * @author shadowyy
 * @version 2017/9/7 11:49
 */
public class ListGroup extends ConnectionWatcher {
    public void list(String groupName) throws KeeperException, InterruptedException {
        String path = "/" + groupName;
        try {
            List<String> children = zk.getChildren(path, false);
            if (children.isEmpty()) {
                System.out.printf("No members in group %s\n", groupName);
                System.exit(1);
            }
            for (String child : children) {
                System.out.println(child);
            }
        } catch (KeeperException.NoNodeException e) {
            System.out.printf("Group %s does not exist\n", groupName);
            System.exit(1);
        }
    }

    public static void main(String[] args) throws Exception {
        ListGroup listGroup = new ListGroup();
        listGroup.connect(PropUtil.load("zk.properties").getProperty("ip"));
        listGroup.list("brokers");
        listGroup.close();
    }
}
