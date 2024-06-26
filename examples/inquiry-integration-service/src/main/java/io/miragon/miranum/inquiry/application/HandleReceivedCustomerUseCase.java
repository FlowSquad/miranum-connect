package io.miragon.miranum.inquiry.application;

import io.miragon.miranum.connect.message.api.CorrelateMessageCommand;
import io.miragon.miranum.connect.message.api.MessageApi;
import io.miragon.miranum.inquiry.application.port.in.model.NewCustomerMailCommand;
import io.miragon.miranum.inquiry.application.port.in.CustomerMailReceived;
import io.miragon.miranum.inquiry.application.port.out.InquiryRepository;
import io.miragon.miranum.inquiry.domain.Inquiry;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.checkerframework.checker.units.qual.C;
import org.springframework.stereotype.Component;

import java.util.Collections;

@Slf4j
@Component
@RequiredArgsConstructor
public class HandleReceivedCustomerUseCase implements CustomerMailReceived {
    private final MessageApi messageApi;
    private final InquiryRepository inquiryRepository;

    private static final String MAIL_ACCEPTED_MESSAGE_NAME = "offer-accepted";
    private static final String MAIL_DECLINED_MESSAGE_NAME = "offer-declined";

    @Override
    public void handle(NewCustomerMailCommand command) {
        // 1. load and update inquiry
        Inquiry loaded = this.inquiryRepository.findById(command.inquiryId());
        Inquiry updated = loaded.update(command);
        Inquiry saved = this.inquiryRepository.save(updated);


        this.messageApi.correlateMessage(new CorrelateMessageCommand(
                saved.offerAccepted() ? MAIL_ACCEPTED_MESSAGE_NAME : MAIL_DECLINED_MESSAGE_NAME,
                saved.id().toString(),
                Collections.emptyMap()
        ));

        log.info("[{}] Inquiry updated by customer mail: {}", saved.id(), saved);
    }
}
