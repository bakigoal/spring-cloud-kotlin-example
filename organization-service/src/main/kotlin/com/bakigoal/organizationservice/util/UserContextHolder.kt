package com.bakigoal.organizationservice.util

class UserContextHolder {
    companion object {

        private val userContext = ThreadLocal<UserContext>()

        fun getContext(): UserContext {
            var context = userContext.get()
            if (context == null) {
                context = UserContext()
                userContext.set(context)
            }
            return context
        }
    }
}