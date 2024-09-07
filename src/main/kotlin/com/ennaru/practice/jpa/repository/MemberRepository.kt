package com.ennaru.practice.jpa.repository

import com.ennaru.practice.jpa.dto.Member
import org.springframework.data.repository.CrudRepository

interface MemberRepository: CrudRepository<Member, Long> {
}