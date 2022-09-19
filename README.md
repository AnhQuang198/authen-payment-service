# authen-payment-service
- This project has created to practise technologies: spring security with jwt, oauth2, upload imge to s3 amazon 
- How to run:
    + Clone code in github
    + reinstall maven and pom
    + setting env in ide:
        MYSQL_CONNECT=jdbc:mysql://127.0.0.1:3306/authen_payment?useSSL\=false&serverTimezone\=UTC&useLegacyDatetimeCode\=false;
        USERNAME=root;
        PASSWORD=123456;
        PORT=8088;
        JWT_TOKEN_EXPIRE_TIME=1000000000;
        JWT_REFRESH_TOKEN_EXPIRE_TIME=1000000000;
        JWT_SECRET_KEY=123456;
        GOOGLE_SECRET_CODE=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;
        GOOGLE_ID=922871260995-p5mg0v8ei5im1erl4u4ag89kp57teigp.apps.googleusercontent.com;
        ACEBOOK_SECRET_CODE=1be2cae6ae607586f460d9296f076c70;
        FACEBOOK_ID=411361853491050;
        GITHUB_SECRET_CODE=cfada3772488ad2153978a8e144eff8933cb32c6;
        GITHUB_ID=5770d42cd1002c18da0a;
        OAUTH_REDIRECT_URI=http://localhost:3000/oauth2/redirect;
        S3_ENPOINT=https://d3hm98fncxeohs.cloudfront.net;
        S3_ACCESS_KEY=AKIARFNWHAI7XOMWA2NI;
        S3_SECRET_KEY=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;
        S3_BUCKET_NAME=imgs-resource;
        S3_REGION=ap-southeast-1;
        REDIS_HOST=127.0.0.1;
        REDIS_PORT=6379;
        JWT_OTP_EXPIRE_TIME=300000
        

Guideline: https://docs.google.com/document/d/15hNd1mW0VrKpOeMaiIyV5ZkjqLEsR2E3f5Q0HAhgJqQ/edit?usp=sharing
<br/>
SQL file: https://drive.google.com/file/d/1fs-xNIG1Ga_LStJPBNIzXScB0aIdoZHR/view?usp=sharing
