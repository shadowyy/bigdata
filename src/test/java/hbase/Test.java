package hbase;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hbase.Cell;
import org.apache.hadoop.hbase.HBaseConfiguration;
import org.apache.hadoop.hbase.HColumnDescriptor;
import org.apache.hadoop.hbase.HTableDescriptor;
import org.apache.hadoop.hbase.KeyValue;
import org.apache.hadoop.hbase.TableName;
import org.apache.hadoop.hbase.client.Admin;
import org.apache.hadoop.hbase.client.Connection;
import org.apache.hadoop.hbase.client.ConnectionFactory;
import org.apache.hadoop.hbase.client.HTable;
import org.apache.hadoop.hbase.client.Result;
import org.apache.hadoop.hbase.client.ResultScanner;
import org.apache.hadoop.hbase.client.Scan;
import org.apache.hadoop.hbase.util.Bytes;

import java.io.IOException;

/**
 * @author yuyue
 * @version 2017-11-8 0008 16:42
 */
public class Test {
    private static Connection connection;
    // 数据库元数据操作对象
    private static Admin admin;

    // 声明静态配置
    static Configuration conf = null;

    static {
        //System.setProperty("hadoop.home.dir", "C:\\dev\\hadoop");
        conf = HBaseConfiguration.create();
        conf.set("hbase.zookeeper.quorum", "localhost");
        conf.set("hbase.zookeeper.property.clientPort", "2181");
        try {
            connection = ConnectionFactory.createConnection(conf);
            admin = connection.getAdmin();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public static void main(String[] args) throws Exception {
        //createTable();
        getResultScann("fk:t_fk_derived_result");
    }


    /**
     * 遍历查询hbase表
     */
    public static void getResultScann(String tableName) throws IOException {
        Scan scan = new Scan();
        ResultScanner rs = null;
        HTable table = new HTable(conf, Bytes.toBytes(tableName));
        try {
            rs = table.getScanner(scan);
            for (Result r : rs) {
                for (KeyValue kv : r.list()) {
                    System.out.println("row:" + Bytes.toString(kv.getRow()));
                    System.out.println("family:" + Bytes.toString(kv.getFamily()));
                    System.out.println("qualifier:" + Bytes.toString(kv.getQualifier()));
                    System.out.println("value:" + Bytes.toString(kv.getValue()));
                    System.out.println("timestamp:" + kv.getTimestamp());
                    System.out.println("-------------------------------------------");
                }
                for (Cell cell : r.listCells()) {
                    System.out.println(cell.getFamilyArray());
                }
            }
        } finally {
            rs.close();
        }
    }

    private static void createTable(String tableName) throws Exception {

        // 新建一个数据表表名对象
        TableName t = TableName.valueOf(tableName);

        if (admin.tableExists(t)) {
            System.out.println("表已经存在！");
        } else {
            // 数据表描述对象
            HTableDescriptor hTableDescriptor = new HTableDescriptor(t);

            // 列族描述对象
            HColumnDescriptor family = new HColumnDescriptor("base");

            // 在数据表中新建一个列族
            hTableDescriptor.addFamily(family);

            // 新建数据表
            admin.createTable(hTableDescriptor);
        }

    }

}
