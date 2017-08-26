# Atomic
 [ ![Download](https://api.bintray.com/packages/infideap2/Atomic/Atomic/images/download.svg) ](https://bintray.com/infideap2/Atomic/Atomic/_latestVersion)

## Including In Your Project
If you are a Maven user you can easily include the library by specifying it as
a dependency:

#### Maven
``` xml
<dependency>
  <groupId>com.infideap.atomic</groupId>
  <artifactId>atomic</artifactId>
  <version>0.0.1</version>
  <type>pom</type>
</dependency>
```
#### Gradle
```groovy
dependencies {
   compile 'com.infideap.atomic:atomic:0.0.1'
}
```

if **the gradle unable to sync**, you may include this line in project level gradle,
```groovy
repositories {
 maven{
   url "https://dl.bintray.com/infideap2/Atomic/"
 }
}
```

**or**,
you can include it by **download this project** and **import /atomic** as **module**.

## How to use
```java
Atom.with(LoginActivity.this)
    .load("https://reqres.in/api/login")
    .setJsonPojoBody(loginRequest)
    .as(LoginResponse.class)
    .setCallback(new FutureCallback<LoginResponse>() {
        @Override
        public void onCompleted(Exception e, LoginResponse result) {
            if (e != null) {
                e.printStackTrace();
            } else if (result.token != null) {
                Snackbar.make(v, "Pass : " + result.token, Snackbar.LENGTH_LONG).show();
            } else if (result.error != null) {
                Snackbar.make(v, "Fail : " + result.error, Snackbar.LENGTH_LONG).show();
            }
        }
    });
```

## Contact
For any enquiries, please send an email to tr32010@gmail.com. 

## License

    Copyright 2016-2017 Shiburagi

    Licensed under the Apache License, Version 2.0 (the "License");
    you may not use this file except in compliance with the License.
    You may obtain a copy of the License at

       http://www.apache.org/licenses/LICENSE-2.0

    Unless required by applicable law or agreed to in writing, software
    distributed under the License is distributed on an "AS IS" BASIS,
    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
    See the License for the specific language governing permissions and
    limitations under the License.
