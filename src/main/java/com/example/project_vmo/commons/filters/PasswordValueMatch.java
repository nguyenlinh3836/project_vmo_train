package com.example.project_vmo.commons.filters;

import java.lang.annotation.Target;
import static java.lang.annotation.ElementType.*;
import static java.lang.annotation.RetentionPolicy.RUNTIME;
import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;
@Target({ TYPE, ANNOTATION_TYPE })
@Retention(RUNTIME)
@Constraint(validatedBy = PasswordFieldsValueMatchValidator.class)
@Documented
public @interface PasswordValueMatch {


  String message() default "Fields values don't match!";

  Class<?>[] groups() default { };

  Class<? extends Payload>[] payload() default { };

  String field();

  String fieldMatch();

  @Target({ ElementType.TYPE })
  @Retention(RetentionPolicy.RUNTIME)
  @interface List {
    PasswordValueMatch[] value();
  }
}
