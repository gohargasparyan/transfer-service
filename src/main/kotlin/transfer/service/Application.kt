package transfer.service

import io.micronaut.runtime.Micronaut

object Application {

    @JvmStatic
    fun main(args: Array<String>) {
        Micronaut.build()
                .packages("transfer.service")
                .mainClass(Application.javaClass)
                .start()
    }
}