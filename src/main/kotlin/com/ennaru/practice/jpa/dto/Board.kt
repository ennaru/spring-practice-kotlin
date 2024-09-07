package com.ennaru.practice.jpa.dto

import jakarta.persistence.Entity
import jakarta.persistence.Id
import jakarta.persistence.PrimaryKeyJoinColumn
import jakarta.persistence.SecondaryTable

@Entity
@SecondaryTable(
    name = "BoardHistory",
    pkJoinColumns = [PrimaryKeyJoinColumn(name = "board_id", referencedColumnName = "id")]
)
data class Board(

    @Id
    private val id: String = "",
    private val title: String = "",
    private var content: String = "",

)