package com.blockchain.blochainapp.core.validator.impl;

import com.blockchain.blochainapp.core.validator.annotation.CheckUniqueName;
import com.blockchain.blochainapp.core.validator.util.ValidatorRequestExtractor;
import com.blockchain.blochainapp.data.functionality.common.domain.CheckUnique;
import com.google.common.base.Strings;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import org.springframework.context.ApplicationContext;

public class CheckUniqueNameImpl implements ConstraintValidator<CheckUniqueName, String> {

    private final ApplicationContext applicationContext;
    private final ValidatorRequestExtractor extractor;
    private CheckUnique queryService;
    private String idPathVariableName;

    public CheckUniqueNameImpl(ApplicationContext applicationContext, ValidatorRequestExtractor extractor) {
        this.applicationContext = applicationContext;
        this.extractor = extractor;
    }

    @Override
    public void initialize(CheckUniqueName constraintAnnotation) {
        queryService = applicationContext.getBean(constraintAnnotation.queryService());
        idPathVariableName = constraintAnnotation.idPathVariableName();
        ConstraintValidator.super.initialize(constraintAnnotation);
    }

    @Override
    public boolean isValid(String s, ConstraintValidatorContext constraintValidatorContext) {
        if(Strings.isNullOrEmpty(s)){
            return true;
        }

        return !queryService.isNameExist(s, extractor.getIdFromRequest(idPathVariableName));
    }
}
