# inngage-android-java-sample

Este é um projeto para exemplificar a integração da biblioteca Inngage em um projeto Android-Java.
Se sinta a vontade para clonar este projeto e analisar o código.

## 🚀 Começando

Essas instruções explicarão melhor cada parte da integração. Seguindo estes passos você conseguirá integrar a lib Inngage de maneira simples e efetiva.

### 📋 Pré-requisitos

* **Uma conta Inngage** - [crie sua conta na plataforma Inngage](https://www.inngage.com.br)
* **Adicione seu projeto no Firebase** - [registre seu aplicativo no Firebase](https://www.firebase.google.com)

### 🔧 Configurando o Google Messaging Services & Firebase Services no seu projeto

Uma série de exemplos passo-a-passo que informam o que você deve executar para ter um ambiente de desenvolvimento em execução.

Antes de implementar a biblioteca, dentro do seu projeto no Android Studio, abra o arquivo build.gradle (Module: app) e adicione as seguintes dependências ao seu projeto.

Implemente essas dependências no arquivo build.gradle (Module: app):

```
apply plugin: 'com.android.application'
// Adicione esta linha
apply plugin: 'com.google.gms.google-services'

dependencies {
  // Importe o Firebase BoM
  implementation platform('com.google.firebase:firebase-bom:27.1.0')

  // Adicione a dependência para o Firebase SDK para Google Analytics
  // Ao usar o BoM, não especifique versões nas dependências do Firebase
  implementation 'com.google.firebase:firebase-analytics'

  // Adicione as dependências para qualquer outro produto Firebase desejado
  
}

```

1.2 - Ainda no arquivo build.gradle (Module: app), adicione no final do arquivo o seguinte plugin:

```
apply plugin: 'com.google.gms.google-services'

```

Em build.gradle (Project: AppName) , Adicione isso :

```
  dependencies {
    // Add this line
    classpath 'com.google.gms:google-services:4.3.2'
  }

```
### 🔧 Implementação da SDK Inngage (inngage-lib)

Em build.gradle (Module: app) implemente nossa dependências

```
dependencies {      
       implementation 'com.github.inngage:inngage-lib:3.1.2-beta'
}

```

Em build.gradle (Project: AppName) adicione o link do repositório

```
buildscript{
  repositories{
              // adicione somente esta linha dentro de "repositories"
              maven { url 'https://jitpack.io' }
        
  }
}

allprojects {
      repositories {
              // adicione somente esta linha dentro deste outro "repositories"
              maven { url 'https://jitpack.io' }
      }
}

```

### 🔧 Adicionando InngageConstants Interface

Visando desacoplar as constantes utilizadas em nossa integração, adicione uma nova interface ao seu projeto denominada "InngageConstants"

Crie o arquivo como uma interface "InngageConstants" e adicione o seguinte código

```
interface InngageConstants {

    String inngageAppToken = "APP_TOKEN"; //application token retirado da plataforma Inngage
    String inngageEnvironment = "prod";
    String googleMessageProvider = "FCM";

}

```


### 🔧 Configuração na Activity de abertura do aplicativo

Na activity configurada para ser a porta de entrada do seu aplicativo, adicione o código abaixo:

```
@Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        // Chame o método startHandleNotifications() para capturar as notificações recebidas
        InngageIntentService.startHandleNotifications(this, getIntent());

        new Handler().postDelayed(() -> startActivity(new Intent(getApplicationContext(), MainActivity.class)), 2000);
    }
    
```


Caso não saiba como identificar a activity inicial do seu projeto, basta procurar no seu arquivo AndroidManifest pela activity que tenha a seguinte configuração:

```
        <activity
            android:name=".SplashActivity"
            android:exported="true">
            <intent-filter>
                <action android:name="android.intent.action.MAIN" />

                <category android:name="android.intent.category.LAUNCHER" />
            </intent-filter>
        </activity>
        
```

### 🔧 Registrando o usuário na plataforma Inngage

Este código deve ser colocado na sua Activity que é exibida logo após a tela de abertura, normalmente é chamada de MainActivity

```

@Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        // Para fazer a integração padrão utilize o método handleSubscription()
        handleSubscription();

    }

    /**
     * Método responsável por fazer a integração padrão (sem campos customizados)
     */
    private void handleSubscription() {

        String myInngageAppToken = InngageConstants.inngageAppToken;
        String myInngageEnvironment = InngageConstants.inngageEnvironment;
        String myGoogleMessageProvider = InngageConstants.googleMessageProvider;

        InngageIntentService.startInit(
                this,
                myInngageAppToken,
                "userIdentifierExample", //Seu identificador
                myInngageEnvironment,
                myGoogleMessageProvider);


        InngageUtils.handleNotification(this, getIntent(), myInngageAppToken, myInngageEnvironment);
    }

```

### 🔧 Configurando o Arquivo AndroidManifest.xml

Depois de implementar a biblioteca, o arquivo de manifesto precisa de algumas permissões e serviços a serem adicionados.

Adicionando Permissões (antes da tag '<application' do AndroidManifest.xml) :

```
<uses-permission android:name="android.permission.INTERNET" />
<uses-permission android:name="android.permission.READ_PHONE_STATE" />
          
```

Antes de fechar a tag </ application>, adicione isto:

```
<service android:name="br.com.inngage.sdk.PushMessagingService">
         <intent-filter>
         <action android:name="com.google.firebase.MESSAGING_EVENT"/>
         </intent-filter>
</service>

<meta-data
         android:name="com.google.firebase.messaging.default_notification_icon"
         android:resource="@drawable/ic_notification" />

<service android:name="br.com.inngage.sdk.InngageIntentService"
         android:exported="false">
         <intent-filter>
         <action android:name="com.google.firebase.INSTANCE_ID_EVENT"/>
         </intent-filter>
</service>

```

### 🔧 Configuração abertura do Dialog

Após finalizar toda implementação da SDK Inngage e demais dependências do Android, finalizaremos configurando na plataforma Inngage os parâmetros do seu aplicativo. Nesta sessão deveremos informar o nome do pacote (br.com.suaempresa.seuapp) e a classe que fará o recebimento do Push, no caso dessa implementação MainActivity.

## 🎁 Parabéns

Terminando este passo você implementou nossa biblioteca com sucesso.

## 🖇️ Consulte também a documentação Inngage

* [Android Java](https://inngage.readme.io/docs/implementa%C3%A7%C3%A3o-android)
* [Site Oficial](https://inngage.com.br/)
