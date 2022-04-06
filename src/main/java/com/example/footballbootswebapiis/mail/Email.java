package com.example.footballbootswebapiis.mail;

import lombok.Getter;

@Getter
public class Email {
    private final String to;
    private final String from;
    private final String subject;
    private final String body;

    public Email(EmailBuilder emailBuilder) {
        this.to = emailBuilder.to;
        this.from = emailBuilder.from;
        this.subject = emailBuilder.subject;
        this.body = emailBuilder.body;
    }

    public static class EmailBuilder {
        private final String to;
        private final String from;
        private String subject;
        private String body;

        public EmailBuilder(String to, String from) {
            this.to = to;
            this.from = from;
        }

        public EmailBuilder subject(String subject) {
            this.subject = subject;
            return this;
        }

        public EmailBuilder body(String body) {
            this.body = body;
            return this;
        }

        public Email build() {
            return new Email(this);
        }
    }
}
