package com.ennaru.practice.kotlin

import com.ennaru.practice.common.vo.logger
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.springframework.boot.test.context.SpringBootTest
import kotlin.test.AfterTest
import kotlin.test.BeforeTest

@SpringBootTest
@DisplayName("Kotlin에서 사용하는 Iterator Test")
class ListTest {

    val log = logger()

    @BeforeTest
    fun before() {
        log.info("[Kotlin Test] ${javaClass.simpleName}")
    }

    @AfterTest
    fun after() {
        log.info("=====================================")
    }

    /**
     * [kotlin의 Iterable 변수 선언 템플릿은 아래와 같습니다:]
     *
     * (val/var) (variable_name): List<(타입)> = (값)
     * (값)
     */

    @DisplayName("Iterator: List")
    @Test
    fun listBasic() {

        // Kotlin에서 List는 대표적으로 2개의 함수로 선언합니다.
        val list = listOf("A", "B", "C")
        val list2 = mutableListOf("A", "B", "C")

        // 또는 java에서 사용했던 ArrayList 타입으로도 만들 수도 있습니다.
        // 실제로 함수를 까보면 mutableListOf와 기능이 같고, return 타입이 다릅니다.
        var list3 = arrayListOf("A", "B", "C")

        // listOf로 만든 변수는 List<T> 타입으로, Read-Only 속성입니다.
        // 따라서 list의 요소를 변경할 수 있는 add, remove 함수가 없습니다.

        // mutableListOf로 만든 변수는 MutableList<T> 타입으로, List 안의 요소를 변형할 수 있습니다.

        // [val]로 선언해도 요소를 변형할 수 있는데, [val]은 재선언만 불가능하기 때문입니다. (변수 선언 시엔 선언할 공간을 가리킬 포인터 주소가 새로 생성됩니다.)
        // list2 = mutableListOf("22")
        list2.remove("B")
        list2.add("D")

        // 아래의 예시는 for {} 로 요소를 꺼내는 방법입니다.
        for(it in list2) {
            print(it)
        }
        println()

        // List는 Iterator형 클래스이므로 forEach 함수를 호출할 수 있습니다. 위의 for {} 문과 기능이 같습니다.
        list3.forEach{
            print(it)
        }

        println()

    }

    @DisplayName("Iterator: List (nullable)")
    @Test
    fun listBasicWithNullable() {

        // list 변수 역시 type 선언 시 nullable 옵션을 부여할 수 있습니다.
        val list: List<String?> = listOf("A", "B", null, "D")

        // 반복문에서 변수를 그대로 꺼냈을 때 값이 null 이면 null 그대로 출력합니다.
        list.forEach{
            print("$it ")
        }
        println()

        // 만약 확장 함수 [let]을 빌려 [?] operator를 사용하면, null 단계에서 사용할 수 없기 되므로 print를 출력하지 않습니다.
        list.forEach{
            it?.let { print("$it ") }
        }
        println()
    }

}