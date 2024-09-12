package com.ennaru.practice.jpa.dto

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.Id

@Entity
data class Member(
    @Column(length = 10)
    var name: String = ""
) {

    @Id
    @GeneratedValue
    val id: Long ?= null
}