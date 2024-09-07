package com.ennaru.practice.jpa.dto

import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.Id

@Entity
data class Member(
    var name: String = ""
) {

    @Id
    @GeneratedValue
    val id: Long ?= null
}