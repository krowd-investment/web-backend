# Welcome to web service for Krowd Investment Project: ***FunFund***

### How to enable HTTPS with self-signed certificate in localhost
#### Generate a key pair:
- Create a folder name self-signed somewhere and open it in cmd, then type:
```
keytool -genkeypair -alias local_ssl -keyalg RSA -keysize 2048 -storetype PKCS12^
More?  -keystore local-ssl.p12 -validity 365 -ext san=dns:localhost
```

#### Enable HTTPS in spring app configuration file
- Copy keystore file into resources folder, then type these in application.yml file:
```
server:
  port: <port>
  ssl:
    enabled: true
    key-alias: local_ssl
    key-store: classpath:local-ssl.p12
    key-store-type: PKCS12
    key-password: <keystore password>
    key-store-password: <keystore password>
```
#### Export certificate:
```
keytool -export -keystore local-ssl.p12 -alias local_ssl -file local-cert.crt
```

#### Install self-signed certificate to Trusted Root Certification Authorities

---
### For more information, please
#### Contact us via email: viettien1602@gmail.com

#### Copyright &#169; 2023 Team SWD392 *Summer 2023 FPT HCM University*