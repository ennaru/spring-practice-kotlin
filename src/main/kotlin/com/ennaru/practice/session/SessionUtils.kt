package com.ennaru.practice.session

import org.springframework.web.context.request.RequestAttributes
import org.springframework.web.context.request.RequestContextHolder
import org.springframework.web.context.request.ServletRequestAttributes



class SessionUtils {

    companion object {
        fun getAttribute(key: String): Any? =
            RequestContextHolder.getRequestAttributes()?.getAttribute(key, RequestAttributes.SCOPE_SESSION)

        fun setAttribute(key: String, value: Any) =
            RequestContextHolder.getRequestAttributes()?.setAttribute(key, value, RequestAttributes.SCOPE_SESSION)

        fun removeAttribute(key: String) =
            RequestContextHolder.getRequestAttributes()?.removeAttribute(key, RequestAttributes.SCOPE_SESSION)

    }


}