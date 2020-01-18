package transfer.service.controller

import io.micronaut.http.HttpRequest
import io.micronaut.http.HttpResponse
import io.micronaut.http.HttpResponse.badRequest
import io.micronaut.http.HttpResponse.serverError
import io.micronaut.http.HttpStatus
import io.micronaut.http.HttpStatus.OK
import io.micronaut.http.annotation.Body
import io.micronaut.http.annotation.Controller
import io.micronaut.http.annotation.Error
import io.micronaut.http.annotation.Post
import io.micronaut.http.hateoas.JsonError
import io.micronaut.validation.Validated
import transfer.service.exception.InvalidTransferException
import transfer.service.service.TransferService
import javax.validation.ConstraintViolationException
import javax.validation.Valid

@Controller("/")
@Validated
class TransferController(private val accountService: TransferService) {

    @Post("/transfer")
    fun transfer(@Body @Valid commandTransfer: CommandTransfer): HttpStatus {
        accountService.transfer(commandTransfer)
        return OK
    }

    @Error
    fun errorHandler(request: HttpRequest<*>, exception: ConstraintViolationException): HttpResponse<JsonError> {
        return badRequest(JsonError(exception.message))
    }

    @Error
    fun errorHandler(request: HttpRequest<*>, exception: InvalidTransferException): HttpResponse<JsonError> {
        return badRequest(JsonError(exception.message))
    }

    @Error
    fun errorHandler(request: HttpRequest<*>, exception: Exception): HttpResponse<JsonError> {
        return serverError(JsonError("Internal Server Error"))
    }
}