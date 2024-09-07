package com.ennaru.practice.jpa.repository

import com.ennaru.practice.jpa.dto.Board
import jakarta.persistence.Id
import org.springframework.data.repository.CrudRepository
import java.util.*

interface BoardRepository: CrudRepository<Board, Id> {

    // CrudRepository 클래스는


    override fun <S : Board?> save(entity: S & Any): S & Any {
        TODO("Not yet implemented")
    }

    override fun <S : Board?> saveAll(entities: MutableIterable<S>): MutableIterable<S> {
        TODO("Not yet implemented")
    }

    override fun findById(id: Id): Optional<Board> {
        TODO("Not yet implemented")
    }

    override fun existsById(id: Id): Boolean {
        TODO("Not yet implemented")
    }

    override fun findAll(): MutableIterable<Board> {
        TODO("Not yet implemented")
    }

    override fun findAllById(ids: MutableIterable<Id>): MutableIterable<Board> {
        TODO("Not yet implemented")
    }

    override fun count(): Long {
        TODO("Not yet implemented")
    }

    override fun deleteById(id: Id) {
        TODO("Not yet implemented")
    }

    override fun delete(entity: Board) {
        TODO("Not yet implemented")
    }

    override fun deleteAllById(ids: MutableIterable<Id>) {
        TODO("Not yet implemented")
    }

    override fun deleteAll(entities: MutableIterable<Board>) {
        TODO("Not yet implemented")
    }

    override fun deleteAll() {
        TODO("Not yet implemented")
    }
}