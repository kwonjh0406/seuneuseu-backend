# SNS 플랫폼 스느스 프로젝트 설명 문서입니다.
프론트엔드 + 백엔드 + 인프라 전 과정을 개발을 진행 중인 개인 프로젝트입니다.

# 기술 스택
- [백엔드] Spring Boot
  - Spring Data JPA, Spring Security, Spring Web MVC
- [백엔드] JAVA 21
- [프론트엔드] Next.js
  - shadcn/ui
- [백엔드] MySQL 8.4
- [인프라] AWS
  - EC2, ELB(ALB, 비용 문제로 인한 인스턴스 로드밸런싱은 X, HTTPS 통신 지원을 위해 사용), Route53
- [인프라] GCP
  - Cloud SQL
- [도구] Git
- [도구] Postman
- [백엔드] Nginx

# 인프라 아키텍처
![제목 없는 다이어그램-2-2 drawio](https://github.com/user-attachments/assets/59ca8841-7466-47c1-a8e8-2522bb45729d)


# 진행 기간
- 요구 분석 및 설계 : 5일
- 단순 기능 개발 : 14일
- 인프라 구성 및 배포 : 3일
- 보안 문제 해결 및 성능 최적화 + 유지보수 (테스터 5명) : 진행 중

# 주요 기능
- [기능] 기본적인 게시판의 CRUD 및 소셜 네트워크 기능 존재
- [OAuth2] Google 아이디로 로그인 가능
- [캐싱] 페이징 처리를 통해 10개씩 게시글을 보여주며, 가장 자주 사용자가 로딩한다 판단되는 상위 50개의 게시물은 메모리에 보관하며 디비 변동 발생 시 동기화

# 데이터베이스
테이블 그림
