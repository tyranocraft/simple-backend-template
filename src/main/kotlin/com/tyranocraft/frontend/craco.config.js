/*
1. craco 사용을 위해 src(= 루트경로)와 동일 레벨에 추가해야함!
2. package.json의 scripts에서 react-scripts 대신 craco를 사용하도록 변경해야함
  - react-scripts start -> craco start
  - react-scripts build -> craco build
  - react-scripts test -> craco test
3. tsconfig.json 에서 paths 설정을 추가해야함
  - "baseUrl": "src",
    "paths": {
      "@/*": ["*"]
    },
*/

const path = require("path");

module.exports = {
    webpack: {
        alias: {
            "@": path.resolve(__dirname, "src")
        }
    }
};
