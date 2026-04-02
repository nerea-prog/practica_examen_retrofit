import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.practica_examen.model.Post
import com.example.practica_examen.repository.PostRepository
import kotlinx.coroutines.launch

class PostViewModel : ViewModel() {


    private val repository = PostRepository()


    private val _posts = MutableLiveData<List<Post>>()
    val posts: LiveData<List<Post>> = _posts

    private val _post = MutableLiveData<Post>()
    val post: LiveData<Post> = _post

    private val _isLoading = MutableLiveData<Boolean>(false)
    val isLoading: LiveData<Boolean> = _isLoading


    private val _error = MutableLiveData<String?>()
    val error: LiveData<String?> = _error


    init { cargar() }


    fun cargar() {
        viewModelScope.launch {
            _isLoading.value = true
            _error.value = null
            try {
                val response = repository.getPosts()
                if (response.isSuccessful) {
                    _posts.value = response.body() ?: emptyList()
                } else {
                    _error.value = "Error HTTP: ${response.code()}"
                }
            } catch (e: Exception) {
                _error.value = "Error de connexio: ${e.message}"
            } finally {
                _isLoading.value = false
            }
        }
    }
}
