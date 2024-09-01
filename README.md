# spring-practice-kotlin
spring-practice-kotlin-mvc

## tests
소스에서 구현한 테스트 프로그램입니다.

### kafka
테스트 프로그램 실행 중 Exception이 발생하거나 circuitBreaker 상태가 변화하면 status message가 kafka로 전달됩니다.
kafka는 [docker-web](http://github.com/ennaru/docker-web) repository 를 기반으로 서버를 띄웠습니다.

### circuitBreaker
@CircuitBreaker 어노테이션이 붙은 함수에 임의로 Exception을 발생시켜 테스트를 진행합니다.
1. failureRate를 활용한 fallback
2. slowRate를 활용한 fallback

### redis
redis에 값을 설정하여 테스트를 진행합니다.
1. get, set, remove
2. lettuce를 활용한 lock

### spring session
스프링 세션에 값을 주고, JVM 성능의 변화를 분석해봅니다.
