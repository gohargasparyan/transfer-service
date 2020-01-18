package transfer.service.domain

import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType
import javax.persistence.Id

@Entity
data class Account(val iban: String = "", val name: String = "") {

    @Id @GeneratedValue(strategy = GenerationType.AUTO) var id: Long = 0
    var balance: Long = 0
}