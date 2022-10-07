# 스프링 배치

## 특징
- 매우 큰 데이터 셋에서 반복적으로 처리되는 복잡한 비즈니스 규칙의 주기적 적용
- 일반적인 형식 지정, 유효성 검사 및 트랜잭션 처리가 필요한 외부 데이터를 일괄 처리하는데 사용
- 엔터프라이즈 시스템의 일상적인 운영에 필수적인 배치 어플리케이션의 개발을 가능하게 하도록 설계된 가볍고 포괄적인 배치 프레임워크
- 스프링 배치는 스케줄링 프레임워크가 아님, 쿼츠, 티볼리, 컨트롤엠이 있음
- 스프링 배치는 로깅/추적 트랜 잭션 관리 작ㅇ버 처리 통계, 재시작, 건너뛰기, 대용량 레코드 처리에 필수적인 재사용가능한 기능들을 제공
- 최적화 및 파티셔닝 기술을 통해 대용량 및 고성능 작업을 가능하게 함


## 아키텍처
### Job
- 배치 처리 과정의 단위
- JobInstance: Job 실행 단위. Job parameter 로 구분되며, 파라미터가 같으면 같은 인스턴스로 본다
- JobExecution: Job 실행 객체. 잡 인스턴스를 실행할 때 마다 새로 생성한다
- JobParameters: JobInstance 를 구분할 수 있고, 4가지 타입을 지원. String, Double, Long, Date

### Step
- Job 을 구성하는 단위 작업 요소
- 독립적이고 순차적으로 수행
- tasklet 기반과 chunk 기반으로 나뉜다

### 트리거, 스케줄링
- 젠킨스 크론 표현식을 이용해 트리거
- API 콜 Controller 호출
- 쿼츠 사용
- 스프링 클라우드 데이터 플로우

## 스코프
스프링 배치에서 빈은 특별한 스코프를 갖는다

### Job scope
- Job 이 실행되는 시점에 생성되어 종료될 때 소멸

### Step scope
- Step 이 실행될때 생성되고 종료될 때 소멸

## 병렬성
다양한 병렬화 기능을 제공한다

- step 멀티스레드 실행
- 비동기 프로세서
- 원격 chunk 처리
- 파티셔닝