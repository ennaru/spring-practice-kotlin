package com.ennaru.practice.kotlin

import com.ennaru.practice.common.vo.logger
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.springframework.boot.test.context.SpringBootTest
import kotlin.test.AfterTest
import kotlin.test.BeforeTest

@DisplayName("Kotlin에서 사용하는 Nullable Test")
class VariableTest {

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
     * [kotlin의 변수 선언 템플릿은 아래와 같습니다:]
     *
     * (val/var) (variable_name): (타입) = (값)
     * - (타입)의 (variable_name) 변수를 생성하고, (값)을 (variable_name) 변수에 담습니다.
     *
     * Note: 변수 생성 시 타입은 반드시 정의되어 있어야 합니다.
     *  - (타입) 생략 시 (값)을 반드시 정의해야 합니다. (Smart Cast가 이뤄져 타입을 자동으로 매핑합니다.)
     *  - (값) 생략 시 (타입)을 반드시 정의해야 합니다.
     */

    @DisplayName("변수: val")
    @Test
    fun valBasic() {

        // [val]로 정의하면 변수에 값을 할당한 후에 변경이 불가합니다.
        val value1: String = "String12"
        val value2: String

        // 초기화하지 않은 채로 변수 호출을 시도하면 컴파일 에러가 발생합니다.
        //println("value1: $value1")

        // value1은 선언 시에 할당했으므로 컴파일 에러가 발생합니다.
        //value1 = "String"

        // value2는 선언 후 값을 할당하지 않았으므로
        value2 = "String34"

        println("value1: $value1")
        println("value2: $value2")

    }

    @DisplayName("변수: var")
    @Test
    fun varBasic() {

        // [var]로 정의하면 변수에 값을 언제든지 교체할 수 있습니다.
        var value1: String = "Tester12"
        var value2: String

        // [var]도 마찬가지로 초기화를 하지 않은 채로 변수 호출을 시도하면 컴파일 에러가 발생합니다.
        //println("value1: $value1")

        // [var]로 선언한 변수는 값을 언제든지 교체할 수 있습니다.
        value1 = "tester"
        value2 = "tester34"
        println("value1: $value1")
        println("value2: $value2")
    }

    // lateInit() 테스트를 위해 간이로 생성한 class
    private class LateTest {
        private lateinit var value: String
        fun isInit(): Boolean {
            return this::value.isInitialized
        }
        fun setValue(value: String) {
            this.value = value
        }
    }

    @DisplayName("변수: lateinit")
    @Test
    fun lateInit() {

        // 만약 변수 선언과 동시에 값을 초기화하고 싶지 않을 때는 [lateinit]을 사용합니다.
        lateinit var value1: String

        // [lateinit]에는 제약사항이 존재합니다.

        // [lateinit]으로 선언한 변수는 초기화와 상관없이 값을 꺼내는 로직을 넣으면 컴파일 오류가 발생하지 않습니다.
        // 대신 런타임에서 값을 꺼내는 순간 UninitializedPropertyAccessException 에러가 반환됩니다.
        try {
            print(value1)
        } catch(e: Exception) {
            println("Exception! : ${e.javaClass.simpleName}")
            println("Exception! : ${e.message}")
        }

        // [lateinit]은 mutable variable 만 허용합니다.
        // 즉, 값을 변경할 수 없는 [val]로 정의할 수 없습니다.
        //lateinit val value2: String

        // [lateinit]은 Primitive Type 에선 사용할 수 없습니다.
        // [Primitive Type]에는 [Byte] [Int] [Double] [Float] [Short] [Long] 이 있습니다.
        //lateinit var value2: Int

        // [lateinit]은 선언과 동시에 값을 초기화할 수 없습니다.
        //lateinit var value2 = "#33"

        // [lateinit]은 클래스 변수에 선언하고 사용하면 아래처럼 활용할 수 있습니다.
        val lateTest: LateTest = LateTest()

        // isInit() 함수에는 클래스에 선언한 변수에 [.isInitialized]를 호출하면 초기화 여부를 확인할 수 있습니다.
        println("lateTest.isInit(): ${lateTest.isInit()}")
        lateTest.setValue("50")
        println("lateTest.isInit(): ${lateTest.isInit()}")

    }

    @DisplayName("변수: ? Operator")
    @Test
    fun safeCallOperator1() {

        // 타입을 지정하면서 null을 허용해야 할 때 타입 뒤에 [?] operator를 붙입니다.
        // 아래의 변수는 [String?] 타입으로 정의됩니다.
        var value2: String? = "String"
        var value3: String?

        value2 = null
        value3 = null

        print("value2: $value2")
        print("value3: $value3")

        // 만약 [?] operator를 붙이지 않는 타입을 지정해 놓고 null을 넣으면 컴파일 에러가 발생합니다.
        //var value1: String = null

        // 만약 null로 값을 정의하면 [Nothing?] 타입으로 선언됩니다.
        // Nothing 타입은 '존재할 수 없는 값'을 의미하며 해당 변수로 Return Type으로 선언하면 '아무것도 반환하지 않겠다'를 의미합니다.
        val value4 = null
        print("value4: $value4")
    }

    @DisplayName("변수: ? Operator 2편")
    @Test
    fun safeCallOperator2() {

        // safeCallOperator1() 함수에서 Nullable 변수 선언을 확인하였습니다.
        // 아래의 변수들로 예로 들어봅니다.
        val value1: String = "Variable"
        var value2: String? = "Variable"

        // String 클래스에는 [public]으로 선언된 [length] 값을 꺼낼 수 있습니다.
        println(value1.length)    // [8] 반환

        // 하지만 클래스에 선언된 변수나 함수를 호출할 때에는 [?] Operator가 붙은 변수에는 호출할 수 없습니다.
        //print(value2.length)
        //value2.get(0)

        // 아래의 toString() 함수는 nullable임에도 호출되는 이유는 Kotlin 기본 모듈인 [Library.kt]의 최상위 함수로 선언되어 있기에 호출이 가능합니다.
        println("value2.toString is ${value2.toString()}")

        // 만약 nullable 변수에서 클래스에 선언된 변수나 함수를 호출할 때에는 [?.] 을 사용하여 Safe Call을 수행합니다.
        println("[String?] type인 value2의 길이: ${value2?.length}")   // [8]

        // null을 집어넣고 클래스 내부의 값을 호출하면 [null]을 반환합니다.
        value2 = null
        println("[String?] type인 value2의 길이: ${value2?.length}")   // [null]

    }

    @DisplayName("변수: Smart Cast 편")
    @Test
    fun smartCast() {

        // Kotlin에서는 Smart-cast 기능을 제공합니다.
        // 타입을 명세하지 않아도 컴파일러에서 타입을 식별하여 적절한 타입으로 Casting을 진행합니다.

        // 파라미터 variable은 Any? 타입이지만
        // if문에서 String 타입을 확인하므로 if-true 문 안에서는 String 타입으로 자동 변환됩니다.
        val isString = fun(variable : Any?) {
            //variable.length // Any? 타입이므로 String class에서 호출할 수 있는 length를 꺼낼 수 없습니다.
            if(variable is String) {
                println(variable.length)
            } else {
                println("not string!")
            }
        }

        // "String" 이라는 String 타입이 들어왔으므로 "6"이 출력됩니다.
        isString("String")

        // 555 라는 Integer 타입이 들어왔으므로 "not String!"이 출력됩니다.
        isString(555)

        // Smart Cast는 if문이 아닌 when 문에서도 작동합니다.
        val typeChecker = fun(variable : Any?) {
            when (variable) {
                is String -> { println("${variable.javaClass.simpleName} > ${"$variable RUN!!"}") }
                is Boolean -> { println("${variable.javaClass.simpleName} > ${!variable}")}
            }
        }

        // 함수 안에서 variable 값을 반대로 찍으므로 [true]가 출력됩니다.
        typeChecker(false)

        // 함수 안에서 varlable 값에 " RUN!!"을 붙였으므로 [seoul RUN!!]이 출력됩니다.
        typeChecker("seoul")

        // try-catch 절에서는 어떻게 핸들링되나요?
        // 먼저 nullabe 타입을 선언합니다.
        var intValue : Int? = null
        intValue = 3
        try {

            // intValue는 [Int?] 타입이지만
            // intValue에 값이 할당되었을 때에는 컴파일러가 자동으로 [Int] 타입으로 변환시킵니다.
            println(intValue.mod(2))
            println(intValue.javaClass.simpleName)

            // 만약 intValue에 null을 집어넣으면
            // 컴파일러가 자동으로 [Int?] 타입으로 변환시킵니다.
            intValue = null
            println(intValue?.mod(2))   // null 출력

            throw Exception()

        } catch(e: Exception) {
            // 이곳에서는 intValue가 Int? 타입이어도, try {} 바깥에서 값이 할당되었기에 Int로 Smart Cast가 이루어집니다.
            // 하지만 intValue가 null일 때 주석으로 표시된 아래 함수가 실행되면 NullPointerException이 발생합니다.
            //println(intValue.mod(3))

            // 해당 함수는 Elvis Operator를 먼저 심어 Null을 확인하기에 null을 반환합니다.
            println(intValue.mod(3))
        }

        // Smart Cast는 컴파일러가 해당 변수의 타입이 확정되었을 때 자동으로 변환해줍니다.
        // 그러므로 변수의 값이 수정될 가능성이 있을 때에는 Smart Cast가 작동하지 않을 수 있기에 타입 명시가 권장됩니다.

    }


}