package com.ennaru.practice.jpa.dto

import jakarta.persistence.Entity
import jakarta.persistence.Id
import java.util.*

@Entity
data class BoardHistory (
    @Id
    private val historyId: String = UUID.randomUUID().toString(),
    private val modifier: String = ""
)