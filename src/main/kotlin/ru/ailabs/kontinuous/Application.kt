package ru.ailabs.kontinuous.initializer

import org.reflections.Reflections
import ru.ailabs.kontinuous.annotation.initializers
import ru.ailabs.kontinuous.controller.ControllerDispatcher
import java.util.Set
import java.util.HashSet

/**
 * Created with IntelliJ IDEA.
 * User: andrew
 * Date: 03.02.13
 * Time: 19:13
 * To change this template use File | Settings | File Templates.
 */

class Application {
    {
        fun scanForInitializers(): jet.MutableSet<java.lang.Class<out jet.Any?>?>? {
            return Reflections("").getTypesAnnotatedWith(javaClass<initializers>())
        }

        for (cls in scanForInitializers()!!.toCollection()) {
//            println("Class: " + cls)
//            for (m in cls!!.getDeclaredMethods()) {
//                println("Method: " + m)
//            }
            val m = cls!!.getMethod("getInitializers");
            val set = m!!.invoke(cls.newInstance()) as (Collection<() -> Unit>);
            for (i in set) {
                i()
            }
        }
    }

    val dispatcher = ControllerDispatcher()

}