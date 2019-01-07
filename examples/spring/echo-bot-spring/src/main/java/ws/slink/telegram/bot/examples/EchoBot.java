package ws.slink.telegram.bot.examples;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.telegram.telegrambots.bots.DefaultBotOptions;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import javax.annotation.PostConstruct;

// based on telegram-spring-boot-starter:
// https://github.com/xabgesagtx/telegram-spring-boot-starter

public class EchoBot extends TelegramLongPollingBot {

    private static final Logger logger = LoggerFactory.getLogger(EchoBot.class);

    @Value("${bot.token}")
    private String token;
    @Value("${bot.username}")
    private String userName;
    @Value("${bot.owner_id:-1}")
    private int owner_id;
    @Value("${proxy:}")
    private String proxyString;

    public EchoBot(DefaultBotOptions opts) {
        super(opts);
    }

    @Override
    public String getBotToken() {
        return token;
    }
    @Override
    public String getBotUsername() {
        return userName;
    }
    public int getOwnerId() {
        return owner_id;
    }
    @Override
    public void onUpdateReceived(Update update) {
        if (update.hasMessage()) {
            Message message = update.getMessage();
            SendMessage response = new SendMessage();
            Long chatId = message.getChatId();
            response.setChatId(chatId);
            String text = message.getText();
            response.setText(text);
            try {
                execute(response);
                logger.info("Sent message \"{}\" to {}", text, chatId);
            } catch (TelegramApiException e) {
                logger.error("Failed to send message \"{}\" to {} due to error: {}", text, chatId, e.getMessage());
            }
        }
    }

    @PostConstruct
    public void start() {
        if (null == proxyString || proxyString.isEmpty())
            logger.info("Starting telegram bot\nusername: {}, token: {}", userName, token);
        else
            logger.info("Starting proxy-connected telegram bot\nusername: {}, token: {}", userName, token);
    }

}