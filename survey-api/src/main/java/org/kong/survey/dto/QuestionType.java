package org.kong.survey.dto;

import lombok.Getter;

@Getter
public enum QuestionType {
    TWO("2"),
    FIVE("5"),
    SUB("sub");

    private final String type;

    QuestionType(String type) {
        this.type = type;
    }
}
