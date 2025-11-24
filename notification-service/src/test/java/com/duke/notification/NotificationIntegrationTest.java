package com.duke.notification;

import com.duke.common.dto.KafkaDto;
import com.duke.common.dto.OperationType;
import com.duke.notification.consumer.UserConsumer;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

/**
 * Интеграционный тест отправки уведомлений на электронную почту.
 */

@SpringBootTest
@Import(TestMailConfig.class)
class NotificationIntegrationTest {

    @Autowired
    private JavaMailSender mailSender;

    @Autowired
    private UserConsumer consumer;

    /**
     * Проверка при обработке события
     */
    @Test
    void testKafkaListenerEmailSending() {

        KafkaDto dto = new KafkaDto();
        dto.setUserId(1L);
        dto.setEmail("test@google.com");
        dto.setOperationType(OperationType.CREATED);

        consumer.consume(dto);

        ArgumentCaptor<SimpleMailMessage> captor =
                ArgumentCaptor.forClass(SimpleMailMessage.class);

        verify(mailSender, times(1)).send(captor.capture());

        SimpleMailMessage sent = captor.getValue();

        assertThat(sent.getTo()).containsExactly("test@google.com");
        assertThat(sent.getSubject()).isEqualTo("Type Create");
        assertThat(sent.getText()).contains("Здравствуйте! Ваш аккаунт на сайте был успешно создан.");
    }
}