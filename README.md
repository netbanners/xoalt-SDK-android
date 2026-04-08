# Интеграция Prebid в Android-приложение

Этот документ написан для Android-разработчика приложения.
Ниже только практические шаги по интеграции Prebid Rendering SDK в приложение.

## 1. Что нужно получить для интеграции

До начала работ разработчику нужны готовые значения:

- `Prebid Server URL`
- `Prebid Server Account ID`
- `configId` для каждого placement
- размер placement, например `320x50` или `240x400`

## 2. Подключение SDK

В этом проекте зависимость подключается через version catalog.

Пример из актуального `build.gradle`:

```gradle
dependencies {
    implementation libs.prebid.mobile.sdk
}
```

## 3. Инициализация SDK

SDK нужно инициализировать один раз до загрузки баннеров.

Минимальный пример:

```java
PrebidMobile.setPrebidServerAccountId("YOUR_ACCOUNT_ID");
PrebidMobile.initializeSdk(
        this,
        "https://YOUR_PREBID_SERVER_URL/",
        status -> {
            if (status == InitializationStatus.SUCCEEDED) {
                Log.d("Prebid", "SDK initialized");
            } else {
                Log.e("Prebid", "SDK init failed: " + status);
            }
        }
);
```

Практически:

- инициализируйте SDK один раз
- создавайте баннеры только после успешной инициализации
- используйте значения `accountId` и `serverUrl`, выданные для проекта

## 4. Создание placement

Для баннера используется `BannerView`.

Минимальный пример:

```java
BannerView bannerView = new BannerView(
        context,
        "CONFIG_ID",
        new AdSize(320, 50)
);
```

После создания вызовите:

```java
bannerView.loadAd();
```

## 5. Пример интеграции в коде

По текущему проекту интеграция выглядит так:

- SDK инициализируется в [MainActivity.java](/src/main/java/com/app/prebid_mobile_sdk_example/MainActivity.java#L22)
- баннеры создаются в [BlockView1.java](/src/main/java/com/app/prebid_mobile_sdk_example/BlockView1.java#L26)

Пример минимального placement:

```java
BannerView bannerView = new BannerView(
        getContext(),
        currentId,
        new AdSize(w, h)
);

bannerView.loadAd();
addView(bannerView);
```

Здесь `addView(bannerView)` означает, что баннер добавляется в родительский layout и становится частью экрана.

## 6. Что важно контролировать в приложении

- SDK инициализируется до создания placement
- для каждого placement используется правильный `configId`
- размер `AdSize` соответствует placement
- `loadAd()` вызывается явно
- баннер добавляется в нужный контейнер интерфейса
- при закрытии экрана или уничтожении контейнера баннер должен корректно освобождаться

Если placement создается динамически, полезно держать ссылку на `BannerView`, чтобы управлять его lifecycle.

## 7. Как проверить, что интеграция работает

Минимальный набор проверок:

- баннер создается без ошибок
- приходит `onAdLoaded()`
- приходит `onAdDisplayed()`
- баннер отображается в нужном месте экрана
- при нажатии приходит `onAdClicked()`
- при ошибке приходит `onAdFailed(...)`

Для отладки рекомендуется вешать `BannerViewListener`.

Пример:

```java
bannerView.setBannerListener(new BannerViewListener() {
    @Override
    public void onAdLoaded(BannerView bannerView) {
        Log.d("Prebid", "loaded");
    }

    @Override
    public void onAdDisplayed(BannerView bannerView) {
        Log.d("Prebid", "displayed");
    }

    @Override
    public void onAdClicked(BannerView bannerView) {
        Log.d("Prebid", "clicked");
    }

    @Override
    public void onAdClosed(BannerView bannerView) {
        Log.d("Prebid", "closed");
    }

    @Override
    public void onAdFailed(BannerView bannerView, AdException exception) {
        Log.e("Prebid", "failed: " + exception.getMessage());
    }
});
```

## 8. Внешний браузер

Если в приложении рекламные ссылки должны открываться только во внешнем браузере устройства, можно убрать встроенный `AdBrowserActivity` через manifest merge.

Пример:

```xml
<manifest
    xmlns:android="http://schemas.android.com/apk/res/android"
    xmlns:tools="http://schemas.android.com/tools">

    <application>
        <activity
            android:name="org.prebid.mobile.rendering.views.browser.AdBrowserActivity"
            tools:node="remove" />
    </application>
</manifest>
```

В текущем проекте это уже сделано в [AndroidManifest.xml](/src/main/AndroidManifest.xml#L16).

## 9. Короткий чек-лист

1. Получить `serverUrl`, `accountId`, `configId`, размер placement
2. Подключить `libs.prebid.mobile.sdk`
3. Инициализировать `PrebidMobile`
4. Создать `BannerView(context, configId, new AdSize(w, h))`
5. Вызвать `loadAd()`
6. Добавить баннер в нужный контейнер через `addView(...)`
7. Для отладки повесить `BannerViewListener`
8. При необходимости настроить открытие ссылок во внешнем браузере

## 10. Итог

Минимальная интеграция состоит из трех шагов:

- инициализировать `PrebidMobile`
- создать `BannerView`
- вызвать `loadAd()`

После этого placement готов к показу в приложении.
