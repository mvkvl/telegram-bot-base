package ws.slink.telegram.bot.examples;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import ws.slink.telegram.BotBuilder;

@SpringBootApplication(scanBasePackages={"ws.slink.telegram"})
public class EchoBotApplication {

    @Autowired
    private BotBuilder<EchoBot> botBuilder;

    @Bean
    public EchoBot getBot() {
        return botBuilder.build(EchoBot.class.getName());
    }

    public static void main(String[] args) {
        SpringApplication.run(EchoBotApplication.class, args);
    }

}