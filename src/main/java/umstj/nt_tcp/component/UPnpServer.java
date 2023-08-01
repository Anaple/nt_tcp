package umstj.nt_tcp.component;

import jakarta.annotation.PostConstruct;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.jmdns.JmDNS;
import javax.jmdns.ServiceEvent;
import javax.jmdns.ServiceInfo;
import javax.jmdns.ServiceListener;
import java.io.IOException;
import java.net.InetAddress;

@Component
public class UPnpServer {

    private JmDNS jmdns;

    @Value("${upnp.server.port}")
    private int port;

    @Value("${upnp.server.name}")
    private String name;

    Logger logger = LoggerFactory.getLogger(UPnpServer.class);

    @PostConstruct
    public void start() {
        try {
            logger.info("Bonjour Server is running on port " + port);
            jmdns = JmDNS.create(InetAddress.getLocalHost());
            String serviceType = "_http._tcp.local.";
            String serviceName = name;
            ServiceInfo serviceInfo = ServiceInfo.create(serviceType, serviceName, port, serviceName);
            jmdns.registerService(serviceInfo);

            jmdns.addServiceListener(serviceType, new ServiceListener() {
                @Override
                public void serviceAdded(ServiceEvent event) {
                    logger.info("Service added: " + event.getName());
                }

                @Override
                public void serviceRemoved(ServiceEvent event) {
                    logger.info("Service removed: " + event.getName());
                }

                @Override
                public void serviceResolved(ServiceEvent event) {
                   // logger.info("Service resolved: " + event.getName() + " | IP: " + event.getInfo().getInetAddress());
                }
            });

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
