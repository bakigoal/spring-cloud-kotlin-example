package com.bakigoal.organizationservice.aop

import org.aspectj.lang.ProceedingJoinPoint
import org.aspectj.lang.annotation.Around
import org.aspectj.lang.annotation.Aspect
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.cloud.sleuth.Tracer
import org.springframework.stereotype.Component

@Aspect
@Component
class ZipkinAspect(
    @Autowired val tracer: Tracer
) {

    @Around("@annotation(zipkinSpan)")
    fun zipkinSpan(joinPoint: ProceedingJoinPoint, zipkinSpan: ZipkinSpan): Any {
        val span = tracer.startScopedSpan(zipkinSpan.name)
        try {
            return joinPoint.proceed()
        } finally {
            zipkinSpan.tags.forEach { span.tag(it.key, it.value) }
            span.event(zipkinSpan.event)
            span.end()
        }
    }
}