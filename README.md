## 메인화면 구성
<h4>메인화면은 총 3개로 구성 하였다.</h4>
<details>
  <summary>
    1. 최신순으로 게시글 10개
  </summary>
  <img src="https://github.com/dlguswo1/SpringBoot/assets/144756943/f371435d-11f5-4b39-bc84-d102306bf5a7"/>
</details>
<details>
  <summary>2. 카테고리별 최신순 10개</summary>
  <img src="https://github.com/dlguswo1/SpringBoot/assets/144756943/4fe43a85-c4a8-4855-8aa7-fe0ae85af701"/>
</details>
<details>
  <summary>3. 전체글</summary>
  <img src="https://github.com/dlguswo1/SpringBoot/assets/144756943/5e99db22-b2bd-4a03-bbe2-ad222baec4d5"/>
</details>

<br/>

## 게시글 작성
<details>
  <summary>게시글 작성</summary>
  <img src="https://github.com/dlguswo1/SpringBoot/assets/144756943/ac7985ec-4a47-4cef-8d15-c54cadc9f058"/>
</details>
<h6>1. 게시글 작성은 React-Quill editor을 사용하였다.</h6>
<h6>2. 파일 첨부와 에디터에 파일은 별개이다.</h6>
<h6>3. 파일과, 에디터 내용의 용량의 합이 30MB가 초과되면 작성이 불가능하다.</h6>

<br/>

<details>
  <summary>게시글 수정</summary>
  <img src="https://github.com/dlguswo1/SpringBoot/assets/144756943/f05252cd-74e9-4d26-823a-46c014e4dd06"/>
</details>
<h6>1. 게시글 작성페이지에서 첨부했던 파일과, 작성했던 내용을 가져온다.</h6>
<h6>2. 수정사항이 없을 경우 버튼은 비활성화 상태이다.</h6>
<h6>3. 파일첨부를 취소하고 새로 추가할 수 있다. <br/>
&nbsp;&nbsp;&nbsp;-(마찬가지로 게시글의 용량이 30MB을 초과하면 작성이 불가능하다.)</h6>
  
<br/>

## 게시글 상세보기
<h5>게시글 상세보기는 제목, 이미지, 내용, 작성자 정보, 댓글 창으로 구성 하였다.</h5>
<details>
  <summary>게시글 상세보기 1</summary>
  <img src="https://github.com/dlguswo1/SpringBoot/assets/144756943/f50d9e38-1118-47e7-b7c8-439b77908f6d"/>
</details>

<details>
  <summary>게시글 상세보기 2 (파일 다운로드)</summary>
  <img src="https://github.com/dlguswo1/SpringBoot/assets/144756943/9c3084a1-3d14-460d-a715-c1242343b547"/>
</details>
<h6>파일버튼을 클릭시 다운로드가 가능하다.</h6>

<details>
  <summary>게시글 상세보기 3 (댓글, 대댓글)</summary>
  <img src="https://github.com/dlguswo1/SpringBoot/assets/144756943/aaeb71a4-06f6-40a1-aa06-b564b5b577b1"/>
</details>
<h6>댓글은 작성자, 시간, 내용이 표시되며 대댓글은 depth을 2단계까지 설정하였다.</h6>

<br/>

## 마이페이지
<h4>마이페이지는 내가 쓴 글, 개인정보 수정, 알람으로 구성하였다.</h4>
<details>
  <summary>내가 쓴 글</summary>
  <img src="https://github.com/dlguswo1/SpringBoot/assets/144756943/68c3d07f-72ad-474d-861c-bccbf2846aa0"/>
</details>
<h6>1. 리스트 형식이고 등록일, 번호, 작성자, 제목으로 나타내었다.</h6>
<h6>2. 리스트중 해당 게시글을 클릭 시 게시글 상세보기가 나온다.</h6>
<h6>3. 파일항목은 다운이 가능하나 댓글작성, 댓글란은 나타내지 않았다.</h6>

<br/>

<details>
  <summary>개인정보 수정 비밀번호 확인</summary>
  <img src="https://github.com/dlguswo1/SpringBoot/assets/144756943/c132aa8f-2f9e-4cd6-8ac8-dc62d9b04551"/>
</details>
<h6>1. 개인정보 수정 전 비밀번호를 확인한다.</h6>

<br/>

<details>
  <summary>개인정보 수정</summary>
  <img src="https://github.com/dlguswo1/SpringBoot/assets/144756943/25836532-15f4-41f6-9a83-ec1f7adb0b86"/>
</details>
<h6>1. 개인 정보를 수정한다.</h6>


<br/>

<details>
  <summary>알림</summary>
  <img src="https://github.com/dlguswo1/SpringBoot/assets/144756943/b6494814-1a53-4292-8a5c-439f6e42b0f0"/>
</details>
<h6>1. 자신의 댓글, 게시글에 댓글이 달렸을 경우, 자신의 게시글, 댓글이 삭제처리 되었을 경우 알림을 적용했다.</h6>
<h6>2. 리스트 형식이고 형식 , 번호 , (작성자/수정자) , (제목/내용)으로 나타내었다.</h6>
<h6>3. 확인하지 않은 알림은 테두리선이 활성화 되어있다.</h6>

<br/>

<details>
  <summary>알림 확인 (댓글)</summary>
  <img src="https://github.com/dlguswo1/SpringBoot/assets/144756943/4c41acb2-4868-40a7-86d9-a236fe625e40"/>
</details>
<h6>1. 알림 클릭 시 게시글에 있는 해당 알림인 댓글까지 포커스 되도록 적용했다.</h6>

<br/>

## 로그인 회원가입
<details>
  <summary>회원가입</summary>
  <img src="https://github.com/dlguswo1/SpringBoot/assets/144756943/109a085f-0b8e-4240-b10e-86c88cab673b"/>
</details>
<h6>1. 성명, 전화번호, 이메일, 아이디, 비밀번호로 구성이 되어있다.</h6>
<h6>2. 입력사항을 지키지 않을 시 회원가입이 불가능하다. + (아이디 중복확인)</h6>
<h6>3. 회원가입 성공 시 로그인 페이지로 넘어간다.</h6>

<br/>

<details>
  <summary>로그인</summary>
  <img src="https://github.com/dlguswo1/SpringBoot/assets/144756943/ec8d71e9-819f-4736-a04b-fffa43b9a5c5"/>
</details>
<h6>1. 아이디, 비밀번호가 DB에 일치하면 해당 회원 정보와 accessToken을 발급한다.</h6>

<br/>

<details>
  <summary>로그인 후 GNB</summary>
  <img src="https://github.com/dlguswo1/SpringBoot/assets/144756943/6a963ee1-af9b-4d78-85ca-ab13a71c7fab"/>
</details>
<h6>1. ~반가워요 클릭 시 마이페이지로 이동한다.</h6>
<h6>2. 종 모양 클릭 시 알림페이지로 이동한다.</h6>

<br/>

## 관리자 페이지
<h4>마이페이지는 페이지관리, 회원관리로 구성되어있다.</h4>

<details>
  <summary>페이지 관리</summary>
  <img src="https://github.com/dlguswo1/SpringBoot/assets/144756943/67f0db93-9696-4d9d-b92e-77a8242f96d2"/>
</details>
<h6>1. 해당 3종류의 메인 페이지들이 나열되어있다.</h6>
<h6>2. 해당 3개의 페이지중 메인 페이지를 선택할 수 있다.</h6>

<br/>

<details>
  <summary>회원관리</summary>
  <img src="https://github.com/dlguswo1/SpringBoot/assets/144756943/3e30ff42-4838-415b-b3a2-f1eed31a12a5"/>
</details>
<h6>1. 회원의 정보들이 리스트로 나열되어 있다.</h6>
<h6>2. 회원 클릭시 해당 회원정보/수정 모달이 띄워진다.</h6>

<br/>

<details>
  <summary>회원수정 모</summary>
  <img src="https://github.com/dlguswo1/SpringBoot/assets/144756943/03d6d03a-9404-45ad-a8e4-4fa34021e270"/>
</details>
<h6>1. 해당 회원을 비활성화, 관한 변경을 할 수 있다.</h6>
