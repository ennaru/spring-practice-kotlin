package com.ennaru.practice.kotlin

import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test

class LoopTest {

    @DisplayName("반복문 기초")
    @Test
    fun testLoop() {

        // Kotlin에서 반복문은 while, do-while, for, forEach를 사용합니다.

        // while문과 do-while은 java에서 사용한 것과 같습니다.
        var count = 3
        while (count > 0) {
            print(count)
            count--
        }

        do {
            println(count)
        } while (count > 0)

        // for문을 사용하여 숫자로 반복문을 표현할 때에는 아래처럼 표현합니다.
        // 반복문의 값과 조건으로 사용한 i는 for 문 밖에서는 사용할 수 없습니다.
        for(i in 0..5) {
            print(i)
        }
        println()
        // 역방향은 downTo를 붙이며 i 값이 1씩 감소합니다.
        for(i in 6 downTo 0) {
            print(i)
        }
        println()
        // step 인자를 붙이면 step에 할당한 값만큼 증가 또는 감소시킵니다.
        for(i in 0..5 step 2) {
            print(i)
        }
        println()
    }

    @DisplayName("Collection 반복문 테스트")
    @Test
    fun loopTestWithCollection() {

        // for문을 사용한 Collection 반복문은 [in]을 사용하여 표현합니다.
        // 단, step와 downTo는 사용할 수 없습니다.

        // 아래는 Array<Char> 타입에서 char 타입을 꺼내 반복문을 사용하는 예시입니다.
        val alphabetic = arrayOf('a', 'b', 'c', 'd', 'e')
        for(item in alphabetic) {
            print(item)
        }
        println()

        // 만약 배열의 순서까지 출력한다면
        for((index, value) in alphabetic.withIndex()) {
            println("index[$index]: [$value]")
        }
        println()

        // map에서 반복문은 아래처럼 표현됩니다.
        // Map에서 Map.Entry를 꺼냅니다.
        val map = mapOf(1 to "one", 2 to "two", 3 to "three")
        for(item in map) {
            println("${item.key}: ${item.value}")
        }

        // list 역시 동일하게 표현됩니다.
        val list = listOf("one", "two", "three")
        for(item in list) {
            println(item)
        }
    }

    // kotlin에서 반복문 루프를 탈출시키기 위해선 세 가지를 사용합니다.
    // [return] => 함수를 탈출시켜 caller에 값을 전달합니다.
    // [break] => 감싸고 있는 반복문에서 탈출합니다.
    // [continue] => 감싸고 있는 반복문에서 다음 step으로 진행합니다.

    @DisplayName("반복문 탈출하기: Return")
    @Test
    fun returnTest() {

        // 아래는 list에서 "ROOT"를 찾으면 반복문을 종료시키는 예시입니다.
        val query = "ROOT"
        val list = listOf("ADMIN", "ROOT", "USER")

        for(item in list.indices) {
            if(list[item] == query) {
                println(item)
                return
            }
        }

        // return을 사용하면 반복문의 위치에 상관 없이 caller에 반환값을 함수를 종료시키기에
        // 아래의 함수는 실행이 불가합니다.
        println("여기까지 실행될까요")

    }

    @DisplayName("반복문 사용하기: break / continue")
    @Test
    fun breakTest() {

        // [break]: 감싸진 반복문 블록 실행을 즉시 종료시킵니다.
        // [continue]: 감싸진 반복문 블록 실행을 건너뛰고 바로 다음 step으로 이동시킵니다.

        // 아래 반복문은 1~30까지 반복문이 실행되지만 25에 도달하면 끊는 반복문입니다.
        for(i in 1..30) {
            print("$i ")
            if(i == 25) {
                println()
                break
            }
        }

        // 아래 반복문은 1~10까지 반복문을 실행하고, i가 짝수이면 건너뜁니다.
        for(i in 1..10) {
            if(i % 2 == 0) {
                println()
                continue    // i가 짝수일 때 아래 함수 실행을 건너뛰고 i++을 실행시킵니다.
            }
            print("$i ")
        }
    }

    @DisplayName("label 사용하기")
    @Test
    fun labelTest() {

        // 반복문에는 @Label을 사용할 수 있습니다.
        // 반복문의 이름을 매핑해주는 역할을 담당하며 중첩 반복문이더라도 특정 반복문을 끊을 수 있습니다.

        // 일반적인 break / continue은 해당 명령어가 속한 반복문을 빠져나오는 데 사용합니다.
        // 따라서 [j] 반복문에서 break가 걸리더라도 [i] 반복문 실행은 계속됩니다.
        println("======================================")
        println(" loop@가 없는 반복문 끊기")
        for (i in 9..12) {
            for(j in 1..9) {
                println("$i x $j = ${i * j}")
                if((i * j) > 100) {
                    break
                }
            }
        }

        // for문에서 label은 for 앞에 [label@] 형태로 표현합니다.
        // [break@label] / [continue@label]을 호출하여 반복문의 흐름을 바꿀 수 있습니다.
        // 아래의 반복문은 i * j의 결과가 100을 초과할 때 모든 반복문을 끊습니다.
        println("======================================")
        println(" loop@를 사용한 반복문 끊기")
        loop@ for (i in 9..12) {
            loop2@ for(j in 1..9) {
                println("$i x $j = ${i * j}")
                if((i * j) > 100) {
                    break@loop
                }
            }
        }

        // forEach lambda 역시 label을 사용할 수 있는데, 차이가 있습니다.
        println("======================================")
        println(" forEach loop@를 사용한 반복문 끊기")
        val array = arrayOf("topaz", "amber", "diamond", "jade", "gadget")
        array.forEach jewel@ {
            if(it == "diamond") {
                // lambda 함수 안에서 @label return을 사용하면
                // forEach caller 쪽에 Unit 값을 전달하므로 실질적으로 반복문은 계속됩니다.
                return@jewel
            }
            print("$it ")
        }
        // 따라서 아래 DONE! 문장 역시 출력이 가능합니다.
        println("DONE1!")

        // label을 명시하지 않아도 기본적으로 @forEach label이 생성됩니다.
        array.forEach {
            if(it == "amber") {
                return@forEach
            }
            print("$it ")
        }
        println("DONE2!")

        // [return]은 감싸고 있는 함수를 탈출시키는 것이므로
        // 익명 함수 안에서 사용하면 익명 함수를 탈출시킵니다.
        array.forEach(fun(value) {
            if(value == "jade") {
                return
            }
            print("$value ")
        })
        println("DONE3!")

    }
}