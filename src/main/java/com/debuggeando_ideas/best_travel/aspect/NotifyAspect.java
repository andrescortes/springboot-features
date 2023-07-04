package com.debuggeando_ideas.best_travel.aspect;

import com.debuggeando_ideas.best_travel.util.BestTravelUtil;
import com.debuggeando_ideas.best_travel.util.anotation.Notification;
import com.debuggeando_ideas.best_travel.util.exceptions.AspectGetArgsException;
import java.lang.reflect.Method;
import java.time.LocalDateTime;
import java.util.Objects;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;

@Component
@Aspect
public class NotifyAspect {

    private static final String LINE_FORMAT = "At %s new request with page %d, size %d and order %s";

    private static String getValueNotification(JoinPoint jp) {
        MethodSignature signature = (MethodSignature) jp.getSignature();
        Method method = signature.getMethod();
        Notification notification = method.getAnnotation(Notification.class);
        return notification.value();
    }

    @After(value = "@annotation(com.debuggeando_ideas.best_travel.util.anotation.Notification)")
    public void notifyInFile(JoinPoint jp) {
        try {
            Object[] args = jp.getArgs();
            Integer page = (Integer) args[0];
            Integer size = (Integer) args[1];
            String order = Objects.isNull(args[2]) ? "NONE" : args[2].toString();
            String lineText = String.format(LINE_FORMAT, LocalDateTime.now(), page, size, order);
            String valueNotification = getValueNotification(jp);
            BestTravelUtil.writeNotification(lineText, valueNotification);
        } catch (Exception e) {
            throw new AspectGetArgsException(e.getMessage());
        }
    }
}
