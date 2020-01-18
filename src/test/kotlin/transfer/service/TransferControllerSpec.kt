package transfer.service

import io.micronaut.http.HttpRequest
import io.micronaut.http.HttpStatus
import io.micronaut.http.client.HttpClient
import io.micronaut.http.client.annotation.Client
import io.micronaut.http.client.exceptions.HttpClientResponseException
import io.micronaut.runtime.server.EmbeddedServer
import org.junit.jupiter.api.Test
import javax.inject.Inject
import io.micronaut.test.annotation.MicronautTest
import org.junit.jupiter.api.BeforeEach
import transfer.service.controller.CommandTransfer
import transfer.service.domain.Account
import kotlin.properties.Delegates
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.assertThrows
import transfer.service.repository.AccountRepository

@MicronautTest
class TransferControllerSpec {

    @Inject
    lateinit var server: EmbeddedServer

    @Inject
    lateinit var accountRepository: AccountRepository

    @Inject
    @field:Client("/")
    lateinit var client: HttpClient

    private var account1: Account by Delegates.notNull()
    private var account2: Account by Delegates.notNull()

    @BeforeEach
    fun setup() {
        populateAccounts()
    }

    @Test
    fun testTransfer_negative_amount() {
        val transfer = CommandTransfer(account1.iban, account2.iban, -100)

        val error = assertThrows<HttpClientResponseException> {
            client.toBlocking().exchange(
                    HttpRequest.POST("/transfer", transfer), Account::class.java)
        }

        assertEquals(HttpStatus.BAD_REQUEST, error.status)
        assertEquals("transfer.commandTransfer.amount: Amount should be positive number.", error.message)

    }

    @Test
    fun testTransfer_account_not_found() {
        val transfer = CommandTransfer("account1.iban", "account2.iban", 100)

        val error = assertThrows<HttpClientResponseException> {
            client.toBlocking().exchange(
                    HttpRequest.POST("/transfer", transfer), Account::class.java)
        }

        assertEquals(HttpStatus.BAD_REQUEST, error.status)
        assertEquals("Account with IBAN account1.iban was not found.", error.message)
    }

    @Test
    fun testTransfer_not_enough_money() {
        val transfer = CommandTransfer(account1.iban, account2.iban, 1100)

        val error = assertThrows<HttpClientResponseException> {
            client.toBlocking().exchange(
                    HttpRequest.POST("/transfer", transfer), Account::class.java)
        }

        assertEquals(HttpStatus.BAD_REQUEST, error.status)
        assertEquals("Not enough money on account to transfer.", error.message)
    }

    @Test
    fun testTransfer() {
        val transfer = CommandTransfer(account1.iban, account2.iban, 100)
        client.toBlocking().exchange(
                HttpRequest.POST("/transfer", transfer), Account::class.java
        )

        assertEquals(900, accountRepository.find(account1.iban).balance)
        assertEquals(100, accountRepository.find(account2.iban).balance)
    }

    private fun populateAccounts() {
        account1 = Account("DE00 0000 0000 0000 00", "name1 surname1")
        account1.balance = 1000
        accountRepository.save(account1)

        account2 = Account("DE00 0000 0000 0000 01", "name2 surname2")
        accountRepository.save(account2)
    }
}