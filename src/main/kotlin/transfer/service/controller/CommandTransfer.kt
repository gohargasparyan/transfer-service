package transfer.service.controller

import io.micronaut.core.annotation.Introspected
import javax.validation.constraints.Positive

@Introspected
open class CommandTransfer() {

    var from: String = ""

    var to: String = ""

    @Positive(message = "Amount should be positive number.") var amount: Long = 0

    constructor(from : String, to: String, amount: Long) : this() {
        this.from = from
        this.to = to
        this.amount = amount
    }
}