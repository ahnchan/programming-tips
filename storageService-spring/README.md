updated(2022.1.14) : 기본 틀은 완료하고 Local Storage에 직접 저장, 읽기를 구현하였다. 추가로 확장을 하면서 Exception 정의와 각종 Storage에 대한 기능을 늘려나갈 것이다.  

# Introduction
파일을 저장하고 읽기 위해서 설정에 따라 Cloud Storage(AWS S3, Google Storage 등) 혹은 Local Storage 등에 저장할 수 있어야 한다. 


# 컨셉
개발시는 Storage의 구성과는 상관없이 StorageService에 정의된 명령으로 저장(upload), 읽기, 다운로드를 할수 있게 한다. Stroage의 특성에 따라 발생하는 오류도 통일하여 개발시에는 상관없이 처리할 수 있도록 한다. 


# StorageService
application.yaml(혹은 application.properties)에 Storage를 정의하고 필요한 정보를 넣는다. 
Config와 Properties에서 해당 Storage에 대한 정보를 만들고 서비스를 사용할 수 있게 한다. 
각 Storage는 ervice/impl에 StorageService Interface에 정의된 기능을 구현한다. 


# 개발해야할 것
추가로 작업을 해야할 것을 정리하였다. 

1. 각종 Cloud Storage 를 늘리기 (Google, AWS, Azure, Dropbox 등등)
2. 여러 Storage 에 저장을 한다.(이 기능이 필요할지는 의문이다) --> 이것은 Event Message Architecture 가 효과적일 듯하다.
3. 여러파일을 동시에 Zip으로 압축하여 Download 하기 (Google Drive에서 처럼)
4. Async 하게 처리하기
5. NAS도 연결하기 


# References
아래 GitHub를 보고 구조에 대한 아이디어를 얻어서 진행하였다. 실제 라이브러리가 가져와지지 않아 fork로 처리하지 못하고 구현을 하게 되었다. 
[Cloud Storage Spring (Java/Kotlin)](https://github.com/sevtech-dev/cloud-storage-spring-api)