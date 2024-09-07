package com.ennaru.practice.jpa

import com.ennaru.practice.common.vo.logger
import com.ennaru.practice.jpa.dto.Board
import com.ennaru.practice.jpa.dto.Member
import com.ennaru.practice.jpa.dto.TransactionLog
import com.ennaru.practice.jpa.repository.BoardRepository
import com.ennaru.practice.jpa.repository.MemberRepository
import com.ennaru.practice.jpa.repository.TransactionLogRepository
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.data.repository.CrudRepository
import org.springframework.data.repository.findByIdOrNull
import java.time.LocalDateTime
import java.util.Optional
import java.util.UUID
import kotlin.test.BeforeTest

@SpringBootTest
@DisplayName("CrudRepository로 진행하는 JPA 테스트")
class CrudRepositoryTest @Autowired constructor(
    private val transactionLogRepository: TransactionLogRepository,
    private val memberRepository: MemberRepository
) {

    val log = logger()

    @BeforeTest
    fun before() {
    }

    /**
     * [CrudRepository] class는 [Repository] class를 상속받은 오브젝트로
     * @Entity class와 DB간 통신하기 위한 인터페이스를 제공합니다.
     */


    @Test
    @DisplayName("save() 함수")
    fun save() {

        // CrudRepository.save(entity) 함수는
        // => SimpleJpaRepository.save() 로 매핑됩니다.
        // isNew(entity) 함수 실행 결과에 따라 persist() / merge() 함수가 실행됩니다.

        // 테스트로 사용할 Entity는 id 값을 초기화하지 않아 null입니다.
        val member = Member()
        member.name = "hong gil"

        // id가 null이면 hibernate에서는 새로운 Entity로 식별하여 persist() 함수를 실행됩니다.
        // @GeneratedValue 어노테이션에 따라 ID가 자동으로 생성되여 insert query를 수행합니다.
        memberRepository.save(member)

        // findAll() 함수는 테이블에서 모든 데이터를 조회하며 Iterable 클래스로 변환합니다.
        // Iterable 클래스이므로 forEach 함수를 사용할 수 있습니다.
        memberRepository.findAll().forEach { log.info("$it") }

        // 데이터를 변형합니다.
        member.name = "hong gil2"

        // id가 null이 아닌 Entity는 새로운 Entity가 아니므로 merge() 함수가 실행됩니다.
        // merge() 함수는 값을 먼저 확인한 후에 update query를 수행합니다.
        memberRepository.save(member)

        memberRepository.findAll().forEach { log.info("$it") }
    }

    @Test
    @DisplayName("CrudRepository의 함수를 알아봅니다.")
    fun save2() {

        // TransactionLogRepository를 사용하여 5개의 레코드를 넣습니다.
        val idList = mutableListOf<UUID>()
        for(i in 1..5) {
            val transactionLog : TransactionLog = TransactionLog(
                functionName = this.javaClass.simpleName,
                startDate = LocalDateTime.now(),
                sequence = i
            )
            idList.add(transactionLogRepository.save(transactionLog).id)
        }

        // count() 함수는 아무 조건을 걸지 않고 테이블을 조회하여 레코드 수를 반환합니다.
        log.info("count() : ${transactionLogRepository.count()}")

        // findAll() 함수는 아무 조건을 걸지 않고 테이블을 조회하여 레코드를 반환합니다.
        transactionLogRepository.findAll().forEach { log.info("$it") }

        // findAllById(list_id) 함수는 id를 list로 받아 레코드를 조회합니다. query는 한 번만 실행됩니다.
        val newList = mutableListOf(idList[0], null, idList[2])
        log.info("findById ${transactionLogRepository.findAllById(newList)}")

        // findById(id) 함수는 id를 조건으로 삼아 레코드를 조회합니다.
        // 반환 타입은 Optional(entity type)입니다.
        log.info("findById[id:2]: ${transactionLogRepository.findById(idList[2])}")
        val log1 = transactionLogRepository.findById(idList[1])

        // findById 함수는 id를 이용하여 테이블 레코드를 조회합니다.
        // 반환 타입은 Optional입니다.
        log.info("findById[nullable]: ${transactionLogRepository.findById(UUID.randomUUID()).get()}")

        // findByIdOrNull 함수는 findById 함수와 역할이 같지만 Kotlin에서만 사용할 수 있고, 바로 Entity로 반환합니다.
        // nullable 객체이므로 safe call을 호출해야 합니다.
        log.info("findByIdOrNull[id:1]: ${transactionLogRepository.findByIdOrNull(idList[1])?.id}")
        log.info("findById[nullable]: ${transactionLogRepository.findByIdOrNull(UUID.randomUUID())?.id}")


    }


}