package com.alix.zookeeper;

import com.alix.util.PropUtil;
import org.apache.zookeeper.KeeperException;

import java.util.List;

/**
 * @author shadowyy
 * @version 2017/9/7 11:57
 */
public class DeleteGroup extends ConnectionWatcher {
    public void delete(String groupName) throws KeeperException, InterruptedException {
        String path = "/" + groupName;

        try {
            List<String> children = zk.getChildren(path, false);
            for (String child : children) {
                zk.delete(path + "/" + child, -1);
            }
            zk.delete(path, -1);
        } catch (KeeperException.NoNodeException e) {
            System.out.printf("Group %s does not exist\n", groupName);
            System.exit(1);
        }
    }

    public static void main(String[] args) throws Exception {
        DeleteGroup deleteGroup = new DeleteGroup();
        deleteGroup.connect(PropUtil.load("zk.properties").getProperty("ip"));
        deleteGroup.delete("zoo");
        deleteGroup.close();
    }
}
