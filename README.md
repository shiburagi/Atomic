# Atomic
 [ ![Download](https://api.bintray.com/packages/infideap2/Atomic/Atomic/images/download.svg) ](https://bintray.com/infideap2/Atomic/Atomic/_latestVersion)
[![Android Arsenal](https://img.shields.io/badge/Android%20Arsenal-Atomic-brightgreen.svg?style=flat)](https://android-arsenal.com/details/1/6344)


An android restful api/networking library using okhttp library as backbone.

Android 9.0+ support

---

<a href='https://ko-fi.com/A0A0FB3V' target='_blank'><img height='36' style='border:0px;height:36px;' src='https://az743702.vo.msecnd.net/cdn/kofi4.png?v=0' border='0' alt='Buy Me a Coffee at ko-fi.com' /></a>
[![paypal](https://www.paypalobjects.com/en_US/i/btn/btn_donateCC_LG.gif)](https://www.paypal.com/cgi-bin/webscr?cmd=_s-xclick&hosted_button_id=D9JKYQL8452AL)

## Including In Your Project
If you are a Maven user you can easily include the library by specifying it as
a dependency:

#### Maven
``` xml
<dependency>
  <groupId>com.infideap.atomic</groupId>
  <artifactId>atomic</artifactId>
  <version>0.0.8</version>
  <type>pom</type>
</dependency>
```
#### Gradle
```groovy
dependencies {
   implementation 'com.infideap.atomic:atomic:0.0.8'
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

**POST**
```java
Atom.with(LoginActivity.this)
    .load("https://reqres.in/api/login")
    .setJsonPojoBody(loginRequest)
    //.setBody(requestString) //Plain String
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

**GET**
```java
Atom.with(LoginActivity.this)
    .load("https://reqres.in/api/users/2")
    .as(UserResponse.class)
    .setCallback(new FutureCallback<LoginResponse>() {
        @Override
        public void onCompleted(Exception e, UserResponse result) {
            if (e != null) {
                e.printStackTrace();
            } else if (result.first_name != null) {
                Snackbar.make(v, "Pass : " + result.first_name, Snackbar.LENGTH_LONG).show();
            } 
        }
    });
```

**Other**, just add method after url on load()
```java
Atom.with(LoginActivity.this)
    .load("https://reqres.in/api/users/2", Atom.DELETE_METHOD)
    .asString()
    .setCallback(new FutureCallback<String>() {
        @Override
        public void onCompleted(Exception e, String result) {
            if (e != null) {
                e.printStackTrace();
            } else if (result != null) {
                Snackbar.make(v, "Pass : " + result, Snackbar.LENGTH_LONG).show();
            } 
        }
    });
```

**Upload File**
```java
Atom.with(LoginActivity.this)
    .load("https://reqres.in/api/user/2")
    .setJsonPojoBody(userRequest)
    .setMultipartFile("uploadFile", new File("video.mp4"))
    //Optional: Upload Progress
    .uploadProgress(new ProgressCallback() {
        @Override
        public void onProgress(long uploaded, long total) {
            
        }
    })
    .as(UserResponse.class)
    .setCallback(new FutureCallback<UserResponse>() {
        @Override
        public void onCompleted(Exception e, UserResponse result) {
            if (e != null) {
                e.printStackTrace();
            } 
        }
    });
```

**Download File**
```java
Atom.with(LoginActivity.this)
    .load("https://developer.android.com/_static/66ebbcad58/images/android/touchicon-180.png")
    //Optional: Progress
    .progress(new ProgressCallback() {
        @Override
        public void onProgress(long downloaded, long total) {
            
        }
    })
    .write(new File("android.png"))
    .setCallback(new FutureCallback<File>() {
        @Override
        public void onCompleted(Exception e, File result) {
            
        }
    });
```

## Advance
**Plain String**
```java
Atom.with(LoginActivity.this)
    .load("https://reqres.in/api/users/2")
    .asString()
    .setCallback(new FutureCallback<String>() {
        @Override
        public void onCompleted(Exception e, String result) {
            if (e != null) {
                e.printStackTrace();
            } else if (result!= null) {
                Snackbar.make(v, "Pass : " + result, Snackbar.LENGTH_LONG).show();
            } 
        }
    });
```
**Form Data**
```java
Atom.with(LoginActivity.this)
    .load("https://reqres.in/api/login")
    .setMultipart("Key1","Value1")
    .setMultipart("Key2","Value2")
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
**Direct call without callback (must call in Thread/Service/AsyncTask)**
```java
String body = Atom.with(LoginActivity.this)
    .load("https://reqres.in/api/users/2")
    .asString()
    .get();
```
**Access OKHttp Response (must call in Thread/Service/AsyncTask)**
```java
Response body = Atom.with(LoginActivity.this)
    .load("https://reqres.in/api/users/2")
    .execute();
```

**Access OKHttp Response**
```java
Atom.with(LoginActivity.this)
    .load("https://reqres.in/api/login")
    .setJsonPojoBody(loginRequest)
    .as(LoginResponse.class)
    .setCallback(new ResponseFutureCallback<LoginResponse>() {
        @Override
        public void onCompleted(Exception e, LoginResponse result, Response response) {
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

**Customize**
```java
OkHttpClient client =new OkHttpClient.Builder().addInterceptor(new Interceptor() {
    @Override
    public Response intercept(Chain chain) throws IOException {
        return null;
    }
}).build();
Atom.setClient(client);
```

## Contact
For any enquiries, please send an email to tr32010@gmail.com. 

## License

    Copyright 2017-2018 Shiburagi

    Licensed under the Apache License, Version 2.0 (the "License");
    you may not use this file except in compliance with the License.
    You may obtain a copy of the License at

       http://www.apache.org/licenses/LICENSE-2.0

    Unless required by applicable law or agreed to in writing, software
    distributed under the License is distributed on an "AS IS" BASIS,
    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
    See the License for the specific language governing permissions and
    limitations under the License.
