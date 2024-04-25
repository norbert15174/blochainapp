package com.blockchain.blochainapp.core.validator.annotation;

import com.blockchain.blochainapp.core.validator.impl.CheckUniqueNameImpl;
import com.blockchain.blochainapp.data.functionality.common.domain.CheckUnique;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;


import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.*;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Constraint(validatedBy = CheckUniqueNameImpl.class)
@Target({TYPE, FIELD, ANNOTATION_TYPE})
@Retention(RUNTIME)
@Documented
public @interface CheckUniqueName {

    Class<? extends CheckUnique> queryService();

    String idPathVariableName() default "";

    String message() default "{annotation.checkUniqueName}";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

}
