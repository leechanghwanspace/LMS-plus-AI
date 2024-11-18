package com.project.LMS_plus.config;

import com.project.LMS_plus.entity.Job;
import com.project.LMS_plus.repository.JobRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
public class JobConfig {

    @Bean
    CommandLineRunner initJobData(JobRepository jobRepository) {
        return args -> {
            List<String> jobNames = List.of(
                    "게임 기획자:게임 디자인, 레벨 디자인, 게임 밸런스, 스토리텔링, UX 설계, 기획 문서, 플로우 차트, 게임 경제 설계, 프로토타입, 유저 피드백","게임 프로그래머: Unity, Unreal Engine, C#, C++, 게임 엔진, 물리 시뮬레이션, AI 알고리즘, 게임 서버, 렌더링 최적화, 멀티플레이 구현, 메모리 관리",
                    "게임 그래픽 디자이너: 3D 모델링, 애니메이션, Blender, Maya, 그래픽 디자인, 텍스처링, 리깅, 이펙트 디자인, 캐릭터 디자인, UI/UX 디자인",
                    "VR/AR 콘텐츠 개발자: 가상현실, 증강현실, Unity, HoloLens, VR 헤드셋, XR 개발, 공간 매핑, 제스처 인식, 오큘러스 퀘스트, WebXR, 인터랙션 디자인",
                    "모바일 게임 개발자: 모바일 플랫폼, iOS, Android, Unity, 게임 최적화, 모바일 UX, 터치 인터페이스, 인앱 결제, 광고 통합, 소셜 미디어 연동",
                    "소프트웨어 엔지니어: 소프트웨어 아키텍처, 설계 패턴, Agile, CI/CD, 코드 리뷰, 시스템 설계, 버전 관리(Git), 클린 코드, 테스트 주도 개발(TDD), DevOps",
                    "백엔드/프론트엔드 개발자: React, Angular, Vue.js, Node.js, Spring Boot, REST API, GraphQL, WebSocket, TypeScript, HTML/CSS, 서버리스 아키텍처",
                    "데이터베이스 관리자(DBA): SQL, MySQL, Oracle DB, 데이터베이스 튜닝, NoSQL, 데이터 백업, 데이터 복구, 데이터 보안, 쿼리 최적화, 데이터 모델링, PostgreSQL",
                    "UI/UX 디자이너: 사용자 경험, 프로토타이핑, Figma, Sketch, 사용성 테스트, 와이어프레임, A/B 테스트, 인터페이스 디자인, 사용자 여정 맵, 적정성 테스트",
                    "클라우드 엔지니어: AWS, Azure, Google Cloud, DevOps, 컨테이너 오케스트레이션, Docker, Kubernetes, IaC(Infrastructure as Code), 클라우드 모니터링, 하이브리드 클라우드",
                    "정보보안 전문가 (CISO): 보안 정책, IT 거버넌스, 위협 관리, 보안 리더십, 규정 준수, ISO 27001, 사이버 공격 대응, 리스크 분석, 사고 관리, 보안 보고",
                    "네트워크 보안 엔지니어: 방화벽, IDS/IPS, VPN, 네트워크 모니터링, 네트워크 트래픽 분석, DDoS 방어, 라우터 및 스위치 보안, ZTNA(Zero Trust Network Access)",
                    "시스템 보안 관리자: 시스템 취약점, 패치 관리, 로그 분석, OS 보안, 사용자 계정 관리, 접근 제어, 시스템 복구, 보안 운영 센터(SOC)",
                    "악성코드 분석가: 리버스 엔지니어링, 바이너리 분석, 악성코드 샌드박스, 디컴파일러, 행동 기반 탐지, 보안 이벤트 분석, 루트킷 분석",
                    "보안 컨설턴트: 보안 감사, 위험 평가, 보안 솔루션, 클라이언트 관리, 보안 침해 대응, 보안 교육, 규정 준수 검토",
                    "디지털 포렌식 전문가: 증거 수집, 데이터 복구, 법의학 분석, 사건 추적, 로그 분석, 포렌식 소프트웨어(EnCase, FTK), 네트워크 포렌식",
                    "침투 테스트 전문가 (펜테스터): 침투 테스트, 취약점 스캐닝, 모의 해킹, Kali Linux, 메타스플로잇, 익스플로잇 개발, OWASP Top 10",
                    "보안 솔루션 개발자: 암호화, 인증 시스템, 보안 소프트웨어, 키 관리, 보안 프로토콜, PKI, SIEM",
                    "개인정보 보호 관리자: 개인정보보호법, GDPR, 데이터 보호, 프라이버시 관리, 개인정보 암호화, 데이터 최소화, 데이터 전송 보호",
                    "클라우드 보안 전문가: 클라우드 보안 아키텍처, 데이터 암호화, 제로 트러스트, 클라우드 규정 준수, CASB(Cloud Access Security Broker), 서버리스 보안",
                    "머신러닝 엔지니어: 머신러닝, TensorFlow, PyTorch, 알고리즘 개발, 데이터 전처리, 모델 배포, 딥러닝, Scikit-learn, 강화학습, Hyperparameter Tuning",
                    "데이터 사이언티스트: 데이터 분석, 통계, Python, R, 빅데이터 처리, 데이터 시각화, Tableau, Power BI, Hadoop, Spark",
                    "AI 연구원: 인공지능 연구, 딥러닝, 자연어 처리, 강화학습, AI 알고리즘, 기계 번역, 지식 그래프, 논문 작성",
                    "컴퓨터 비전 엔지니어: OpenCV, 이미지 처리, 객체 인식, 딥러닝, YOLO, 이미지 분할, GAN, 영상 처리, 3D 비전, SLAM",
                    "자연어 처리(NLP) 전문가: 텍스트 분석, 언어 모델, GPT, BERT, 토큰화, 기계 번역, 음성-텍스트 변환, 감정 분석",
                    "로보틱스 소프트웨어 엔지니어: ROS, 로봇 제어, 센서 융합, 자율주행 로봇, 매니퓰레이터, 경로 계획, LiDAR, SLAM",
                    "음성 인식 개발자: 음성 합성, 음성 인식 API, STT/TTS, 딥러닝, 음성 데이터 처리, Google Speech-to-Text, 텍스트 처리",
                    "AI 윤리 컨설턴트: AI 윤리, 책임 있는 AI, 편향 제거, AI 정책, 윤리적 AI 설계, 공정성, 투명성",
                    "추천 시스템 개발자: 추천 알고리즘, 협업 필터링, 개인화, 사용자 데이터, 콘텐츠 기반 추천, 행렬 분해, 그래프 기반 추천",
                    "자율주행차 소프트웨어 엔지니어: 자율주행, 경로 계획, 센서 융합, 차량 네트워크, 라이다(LiDAR), ADAS, SLAM, 차량 제어"
            );

            for (String name : jobNames) {
                if (!jobRepository.existsByJobName(name)) {
                    jobRepository.save(new Job(name));
                }
            }
        };
    }
}
