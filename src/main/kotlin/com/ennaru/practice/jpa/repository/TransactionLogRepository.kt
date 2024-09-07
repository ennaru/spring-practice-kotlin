package com.ennaru.practice.jpa.repository

import com.ennaru.practice.jpa.dto.TransactionLog
import org.springframework.data.repository.CrudRepository
import java.util.UUID

interface TransactionLogRepository: CrudRepository<TransactionLog, UUID> {
}