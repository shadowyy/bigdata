package hdfs;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FileStatus;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;

import java.net.URI;

/**
 * //InetAddress.getCanonicalHostName()
 *
 * @author yuyue
 * @version 2017-11-3 0003 11:51
 */
public class Test {

    public static void main(String[] args) throws Exception {

        //System.out.println(InetAddress.getLocalHost().getHostAddress());

        String uri = "hdfs://localhost:9000/hbase";//指定文件
        Configuration conf = new Configuration();
        //FileSystem fs = FileSystem.get(new URI(uri), conf);
        //Path path = new Path(uri);
        //boolean isExists = fs.exists(path);
        //String str = isExists ? "Exists" : "Not Exists";
        //System.out.println("指定文件或目录" + str);

        FileSystem fs = FileSystem.get(new URI(uri), conf);
        Path path = new Path(uri);
        FileStatus status[] = fs.listStatus(path);
        for (int i = 0; i < status.length; i++) {
            System.out.println(status[i].getPath().toString());
        }
    }
}
