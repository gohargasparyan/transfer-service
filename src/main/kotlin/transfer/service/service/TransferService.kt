package transfer.service.service

import io.micronaut.data.exceptions.EmptyResultException
import transfer.service.controller.CommandTransfer
import transfer.service.domain.Account
import transfer.service.exception.InvalidTransferException
import transfer.service.repository.AccountRepository
import javax.inject.Singleton
import javax.transaction.Transactional

@Singleton
open class TransferService(private val accountRepository: AccountRepository) {

    @Transactional
    open fun transfer(transfer: CommandTransfer) {
        val from: Account = findAccount(transfer.from)
        val to: Account = findAccount(transfer.to)

        if(from.balance < transfer.amount){
            throw InvalidTransferException("Not enough money on account to transfer.")
        }

        from.balance = from.balance - transfer.amount
        to.balance = to.balance + transfer.amount

        accountRepository.save(from)
        accountRepository.save(to)
    }

    private fun findAccount(iban: String): Account {
        val account: Account
        try {
            account = accountRepository.find(iban)
        } catch (e: EmptyResultException) {
            throw InvalidTransferException("Account with IBAN $iban was not found.")
        }
        return account
    }

}