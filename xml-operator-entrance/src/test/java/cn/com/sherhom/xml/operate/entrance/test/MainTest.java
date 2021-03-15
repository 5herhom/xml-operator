package cn.com.sherhom.xml.operate.entrance.test;

import cn.com.sherhom.xml.operator.entrance.XmlOperatorApplication;
import org.junit.jupiter.api.Test;

/**
 * @author Sherhom
 * @date 2021/3/15 16:28
 */
public class MainTest {
    @Test
    public void test(){
        String[] args={
                "put",
                "G:\\HZY\\coding\\Java\\IdeaCoding\\xml-operator\\data\\config.xml",
                "/yandex/openSSL/server",
                "<logger>       <level id=\"hzy123123\">trace</level>          <log>/var/log/clickhouse-server/clickhouse-server.log</log>          <errorlog>/var/log/clickhouse-server/clickhouse-server.err.log</errorlog>       </logger>"
        };
        XmlOperatorApplication.main(args);
    }
}
