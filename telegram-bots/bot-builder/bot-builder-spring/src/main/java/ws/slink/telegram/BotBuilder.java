package ws.slink.telegram;

import org.apache.http.HttpHost;
import org.apache.http.client.config.RequestConfig;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.DefaultBotOptions;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.ApiContext;

import javax.management.RuntimeErrorException;
import java.lang.reflect.InvocationTargetException;

@Component
public class BotBuilder<T extends TelegramLongPollingBot> {

    @Value("${proxy:}")
    private String proxyString;

    public BotBuilder() {}

    private DefaultBotOptions configureBotOptions() {
        DefaultBotOptions botOptions = ApiContext.getInstance(DefaultBotOptions.class);
        String host = null, port_str = null; 
        String [] parts = proxyString.split(":");
        if (parts.length > 1) {
            host = parts[parts.length-2].replaceAll("/", "");
            port_str = parts[parts.length-1];
        }
        if (host != null && port_str != null) {
            int port = Integer.parseInt(port_str);
            HttpHost httpHost = new HttpHost(host, port);
            RequestConfig requestConfig = RequestConfig.custom().setProxy(httpHost).setAuthenticationEnabled(false).build();
            botOptions.setRequestConfig(requestConfig);
            botOptions.setProxyHost(host);
            botOptions.setProxyPort(port);
        }
        return botOptions;
    }

    @SuppressWarnings("unchecked")
    public T build(String className) {
        try {
            return (T) Class.forName(className)
                            .getConstructor(DefaultBotOptions.class)
                            .newInstance(configureBotOptions());
        } catch ( InstantiationException
                | IllegalAccessException
                | IllegalArgumentException
                | InvocationTargetException
                | NoSuchMethodException
                | SecurityException
                | ClassNotFoundException e) {
            e.printStackTrace();
            throw new RuntimeErrorException(null, "BotBuilder: error creating a new bot");
        }
    }
}
