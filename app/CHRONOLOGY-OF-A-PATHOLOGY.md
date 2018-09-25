# Grin Bluetooth


### Organización del proyecto
Comencé por crear la estructura del proyecto. Está basada en clean architecture. Tomé como ejemplo el proyecto en el que estoy trabajando ahora mas unos cambios que mejoran la organización del código, por ejemplo la interfaz:
```
interface BaseView<in T : BasePresenter> {
    fun setPresenter(presenter: T)
}
```
en el módulo `presentation`, nos asegura que siempre tendremos un `presenter` en cualquier clase que implemente la clase `BaseView`.

Luego quise implementar una nueva caracteristica de `dagger` para la implementación de inyección de dependencias. El proyecto en el que trabajo ahora, requiere que construyamos "el grafo de dependencias" con ayuda de una variable miembro en la clase, así:

```
public abstract class BaseActivity extends AppCompatActivity {
  private ActivityComponent activityComponent;
  
  protected void onCreate(Bundle bundle) {
    super.onCreate(savedInstanceState);

    activityComponent = DaggerActivityComponent.builder()
            .applicationComponent(MyAppComponent())
            .oneModule(new OneModule(this))
            .otherModule(new OtherModule(this))
            .build();
    activityComponent.inject(this)
}
```

Aunque funciona, la teoria de la inyeccion de dependencias dice que un objecto no debe de saber nada sobre como es inyectado.

Ahora, desde `dagger 2.11` podemos crear un módulo por cada clase que queramos inyectar. En el proyecto, por ejemplo, agrego todas las dependencias de `MainActivity` en la clase `MainACtivityModule`, luego, esta ultima clase se agrega al modulo `ActivityBindingModule` en donde específicamos que clase inyectará.

Por último, la clase `ActivityBindingModule` se registra en `ApplicationComponent` anotada con `@Component` para crear todo el grafo de dependencias.

De esta manera las clases quedan mas limpias, no tienen que "auto-inyectarse" sus dependencias si no que solo las gestionan con la anotación @Inject, así:
```
@Inject
lateinit var grinPermissionManager: GrinPermissionsManager

@Inject
lateinit var grinPreferenceManager: GrinPreferenceManager
```

### Arquitectura

En cuanto a la elección de Clean Architecture, lo hice así por que es una arquitectura que ya conozco, me muevo fácil entre clases, dependencias, métodos, pruebas, etc.

Un pequeño cambio que ahora implementé fue el uso de `Single` en lugar de `Observable`, la diferencia está en que `Single` solamente usa dos métodos para notificar, son `onSucces()` y `onError()`. No es asi con un `Observable`, que tiene 3 métodos: `onNext()`, `onComplete()` y `onError()`.

Es decir, preferí esto:
```
getDeviceList.execute(object : DisposableSingleObserver<List<Device>>() {
  override fun onSuccess(deviceList: List<Device>) {
      deviceListView.showDeviceList(deviceList)
  }

  override fun onError(e: Throwable) {
      deviceListView.showError()
  }
}, null)
```

...sobre esto:

```
getDeviceList.execute(object : DisposableSubscriber<List<Device>>() {
    override fun onNext(deviceList: List<Device>) {
      // ...
    }
    override fun onComplete() {
      // ...
    }
    override fun onError(t: Throwable?) {
      // ...
    }
}, null)
```

**Opciones**

Llevo tentado ya un tiempo con querer implementar el patrón MVVM (`ViewModel`, `LiveData`, etc), dado que es algo que apenas estoy aprendiendo, decidī no hacerlo con Grin Bluetooth.

Creo que es buena idea implementarlo por que, según la teoría, la vista es notificada automáticamente según cambien los datos de un repositorio. Esto suena muy interesante ya que me he topado con casos en los que tengo que tengo que actualizar una UI tan pronto cambien unos datos, y para ello he tenido que implementar `BroacastReceiver`s que...no estoy seguro si es la forma de arreglar el problema.

Otro punto, según mi criterio, a favor de MVVM es que no se crean muchos archivos/clases como con MVP, siendo honesto son muchas las clases que se tienen que crear para poder tener un codigo debilmente acoplado. Esto sin contar los "mappers" que deben de existir en cada capa, y así cada capa pueda trabajar con su propia lista de modelos.

### Bluetooth
La API de `Buetooth` fué la que me tomó más tiempo de lo normal poder entenderla, me confundí bastante en cuanto al requerimiento del permiso `android.permission.ACCESS_COARSE_LOCATION` para que pudiera funcionar. Una vez teniendo el permiso y registrado el `Receiver` inmediatamente se reciben datos para las `action`s

- `BluetoothDevice.ACTION_FOUND`
- `BluetoothAdapter.ACTION_DISCOVERY_STARTED`
- `BluetoothAdapter.ACTION_DISCOVERY_FINISHED`

Lo que seguía era manejar los diferentes estados del bluetooth (activado, no-disponible, desactivado). Además del permiso `android.permission.ACCESS_COARSE_LOCATION`.

El ejercicio mencionaba un botón para comenzar a buscar los dispositivos bluetooth cercanos, personalmente preferí implementar `SwipeRefreshLayout`. Es mas intuitivo y sencillo para el usuario desde una perspectiva de UX. Y también desde la perspectiva de desarrollo, `SwipeRefreshLayout` nos proveé de un método `setOnRefreshListener()` para comenzar a obtener datos de alguna fuente y solo basta con usar el método
```
swipeRefreshLayout.isRefreshing = false`
````
para indicar que la carga de datos se completó.

También revisé la guía de [Material Design](https://material.io) para implementar `SwipeRefreshLayout`, existe información muy valiosa en [esta página](https://material.io).

En mi caso, fué el lugar en donde realizo las validaciones del bluetooth, permisos y comienzo el scan de dispositivos.

Un escenario curioso de la API de bluetooth es que el evento `BluetoothDevice.ACTION_FOUND` se puede lanzar más de una vez para un mismo dispositivo que ya fué detectado. La solucion fué fácil, simplemente se mantenía una lista unica de dispositivos diferenciados por su dirección MAC.

### Kotlin extension functions
Una vez que tenía disponible la lista de dispositivos, era necesario mostrar los valores en una lista. Fué entonces que necesité de una "extension function" para la clase `Date` y poder mostrar un texto legible para el usuario de la fecha en la que se había creado el dispositivo.

### Conectar todo
Ya con la lista de dispositivos disponible, era hora de conectar todas las capas para leer/guardar datos de la API. Desde aquí fué algo sencillo, era cuestion de implementar la lógica correspondiente en cada una de las capas de la arquitectura del proyecto.

De hecho, me tomé el tiempo de construir una API para esta app, fué construida con node js/express y está corriendo en Heroku. El repo esta [aquí](https://github.com/lalongooo/grin-bluetooth-api). En el mismo repo están las credenciales de la base de datos MongoDB, es una instancia que corre en [mLab](https://mlab.com/)

### Adicionales
Hubo una lista de cosas que quise implementar adicionalmente:
- Agregué una opción más para ordenar los dispositivos guardados en la API (fecha de creación, además de nombre)
- Manejé los diferentes "estados" de los permisos al iniciar la app
  Los permisos pueden ser rechazados o totalmente negados (opción "Never ask again" del sistema). Para cuando el usuario decide no volver a pedir el permiso, se abren los "settings" de la app para que el usuario decida autorizar los permisos desde ahí
- Traté de minimizar la interacción del usuario con varios botones, por ello la implementación de [`SwipeRefreshLayout`](https://material.io/design/platform-guidance/android-swipe-to-refresh.html) y [`FloatingActionButton`](https://material.io/design/components/buttons-floating-action-button.html) en la parte inferior derecha de ambas pantallas
- Mostré unos íconos que indican progreso cuando un dispositivo se esta guardando en la API. Una vez completado el proceso, se muestrá una "palomita verde"
- Muestro un mensaje cuando no se detecta ningún dispositivo bluetooth disponible

### Áreas de oportunidad
- Hace falta afinar detalles para soportar pantallas muy pequeñas, es decir, agregar valores de dimension en las carpetas:
  - values-mdpi/dimens.xml
  - values-hdpi/dimens.xml
  - values-xhhdpi/dimens.xml
  - etc
- Implementar [Room](https://developer.android.com/topic/libraries/architecture/room) para guardar la lista de dispositivos guardados en la API. Fué por ello que la clase [`CacheImpl`](https://github.com/lalongooo/grin-bluetooth/blob/master/cache/src/main/java/com/ongrin/cache/DeviceCacheImpl.kt) no tiene implementación. Funcionaría así:
    - Hacer el request `GET /devices`
    - Guardar la respuesta en una base de datos SQLite local usando [Room](https://developer.android.com/topic/libraries/architecture/room)
    - Al abrir la segunda pantalla, cargar los datos guardados en la base de datos SQLite local (cache)
    - Al hacer el gesture "pull-down-to-refresh", hacer el request a `GET /devices` de nuevo y reemplazar los datos en cache
- Implementar pruebas de integración con Espresso
- Implementar continuous integration con GitLab, dado que el repo está hosteado en GitHub, ya no tuve tiempo para configurar el CI de GitLAb
