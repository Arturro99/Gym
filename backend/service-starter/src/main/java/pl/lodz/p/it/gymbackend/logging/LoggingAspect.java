package pl.lodz.p.it.gymbackend.logging;

import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.time.Duration;
import java.time.Instant;
import java.util.Collection;
import java.util.Map;
import java.util.Objects;
import java.util.StringJoiner;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

@Aspect
@Slf4j
@Component
public class LoggingAspect {

    /**
     * Pointcut leading to all the methods in all classes
     */
    @Pointcut("execution(* pl.lodz.p.it..*(..)) && !execution(* pl.lodz.p.it.gymbackend..*(..))")
    public void allMethods() {
    }

    /**
     * Pointcut leading to methods in controllers' implementations
     */
    @Pointcut("execution(* pl.lodz.p.it.restapi.controllerImplementation..*(..))")
    public void methodsInImplementedControllers() {
    }

    /**
     * Pointcut leading to methods in generated controllers
     */
    @Pointcut("execution(* pl.lodz.p.it.restapi.controller..*(..))")
    public void methodsInGeneratedControllers() {
    }

    /**
     * Pointcut leading to methods in AuthController
     */
    @Pointcut("within(pl.lodz.p.it.restapi.controllerImplementation.AuthController)")
    public void methodsInAuthController() {
    }

    /**
     * Pointcut leading to methods in restapi mappers
     */
    @Pointcut("execution(* pl.lodz.p.it.restapi.mapper..*(..))")
    public void methodsInControllerMappers() {
    }

    /**
     * Pointcut leading to methods in core mappers
     */
    @Pointcut("execution(* pl.lodz.p.it.core.application.secondary.mapper..*(..))")
    public void methodsInCoreMappers() {
    }

    /**
     * Method responsible for logging the start of method invocation, its name, parameters, result
     * and duration
     *
     * @param joinPoint Object providing lots of information about newly invoked method
     * @return Object being a result of the running method
     */
    @Around("allMethods() && !methodsInControllerMappers() && !methodsInCoreMappers() && "
        + "!methodsInGeneratedControllers() && !methodsInImplementedControllers()")
    public Object logMethods(ProceedingJoinPoint joinPoint) throws Throwable {
        MethodSignature methodSignature = (MethodSignature) joinPoint.getSignature();
        Method method = methodSignature.getMethod();

        logEntry(joinPoint, method);

        return logExit(joinPoint, method);
    }

    /**
     * Method responsible for logging the start of one of the controllers' method invocation, its
     * name, parameters, result and duration
     *
     * @param joinPoint Object providing lots of information about newly invoked method
     * @return Object being a result of the running method
     */
    @Around("methodsInImplementedControllers() && !methodsInAuthController()")
    public Object logControllerMethods(ProceedingJoinPoint joinPoint) throws Throwable {
        MethodSignature methodSignature = (MethodSignature) joinPoint.getSignature();
        Method method = methodSignature.getMethod();

        logEntry(joinPoint, method);

        return logExitController(joinPoint, method);
    }

    /**
     * Method responsible for logging any occurred exception.
     *
     * @param ex Thrown exception
     */
    @AfterThrowing(pointcut = "allMethods()", throwing = "ex")
    public void logExceptions(Exception ex) {
        log.error(createExceptionMessage(ex));
    }

    //Method logging the start of running methods
    private void logEntry(ProceedingJoinPoint joinPoint, Method method) {
        Object[] args = joinPoint.getArgs();
        Parameter[] params = method.getParameters();
        String className = method.getDeclaringClass().getSimpleName();
        String methodName = method.getName();

        log.info(createEntryMessage(className, methodName, params, args));
    }

    //Method logging the end of running methods
    private Object logExit(ProceedingJoinPoint joinPoint, Method method) throws Throwable {
        String className = method.getDeclaringClass().getSimpleName();
        String methodName = method.getName();
        String returnType = method.getReturnType().getSimpleName();

        Instant start = Instant.now();
        Object result = joinPoint.proceed();
        Instant end = Instant.now();
        Long duration = Duration.between(start, end).toMillis();

        log.info(createExitMessage(className, methodName, duration, result,
            returnType));
        return result;
    }

    //Method logging the end of running methods
    private Object logExitController(ProceedingJoinPoint joinPoint, Method method)
        throws Throwable {
        String className = method.getDeclaringClass().getSimpleName();
        String methodName = method.getName();

        Instant start = Instant.now();
        Object result = joinPoint.proceed();
        Instant end = Instant.now();
        Long duration = Duration.between(start, end).toMillis();

        log.info(createExitControllerMessage(className, methodName, duration,
            result));
        return result;
    }

    //Method creating an entry message used for logging the start of a method
    private String createEntryMessage(String className, String methodName, Parameter[] params,
        Object[] args) {
        StringJoiner message = new StringJoiner(" ")
            .add("Class").add(className)
            .add("invoked method:").add(methodName);

        if (Objects.nonNull(params) && Objects.nonNull(args) && params.length == args.length) {

            Map<String, Object> arguments = IntStream.range(0, params.length)
                .boxed()
                .collect(
                    Collectors.toMap(
                        i -> params[i].getName(),
                        i -> args[i]
                    )
                );

            message.add("with args:")
                .add(arguments.toString());
        }

        return message.toString();
    }

    //Method creating an exit message used for logging the end of a method
    private String createExitMessage(String className, String methodName, Long duration,
        Object result, String returnType) {
        StringJoiner message = createBaseExitMessage(className, methodName, duration);

        if ("void".equals(returnType)) {
            return message.toString();
        }
        message.add("and returned:");

        if (result instanceof Collection) {
            message.add(result.getClass().getSimpleName())
                .add("with size of:").add(String.valueOf(((Collection<?>) result).size()));
        } else if (Objects.nonNull(result)) {
            message.add(result.toString());
        } else {
            message.add("null");
        }

        return message.toString();
    }

    //Method creating an exit message used for logging the end of a method invoked in a controller
    private String createExitControllerMessage(String className, String methodName, Long duration,
        Object result) {
        StringJoiner message = createBaseExitMessage(className, methodName, duration);

        Object body = ((ResponseEntity<?>) result).getBody();

        if (body instanceof Collection) {
            message.add(result.getClass().getSimpleName()).add("with HTTP status code:")
                .add(((ResponseEntity<?>) result).getStatusCode().name())
                .add("and body containing:").add((Objects
                .requireNonNull(body).getClass().getSimpleName()))
                .add("with size of:")
                .add(String.valueOf((((Collection<?>) Objects.requireNonNull(body)).size())));
        } else {
            message.add(result.toString());
        }

        return message.toString();
    }

    //Method creating an message in case of thrown exception
    private String createExceptionMessage(Exception ex) {
        return new StringJoiner(" ")
            .add(ex.getClass().getSimpleName())
            .add("thrown in line:").add(ex.getStackTrace()[2].toString())
            .add("with message:").add(ex.getMessage())
            .toString();
    }


    //Method creating a base exit message
    private StringJoiner createBaseExitMessage(String className, String methodName, Long duration) {
        return new StringJoiner(" ")
            .add("Class").add(className)
            .add("finished method:").add(methodName)
            .add("in").add(duration.toString()).add("ms");
    }
}
