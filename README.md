# SNS 플랫폼 스느스 프로젝트 설명 문서입니다.
프론트엔드 + 백엔드 + 인프라 전 과정 개발을 진행 중인 개인 프로젝트입니다.
배포 후 사용자를 모집하여 실제 발생하는 문제들을 찾고 해결하고 있습니다.

구글 검색 : 스느스 또는 https://seuneuseu.com 접속 가능

# 핵심 기능
- 게시글의 데이터 중 댓글의 개수는 해당 게시글 댓글 수를 디비에서 세는 것이 아닌 각 댓글 작성 시마다 게시글 댓글 필드의 값을 1씩 증가시켜 주는 방식으로 되어있음.
  - 이는 근데 여러 사용자가 댓글을 동시에 달 경우 동시성 문제가 발생할 확률이 존재
  - 해결 방법 3가지가 존재 ( 낙관적 락으로 해결 )
    - 비관적 락: 실제 DB에 쓰기 락 걸고 진행     < 불필요한 성능 저하
    - 낙관적 락                                 < 한 게시글 내에서 댓글이 곂칠 일이 그렇게 많지 않음
      - 마지막 댓글 작성 시간을 게시글 DB에서 관리하고 이 값을 바탕으로 낙관적 락 진행, 최대 시도 횟수 3회
    - 주기적으로 댓글 개수 따로 업데이트         < 굳이?

# issue
- 2025.01.12 CORS 문제 발생.
  - 도메인 접근 시 https로 리다이렉션 안되고 종종 http(80포트)로 접근하여 발생하던 문제.
    - AWS ALB에서 80포트 리스너가 443으로 강제 리다이렉션 하도록 설정
- 2025.01.13 아이폰 사용자 이미지가 업로드되지 않는 문제 (HEIC 확장자 및 고용량이 원인)
  - 해결 방법 3가지(jpg로 확장자는 통일)
    - **클라이언트 측에서 jpg로 확장자 변환 및 압축 후 서버로 전송**            << 클라이언트 고사양 시대에 클라이언트에 부담을 주는 게 맞는 거 같아서 O
      - heic2any 라이브러리 이용 중 최신 아이폰 기종의 heic는 변환이 되지 않는 문제가 있음. issue 해결되지 않음, 따라서 heic-to 라이브러리를 이용
    - 백엔드에서 jpg로 확장자 변환 및 압축 후 S3로 업로드                          << EC2 프리티어 인스턴스 기준으로 서버에 부담을 주고싶지는 않아서 X
    - 백엔드 -> S3로 전송 후 S3에서 lambda로 처리                                 << AWS 람다 이용 비용 원치 않아서 X
- 2025.01.16 서버 터짐. 원인 파악 중
  - Ec2 인스턴스 시피유 사용량 100 찍고 터짐. 메모리도 아니고 시피유? 재부팅 후 해결. 최적화를 해야 될 듯?
    - 메모리 모니터링 결과 서버 메모리 문제였음. SWAP MEM 2GB 할당 후 해결

# 추가적으로 해야 될 작업
- 팔로우 팔로잉: 팔로잉 게시글 알고리즘 구상 후 구현 예정
- 게시글 좋아요
- 활동 알림: 댓글, 답글 및 팔로우 팔로잉 알림
- 게시글 페이징 처리

# 기술 스택
- [백엔드/프레임워크] Spring Boot
  - Spring Data JPA, Spring Security, Spring Web MVC
- [백엔드/언어] JAVA 21
- [프론트엔드/프레임워크] Next.js
  - shadcn/ui
- [백엔드/데이터베이스] MySQL 8.4
- [인프라/퍼블릭클라우드] AWS
  - EC2, ELB(ALB, 비용 문제로 인한 인스턴스 로드밸런싱은 X, HTTPS 통신 지원을 위해 사용), Route53
- [인프라/퍼블릭클라우드] GCP
  - Cloud SQL
- [도구] Git
- [도구] Postman
- [백엔드/서버] Nginx

# 인프라 아키텍처
![제목 없는 다이어그램-2-2 drawio](https://github.com/user-attachments/assets/59ca8841-7466-47c1-a8e8-2522bb45729d)

# 진행 과정 및 기간
- 요구 분석 및 설계 : 5일
- 기능 개발 : 1월 초 ~ 진행 중
- 인프라 구성 및 배포 준비 : 3일
- 문제 해결 및 성능 최적화 + 유지보수(리팩토링) : 진행 중

# 기본 기능
- [기능] 기본적인 SNS 소셜 네트워크 기능 존재
- [OAuth2] Google 아이디로 로그인 가능 + Session 방식
