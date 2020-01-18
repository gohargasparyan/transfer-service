package transfer.service.repository


import io.micronaut.data.annotation.*
import io.micronaut.data.repository.CrudRepository
import transfer.service.domain.Account

@Repository
interface AccountRepository : CrudRepository<Account, Long> {

    fun find(iban: String): Account
}