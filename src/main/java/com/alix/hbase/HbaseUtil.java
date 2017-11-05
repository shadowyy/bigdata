package com.alix.hbase;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hbase.HBaseConfiguration;
import org.apache.hadoop.hbase.HColumnDescriptor;
import org.apache.hadoop.hbase.HTableDescriptor;
import org.apache.hadoop.hbase.TableName;
import org.apache.hadoop.hbase.client.Admin;
import org.apache.hadoop.hbase.client.Connection;
import org.apache.hadoop.hbase.client.ConnectionFactory;

/**
 * hbase的工具类
 *
 * @author yuyue
 * @version 2017-10-31 0031 18:11
 */
public class HbaseUtil {
    // 与HBase数据库的连接对象
    private static Connection connection;

    // 数据库元数据操作对象
    private static Admin admin;


    public static void main(String[] args) throws Exception {
        test0();
        createTable();

    }

    private static void test0() throws Exception {
        System.setProperty("hadoop.home.dir","C:\\dev\\hadoop");

        // 取得一个数据库连接的配置参数对象
        Configuration conf = HBaseConfiguration.create();

        // 设置连接参数：HBase数据库所在的主机IP
        conf.set("hbase.zookeeper.quorum", "192.168.11.4");
        //conf.set("hbase.zookeeper.quorum", "10.15.100.248");
        // 设置连接参数：HBase数据库使用的端口
        conf.set("hbase.zookeeper.property.clientPort", "2181");

        // 取得一个数据库连接对象
        connection = ConnectionFactory.createConnection(conf);

        // 取得一个数据库元数据操作对象
        admin = connection.getAdmin();
    }

    private static void createTable() throws Exception {
        // 数据表表名
        String tableNameString = "t_book";

        // 新建一个数据表表名对象
        TableName tableName = TableName.valueOf(tableNameString);

        // 如果需要新建的表已经存在
        if(admin.tableExists(tableName)){

            System.out.println("表已经存在！");
        }
        // 如果需要新建的表不存在
        else{

            // 数据表描述对象
            HTableDescriptor hTableDescriptor = new HTableDescriptor(tableName);

            // 列族描述对象
            HColumnDescriptor family= new HColumnDescriptor("base");

            // 在数据表中新建一个列族
            hTableDescriptor.addFamily(family);

            // 新建数据表
            admin.createTable(hTableDescriptor);
        }

    }
}
