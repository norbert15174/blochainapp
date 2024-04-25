package com.blockchain.blochainapp.core.validator.util;

import com.google.common.base.Strings;
import jakarta.servlet.http.HttpServletRequest;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerMapping;

import java.util.Map;
import java.util.Objects;
import java.util.Optional;

@AllArgsConstructor
@Component
public class ValidatorRequestExtractor {

    private final HttpServletRequest httpServletRequest;

    public Optional<Long> getIdFromRequest(String pathVariableName){
        if(Strings.isNullOrEmpty(pathVariableName)){
            return Optional.empty();
        }

        @SuppressWarnings("unchecked") var attribute = (Map<String, String>) httpServletRequest.getAttribute(HandlerMapping.URI_TEMPLATE_VARIABLES_ATTRIBUTE);
        var value = attribute.get(pathVariableName);
        return Objects.isNull(value) ? Optional.empty() : Optional.of(Long.valueOf(value));
    }

}
