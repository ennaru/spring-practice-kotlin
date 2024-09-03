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
        log.info("[Kotlin Test] ${javaClass.simpleName} Start")
    }

    @AfterTest
    fun after() {
        log.info("[Kotlin Test] ${javaClass.simpleName} End")
    }

    /**
     * [kotlin의 Iterable 변수 선언 템플릿은 아래와 같습니다:]
     *
     * (val/var) (variable_name): List<(타입)> = (값)
     * (val/var) (variable_name): Set<(타입)> = (값)
     */

    @DisplayName("Iterator: List")
    @Test
    fun listBasic() {

        // Kotlin에서 List는 대표적으로 2개의 함수로 선언합니다.
        val list = listOf("A", "B", "C")
        val list2 = mutableListOf("A", "B", "C")

        // 또는 java에서 사용했던 ArrayList 타입으로도 만들 수도 있습니다.
        // 실제로 함수를 까보면 mutableListOf와 기능이 같지만 return 타입이 다릅니다.
        var list3 = arrayListOf("A", "B", "C")

        // listOf로 만든 변수는 List<T> 타입으로, Read-Only 속성입니다.
        // 따라서 요소를 변경할 수 있는 add, remove 함수가 없습니다.

        // mutableListOf로 만든 변수는 MutableList<T> 타입으로, List 안의 요소를 변형할 수 있습니다.

        // [val]로 선언해도 요소를 변경할 수 있는데, [val]은 재선언만 불가능하기 때문입니다. (변수 선언 시엔 선언할 공간을 가리킬 포인터 주소가 새로 생성됩니다.)
        // list2 = mutableListOf("22")
        list2.remove("B")
        list2.add("D")

        // list 값을 그대로 출력에 사용할 수 있습니다.
        println(list2)
    }

    @DisplayName("Iterator: 반복문으로 변수 꺼내기")
    @Test
    fun forEachPractice() {
        val list: List<String?> = listOf("A", "B", null, "D")

        // 아래의 예시는 for {} 로 요소를 꺼내는 방법입니다.
        for(it in list) {
            print("$it ")
        }
        println()

        // Iterator 클래스는 forEach 함수를 사용할 수 있습니다.
        list.forEach {
            println("[value: $it]")
        }

        // 또한 [index]도 변수로 받는 forEachIndexed 함수도 지원합니다.
        list.forEachIndexed { index, s ->
            println("[index: $index][value: $s]")
        }
        println()
    }

    @DisplayName("Iterator에서 지원하는 함수들")
    @Test
    fun iterableFunctions() {
        val list1: List<String> = listOf("해", "뤼", "보")
        val list2: List<String?> = listOf("해", "뤼", "보")

        // sorted() / sortedDescending() 함수는 정렬된 list를 반환합니다.
        // nullable 타입은 호출이 불가합니다.
        println(list1.sorted())              // 오름차순
        println(list1.sortedDescending())    // 내림차순
        //println(list2.sorted())

        // map() 함수는 요소의 값을 변형한 list를 반환합니다.
        // nullable type의 List도 사용할 수 있습니다. String? 타입이면 null을 문자열로 판단합니다.
        val newList1 = list1.map { it + 12345 }
        val newList2 = list2.map { it + 12345 }
        println("newList1: $newList1")
        println("newList2: $newList2")

        // 단, [Int]처럼 연산 작업 시엔 Safe Call을 하지 않거나 null을 확인하지 않으면 오류가 발생합니다.
        val list3: List<Int?> = listOf(10, 20, null, 50, 40, 15)
        //val newList3 = list3.map { it * 20 }
        val newList3 = list3.map { it?.times(20) }
        println(newList3)

        // filter() 함수는 조건에 부합하는 값만 추출한 뒤 list로 반환합니다.
        val filteredList = list2.filter { it?.startsWith("하") == true }
        println(filteredList)

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

        // 만약 scope 함수인 [let]을 빌려 [?] operator를 사용하면, null 단계에서 사용할 수 없기 되므로 print를 출력하지 않습니다.
        list.forEach{
            it?.let { print("$it ") }
        }
        println()
    }

}