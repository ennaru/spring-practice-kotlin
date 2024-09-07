package com.ennaru.practice.jpa.dto

import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.Id
import java.time.LocalDateTime
import java.util.*

@Entity
data class TransactionLog (
    @Id
    val id: UUID = UUID.randomUUID(),
    val functionName: String,
    val startDate: LocalDateTime,
    val sequence: Int,
    var endDate: LocalDateTime? = null
)