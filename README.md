# Prebid-mobile-sdk example


## Подключение Prebid:
  В файле аппликации build.gradle добавляем библиотеку
  
    dependencies {
    //
    implementation libs.prebid.mobile.sdk
    //
    }

## Инициация:

 При создании своей активности инициируйте:

   PrebidMobile.setPrebidServerAccountId(AccountId);
   PrebidMobile.initializeSdk(this,  serverUrl
      ,status -> {
       if (status == InitializationStatus.SUCCEEDED) {
       Log.d(TAG, "SDK initialized successfully!");
       onResume();
       } else {
       Log.e(TAG, "SDK initialization error: $status\n${status.description}");
       }
       });
       

## Добавление баннера:

  После успешной инициации открывается возможность добавить баннер в Вашу разметку
  
    BannerView bannerView = new BannerView(
       getContext(),
       currentId,
       new AdSize(w, h)
       );
       
       addView(bannerView);
 
## Инициация баннера:
       
       bannerView.loadAd();
       
## Общее:

   Вы можете реализовать людое количество баннеров на одной странице.
   BannerView это расширение FrameLayout имеющий размеры которые Вы укажите, встраивайте баннеры в любых местах,
    в зависимости от Вашей разметки.
   Все ссылки включая deeplink+ которые принадлежат баннеру будут открываться во внешнем, встроенном браузере если Вы добавите
   в свой манифест:
   <activity
         android:exported="true"
         android:name="org.prebid.mobile.rendering.views.browser.AdBrowserActivity"
         tools:node="remove" />
       
       
        
        
         


