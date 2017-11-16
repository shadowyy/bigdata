package kerberos;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.hbase.HBaseConfiguration;
import org.apache.hadoop.hbase.client.Connection;
import org.apache.hadoop.hbase.client.ConnectionFactory;
import org.apache.hadoop.security.UserGroupInformation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

/**
 * @author yuyue
 * @version 2017-11-9 0009 17:59
 */
public class Test {
    private static final Logger LOGGER = LoggerFactory.getLogger(Test.class);

    public static void main(String[] args) {
        Configuration conf = HBaseConfiguration.create();
        //conf.addResource("C:\\bigdata\\hbase\\kerberos\\core-site.xml");	//hBaseSitePath 改成hbase-site.xml文件的路径
        //conf.addResource("C:\\bigdata\\hbase\\kerberos\\core-site.xml");	//coreSitePath 改成core-site.xml文件的路径
        //conf.set("hadoop.security.authentication", "Kerberos");
        //UserGroupInformation.setConfiguration(conf);
        //System.setProperty("java.security.krb5.conf", "C:\\ProgramData\\MIT\\Kerberos5\\krb5.ini");
        //try {
        //    UserGroupInformation.loginUserFromKeytab("wangpeng@ZZ.COM", "C:\\ProgramData\\MIT\\Kerberos5\\krb5.ini");   //userPrincipal 改成对应的你要登录hbase的principal，我用的是wangpeng@ZZ.COM,
        //    Connection connection = ConnectionFactory.createConnection(conf);
        //} catch (IOException e) {
        //    LOGGER.error("",e);
        //}


        conf.addResource(new Path("C:\\bigdata\\hbase\\kerberos\\core-site.xml"));
        conf.addResource(new Path("C:\\bigdata\\hbase\\kerberos\\hbase-site.xml"));
        conf.set("hadoop.security.authentication", "Kerberos");
        UserGroupInformation.setConfiguration(conf);
        System.setProperty("java.security.krb5.conf", "C:\\documents\\krb5.conf");
        try {
            UserGroupInformation.loginUserFromKeytab("wangpeng@ZZ.COM", "C:\\bigdata\\hbase\\kerberos\\wangpeng.keytab");
            Connection connection = ConnectionFactory.createConnection(conf);
        } catch (IOException ex) {
            LOGGER.error("HbaseService | UserGroupInformation doAs failed | {}", conf, ex);
        }
    }

}
