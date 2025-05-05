[추가한 프론트 라이브러리]

1. npm install sass
    - CSS를 더 편하게 쓸 수 있게 해주는 CSS 확장 언어
    - scss 파일을 사용하기 위해 설치
    - {name}.module.scss 형식으로 생성
2. npm install @craco/craco
    - "@" 으로 scss 경로 단축을 위한 Webpack alias 설정
    - component 가 있는 곳에 index.ts 생성하여 사용 가능
3. npm install zustand
    - 상태관리 라이브러리
    - [useAuthStore.ts](src/store/useAuthStore.ts) 참고