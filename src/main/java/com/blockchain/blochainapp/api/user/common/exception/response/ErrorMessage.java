package com.blockchain.blochainapp.api.user.common.exception.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.google.common.collect.Sets;
import lombok.*;

import java.util.Objects;
import java.util.Set;

@AllArgsConstructor
@Setter
@Getter
@NoArgsConstructor
@ToString
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ErrorMessage {

    private Integer code;
    private String message;
    private Set<Error> errors;

    public void add(Error error) {
        if (Objects.isNull(getErrors())) {
            setErrors(Sets.newHashSet(error));
            return;
        }

        getErrors().add(error);
    }

    @AllArgsConstructor
    @Setter
    @Getter
    @NoArgsConstructor
    @ToString
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public static class Error {
        private String field;
        private String message;

        public Error(String message) {
            this.message = message;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            Error error = (Error) o;
            return Objects.equals(getField(), error.getField()) && Objects.equals(getMessage(), error.getMessage());
        }

        @Override
        public int hashCode() {
            return Objects.hash(getField(), getMessage());
        }
    }

}
