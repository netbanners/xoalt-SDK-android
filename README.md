Страница описывает последовательность действий для добавления SDK в проект.

Весь код SDK взят от сюда:    
https://docs.prebid.org/prebid-mobile/pbm-api/android/code-integration-android.html.  

Полный пример находиться по адресу:   
 https://github.com/netbanners/xoalt-SDK-android/tree/main/src            

# Загрузка библиотеки

Для начала загрузите файл **xoalt-sdk-android-v1.2.2.aar** по адресу:    
https://github.com/netbanners/xoalt-SDK-android/releases в папку /lib.   

В app/build.gradle должно быть прописано 

```java

    implementation files('libs/xoalt-sdk-android-v1.2.2.aar')
    implementation 'com.google.android.gms:play-services-ads:24.0.0'
    implementation "com.google.android.gms:play-services-ads-identifier:18.0.1"

``` 

Вы должны убедиться что файлы загружены.    


# Инициализация 

Код ниже содержит макрос **${BLOCK_ID}** этот макрос должен быть заменен реальным идентификатором блока, 
например это может быть 35733. Этот идентификатор предоставит специалист осуществляющий интеграцию. 

```java
        BannerSDK.initializeSdk(this, Integer.parseInt(pref.getString("current_request", "${BLOCK_ID}")),
                status -> {
                    if (status == InitializationStatus.SUCCEEDED) {
                        Log.d(TAG, "SDK initialized successfully!");
                        start( );
                    } else {
                        Log.e(TAG, "SDK initialization error: $status\n${status.description}");
                    }
                }
        );
```


# Вставка рекламного блока  

```java
   // Создание баннера
   BannerView bannerView =  new BannerView(this); 

   // Через метод setSizeBanner можно задать размеры баннера
   // Если размеры не указаны будет создан баннер 300х250      
   bannerView.setSizeBanner(320 ,50);

   // Загрузка баннера
   bannerView.addBanner();
```


# Вариант обработки клика по баннеру 

Так как клик по баннеру должен открывать страницу внутри приложения, добавляется событие отслеживания клика по баннеру. Это событие перехватывается и в зависимости от ссылки перехода идет обработка внутри приложения, какой экран открыть. 


```java
        bannerView.setCallback(new BannerView.Callback() {
            @Override
            public void LoadPage(String url) {
                Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse( url));
                if (browserIntent.resolveActivity(getPackageManager()) != null) {
                    startActivity(browserIntent);
                }else{
                    Toast.makeText(getApplicationContext(), "No application found to open this link.", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void Error(Exception e) {
               e.printStackTrace();
            }
            @Override
            public void RequesterListener(BidRequesterListener loadlistener) {
            }
        });
```
