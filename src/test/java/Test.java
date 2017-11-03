import java.net.InetAddress;

/**
 *         //InetAddress.getCanonicalHostName()
 * @author yuyue
 * @version 2017-11-3 0003 11:51
 */
public class Test {

    public static void main(String[] args) throws Exception {

        System.out.println(InetAddress.getLocalHost().getHostAddress());
    }
}
