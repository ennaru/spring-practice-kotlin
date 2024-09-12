package com.ennaru.practice.jpa

import com.ennaru.practice.common.vo.logger
import com.ennaru.practice.jpa.dto.Member
import com.ennaru.practice.jpa.dto.TransactionLog
import com.ennaru.practice.jpa.repository.MemberRepository
import com.ennaru.practice.jpa.repository.TransactionLogRepository
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.data.repository.CrudRepository
import org.springframework.data.repository.findByIdOrNull
import org.springframework.transaction.annotation.Transactional
import java.time.LocalDateTime
import java.util.UUID
import kotlin.test.BeforeTest

@SpringBootTest
@DisplayName("CrudRepository로 진행하는 JPA 테스트")
class JpaSaveTest @Autowired constructor(
    private val transactionLogRepository: TransactionLogRepository,
    private val memberRepository: MemberRepository
) {

    val log = logger()

    @BeforeTest
    fun before() {
    }

    @AfterEach
    fun after() {
        log.info("{}", memberRepository.count())
    }

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
    @DisplayName("SaveAll 함수를 알아봅니다.")
    fun saveAll() {

        // saveAll() 함수는 Iterable Entity를 파라미터로 받아 Entity를 DB에 반영합니다.

        // 함수를 실행하기 전, DB의 값을 확인합니다.
        log.info("{}", transactionLogRepository.count())

        // DB에 넣을 5개의 레코드를 생성하고 mutableList에 담습니다.
        val trLogList = mutableListOf<TransactionLog>()
        for(i in 1..5) {
            val transactionLog = TransactionLog(
                functionName = this.javaClass.simpleName,
                startDate = LocalDateTime.now(),
                sequence = i
            )
            trLogList.add(transactionLog)
        }

        // 모든 값을 saveAll() 함수로 DB에 저장합니다.
        transactionLogRepository.saveAll(trLogList)

        // saveAll 함수를 실행한 결과를 확인합니다.
        log.info("{}", transactionLogRepository.count())

    }

    @Test
    @DisplayName("saveAll() 함수 실행 시 중간에 Exception이 발생한다면")
    fun savaAllException() {

        // saveAll() 함수 실행 시 Exception이 발생하면 어떻게 되는지 알아봅니다.
        // saveAll() 함수는 save() 함수를 반복하여 실행합니다.

        // 먼저 제약 조건을 어길 수 있는 데이터를 생성합니다.
        val errorMember = Member(
            name = "Jensen Hwang NIKE",
        )

        // 정상 데이터 속에 제약 조건을 위반한 데이터를 끼워넣습니다.
        val memberList = mutableListOf<Member>()
        for(i in 1..3) {
            val member = Member(
                name = "User $i"
            )
            memberList.add(member)
        }
        memberList.add(errorMember)

        // try 안에서 saveAll() 함수를 실행시킵니다.
        try {
            memberRepository.saveAll(memberList)
        } catch (e: Exception) {
            // 무결성 제약조건을 위반했으므로 [DataIntegrityViolationException] 이 발생하며
            // @Transaction 으로 인해 삽입되었던 데이터가 전부 롤백됩니다.
            log.error("{}", e.message)
        }

        log.info("{}", memberRepository.count())
    }

    @Test
    @DisplayName("롤백의 범위는 어디인가요?")
    fun transactionalTest() {

        val errorMember = Member(
            name = "Jensen Hwang NIKE",
        )

        try {
            saveSet(errorMember)
        } catch (e: Exception) {
            log.error("{}", e.message)
        }

    }

    @Transactional
    fun saveSet(member: Member) {
        // insert
        val transactionLog = TransactionLog(
            functionName = this.javaClass.simpleName,
            startDate = LocalDateTime.now(),
            sequence = 1,
        )
        transactionLogRepository.save(transactionLog)

        // main logic
        memberRepository.save(member)

        // update
        transactionLog.endDate = LocalDateTime.now()
        transactionLogRepository.save(transactionLog)
    }


}