package com.bakigoal.organizationservice.aop

@Target(AnnotationTarget.FUNCTION)
annotation class ZipkinSpan(val name: String, val tags: Array<ZipkinTag>, val event: String)

@Target(AnnotationTarget.FUNCTION)
annotation class ZipkinTag(val key: String, val value: String)

