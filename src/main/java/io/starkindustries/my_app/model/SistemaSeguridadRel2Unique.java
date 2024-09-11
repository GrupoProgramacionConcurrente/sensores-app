package io.starkindustries.my_app.model;

import static java.lang.annotation.ElementType.ANNOTATION_TYPE;
import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.ElementType.METHOD;

import io.starkindustries.my_app.service.SistemaSeguridadService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Constraint;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import jakarta.validation.Payload;
import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.util.Map;
import org.springframework.web.servlet.HandlerMapping;


/**
 * Validate that the id value isn't taken yet.
 */
@Target({ FIELD, METHOD, ANNOTATION_TYPE })
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Constraint(
        validatedBy = SistemaSeguridadRel2Unique.SistemaSeguridadRel2UniqueValidator.class
)
public @interface SistemaSeguridadRel2Unique {

    String message() default "{Exists.sistemaSeguridad.rel2}";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

    class SistemaSeguridadRel2UniqueValidator implements ConstraintValidator<SistemaSeguridadRel2Unique, Long> {

        private final SistemaSeguridadService sistemaSeguridadService;
        private final HttpServletRequest request;

        public SistemaSeguridadRel2UniqueValidator(
                final SistemaSeguridadService sistemaSeguridadService,
                final HttpServletRequest request) {
            this.sistemaSeguridadService = sistemaSeguridadService;
            this.request = request;
        }

        @Override
        public boolean isValid(final Long value, final ConstraintValidatorContext cvContext) {
            if (value == null) {
                // no value present
                return true;
            }
            @SuppressWarnings("unchecked") final Map<String, String> pathVariables =
                    ((Map<String, String>)request.getAttribute(HandlerMapping.URI_TEMPLATE_VARIABLES_ATTRIBUTE));
            final String currentId = pathVariables.get("id");
            if (currentId != null && value.equals(sistemaSeguridadService.get(Long.parseLong(currentId)).getRel2())) {
                // value hasn't changed
                return true;
            }
            return !sistemaSeguridadService.rel2Exists(value);
        }

    }

}
