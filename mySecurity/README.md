# Spring Secuirty
> ### 시작전
> > #### 인증 (Authentication)
> > - 사용자가 본인인지 확인하는 절차
> > #### 인가 (Authorization)
> > - 인증된 사용자가 요청 자원에 접근 가능한지 결정하는 절차 
> > #### 접근 주체(Principal)
> > - 보호받는 Resource에 접근하는 대상
> > #### 비밀번호(Crendential)
> > - Resource에 접근하는 대상의 비밀번호 
> > #### 권한 
> > - 인증된 주체에게 동작 수행 권한 



> ### 기본 개념 체크 
- #### Spring Security의 핵심 아키텍처 이해
- #### Authentication(인증)과 Authorization(인가)의 차이
- #### SecurityContextHolder, SecurityContext, Authentication 객체의 관계
- #### Filter Chain의 동작 방식

> ### Security 학습하면서 체크할 것
- #### Filter Chain의 동작 방식
- #### Authentication Provider의 구현
- #### UserDetails와 UserDetailsService
- #### PasswordEncoder의 사용
- #### Security Expression Language

`@Configuration` : 자바로 진행하는 설정 클래스에 붙이는 어노테이션으로 스프링 빈으로 만들고 스프링 프로젝트가 시작될 때 스프링 시큐리티 설정내용에 반영해줌.

`@EnableWebSecurity` : 스프링 시큐리티를 활성화하는 어노테이션

`WebSecurityConfigureAdapter` : 스프링 시큐리티 설정관련 클래스로 커스텀 설정클래스가 이 클래스의 메소드를 오버라이딩하여 설정하여야 스프링 시큐리티에 반영된다.
'Security 5.7이상에서는 더 이상 사용 권장 X ' -> FilterChain방식으로 변경 
`cors, csrf, session`과 같은 정책은 설정 초기에 하는 것이 좋음.

`cors().and()` : CorsFilter라는 필터가 존재하는데 이를 활성화 시키는 작업

`csrf().disable()` : 세션을 사용하지 않고 JWT 토큰을 활용하여 진행하는 REST API 작업에서는 csrf 사용은 disable 처리해줘야함.

`antMatchers()` : 특정 URL 에 대해서 어떻게 인증처리를 할지 결정

`permitAll()` : 스프링 시큐리티에서 인증이 되지 않더라도 통과시켜 누구에게나 사용을 열어줌.

`authenticated()` : 요청내에 스프링 시큐리티 컨텍스트 내에서 인증이 완료되어야 api를 사용할 수 있음.

`sessionManagement(session->session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
> ### UserDetails
- User 객체 
- loadUserByUsername을 오버라이딩하여 Request에 받은 데이터를 기반으로 로그인 작업 구현
- loadUserByUsername(loginId) -> UserDetails 객체 리턴

> ### 동작과정
> > #### 로그인 시도 -> HTTP BODY에 담긴 내용이 전달 -> 인증 관리 -> UserDetailsService에게 username 전달 -> 회원정보 요청 
> > #### 조회 정보 UserDetails에 반환 -> 인증 관리자 인증 처리 -> UserDetailsServiceㅇ가 전달한 정보를 바탕으로 아이디 암호 일치 여부 확인
