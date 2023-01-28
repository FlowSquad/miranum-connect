package io.miragon.miranum.integrations.mail.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
@AllArgsConstructor
public class Mail {

    private String text;

    private String subject;

    private String recipient;

}
