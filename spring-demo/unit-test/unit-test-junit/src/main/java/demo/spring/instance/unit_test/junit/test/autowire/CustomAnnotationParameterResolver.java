package demo.spring.instance.unit_test.junit.test.autowire;

import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.api.extension.ParameterContext;
import org.junit.jupiter.api.extension.ParameterResolutionException;
import org.junit.jupiter.api.extension.ParameterResolver;
import org.junit.platform.commons.util.ReflectionUtils;

public class CustomAnnotationParameterResolver implements ParameterResolver {

    @Override
    public boolean supportsParameter(ParameterContext parameterContext, ExtensionContext extensionContext) throws ParameterResolutionException {
        // We invoke parameterContext.isAnnotated() instead of parameterContext.getParameter().isAnnotationPresent()
        // in order to verify support for the convenience method in the ParameterContext API.
        return parameterContext.isAnnotated(CustomAnnotation.class);
    }

    @Override
    public Object resolveParameter(ParameterContext parameterContext, ExtensionContext extensionContext) throws ParameterResolutionException {
        return ReflectionUtils.newInstance(parameterContext.getParameter().getType());
    }

}
