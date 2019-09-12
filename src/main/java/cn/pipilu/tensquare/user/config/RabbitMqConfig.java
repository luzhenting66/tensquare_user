package cn.pipilu.tensquare.user.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.connection.CorrelationData;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMqConfig {
    private static final Logger log= LoggerFactory.getLogger(RabbitMqConfig.class);
    @Autowired
    private CachingConnectionFactory connectionFactory;



    @Value("${spring.rabbitmq.listener.concurrency}")
    private int concurrency;
    @Value("${spring.rabbitmq.listener.max-concurrency}")
    private int maxConcurrency;
    @Value("${spring.rabbitmq.listener.prefetch}")
    private int prefetch;
    @Value("${spring.rabbitmq.sms.queue.name}")
    private String smsQueueName;
    @Value("${spring.rabbitmq.sms.exchange.name}")
    private String smsExchangeName;
    @Value("${spring.rabbitmq.sms.routing.key.name}")
    private String smsRoutingKeyName;


    @Bean
    public RabbitTemplate rabbitTemplate(){
        connectionFactory.setPublisherConfirms(true);
        connectionFactory.setPublisherReturns(true);
        RabbitTemplate rabbitTemplate = new RabbitTemplate(connectionFactory);
        rabbitTemplate.setMandatory(true);
        rabbitTemplate.setConfirmCallback(new RabbitTemplate.ConfirmCallback() {
            @Override
            public void confirm(CorrelationData correlationData, boolean ack, String cause) {
                log.info("消息发送成功:correlationData({}),ack({}),cause({})",correlationData,ack,cause);
            }
        });
        rabbitTemplate.setReturnCallback(new RabbitTemplate.ReturnCallback() {
            @Override
            public void returnedMessage(Message message, int replyCode, String replyText, String exchange, String routingKey) {
                log.info("消息丢失:exchange({}),route({}),replyCode({}),replyText({}),message:{}",exchange,routingKey,replyCode,replyText,message);
            }
        });
        return rabbitTemplate;
    }

    @Bean(name = "smsQueue")
    public Queue smsQueue(){
        return new Queue(smsQueueName,true);
    }

    @Bean(name = "smsExchange")
    public DirectExchange smsExchange(){
        return new DirectExchange(smsExchangeName,true,false);
    }
    @Bean
    public Binding logUserBinding(){
        return BindingBuilder.bind(smsQueue()).to(smsExchange()).with(smsRoutingKeyName);
    }
}
