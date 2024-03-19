# Пример внедрения SDK отзывов и оценок RuStore
## [Документация SDK отзывов и оценок](https://help.rustore.ru/rustore/for_developers/developer-documentation/SDK-reviews-ratings)

### Требуемые условия

Для корректной работы SDK необходимо соблюдать следующие условия:

Требования для пользователя:
- На устройстве пользователя установлено приложение RuStore.

- Версия RuStoreApp на устройстве пользователя актуальная.

- Пользователь  авторизован в приложении RuStore.

Требования для разработчика/приложения:

- Android 7 или выше

- ApplicationId, указанный в build.gradle, совпадает с applicationId apk-файла, который вы публиковали в консоль RuStore:
```
android {
    defaultConfig {
        applicationId = "ru.rustore.sdk.reviewexample" // Зачастую в buildTypes приписывается .debug
    }
}
```

- Подпись keystore должна совпадать с подписью, которой было подписано приложение, опубликованное в консоль RuStore. Убедитесь, что используемый buildType (пр. debug) использует такую же подпись, что и опубликованное приложение (пр. release).

### Описание приложения

Представим, что у нас есть игра, где нужно нажать 5 раз кнопку, чтобы победить.
Это означает, что начало флоу пользователя - старт игры (открытие экрана), а конец флоу - победа в игре.

![Начало флоу](https://i.imgur.com/mUmqqHl.jpg) ![Оценка приложения](https://i.imgur.com/qBteJTG.jpg) ![Конец флоу](https://i.imgur.com/FnDmNYP.jpg)

### Использование методов

Рекомендуется использовать requestReviewFlow() в начале флоу пользователя (где-то за 0-3 минуты до показа шторки), чтобы запуск шторки оценки произошел мгновенно:
```
public class UserFlowFragment extends Fragment {

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        reviewManager = RuStoreReviewManagerFactory.INSTANCE.create(requireContext());
        
        requestReviewFlow();
        
```

Открывать launchReviewFlow() необходимо в конце флоу пользователя (победа в игре):
```
public class UserFlowFragment extends Fragment {
    
    private void onButtonClicked() {
        counter++;
        int counterWinCondition = 5;
        if (counter >= counterWinCondition) {
            launchReviewFlow();
            counter = 0;
        }

    }

    private void launchReviewFlow(){
        if (reviewInfo != null) {
            reviewManager.launchReviewFlow(reviewInfo)
                    .addOnSuccessListener(reviewInfo -> Log.w("ReviewFlow", "Review Flow started"))
                    .addOnFailureListener(throwable -> Log.w("ReviewFlow", "Review Flow error" + throwable));
        }
    }
}

```
при этом продолжать флоу приложения не в зависимости от поставленной оценки (в любом случае показываем диалог).

> Полный код приведенных выше примеров кода приведен в данном репозитории (UserFlowExampleFragment).

### Есть вопросы
Если появились вопросы по интеграции SDK оценок и отзывов, обратитесь по этой ссылке:
https://help.rustore.ru/rustore/trouble/user/help_user_email
или напишите на почту support@rustore.ru.
