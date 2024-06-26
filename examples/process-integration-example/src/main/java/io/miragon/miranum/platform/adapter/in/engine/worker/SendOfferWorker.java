package io.miragon.miranum.platform.adapter.in.engine.worker;

import io.miragon.miranum.connect.elementtemplate.api.ElementTemplate;
import io.miragon.miranum.connect.worker.api.Worker;
import io.miragon.miranum.platform.adapter.in.engine.worker.model.InquiryDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class SendOfferWorker {
    @Worker(type = "send-offer")
    @ElementTemplate(name = "Inquiry - Send Offer")
    public void sendOffer(InquiryDto dto) {
        log.info("Offer sent");
    }
}
