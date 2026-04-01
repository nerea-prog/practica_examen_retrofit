# 📱 Proyecto Práctica Retrofit - RetrofitPractica

> Un solo proyecto con 4 ejercicios progresivos. Cada ejercicio añade una pantalla nueva.

---

## 🗂️ ESTRUCTURA DEL PROYECTO

```
app/
├── manifests/
│   └── AndroidManifest.xml
├── java/com/tuapp/retrofitpractica/
│   ├── network/
│   │   ├── ApiService.kt
│   │   └── RetrofitInstance.kt
│   ├── model/
│   │   ├── Post.kt
│   │   └── Producto.kt
│   ├── repository/
│   │   ├── PostRepository.kt
│   │   └── ProductoRepository.kt
│   ├── viewmodel/
│   │   ├── PostViewModel.kt
│   │   └── ProductoViewModel.kt
│   ├── adapter/
│   │   ├── PostAdapter.kt
│   │   └── ProductoAdapter.kt
│   └── ui/
│       ├── MainActivity.kt          (menú principal)
│       ├── Ejercicio1Activity.kt    (lista de posts)
│       ├── Ejercicio2Activity.kt    (productos con imagen)
│       ├── DetallePostActivity.kt   (detalle post - ejercicio 3)
│       └── Ejercicio4Activity.kt    (crear post - ejercicio 4)
└── res/
    ├── layout/
    │   ├── activity_main.xml
    │   ├── activity_ejercicio1.xml
    │   ├── item_post.xml
    │   ├── activity_ejercicio2.xml
    │   ├── item_producto.xml
    │   ├── activity_detalle_post.xml
    │   └── activity_ejercicio4.xml
    └── values/
        └── strings.xml
```

---

## ⚙️ CONFIGURACIÓN INICIAL

### build.gradle (Module: app) - añadir en dependencies:
```gradle
dependencies {
    // Retrofit
    implementation 'com.squareup.retrofit2:retrofit:2.9.0'
    implementation 'com.squareup.retrofit2:converter-gson:2.9.0'

    // Coroutines
    implementation 'org.jetbrains.kotlinx:kotlinx-coroutines-android:1.7.3'

    // ViewModel + LiveData
    implementation 'androidx.lifecycle:lifecycle-viewmodel-ktx:2.7.0'
    implementation 'androidx.lifecycle:lifecycle-livedata-ktx:2.7.0'
    implementation 'androidx.activity:activity-ktx:1.8.2'

    // Glide (para imágenes)
    implementation 'com.github.bumptech.glide:glide:4.16.0'
}
```

### AndroidManifest.xml - añadir permiso de internet:
```xml
<manifest xmlns:android="http://schemas.android.com/apk/res/android">

    <uses-permission android:name="android.permission.INTERNET" />

    <application ...>

        <activity android:name=".ui.MainActivity"
            android:exported="true">
            <intent-filter>
                <action android:name="android.intent.action.MAIN" />
                <category android:name="android.intent.category.LAUNCHER" />
            </intent-filter>
        </activity>

        <activity android:name=".ui.Ejercicio1Activity" />
        <activity android:name=".ui.Ejercicio2Activity" />
        <activity android:name=".ui.DetallePostActivity" />
        <activity android:name=".ui.Ejercicio4Activity" />

    </application>
</manifest>
```

---

## 🏠 MENÚ PRINCIPAL

### activity_main.xml
```xml
<?xml version="1.0" encoding="utf-8"?>
<LinearLayout xmlns:android="http://schemas.android.com/apk/res/android"
    android:layout_width="match_parent"
    android:layout_height="match_parent"
    android:orientation="vertical"
    android:gravity="center"
    android:padding="32dp"
    android:background="#F5F5F5">

    <TextView
        android:layout_width="wrap_content"
        android:layout_height="wrap_content"
        android:text="Retrofit Práctica"
        android:textSize="28sp"
        android:textStyle="bold"
        android:textColor="#1A1A1A"
        android:layout_marginBottom="8dp"/>

    <TextView
        android:layout_width="wrap_content"
        android:layout_height="wrap_content"
        android:text="Selecciona un ejercicio"
        android:textSize="14sp"
        android:textColor="#888888"
        android:layout_marginBottom="48dp"/>

    <Button
        android:id="@+id/btnEjercicio1"
        android:layout_width="match_parent"
        android:layout_height="56dp"
        android:text="Ejercicio 1 — Lista de Posts"
        android:backgroundTint="#2196F3"
        android:layout_marginBottom="16dp"/>

    <Button
        android:id="@+id/btnEjercicio2"
        android:layout_width="match_parent"
        android:layout_height="56dp"
        android:text="Ejercicio 2 — Productos con Imagen"
        android:backgroundTint="#4CAF50"
        android:layout_marginBottom="16dp"/>

    <Button
        android:id="@+id/btnEjercicio3"
        android:layout_width="match_parent"
        android:layout_height="56dp"
        android:text="Ejercicio 3 — Detalle + Navegación"
        android:backgroundTint="#FF9800"
        android:layout_marginBottom="16dp"/>

    <Button
        android:id="@+id/btnEjercicio4"
        android:layout_width="match_parent"
        android:layout_height="56dp"
        android:text="Ejercicio 4 — Crear Post (POST)"
        android:backgroundTint="#F44336"/>

</LinearLayout>
```

### MainActivity.kt
```kotlin
class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        findViewById<Button>(R.id.btnEjercicio1).setOnClickListener {
            startActivity(Intent(this, Ejercicio1Activity::class.java))
        }
        findViewById<Button>(R.id.btnEjercicio2).setOnClickListener {
            startActivity(Intent(this, Ejercicio2Activity::class.java))
        }
        findViewById<Button>(R.id.btnEjercicio3).setOnClickListener {
            startActivity(Intent(this, Ejercicio1Activity::class.java)) // misma lista, al pulsar navega al detalle
        }
        findViewById<Button>(R.id.btnEjercicio4).setOnClickListener {
            startActivity(Intent(this, Ejercicio4Activity::class.java))
        }
    }
}
```

---

## 🟢 EJERCICIO 1 — Lista de Posts

**API:** `https://jsonplaceholder.typicode.com/posts`

### activity_ejercicio1.xml
```xml
<?xml version="1.0" encoding="utf-8"?>
<FrameLayout xmlns:android="http://schemas.android.com/apk/res/android"
    android:layout_width="match_parent"
    android:layout_height="match_parent"
    android:background="#F5F5F5">

    <androidx.recyclerview.widget.RecyclerView
        android:id="@+id/recyclerView"
        android:layout_width="match_parent"
        android:layout_height="match_parent"
        android:padding="8dp"
        android:clipToPadding="false"/>

    <ProgressBar
        android:id="@+id/progressBar"
        android:layout_width="wrap_content"
        android:layout_height="wrap_content"
        android:layout_gravity="center"
        android:visibility="gone"/>

    <TextView
        android:id="@+id/tvError"
        android:layout_width="wrap_content"
        android:layout_height="wrap_content"
        android:layout_gravity="center"
        android:textColor="#F44336"
        android:textSize="16sp"
        android:visibility="gone"/>

</FrameLayout>
```

### item_post.xml
```xml
<?xml version="1.0" encoding="utf-8"?>
<androidx.cardview.widget.CardView
    xmlns:android="http://schemas.android.com/apk/res/android"
    xmlns:app="http://schemas.android.com/apk/res-auto"
    android:layout_width="match_parent"
    android:layout_height="wrap_content"
    android:layout_margin="8dp"
    app:cardCornerRadius="8dp"
    app:cardElevation="2dp">

    <LinearLayout
        android:layout_width="match_parent"
        android:layout_height="wrap_content"
        android:orientation="vertical"
        android:padding="16dp">

        <TextView
            android:id="@+id/tvId"
            android:layout_width="wrap_content"
            android:layout_height="wrap_content"
            android:text="#1"
            android:textSize="11sp"
            android:textColor="#2196F3"
            android:textStyle="bold"
            android:layout_marginBottom="4dp"/>

        <TextView
            android:id="@+id/tvTitle"
            android:layout_width="match_parent"
            android:layout_height="wrap_content"
            android:text="Título del post"
            android:textSize="15sp"
            android:textStyle="bold"
            android:textColor="#1A1A1A"
            android:layout_marginBottom="6dp"/>

        <TextView
            android:id="@+id/tvBody"
            android:layout_width="match_parent"
            android:layout_height="wrap_content"
            android:text="Contenido del post..."
            android:textSize="13sp"
            android:textColor="#666666"
            android:maxLines="2"
            android:ellipsize="end"/>

    </LinearLayout>

</androidx.cardview.widget.CardView>
```

---

## 🟡 EJERCICIO 2 — Productos con Imagen

**API:** `https://fakestoreapi.com/products`

### activity_ejercicio2.xml
```xml
<?xml version="1.0" encoding="utf-8"?>
<FrameLayout xmlns:android="http://schemas.android.com/apk/res/android"
    android:layout_width="match_parent"
    android:layout_height="match_parent"
    android:background="#F5F5F5">

    <androidx.recyclerview.widget.RecyclerView
        android:id="@+id/recyclerView"
        android:layout_width="match_parent"
        android:layout_height="match_parent"
        android:padding="8dp"
        android:clipToPadding="false"/>

    <ProgressBar
        android:id="@+id/progressBar"
        android:layout_width="wrap_content"
        android:layout_height="wrap_content"
        android:layout_gravity="center"
        android:visibility="gone"/>

    <TextView
        android:id="@+id/tvError"
        android:layout_width="wrap_content"
        android:layout_height="wrap_content"
        android:layout_gravity="center"
        android:textColor="#F44336"
        android:textSize="16sp"
        android:visibility="gone"/>

</FrameLayout>
```

### item_producto.xml
```xml
<?xml version="1.0" encoding="utf-8"?>
<androidx.cardview.widget.CardView
    xmlns:android="http://schemas.android.com/apk/res/android"
    xmlns:app="http://schemas.android.com/apk/res-auto"
    android:layout_width="match_parent"
    android:layout_height="wrap_content"
    android:layout_margin="8dp"
    app:cardCornerRadius="8dp"
    app:cardElevation="2dp">

    <LinearLayout
        android:layout_width="match_parent"
        android:layout_height="wrap_content"
        android:orientation="horizontal"
        android:padding="12dp"
        android:gravity="center_vertical">

        <ImageView
            android:id="@+id/ivProducto"
            android:layout_width="80dp"
            android:layout_height="80dp"
            android:scaleType="centerCrop"
            android:background="#F0F0F0"
            android:layout_marginEnd="12dp"/>

        <LinearLayout
            android:layout_width="0dp"
            android:layout_height="wrap_content"
            android:layout_weight="1"
            android:orientation="vertical">

            <TextView
                android:id="@+id/tvNombre"
                android:layout_width="match_parent"
                android:layout_height="wrap_content"
                android:text="Nombre del producto"
                android:textSize="14sp"
                android:textStyle="bold"
                android:textColor="#1A1A1A"
                android:maxLines="2"
                android:ellipsize="end"
                android:layout_marginBottom="6dp"/>

            <TextView
                android:id="@+id/tvPrecio"
                android:layout_width="wrap_content"
                android:layout_height="wrap_content"
                android:text="9.99€"
                android:textSize="16sp"
                android:textColor="#4CAF50"
                android:textStyle="bold"/>

        </LinearLayout>

    </LinearLayout>

</androidx.cardview.widget.CardView>
```

---

## 🟠 EJERCICIO 3 — Detalle del Post

**API:** `https://jsonplaceholder.typicode.com/posts/{id}`

> Usa la misma lista del Ejercicio 1. Al pulsar un item navega a esta pantalla.

### activity_detalle_post.xml
```xml
<?xml version="1.0" encoding="utf-8"?>
<ScrollView xmlns:android="http://schemas.android.com/apk/res/android"
    android:layout_width="match_parent"
    android:layout_height="match_parent"
    android:background="#F5F5F5">

    <LinearLayout
        android:layout_width="match_parent"
        android:layout_height="wrap_content"
        android:orientation="vertical"
        android:padding="24dp">

        <ProgressBar
            android:id="@+id/progressBar"
            android:layout_width="wrap_content"
            android:layout_height="wrap_content"
            android:layout_gravity="center_horizontal"
            android:visibility="gone"/>

        <TextView
            android:id="@+id/tvIdDetalle"
            android:layout_width="wrap_content"
            android:layout_height="wrap_content"
            android:text="Post #1"
            android:textSize="12sp"
            android:textColor="#2196F3"
            android:textStyle="bold"
            android:layout_marginBottom="12dp"/>

        <TextView
            android:id="@+id/tvTituloDetalle"
            android:layout_width="match_parent"
            android:layout_height="wrap_content"
            android:text="Título completo del post"
            android:textSize="22sp"
            android:textStyle="bold"
            android:textColor="#1A1A1A"
            android:layout_marginBottom="16dp"/>

        <View
            android:layout_width="match_parent"
            android:layout_height="1dp"
            android:background="#E0E0E0"
            android:layout_marginBottom="16dp"/>

        <TextView
            android:id="@+id/tvBodyDetalle"
            android:layout_width="match_parent"
            android:layout_height="wrap_content"
            android:text="Contenido completo del post..."
            android:textSize="16sp"
            android:textColor="#444444"
            android:lineSpacingMultiplier="1.5"/>

    </LinearLayout>

</ScrollView>
```

---

## 🔴 EJERCICIO 4 — Crear Post (POST)

**API:** `https://jsonplaceholder.typicode.com/posts` (método POST)

### activity_ejercicio4.xml
```xml
<?xml version="1.0" encoding="utf-8"?>
<ScrollView xmlns:android="http://schemas.android.com/apk/res/android"
    android:layout_width="match_parent"
    android:layout_height="match_parent"
    android:background="#F5F5F5">

    <LinearLayout
        android:layout_width="match_parent"
        android:layout_height="wrap_content"
        android:orientation="vertical"
        android:padding="24dp">

        <TextView
            android:layout_width="wrap_content"
            android:layout_height="wrap_content"
            android:text="Crear nuevo Post"
            android:textSize="24sp"
            android:textStyle="bold"
            android:textColor="#1A1A1A"
            android:layout_marginBottom="24dp"/>

        <TextView
            android:layout_width="wrap_content"
            android:layout_height="wrap_content"
            android:text="Título"
            android:textSize="14sp"
            android:textColor="#666666"
            android:layout_marginBottom="6dp"/>

        <EditText
            android:id="@+id/etTitulo"
            android:layout_width="match_parent"
            android:layout_height="wrap_content"
            android:hint="Escribe el título..."
            android:inputType="text"
            android:background="@android:color/white"
            android:padding="12dp"
            android:layout_marginBottom="16dp"/>

        <TextView
            android:layout_width="wrap_content"
            android:layout_height="wrap_content"
            android:text="Contenido"
            android:textSize="14sp"
            android:textColor="#666666"
            android:layout_marginBottom="6dp"/>

        <EditText
            android:id="@+id/etBody"
            android:layout_width="match_parent"
            android:layout_height="120dp"
            android:hint="Escribe el contenido..."
            android:inputType="textMultiLine"
            android:gravity="top"
            android:background="@android:color/white"
            android:padding="12dp"
            android:layout_marginBottom="24dp"/>

        <Button
            android:id="@+id/btnCrear"
            android:layout_width="match_parent"
            android:layout_height="56dp"
            android:text="Crear Post"
            android:backgroundTint="#F44336"
            android:layout_marginBottom="16dp"/>

        <ProgressBar
            android:id="@+id/progressBar"
            android:layout_width="wrap_content"
            android:layout_height="wrap_content"
            android:layout_gravity="center_horizontal"
            android:visibility="gone"/>

        <!-- Aquí se mostrará la respuesta del servidor -->
        <androidx.cardview.widget.CardView
            xmlns:app="http://schemas.android.com/apk/res-auto"
            android:id="@+id/cardRespuesta"
            android:layout_width="match_parent"
            android:layout_height="wrap_content"
            app:cardCornerRadius="8dp"
            app:cardElevation="2dp"
            android:visibility="gone">

            <LinearLayout
                android:layout_width="match_parent"
                android:layout_height="wrap_content"
                android:orientation="vertical"
                android:padding="16dp">

                <TextView
                    android:layout_width="wrap_content"
                    android:layout_height="wrap_content"
                    android:text="✅ Post creado:"
                    android:textSize="14sp"
                    android:textColor="#4CAF50"
                    android:textStyle="bold"
                    android:layout_marginBottom="8dp"/>

                <TextView
                    android:id="@+id/tvRespuesta"
                    android:layout_width="match_parent"
                    android:layout_height="wrap_content"
                    android:textSize="13sp"
                    android:textColor="#444444"/>

            </LinearLayout>

        </androidx.cardview.widget.CardView>

    </LinearLayout>

</ScrollView>
```

---

## 📋 ORDEN PARA IMPLEMENTAR

1. Configura el proyecto (dependencias + permiso internet)
2. Crea `RetrofitInstance.kt` y `ApiService.kt`
3. **Ejercicio 1:** Data class Post → Repository → ViewModel → Adapter → Activity
4. **Ejercicio 2:** Data class Producto → Repository → ViewModel → Adapter (con Glide) → Activity
5. **Ejercicio 3:** Añade click listener en PostAdapter → Intent con el id → DetallePostActivity hace GET por id
6. **Ejercicio 4:** ViewModel con función `crearPost()` → Ejercicio4Activity recoge el formulario y llama al ViewModel

---

## 🔑 APIS UTILIZADAS

| Ejercicio | Método | URL |
|-----------|--------|-----|
| 1 | GET | `https://jsonplaceholder.typicode.com/posts` |
| 2 | GET | `https://fakestoreapi.com/products` |
| 3 | GET | `https://jsonplaceholder.typicode.com/posts/{id}` |
| 4 | POST | `https://jsonplaceholder.typicode.com/posts` |
