package org.jordicunillerarivera.spaceship.aspect;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class NegativeIdLoggingAspect {
    private static final Logger log = LoggerFactory.getLogger(NegativeIdLoggingAspect.class);

    @Before("execution(* org.jordicunillerarivera.spaceship.service.SpaceshipService.findById(..)) && args(id)")
    public void logIfNegativeId(JoinPoint joinPoint, Long id) {
        if (id != null && id < 0) {
            log.warn("[Aspect] Solicitud de nave con ID negativo: {} en {}", id, joinPoint.getSignature());
        }
    }
}
